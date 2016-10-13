package app.service;

import app.bean.XueHistories;
import app.bean.YJBAccount;
import app.bean.YJBBalance;
import app.entity.TraderSession;
import app.repository.TraderRepository;
import cn.skypark.code.MyCheckCodeTool;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("TraderService")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Lazy(value = false)
public class TraderYJBService implements TraderService, InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(TraderYJBService.class);
    @Autowired
    HolidayService holidayService;
    @Autowired
    TraderRepository traderRepository;
    @Autowired
    TraderSessionService traderSessionService;

    ObjectMapper objectMapper;

    String userAgent = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET4.0C; .NET4.0E)";
    private Map<String, YJBAccount> yjbAccountMap = new HashMap<>();
    // private Double yjbBalance = 0D;
    private Double totalBalance = 200000D;
    private Boolean isLogin = false;
    BasicCookieStore cookieStore;
    TraderSession entity;

    @Override
    public void afterPropertiesSet() throws Exception {
        objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        this.cookieStore = new BasicCookieStore();
        this.entity = traderSessionService.findOne("40128457");
        //  login();
         cornJob();
    }

    @Scheduled(cron = "0/30 * 9-16 * * MON-FRI")
    public void cornJob() {
        if (holidayService.isTradeDayTimeByMarket()) {
            if (isLogin) {
                yjbAccount();
                balance();
            } else {
                login();
            }
        }
    }

    @Override
    public void trading(XueHistories obj) {
        String symbol = obj.getStock_symbol();
        String code = null, market = null, type , account = null, requestId ;
        int amount ;
        if (symbol.startsWith("SZ")) {
            market = "2";
            code = org.apache.commons.lang3.StringUtils.removeStart(symbol, "SZ");
            account = entity.getSzAccount();
        } else if (symbol.startsWith("SH")) {
            market = "1";
            code = org.apache.commons.lang3.StringUtils.removeStart(symbol, "SH");
            account = entity.getShAccount();
        }
        Double weight = obj.getWeight();
        Double preWeight = obj.getPrev_weight_adjusted() == null ? 0d : obj.getPrev_weight_adjusted();
        Double price = obj.getPrice();
        if (weight > preWeight) {
            type = "1";
            Double _amount = ((totalBalance * (weight - preWeight)) / 100d) / price / 100d;
            amount = _amount.intValue() * 100;
            requestId = "buystock_302";
        } else {
            type = "2";
            YJBAccount yjbAccount = yjbAccountMap.get(code);
            if (yjbAccount == null) {
                log.info("yjb account can not find amount by code[" + code + "]");
                return;
            }
            if (weight == 0d) {
                amount = yjbAccount.getEnableAmount();
                yjbAccountMap.remove(code);
                //log.info("clear stock amount in yjb is " + amount);
            } else {
                Double _amount = ((totalBalance * (preWeight - weight)) / 100d) / price / 100d;
                amount = _amount.intValue() * 100;
                if (amount > yjbAccount.getEnableAmount()) {
                    amount = yjbAccount.getEnableAmount();
                    yjbAccountMap.remove(code);
                }
                //log.info("sell amount:" + amount);
            }
            requestId = "sellstock_302";
        }
        try {
            CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore)
                    .setUserAgent(userAgent)
                    .build();
            HttpUriRequest trading = RequestBuilder.get()
                    .setUri(new URI("https://jy.yongjinbao.com.cn/winner_gj/gjzq/stock/exchange.action"))
                    .addParameter("CSRF_Token", "undefined")
                    .addParameter("request_id", requestId)
                    .addParameter("stock_account", account)
                    .addParameter("exchange_type", market)
                    .addParameter("entrust_prop", "0")
                    .addParameter("entrust_bs", type)
                    .addParameter("stock_code", code)
                    .addParameter("entrust_price", String.valueOf(price))
                    .addParameter("entrust_amount", String.valueOf(amount))
                    .addParameter("elig_riskmatch_flag", "1")
                    .addParameter("service_type", "stock")
                    .build();
            CloseableHttpResponse response3 = httpclient.execute(trading);
            HttpEntity entity = response3.getEntity();
            log.info(EntityUtils.toString(entity));
            EntityUtils.consume(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("------ code[" + code + "] amount[" + amount + "] price[" + price + "] type[" + type + "]");
    }


    public void yjbAccount() {
        try {
            CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore)
                    .setUserAgent(userAgent)
                    .build();
            HttpGet httpget = new HttpGet("https://jy.yongjinbao.com.cn/winner_gj/gjzq/stock/exchange.action?CSRF_Token=undefined&service_type=stock&sort_direction=0&request_id=mystock_403");
            CloseableHttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            String result = IOUtils.toString(entity.getContent(), "UTF-8");
            //log.info(result);
            if (result.indexOf("msg_no: '0'") == -1) {
                isLogin = false;
            } else {
                String str = "[" + (result.substring(346, result.length() - 14));
                //log.info("yo hua:"+str);
                if (str.length() > 50) {
                    List<YJBAccount> list = objectMapper.readValue(str, new TypeReference<List<YJBAccount>>() {
                    });
                    //if (list.size() > 1) {
                    for (YJBAccount bean : list) {
                        //System.out.println(bean);
                        yjbAccountMap.put(bean.getStockCode(), bean);
                    }
                    //}
                }
            }
        } catch (IOException e) {
            isLogin = false;
            log.info(e.getMessage());
            //e.printStackTrace();
        }
    }


    public void balance() {
        try {
            CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore)
                    .setUserAgent(userAgent)
                    .build();
            HttpGet httpget3 = new HttpGet("https://jy.yongjinbao.com.cn/winner_gj/gjzq/stock/exchange.action?request_id=mystock_405");
            CloseableHttpResponse response3 = httpclient.execute(httpget3);
            HttpEntity entity = response3.getEntity();
            String str = IOUtils.toString(entity.getContent(), "UTF-8");
            if(str.indexOf("-10002")>0){
                this.isLogin=false;
                return;
            }
            str = (str.substring(260, str.length() - 15));
            YJBBalance yjbBalance = this.objectMapper.readValue(str, YJBBalance.class);
            this.totalBalance = yjbBalance.getAssetBalance();
            EntityUtils.consume(entity);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }


    public synchronized void login() {
        try {
            long start = System.currentTimeMillis();
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setDefaultCookieStore(cookieStore)
                    .setUserAgent(userAgent)
                    .build();
            try {
                HttpGet httpget3 = new HttpGet("https://jy.yongjinbao.com.cn/winner_gj/gjzq/user/extraCode.jsp");
                CloseableHttpResponse response3 = httpclient.execute(httpget3);
                HttpEntity entity3 = response3.getEntity();
                BufferedImage image = ImageIO.read(entity3.getContent());
                EntityUtils.consume(entity3);
                MyCheckCodeTool tool = new MyCheckCodeTool("guojin");
                String code = tool.getCheckCode_from_image(image);
                HttpUriRequest login = RequestBuilder.post()
                        .setUri(new URI("https://jy.yongjinbao.com.cn/winner_gj/gjzq/exchange.action"))
                        .addParameter("function_id", "200")
                        .addParameter("login_type", "stock")
                        .addParameter("version", "200")
                        .addParameter("identity_type", "")
                        .addParameter("remember_me", "")
                        .addParameter("input_content", "1")
                        .addParameter("content_type", "0")
                        .addParameter("account_content", entity.getSid())
                        .addParameter("password", entity.getPassword())
                        .addParameter("loginPasswordType", "B64")
                        .addParameter("validateCode", code)
                        .addParameter("mac_addr", "54-59-57-07-B9-0F")
                        .addParameter("cpuid", "-306C3-7FFAFBBF")
                        .addParameter("disk_serial_id", "WD-WMC3F0J4P22T")
                        .addParameter("machinecode", "-306C3-7FFAFBBF")
                        .setHeader("Referer", "https://jy.yongjinbao.com.cn/winner_gj/gjzq/")
                        .build();

                CloseableHttpResponse response2 = httpclient.execute(login);
                try {
                    HttpEntity entity = response2.getEntity();
                    String result = IOUtils.toString(entity.getContent(), "UTF-8");
                    EntityUtils.consume(entity);
                    System.out.println("result:" + result);
                    System.out.println("Post logon cookies:");
                    List<Cookie> cookies = cookieStore.getCookies();
                    if (cookies.isEmpty()) {
                        System.out.println("None");
                    } else {
                        isLogin = true;
                        for (int i = 0; i < cookies.size(); i++) {
                            //cookieStore.addCookie(cookies.get(i));
                            System.out.println("- " + cookies.get(i).toString());
                        }
                    }
                } finally {
                    response2.close();
                }
            } finally {
                httpclient.close();
            }
            long end = System.currentTimeMillis() - start;
            log.info("use times :" + end);
        } catch (Exception e) {
            // e.printStackTrace();
            log.info(e.getMessage());
        }
    }

}
