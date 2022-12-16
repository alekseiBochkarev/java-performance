package model.userdatavalue;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class UserDataValueCreateDTO extends UserDataValueBaseDTO {
    private UUID userId;

    public UserDataValueCreateDTO() {
    }

    public UserDataValueCreateDTO(final UUID userId) {
        this.userId = userId;
    }
}
