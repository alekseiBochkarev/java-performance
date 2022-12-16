package model.orgunit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrgUnitPatchDTO extends OrgUnitBaseDTO {
}

