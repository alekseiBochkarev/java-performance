package util.groupmembership;

import base.BaseAuthData;

import model.groupmembership.*;

import java.util.UUID;


import static util.RandomUtil.getRandomString;

public class GroupMembershipLoadTestClient extends BaseAuthData {
    public GroupMembershipCreateDTO getGroupMembershipCreateDto() {
        return fillUpdateDto(new GroupMembershipCreateDTO(UUID.randomUUID()));
    }

    public GroupMembershipUpdateDTO getGroupMembershipUpdateDto() {
        return fillUpdateDto(new GroupMembershipUpdateDTO());
    }

    public GroupMembership getGroupMembership() {
        return fillUpdateDto(new GroupMembership());
    }

    public GroupMembershipPatchDTO getGroupMembershipPatchDto() {
        return fillUpdateDto(new GroupMembershipPatchDTO());
    }

    public <T extends GroupMembershipBaseDTO> T fillUpdateDto(final T updateDto) {
        updateDto.setGroupId(UUID.randomUUID());
        updateDto.setMembershipNotes(getRandomString(10));
        updateDto.setUserId(UUID.randomUUID());

        return updateDto;
    }

    public <T extends GroupMembershipPatchDTO> T fillUpdateDto(final T updateDto) {
        updateDto.setMembershipNotes(getRandomString(10));

        return updateDto;
    }

    public <T extends GroupMembership> T fillUpdateDto(final T updateDto) {
        updateDto.setId(UUID.randomUUID());
        updateDto.setMembershipNotes(getRandomString(10));
        updateDto.setGroupId(UUID.randomUUID());

        return updateDto;
    }
}
