package util.userpreferences;

import base.BaseAuthData;
import model.MailReceiveType;

import model.userpreferences.UserPreferencesBaseDTO;
import model.userpreferences.UserPreferencesPatchDTO;
import model.userpreferences.UserPreferencesUpdateDTO;
import util.RandomUtil;

import java.util.UUID;

import static util.RandomUtil.*;

public class UserPreferencesLoadTestClient extends BaseAuthData {

    public UserPreferencesUpdateDTO getUserPreferencesUpdateDto() {
        return fillUpdateDto(new UserPreferencesUpdateDTO());
    }

    public UserPreferencesPatchDTO getUserPreferencesPatchDto() {
        return fillUpdateDto(new UserPreferencesPatchDTO());
    }

    public <T extends UserPreferencesBaseDTO> T fillUpdateDto(final T updateDto) {
        updateDto.setLanguage(getRandomStringOrNull(10));
        updateDto.setMailReceive(RandomUtil.<MailReceiveType>getRandomEnum(MailReceiveType.class));
        updateDto.setMailSignature(getRandomStringOrNull(10));
        updateDto.setPreferredLengthUnit(getRandomStringOrNull(5));
        updateDto.setRegionId(String.valueOf(getRandomIndex(1,10)));
        updateDto.setSkin(getRandomStringOrNull(10));
        updateDto.setStartPage(UUID.randomUUID());
        updateDto.setTimeZoneId(getRandomStringOrNull(10));
        updateDto.setTimeZoneOffset(getRandomStringOrNull(10));

        return updateDto;
    }

    public <T extends UserPreferencesPatchDTO> T fillUpdateDto(final T updateDto) {
        updateDto.setLanguage(getRandomStringOrNull(10));
        updateDto.setMailReceive(RandomUtil.<MailReceiveType>getRandomEnum(MailReceiveType.class));
        updateDto.setMailSignature(getRandomStringOrNull(10));
        updateDto.setPreferredLengthUnit(getRandomStringOrNull(5));
        updateDto.setRegionId(String.valueOf(getRandomIndex(1,10)));
        updateDto.setSkin(getRandomStringOrNull(10));
        updateDto.setStartPage(UUID.randomUUID());
        updateDto.setTimeZoneId(getRandomStringOrNull(10));
        updateDto.setTimeZoneOffset(getRandomStringOrNull(10));

        return updateDto;
    }
}
