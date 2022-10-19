package model.role;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleBaseDTO {
    private Optional<Boolean> isAdmin;
    private Optional<Boolean> isDefault;
    private String name;
    private int orderIndex;
    private String virtualDbId;
    private List<String> permissionsIds;
    private String type;

    public RoleBaseDTO() {
    }
}
