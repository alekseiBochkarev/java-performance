package util.userdatavalue;

import base.BaseAuthData;
import model.userdatavalue.*;

import java.util.UUID;


import static util.RandomUtil.getRandomIndex;
import static util.RandomUtil.getRandomString;

public class UserDataValueLoadTestClient extends BaseAuthData {
    public UserDataValueCreateDTO getUserDataValueCreateDto(String userId) {
        return fillUpdateDto(new UserDataValueCreateDTO(UUID.fromString(userId)));
    }

    public UserDataValueUpdateDTO getUserDataValueUpdateDto() {
        return fillUpdateDto(new UserDataValueUpdateDTO());
    }

    public UserDataValuePatchDTO getUserDataValuePatchDto() {
        return fillUpdateDto(new UserDataValuePatchDTO());
    }

    public UserDataValue getUserDataValue() {
        return fillUpdateDto(new UserDataValue());
    }

    public <T extends UserDataValueBaseDTO> T fillUpdateDto(final T updateDto) {
        updateDto.setFieldName(getRandomString(10));
        //updateDto.setUserId(UUID.randomUUID());
        updateDto.setValue(String.valueOf(getRandomIndex(1,10)));

        return updateDto;
    }

    public <T extends UserDataValuePatchDTO> T fillUpdateDto(final T updateDto) {
        updateDto.setFieldName(getRandomString(10));

        return updateDto;
    }

    public <T extends UserDataValue> T fillUpdateDto(final T updateDto) {
        updateDto.setFieldName(getRandomString(10));
        updateDto.setValue(String.valueOf(getRandomIndex(1,10)));

        return updateDto;
    }
}
