package model.groupmembership;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupMembershipPatchDTO {
    private String membershipNotes;

    public GroupMembershipPatchDTO() {
    }
}

