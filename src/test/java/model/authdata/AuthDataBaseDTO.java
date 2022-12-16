package model.authdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthDataBaseDTO {
    private boolean active;

    private ZonedDateTime expiresAt;

    private boolean external;

    private Integer failedLoginAttemptsAmount;

    private ZonedDateTime failedLoginStartedAt;

    private ZonedDateTime gtcAcceptedAt;

    private ZonedDateTime lastPasswordChangeAt;

    private boolean locked;

    private boolean loginAsUserAllowed;

    private String password;

    private String passwordHash;

    private boolean publicUser;

    public AuthDataBaseDTO() {
    }
}
