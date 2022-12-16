package model.virtualdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VirtualDbPatchDTO extends VirtualDbBaseDTO {
}

