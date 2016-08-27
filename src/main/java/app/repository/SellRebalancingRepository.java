package app.repository;

import app.entity.SellRebalancing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Administrator on 2016/7/25 0025.
 */
public interface SellRebalancingRepository extends JpaRepository<SellRebalancing, Long>, JpaSpecificationExecutor {
     @Query("select t from SellRebalancing t where t.id=?1")
    SellRebalancing findSellRebalancingByPK(Long id);
}
