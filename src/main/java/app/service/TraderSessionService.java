package app.service;

import app.entity.TraderSession;
import app.repository.TraderSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by terry.wu on 2016/4/12 0012.
 */
@Service
public class TraderSessionService {
    @Autowired
    TraderSessionRepository traderSessionRepository;
/*    public TraderSession getSession(){
        List<TraderSession> iterable =traderSessionRepository.findAllData();
        if(iterable.size()>0){
           return  iterable.get(0);
        }
        return null;
    }*/
    public TraderSession findOne(String id){
        return traderSessionRepository.findOne(id);
    }

/*    public void save(TraderSession entity){
        traderSessionRepository.save(entity);
    }
    public void delete(String id){
        traderSessionRepository.delete(id);
    }*/
}
