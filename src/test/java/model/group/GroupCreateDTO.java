package model.group;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class GroupCreateDTO extends GroupBaseDTO {
    private UUID id;

    public GroupCreateDTO() {
    }

    public GroupCreateDTO(final UUID id) {
        this.id = id;
    }
}
