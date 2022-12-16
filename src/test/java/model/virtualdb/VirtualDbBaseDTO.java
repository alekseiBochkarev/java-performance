package model.virtualdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VirtualDbBaseDTO {
    private boolean digitalWatermark;

    private String name;

    private boolean needsApproval;

    private boolean visualWatermark;

    public VirtualDbBaseDTO() {
    }
}
