package app.service;

import app.bean.*;
import app.entity.TraderSession;
import app.repository.TraderRepository;
import app.utils.CommandUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
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

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("TraderService")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Lazy(value = false)
public class TraderGFService implements TraderService, InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(TraderGFService.class);
    @Autowired
    HolidayService holidayService;
    @Autowired
    TraderRepository traderRepository;
    @Autowired
    TraderSessionService traderSessionService;
    ObjectMapper objectMapper;
    public String domain = "https://etrade.gf.com.cn";
    String userAgent = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET4.0C; .NET4.0E)";
    private Map<String, GFAccount> yjbAccountMap = new HashMap<>();
    // private Double yjbBalance = 0D;
    private Double totalBalance = 200000D;
    private Boolean isLogin = false;
    BasicCookieStore cookieStore;
    TraderSession entity;
    public String dseSessionId = null;
    private int number =0;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.cookieStore = new BasicCookieStore();
        this.objectMapper = new ObjectMapper();
       // login();
    }

    @Scheduled(cron = "0/10 * 9-16 * * MON-FRI")
    public void cornJob() {
        if (holidayService.isTradeDayTimeByMarket()) {
            if (isLogin) {
                if(number%2==0){
                    balance();
                }else{
                    yjbAccount();
                }
                number++;
            } else {
                number =0;
                login();
            }
        }else {
            number =0;
            isLogin = false;
        }
    }

    @Override
    public void trading(XueHistories obj) {
   /*     String symbol = obj.getStock_symbol();
        String code = null, market = null, type , account = null, requestId ;
        int amount ;
        if (symbol.startsWith("SZ")) {
            market = "2";
            code = StringUtils.removeStart(symbol, "SZ");
            account = entity.getSzAccount();
        } else if (symbol.startsWith("SH")) {
            market = "1";
            code = StringUtils.removeStart(symbol, "SH");
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
            String result =EntityUtils.toString(entity);
            if(result.indexOf("-10002")>0){
                log.info(result);
                this.isLogin=false;
            }
            EntityUtils.consume(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("------ code[" + code + "] amount[" + amount + "] price[" + price + "] type[" + type + "]");
        */
    }

  /*
  * {"total":1,
  * "data":[{"entrust_sell_amount":"0","delist_date":"0","asset_price":"1.095",
  * "exchange_type_dict":"上海","income_balance":"435.94",
  * "cost_balance":"48839.06","last_price":"1.0950",
  * "current_amount":"45000.00","exchange_type":"1",
  * "stock_code":"512880",
  * "income_balance_nofare":"435.94","position_str":"0030400000000001112526200010000000000A290985659512880",
  * "market_value":"49275.00",
  * "hand_flag":"0",
  * "real_buy_amount":"0",
  * "fund_account":"11125262","stock_type":"T",
  * "stock_account":"A290985659","client_id":"030400028937",
  * "stock_name":"证券ETF","par_value":"1.000","uncome_sell_amount":"0","keep_cost_price":"1.085","cost_price":"1.085","enable_amount":"45000.00","frozen_amount":"0","av_buy_price":"1.085","delist_flag":"0","real_sell_amount":"0","av_income_balance":"0","hold_amount":"45000.00","profit_ratio":"0.92","uncome_buy_amount":"0"}],"success":true,"error_no":0}
  * **/
    public void yjbAccount() {
        try {
            CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore)
                    .setUserAgent(userAgent)
                    .build();

            HttpUriRequest login = RequestBuilder.post()
                    .setUri(new URI(domain + "/entry?classname=com.gf.etrade.control.StockUF2Control&method=queryCC"))
                    .addParameter("dse_sessionId", this.dseSessionId)
                    .addParameter("request_num", "500")
                    .addParameter("start", "0")
                    .addParameter("limit", "10")
                    .build();
            CloseableHttpResponse response = httpclient.execute(login);
            int statusCode =  response.getStatusLine().getStatusCode();
            if(statusCode!=200){
                log.info("yjbAccount statusCode:"+ statusCode);
                isLogin =false;
                return;
            }
            //log.info("yjbAccount statusCode:"+);
            HttpEntity entity = response.getEntity();
            String result = IOUtils.toString(entity.getContent(), "UTF-8");
            List<GFAccount> list = objectMapper.readValue(result, GFResult.class).getList();
            for (GFAccount bean : list) {
                //System.out.println(bean);
                yjbAccountMap.put(bean.getStockCode(), bean);
            }


        } catch (IOException e) {
            log.info(e.getMessage());
            //e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

   /*
   * {"total":1,
   * "data":[{"money_type":"0","current_balance":"1000.04",
   * "real_sell_balance":"0",
   * "integral_balance":"89093.99",
   * "market_value":"49275.00",
   * "foregift_balance":"0",
   * "money_type_dict":"人民币",
   * "mortgage_balance":"0",
   * "enable_balance":"3645.04",
   * "interest":"0",
   * "real_buy_balance":"0",
   * "begin_balance":"1000.04",
   * "pre_interest_tax":"0",
   * "pre_fine":"0","fetch_balance":"1000.04",
   * "correct_balance":"0","fetch_balance_old":"0",
   * "net_asset":"552920.04",
   * "frozen_balance":"0",
   * "unfrozen_balance":"0",
   * "asset_balance":"552920.04",
   * "pre_interest":"0.74",
   * "fetch_cash":"1000.04",
   * "fine_integral":"0","opfund_market_value":"502645.00","entrust_buy_balance":"0","fund_balance":"1000.04","rate_kind":"0"}],
   * "success":true}
   * */
    public void balance() {
        try {
            CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore)
                    .setUserAgent(userAgent)
                    .build();
            HttpGet httpget3 = new HttpGet("https://etrade.gf.com.cn/entry?classname=com.gf.etrade.control.StockUF2Control&method=queryAssert&dse_sessionId="+this.dseSessionId);
            CloseableHttpResponse response3 = httpclient.execute(httpget3);
            HttpEntity entity = response3.getEntity();
            String str = IOUtils.toString(entity.getContent(), "UTF-8");
            int statusCode =  response3.getStatusLine().getStatusCode();
            if(statusCode!=200){
                log.info("yjbAccount statusCode:"+ statusCode);
                isLogin =false;
                return;
            }
            Gson gson = new Gson();
            Map map = gson.fromJson(str, Map.class);
            String assetBalance = ((List<Map<String,String>>)map.get("data")).get(0).get("asset_balance");
            log.info("assetBalance is "+assetBalance);
            this.totalBalance = Double.valueOf(assetBalance);
            EntityUtils.consume(entity);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }


    public synchronized void login() {
        if (isLogin) {
            return;
        }
        Gson gson = new Gson();
        try {
            long start = System.currentTimeMillis();
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setDefaultCookieStore(cookieStore)
                    .setUserAgent(userAgent)
                    .build();
            try {
                HttpGet httpGet = new HttpGet(domain + "/yzm.jpgx");
                CloseableHttpResponse response3 = httpclient.execute(httpGet);
                HttpEntity entity3 = response3.getEntity();
                File file = new File("d:/gf/gf.jpg");
                FileUtils.copyInputStreamToFile(entity3.getContent(), file);
                // BufferedImage image = ImageIO.read(entity3.getContent());
                EntityUtils.consume(entity3);
                String capthca = null;
                {




                 /*   InputStream inputStream = System.in;
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    System.out.println("请输入验证码:");
                    capthca = bufferedReader.readLine();*/
                    CommandUtils.executeCommand("python d:\\gf\\verify.py");

                    CommandUtils.executeCommand("D:\\Program Files (x86)\\Tesseract-OCR\\tesseract.exe D:\\gf\\gf_bw.jpg d:\\gf\\code");
                    capthca = org.springframework.util.StringUtils.trimAllWhitespace(FileUtils.readFileToString(new File("d:\\gf\\code.txt"), Charset.forName("UTF-8")));
                    if (capthca.length() != 5) {
                        System.out.println("error capthca[" + capthca + "] re login");
                        return;
                    }
                    HttpUriRequest login = RequestBuilder.post()
                            .setUri(new URI(domain + "/login"))
                            .addParameter("username", "*1B*8DJo*0FJd*D9*28rq*5E*FF*8Fj*9EG*97*883*91G*16bw*22*A05*A8*CCL8G*97*883*91G*16bw*22*A05*A8*CCL8G*97*883*91G*16bw*22*A05*A8*CCL8*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00")
                            .addParameter("password", "*E1*B58F*23*B7*C6*2E*05*3F*E6*5D*09*C2*122G*97*883*91G*16bw*22*A05*A8*CCL8G*97*883*91G*16bw*22*A05*A8*CCL8G*97*883*91G*16bw*22*A05*A8*CCL8*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00*00")
                            .addParameter("tmp_yzm", capthca)
                            .addParameter("authtype", "2")
                            .addParameter("mac", "B4-6D-83-6C-A8-09,192.168.2.2")
                            .addParameter("disknum", "S30YJ9JG914606")
                            .addParameter("loginType", "2")
                            .addParameter("origin", "web")
                            .build();
                    CloseableHttpResponse response2 = httpclient.execute(login);
                    try {
                        HttpEntity entity = response2.getEntity();
                        //  System.out.println("Login form get: " + response2.getStatusLine());
                        //   String result = IOUtils.toString(entity.getContent(), "UTF-8");
                        //System.out.println("result:" + EntityUtils.toString(entity));
                        Map map = gson.fromJson(EntityUtils.toString(entity), Map.class);
                        System.out.println(map);
                        EntityUtils.consume(entity);
                        if (MapUtils.getBoolean(map, "success")) {
                            System.out.println("Post logon cookies:");
                            /*DailyEntity userSession = new DailyEntity();
                            userSession.setCookieStore(cookieStore);*/
                            List<Cookie> cookies = cookieStore.getCookies();
                            if (cookies.isEmpty()) {
                                System.out.println("None");
                            } else {
                                for (int i = 0; i < cookies.size(); i++) {
                                    String name = cookies.get(i).getName();
                                    if (name.equals("dse_sessionId")) {
                                        dseSessionId = cookies.get(i).getValue();
                                        // userSession.setDseSessionId(dseSessionId);
                                    }
                                    //cookieStore.addCookie(cookies.get(i));
                                    System.out.println("- " + cookies.get(i).toString());
                                }
                                this.isLogin = true;
                                // this.setUserSession(userSession);
                            }


                        } else {
                            System.out.println("verify code error re login");

                        }
                    } finally {
                        response2.close();
                    }
                }
            } finally {
                httpclient.close();
            }
            long end = System.currentTimeMillis() - start;
            log.info("use times :" + end);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
