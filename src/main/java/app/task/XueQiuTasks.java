package app.task;

import app.bean.*;
import app.entity.SellRebalancing;
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

    Double totalBalance=201000d ; //20W

    @Autowired
    HolidayService holidayService;
    @Override
    public void afterPropertiesSet() throws Exception {
        //System.setProperty("http.agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.76 Mobile Safari/537.36");
        this.objectMapper = new ObjectMapper();
    }
    //@Scheduled(fixedDelay = 1)
    public  void init() {
        if (holidayService.isTradeDayTimeByMarket()) {
            //long a = System.currentTimeMillis();
            HttpURLConnection connection = null;
            try {
               // URL url = new URL("https://xueqiu.com/P/ZH914042"); //wuzhiming
                URL url = new URL("https://xueqiu.com/P/ZH902949"); // cheng lao shi
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("User-agent","Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.76 Mobile Safari/537.36");
                InputStream in = connection.getInputStream();
                BufferedReader bd = new BufferedReader(new InputStreamReader(in));
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
                //System.out.println(xueSellRebalancing);
                SellRebalancing entity =xueService.findXueSellRebalancingByPK(xueSellRebalancing.getId());
                if(entity ==null){
                    List<XueHistories>  histories = xueSellRebalancing.getXueHistories();
                    for(XueHistories obj : histories){
                       // traderService.trading(market,obj.getId(),code,amount,obj.getPrice(),type,true);
                        traderService.trading(obj);
                    }
                    SellRebalancing sellRebalancing =new SellRebalancing();
                    sellRebalancing.setId(xueSellRebalancing.getId());
                    xueService.saveXueSellRebalancing(sellRebalancing);
                }
                connection.disconnect();
            } catch (Exception e) {
                //e.printStackTrace();
                log.info(" error: "+e.getMessage());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            //long b = (System.currentTimeMillis()-a);
      } else {
            xueService.findXueSellRebalancingByPK(21290092l);
            try {
                Thread.sleep(1000 * 60 * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
