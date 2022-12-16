package util.user;

import base.BaseAuthData;

import model.user.*;
import util.authdata.AuthDataLoadTestClient;
import util.groupmembership.GroupMembershipLoadTestClient;
import util.personaldata.PersonalDataLoadTestClient;
import util.userdatavalue.UserDataValueLoadTestClient;
import util.userpreferences.UserPreferencesLoadTestClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static util.RandomUtil.getRandomEmail;
import static util.RandomUtil.getRandomString;


public class UserLoadTestClient extends BaseAuthData {
    GroupMembershipLoadTestClient groupMembershipLoadTestClient = new GroupMembershipLoadTestClient();
    UserDataValueLoadTestClient userDataValueLoadTestClient = new UserDataValueLoadTestClient();
    UserPreferencesLoadTestClient userPreferencesLoadTestClient = new UserPreferencesLoadTestClient();
    PersonalDataLoadTestClient personalDataLoadTestClient = new PersonalDataLoadTestClient();
    AuthDataLoadTestClient authDataLoadTestClient = new AuthDataLoadTestClient();
    public UserCreateDTO getUserCreateDto() {
        return fillUpdateDto(new UserCreateDTO(UUID.randomUUID(), "UPTEMPO", new ArrayList(Arrays.asList(groupMembershipLoadTestClient.getGroupMembership(), groupMembershipLoadTestClient.getGroupMembership())), new ArrayList(Arrays.asList(userDataValueLoadTestClient.getUserDataValue(), userDataValueLoadTestClient.getUserDataValue()))));
    }

    public UserCreateDTO getUserCreateDto(String VDBGroupUuid, String orgUnitUuid) {
        return fillUpdateDto(new UserCreateDTO(UUID.randomUUID(), "UPTEMPO", new ArrayList(Arrays.asList(groupMembershipLoadTestClient.getGroupMembership())), new ArrayList(Arrays.asList(userDataValueLoadTestClient.getUserDataValue()))), VDBGroupUuid, orgUnitUuid);
    }

    public UserCreateShortDTO getUserCreateShortDto(String VDBGroupUuid, String orgUnitUuid) {
        return fillUpdateDto(new UserCreateShortDTO(UUID.randomUUID(), "UPTEMPO", new ArrayList(Arrays.asList(groupMembershipLoadTestClient.getGroupMembership()))), VDBGroupUuid, orgUnitUuid);
    }

    public UserUpdateDTO getUserUpdateDto(String VDBGroupUuid, String orgUnitUuid) {
        return fillUpdateDto(new UserUpdateDTO(), VDBGroupUuid, orgUnitUuid);
    }

    public UserPatchDTO getUserPatchDto() {
        return fillUpdateDto(new UserPatchDTO());
    }

    public <T extends UserBaseDTO> T fillUpdateDto(final T updateDto) {
        updateDto.setUserPreference(userPreferencesLoadTestClient.getUserPreferencesUpdateDto());
        updateDto.setPersonalData(personalDataLoadTestClient.getPersonalDataUpdateDto());
        updateDto.setAuthData(authDataLoadTestClient.getAuthDataUpdateDto());
        updateDto.setEmail(getRandomEmail(10));
        updateDto.setFirstName(getRandomString(10));
        updateDto.setLastName(getRandomString(10));
        updateDto.setLogin(getRandomString(10));
        updateDto.setVirtualDbGroupId(UUID.randomUUID());
        updateDto.setOrgUnitId(UUID.randomUUID());
        updateDto.setRoleIds(new ArrayList<>(Arrays.asList(UUID.randomUUID(), UUID.randomUUID())));
        updateDto.setLabelIds(new ArrayList<>(Arrays.asList(UUID.randomUUID(), UUID.randomUUID())));
//        updateDto.setIncludedCalendarsIds(new ArrayList<>(Arrays.asList(UUID.randomUUID(), UUID.randomUUID())));
//        updateDto.setExcludedCalendarsIds(new ArrayList<>(Arrays.asList(UUID.randomUUID(), UUID.randomUUID())));

        return updateDto;
    }

    public <T extends UserBaseDTO> T fillUpdateDto(final T updateDto, String vdbGroupUuid, String orgUnitUuid) {
        updateDto.setUserPreference(userPreferencesLoadTestClient.getUserPreferencesUpdateDto());
        updateDto.setPersonalData(personalDataLoadTestClient.getPersonalDataUpdateDto());
        updateDto.setAuthData(authDataLoadTestClient.getAuthDataUpdateDto());
        updateDto.setEmail(getRandomEmail(10));
        updateDto.setFirstName(getRandomString(10));
        updateDto.setLastName(getRandomString(10));
        updateDto.setLogin(getRandomString(10));
        updateDto.setVirtualDbGroupId(UUID.fromString(vdbGroupUuid));
        updateDto.setOrgUnitId(UUID.fromString(orgUnitUuid));
        updateDto.setRoleIds(new ArrayList<>(Arrays.asList(UUID.randomUUID(), UUID.randomUUID())));
        updateDto.setLabelIds(new ArrayList<>(Arrays.asList(UUID.randomUUID(), UUID.randomUUID())));
//        updateDto.setIncludedCalendarsIds(new ArrayList<>(Arrays.asList(UUID.randomUUID(), UUID.randomUUID())));
//        updateDto.setExcludedCalendarsIds(new ArrayList<>(Arrays.asList(UUID.randomUUID(), UUID.randomUUID())));

        return updateDto;
    }

    public <T extends UserPatchDTO> T fillUpdateDto(final T updateDto) {
        updateDto.setFirstName(getRandomString(10));
        updateDto.setLastName(getRandomString(10));

        return updateDto;
    }

}
