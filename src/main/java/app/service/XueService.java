package app.service;

import app.entity.SellRebalancing;
import app.repository.SellRebalancingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by terry.wu on 2016/4/12 0012.
 */
@Service
public class XueService {
    private static final Logger log = LoggerFactory.getLogger(XueService.class);
    @Autowired
    SellRebalancingRepository sellRebalancingRepository;

    @Cacheable(value="SellRebalancingByPK",key = "#id" ,unless="#result == null")
    public SellRebalancing findXueSellRebalancingByPK(Long id){
        return  sellRebalancingRepository.findSellRebalancingByPK(id);
    }
    @CacheEvict(value="SellRebalancingByPK", allEntries = true)
    public void saveXueSellRebalancing(SellRebalancing entity){
        sellRebalancingRepository.save(entity);
    }

}
