package model.virtualdbgroup;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class VirtualDbGroupCreateDTO extends VirtualDbGroupBaseDTO {
    private UUID id;

    public VirtualDbGroupCreateDTO() {
    }

    public VirtualDbGroupCreateDTO(final UUID id) {
        this.id = id;
    }
}
