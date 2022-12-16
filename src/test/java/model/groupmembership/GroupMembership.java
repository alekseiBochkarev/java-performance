package model.groupmembership;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupMembership {
    private UUID id;

    private UUID groupId;

    private String membershipNotes;
}
