package model.personaldata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonalDataPatchDTO  {
    private String title;

    private String workPhone;
}

