package model.userpreferences;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import model.MailReceiveType;

import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPreferencesBaseDTO {
    private String language;

    private MailReceiveType mailReceive;

    private String mailSignature;

    private String preferredLengthUnit;

    private String regionId;

    private String skin;

    private UUID startPage;

    private String timeZoneId;

    private String timeZoneOffset;

    public UserPreferencesBaseDTO() {
    }

    public void setMailReceive(MailReceiveType randomEnum) {
    }
}
