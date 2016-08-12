package app.task;

import app.bean.*;
import app.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Component
@Transactional
public class XueQiuTasks  implements  InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(XueQiuTasks.class);

    ObjectMapper objectMapper;

    @Autowired
    XueService xueService;

    @Autowired
    TraderService traderService;

    Double totalBalance=200000d ; //20W

    @Autowired
    HolidayService holidayService;

    @Scheduled(fixedDelay = 1)
    public  void init() {
        if (holidayService.isTradeDayTimeByMarket()) {
            //long a = System.currentTimeMillis();
            HttpURLConnection connection = null;
            try {
               // URL url = new URL("https://xueqiu.com/P/ZH914042");
                URL url = new URL("https://xueqiu.com/P/ZH902949"); // cheng lao shi
                connection = (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                BufferedReader bd = new BufferedReader(new InputStreamReader(in));
                //StringBuilder builder = new StringBuilder();
                String text;
                String cubeInfo = null;
                while ((text = bd.readLine()) != null){
                    if(text.startsWith("SNB.cubeInfo")){
                        cubeInfo = text.substring(15);
                        break;
                    }
                }
                XueReturnJson xueReturnJson =  objectMapper.readValue(cubeInfo, XueReturnJson.class);
                XueSellRebalancing xueSellRebalancing = xueReturnJson.getSellRebalancing();
                XueSellRebalancing entity =xueService.findXueSellRebalancingByPK(xueSellRebalancing.getId());
                if(entity ==null){
                    List<XueHistories>  histories = xueSellRebalancing.getXueHistories();
                    for(XueHistories obj : histories){
                        String symbol =obj.getStock_symbol();
                        String code = null,market =null,type=null;
                        Integer amount =0;
                        if(symbol.startsWith("SZ")){
                            market ="2";
                            code = org.apache.commons.lang3.StringUtils.removeStart(symbol,"SZ");
                        }else if(symbol.startsWith("SH")){
                            market ="1";
                            code = org.apache.commons.lang3.StringUtils.removeStart(symbol,"SH");
                        }
                        Double weight = obj.getWeight();
                        Double preWeight = obj.getPrev_weight_adjusted()==null?0d:obj.getPrev_weight_adjusted();
                        Double price = Double.valueOf(obj.getPrice());
                        if(weight>preWeight){
                            type="1";
                            Double _amount =  ((totalBalance * (weight-preWeight))/100d) / price/100d;
                            amount = _amount.intValue()*100;
                        }else{
                            type="2";
                            if(weight==0d){
                                Double _amount =  (totalBalance/price);
                                amount = _amount.intValue(); //clear stock
                            }else{
                                Double _amount =  ((totalBalance * (preWeight - weight))/100d) / price/100d;
                                amount = _amount.intValue()*100;
                            }
                        }
                        traderService.trading(market,obj.getId(),code,amount,obj.getPrice(),type,true);
                    }


                    xueService.saveXueSellRebalancing(xueSellRebalancing);
                }
            } catch (Exception e) {
                log.info(e.getMessage());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            //long b = (System.currentTimeMillis()-a);
      } else {
            xueService.findXueSellRebalancingByPK(1l);
            try {
                Thread.sleep(1000 * 60 * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.objectMapper = new ObjectMapper();
    }
}
