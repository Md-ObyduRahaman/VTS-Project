package nex.vts.backend.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshLogin {

    @JsonProperty("RefreshSuccess")
    private boolean RefreshSuccess;

    @JsonProperty("serverDateTime")
    private String serverDateTime;

    @JsonProperty("token")
    private String token;
}
