package util.virtualdbgroup;

import base.BaseAuthData;
import model.virtualdbgroup.VirtualDbGroupBaseDTO;
import model.virtualdbgroup.VirtualDbGroupCreateDTO;
import model.virtualdbgroup.VirtualDbGroupPatchDTO;
import model.virtualdbgroup.VirtualDbGroupUpdateDTO;

import java.util.List;
import java.util.UUID;

import static util.RandomUtil.getRandomString;

public class VirtualDbGroupLoadTestClient extends BaseAuthData {
    public VirtualDbGroupCreateDTO getVirtualDbGroupCreateDto() {
        return fillUpdateDto(new VirtualDbGroupCreateDTO(UUID.randomUUID()));
    }

    public VirtualDbGroupUpdateDTO getVirtualDbGroupUpdateDto(List<UUID> virtualDbsIds) {
        return fillUpdateDto(new VirtualDbGroupUpdateDTO(virtualDbsIds));
    }

    public VirtualDbGroupPatchDTO getVirtualDbGroupPatchDto() {
        return fillUpdateDto(new VirtualDbGroupPatchDTO());
    }

    public <T extends VirtualDbGroupBaseDTO> T fillUpdateDto(final T updateDto) {
        updateDto.setName(getRandomString(10));

        return updateDto;
    }

    public <T extends VirtualDbGroupPatchDTO> T fillUpdateDto(final T updateDto) {
        updateDto.setName(getRandomString(10));

        return updateDto;
    }
}
