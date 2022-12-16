package util.virtualdb;

import base.BaseAuthData;
import model.virtualdb.VirtualDbBaseDTO;
import model.virtualdb.VirtualDbCreateDTO;
import model.virtualdb.VirtualDbPatchDTO;
import model.virtualdb.VirtualDbUpdateDTO;

import java.util.UUID;

import static util.RandomUtil.getRandomBoolean;
import static util.RandomUtil.getRandomString;

public class VirtualDbLoadTestClient extends BaseAuthData {
    public VirtualDbCreateDTO getVirtualDbCreateDto() {
        return fillUpdateDto(new VirtualDbCreateDTO(UUID.randomUUID()));
    }

    public VirtualDbUpdateDTO getVirtualDbUpdateDto() {
        return fillUpdateDto(new VirtualDbUpdateDTO());
    }

    public VirtualDbPatchDTO getVirtualDbPatchDto() {
        return fillUpdateDto(new VirtualDbPatchDTO());
    }

    public <T extends VirtualDbBaseDTO> T fillUpdateDto(final T updateDto) {
        updateDto.setDigitalWatermark(getRandomBoolean());
        updateDto.setName(getRandomString(10));
        updateDto.setNeedsApproval(getRandomBoolean());
        updateDto.setVisualWatermark(getRandomBoolean());

        return updateDto;
    }

    public <T extends VirtualDbPatchDTO> T fillUpdateDto(final T updateDto) {
        updateDto.setDigitalWatermark(getRandomBoolean());
        updateDto.setName(getRandomString(10));
        updateDto.setNeedsApproval(getRandomBoolean());
        updateDto.setVisualWatermark(getRandomBoolean());

        return updateDto;
    }
}
