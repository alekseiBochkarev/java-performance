package model.role;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RolePatchDTO {
    private String name;
    private int orderIndex;

    public RolePatchDTO() {
    }
}
