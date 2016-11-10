import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by riky on 2016/11/10.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class LoginResult{

    @JsonProperty
    public String errorMsg;
    @JsonProperty
    public Integer errorNo;
    @JsonProperty(value = "data")
    public LoginResponse data;


}
