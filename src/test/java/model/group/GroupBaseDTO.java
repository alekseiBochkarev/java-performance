package model.group;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupBaseDTO {
    private boolean admin;
    private String name;
    private String type;

    public GroupBaseDTO() {
    }
}
