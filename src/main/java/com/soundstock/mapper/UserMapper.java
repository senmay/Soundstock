package com.soundstock.mapper;

import com.soundstock.model.dto.UserDTO;
import com.soundstock.model.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    UserEntity mapToUserEntity(UserDTO user);
    UserDTO mapToUserDTO(UserEntity userEntity);
    List<UserDTO> mapToUserList(List<UserEntity> users);
}
