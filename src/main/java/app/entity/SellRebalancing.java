package app.entity;

import app.bean.XueHistories;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;


@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SellRebalancing implements Serializable {
    @Id
    private Long  id;

    /*
    private Long prev_bebalancing_id;
    private Long created_at;
    private String status;
    private Double cash;
*/

}
