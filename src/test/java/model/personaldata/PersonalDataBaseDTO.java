package model.personaldata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonalDataBaseDTO {
    private String alternativeEmail;

    private String company;

    private String fax;

    private String homePhone;

    private String jobPosition;

    private String mobile;

    private String title;

    private String workPhone;

    public PersonalDataBaseDTO() {
    }
}
