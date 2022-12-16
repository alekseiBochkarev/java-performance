package model.groupmembership;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupMembershipBaseDTO {
    private UUID groupId;

    private String membershipNotes;

    private UUID userId;

    public GroupMembershipBaseDTO() {
    }
}
