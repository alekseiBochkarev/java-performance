package util.personaldata;

import base.BaseAuthData;

import model.personaldata.PersonalDataBaseDTO;
import model.personaldata.PersonalDataPatchDTO;
import model.personaldata.PersonalDataUpdateDTO;

import static util.RandomUtil.getRandomString;
import static util.RandomUtil.getRandomStringOrNull;

public class PersonalDataLoadTestClient extends BaseAuthData {

    public PersonalDataUpdateDTO getPersonalDataUpdateDto() {
        return fillUpdateDto(new PersonalDataUpdateDTO());
    }

    public PersonalDataPatchDTO getPersonalDataPatchDto() {
        return fillUpdateDto(new PersonalDataPatchDTO());
    }

    public <T extends PersonalDataBaseDTO> T fillUpdateDto(final T updateDto) {
        updateDto.setAlternativeEmail(getRandomStringOrNull(10));
        updateDto.setCompany(getRandomStringOrNull(10));
        updateDto.setFax(getRandomStringOrNull(10));
        updateDto.setHomePhone(getRandomStringOrNull(10));
        updateDto.setJobPosition(getRandomStringOrNull(10));
        updateDto.setMobile(getRandomStringOrNull(10));
        updateDto.setTitle(getRandomStringOrNull(10));
        updateDto.setWorkPhone(getRandomStringOrNull(10));

        return updateDto;
    }

    public <T extends PersonalDataPatchDTO> T fillUpdateDto(final T updateDto) {
        updateDto.setTitle(getRandomString(10));
        updateDto.setWorkPhone(getRandomString(10));

        return updateDto;
    }
}
