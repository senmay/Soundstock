package com.soundstock.mapper;

import com.soundstock.model.dto.UserDTO;
import com.soundstock.model.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    UserEntity toEntity(UserDTO user);
    UserDTO toDTO(UserEntity userEntity);
    List<UserDTO> toDTO(List<UserEntity> users);
}
