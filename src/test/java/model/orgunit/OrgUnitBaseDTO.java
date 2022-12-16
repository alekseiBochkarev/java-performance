package model.orgunit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrgUnitBaseDTO {
    private String fullName;

    private String note;

    private UUID parentId;

    private String path;

    private String shortName;

    private String ssoKey;

    public OrgUnitBaseDTO() {
    }
}
