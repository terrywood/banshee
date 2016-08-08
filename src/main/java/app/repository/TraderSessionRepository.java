package app.repository;

import app.entity.TraderSession;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TraderSessionRepository extends CrudRepository<TraderSession, String> {

    @Query("select o from TraderSession o")
    List<TraderSession> findAllData();

}
