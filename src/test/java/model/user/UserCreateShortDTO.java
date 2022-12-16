package model.user;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import model.groupmembership.GroupMembership;

import java.util.List;
import java.util.UUID;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class UserCreateShortDTO extends UserBaseDTO {
    private UUID id;

    private String type;

    private List<GroupMembership> groupMemberships;


    public UserCreateShortDTO() {
    }

    public UserCreateShortDTO(final UUID id, final String type, final List<GroupMembership> groupMemberships) {
        this.id = id;
        this.type = type;
        this.groupMemberships = groupMemberships;
    }
}
