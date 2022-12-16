package util.group;

import base.BaseAuthData;
import model.group.GroupBaseDTO;
import model.group.GroupCreateDTO;
import model.group.GroupPatchDTO;
import model.group.GroupUpdateDTO;

import java.util.UUID;

import static util.RandomUtil.getRandomBoolean;
import static util.RandomUtil.getRandomString;

public class GroupLoadTestClient extends BaseAuthData {
    public GroupCreateDTO getGroupCreateDto() {
        return fillUpdateDto(new GroupCreateDTO(UUID.randomUUID()));
    }

    public GroupUpdateDTO getGroupUpdateDto() {
        return fillUpdateDto(new GroupUpdateDTO());
    }

    public GroupPatchDTO getGroupPatchDto() {
        return fillUpdateDto(new GroupPatchDTO());
    }

    public <T extends GroupBaseDTO> T fillUpdateDto(final T updateDto) {
        updateDto.setName(getRandomString(10));
        updateDto.setAdmin(getRandomBoolean());
        updateDto.setType(getRandomString(10));
        return updateDto;
    }

    public <T extends GroupPatchDTO> T fillUpdateDto(final T updateDto) {
        updateDto.setName(getRandomString(10));
        return updateDto;
    }
}
