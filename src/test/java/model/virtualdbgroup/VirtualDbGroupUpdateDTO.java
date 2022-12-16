package model.virtualdbgroup;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class VirtualDbGroupUpdateDTO extends VirtualDbGroupBaseDTO {
    private List<UUID> virtualDbsIds;

    public VirtualDbGroupUpdateDTO(List<UUID> virtualDbsIds) {
        this.virtualDbsIds = virtualDbsIds;
    }
}
