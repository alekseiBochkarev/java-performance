package model.user;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import model.groupmembership.GroupMembership;
import model.userdatavalue.UserDataValue;

import java.util.List;
import java.util.UUID;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class UserCreateDTO extends UserBaseDTO {
    private UUID id;

    private String type;

    private List<GroupMembership> groupMemberships;

    private List<UserDataValue> userDataValues;

    public UserCreateDTO() {
    }

    public UserCreateDTO(final UUID id, final String type, final List<GroupMembership> groupMemberships, List<UserDataValue> userDataValues) {
        this.id = id;
        this.type = type;
        this.groupMemberships = groupMemberships;
        this.userDataValues = userDataValues;
    }
}
