package com.soundstock.mapper;

import com.soundstock.model.dto.UserDTO;
import com.soundstock.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface UserMapper {
    UserEntity mapToUserEntity(UserDTO user);
    @Mapping(target = "orders", ignore = true) // Ignorowanie pola orders podczas mapowania w celu unikniecia rekurencji
    UserDTO mapToUserDTO(UserEntity userEntity);
    List<UserDTO> mapToUserList(List<UserEntity> users);
}
