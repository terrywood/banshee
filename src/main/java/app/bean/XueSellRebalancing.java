package app.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class XueSellRebalancing implements Serializable {
    private Long  id;
    /*
    private Long prev_bebalancing_id;
    private Long created_at;
    private String status;
    private Double cash;
*/
    @JsonProperty(value = "rebalancing_histories")
    private List<XueHistories> xueHistories ;

}
