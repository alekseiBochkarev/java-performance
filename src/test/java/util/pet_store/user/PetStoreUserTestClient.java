package util.pet_store.user;

import base.BaseAuthData;
import model.pet_store.user.UserBaseDTO;
import model.pet_store.user.UserCreateDTO;
import model.pet_store.user.UserUpdateDTO;
import util.RandomUtil;

public class PetStoreUserTestClient extends BaseAuthData {

    public UserCreateDTO getUserCreateDto() {
        return fillUpdateDto(new UserCreateDTO());
    }

    public UserUpdateDTO getUserUpdateDto()
    {
        return fillUpdateDto(new UserUpdateDTO());
    }

    public <T extends UserBaseDTO> T fillUpdateDto(final T updateDto)
    {
        updateDto.setId(RandomUtil.getRandomIndex(0, 1000));
        updateDto.setUsername("SOME_USER");
        updateDto.setFirstName(RandomUtil.getRandomString(10));
        updateDto.setLastName(RandomUtil.getRandomString(10));
        updateDto.setEmail(RandomUtil.getRandomEmail(10));
        updateDto.setPassword(RandomUtil.getRandomString(25));
        updateDto.setPhone(RandomUtil.getRandomString(7));
        updateDto.setUserStatus(RandomUtil.getRandomIndex(0, 11));

        return  updateDto;
    }
}
