package app.service;

import app.entity.SellRebalancing;
import app.repository.SellRebalancingRepository;
import app.repository.XueHistoriesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * Created by terry.wu on 2016/4/12 0012.
 */
@Service
public class XueService {
    private static final Logger log = LoggerFactory.getLogger(XueService.class);

    @Autowired
    SellRebalancingRepository xueSellRebalancingRepository;

    @Autowired
    XueHistoriesRepository xueHistoriesRepository;

    //@Cacheable(value="SellRebalancingByPK",key = "#id" ,unless="#result == null")

    public SellRebalancing findXueSellRebalancingByPK(Long id){
        return  xueSellRebalancingRepository.findSellRebalancingByPK(id);
    }


   /* public Boolean existsSellRebalancingRepository(Long id){
        return  xueSellRebalancingRepository.exists(id);
    }
*/

/*    @Cacheable(value="findHistoriesByRBID",key = "#rbid")
    public List<XueHistories> findHistoriesByRBID(Long rbid){
        return  xueHistoriesRepository.findByRebalancing_id(rbid);
    }
    */

    @CacheEvict(value="SellRebalancingByPK", allEntries = true)
    public void saveXueSellRebalancing(SellRebalancing entity){
        xueSellRebalancingRepository.save(entity);
    }

}
