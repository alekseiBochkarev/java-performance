package model.orgunit;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class OrgUnitCreateDTO extends OrgUnitBaseDTO {
    private UUID id;

    public OrgUnitCreateDTO() {
    }

    public OrgUnitCreateDTO(final UUID id) {
        this.id = id;
    }
}
