package model.virtualdb;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class VirtualDbCreateDTO extends VirtualDbBaseDTO {
    private UUID id;

    public VirtualDbCreateDTO() {
    }

    public VirtualDbCreateDTO(final UUID id) {
        this.id = id;
    }
}
