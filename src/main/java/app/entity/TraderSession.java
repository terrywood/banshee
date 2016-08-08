package app.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by terry.wu on 2016/4/12 0012.
 */
@Entity
@Data
public class TraderSession implements Serializable {

    @Id
    private String sid;
    private String szAccount;
    private String shAccount;
    private String password;

/*    private String brand;
    @Column(length = 500)
    private String cookie;*/
/*
    @LastModifiedDate
    @Column(name = "last_update_time")
    private Date lastUpdateTime;

    @PrePersist
    void prePersist(){
        this.setLastUpdateTime( new Date() );
    }
    @PreUpdate
    void preUpdate(){
        this.setLastUpdateTime( new Date() );
    }*/
}
