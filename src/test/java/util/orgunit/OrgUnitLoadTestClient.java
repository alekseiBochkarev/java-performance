package util.orgunit;

import base.BaseAuthData;

import model.orgunit.OrgUnitBaseDTO;
import model.orgunit.OrgUnitCreateDTO;
import model.orgunit.OrgUnitPatchDTO;
import model.orgunit.OrgUnitUpdateDTO;

import java.util.UUID;

import static util.RandomUtil.getRandomString;
import static util.RandomUtil.getRandomStringOrNull;

public class OrgUnitLoadTestClient extends BaseAuthData {
    public OrgUnitCreateDTO getOrgUnitCreateDto() {
        return fillUpdateDto(new OrgUnitCreateDTO(UUID.randomUUID()));
    }

    public OrgUnitUpdateDTO getOrgUnitUpdateDto() {
        return fillUpdateDto(new OrgUnitUpdateDTO());
    }

    public OrgUnitPatchDTO getOrgUnitPatchDto() {
        return fillUpdateDto(new OrgUnitPatchDTO());
    }

    public <T extends OrgUnitBaseDTO> T fillUpdateDto(final T updateDto) {
        updateDto.setFullName(getRandomString(10));
        updateDto.setNote(getRandomStringOrNull(10));
        updateDto.setParentId(UUID.randomUUID());
        updateDto.setPath(getRandomStringOrNull(10));
        updateDto.setShortName(getRandomString(10));
        updateDto.setSsoKey(getRandomStringOrNull(10));

        return updateDto;
    }

    public <T extends OrgUnitPatchDTO> T fillUpdateDto(final T updateDto) {
        updateDto.setFullName(getRandomString(10));
        updateDto.setNote(getRandomStringOrNull(10));
        updateDto.setParentId(UUID.randomUUID());
        updateDto.setPath(getRandomStringOrNull(10));
        updateDto.setShortName(getRandomString(10));
        updateDto.setSsoKey(getRandomStringOrNull(10));

        return updateDto;
    }
}
