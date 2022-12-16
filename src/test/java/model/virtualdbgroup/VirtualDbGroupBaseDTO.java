package model.virtualdbgroup;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VirtualDbGroupBaseDTO {
    private String name;

    public VirtualDbGroupBaseDTO() {
    }
}
