package model.role;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class RoleCreateDTO extends RoleBaseDTO {

    private UUID id;

    public RoleCreateDTO() {
    }

    public RoleCreateDTO(final UUID id) {
        this.id = id;
    }
}
