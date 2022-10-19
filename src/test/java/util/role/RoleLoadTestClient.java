package util.role;

import base.BaseAuthData;
import model.role.RoleBaseDTO;
import model.role.RoleCreateDTO;
import model.role.RolePatchDTO;
import model.role.RoleUpdateDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static util.RandomUtil.getRandomIndex;
import static util.RandomUtil.getRandomString;


public class RoleLoadTestClient extends BaseAuthData {
    List<String> permissionsIds = new ArrayList<>(Collections.singleton(
            "3fa85f64-5717-4562-b3fc-2c963f66afa6"));

    public RoleCreateDTO getRoleCreateDto() {
        return fillUpdateDto(new RoleCreateDTO(UUID.randomUUID()));
    }

    public RoleUpdateDTO getRoleUpdateDto() {
        return fillUpdateDto(new RoleUpdateDTO());
    }

    public RolePatchDTO getRolePatchDto() {
        return fillUpdateDto(new RolePatchDTO());
    }

    public <T extends RoleBaseDTO> T fillUpdateDto(final T updateDto) {
        updateDto.setName(getRandomString(10));
        updateDto.setVirtualDbId(String.valueOf(UUID.randomUUID()));
        // updateDto.setIsAdmin(Optional.of(true));
        // updateDto.setIsDefault(Optional.of(true));
        updateDto.setOrderIndex(getRandomIndex(0, 100));
        updateDto.setType(getRandomString(10));
        updateDto.setPermissionsIds(permissionsIds);
        return updateDto;
    }

    public <T extends RolePatchDTO>T fillUpdateDto(final T updateDto) {
        updateDto.setName(getRandomString(10));
        updateDto.setOrderIndex(getRandomIndex(0, 100));
        return updateDto;
    }

}
