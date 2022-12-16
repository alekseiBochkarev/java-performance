package util.authdata;

import base.BaseAuthData;

import model.authdata.AuthDataBaseDTO;
import model.authdata.AuthDataPatchDTO;
import model.authdata.AuthDataUpdateDTO;

import static util.RandomUtil.*;


public class AuthDataLoadTestClient extends BaseAuthData {

    public AuthDataUpdateDTO getAuthDataUpdateDto() {
        return fillUpdateDto(new AuthDataUpdateDTO());
    }

    public AuthDataPatchDTO getAuthDataPatchDto() {
        return fillUpdateDto(new AuthDataPatchDTO());
    }

    public <T extends AuthDataBaseDTO> T fillUpdateDto(final T updateDto) {
        updateDto.setActive(getRandomBoolean());
        updateDto.setExpiresAt(getRandomDateTime());
        updateDto.setExternal(getRandomBoolean());
        updateDto.setFailedLoginAttemptsAmount(getRandomIndex(0, 1000));
        updateDto.setFailedLoginStartedAt(getRandomDateTime());
        updateDto.setGtcAcceptedAt(getRandomDateTime());
        updateDto.setLastPasswordChangeAt(getRandomDateTime());
        updateDto.setLocked(getRandomBoolean());
        updateDto.setLoginAsUserAllowed(getRandomBoolean());
        updateDto.setPassword(getRandomStringOrNull(10));
//        updateDto.setPasswordHash(getRandomStringOrNull(10));
        updateDto.setPublicUser(getRandomBoolean());

        return updateDto;
    }

    public <T extends AuthDataPatchDTO> T fillUpdateDto(final T updateDto) {
        updateDto.setLocked(getRandomBoolean());
        updateDto.setLoginAsUserAllowed(getRandomBoolean());
        updateDto.setPassword(getRandomStringOrNull(10));

        return updateDto;
    }
}
