package app.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by terry.wu on 2016/4/18 0018.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class GFAccount implements Serializable{



    @JsonProperty(value = "stock_code")
    public  String stockCode;
    @JsonProperty(value = "enable_amount")
    public  Double enableAmount;

}
