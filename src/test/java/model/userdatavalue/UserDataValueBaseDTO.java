package model.userdatavalue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDataValueBaseDTO {

    private String fieldName;

    private UUID userId;

    private String value;

    public UserDataValueBaseDTO() {
    }
}
