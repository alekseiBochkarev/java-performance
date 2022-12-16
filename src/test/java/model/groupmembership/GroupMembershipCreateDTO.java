package model.groupmembership;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class GroupMembershipCreateDTO extends GroupMembershipBaseDTO {
    private UUID id;

    public GroupMembershipCreateDTO() {
    }

    public GroupMembershipCreateDTO(final UUID id) {
        this.id = id;
    }
}
