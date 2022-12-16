package model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import model.authdata.AuthDataUpdateDTO;
import model.personaldata.PersonalDataUpdateDTO;
import model.userpreferences.UserPreferencesUpdateDTO;

import java.util.List;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserBaseDTO {

    private UserPreferencesUpdateDTO userPreference;

    private PersonalDataUpdateDTO personalData;

    private AuthDataUpdateDTO authData;

    private String email;

    private String firstName;

    private String lastName;

    private String login;

    private UUID virtualDbGroupId;

    private UUID orgUnitId;

    private List<UUID> roleIds;

    private List<UUID> labelIds;

    private List<UUID> includedCalendarsIds;

    private List<UUID> excludedCalendarsIds;

    public UserBaseDTO() {
    }
}
