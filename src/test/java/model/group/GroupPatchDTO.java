package model.group;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupPatchDTO {
    private String name;

    public GroupPatchDTO() {
    }
}
