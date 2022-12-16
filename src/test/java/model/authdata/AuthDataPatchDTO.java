package model.authdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthDataPatchDTO {
    private boolean locked;

    private boolean loginAsUserAllowed;

    private String password;

    public AuthDataPatchDTO() {
    }
}

