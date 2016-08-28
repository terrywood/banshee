package app.service;

import app.bean.XueHistories;
import app.entity.Trader;

/**
 * Created by terry.wu on 2016/4/15 0015.
 */
public interface TraderService {

   void trading(XueHistories obj);
   // Trader findOne(Long id);
    void trading(String market, Long id, String code, Integer amount, Double price, String type, Boolean fast);

    
}
