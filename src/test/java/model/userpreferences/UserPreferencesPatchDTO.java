package model.userpreferences;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPreferencesPatchDTO extends UserPreferencesBaseDTO {
}

