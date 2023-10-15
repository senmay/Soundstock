package com.soundstock.mapper;

import com.soundstock.model.User;
import com.soundstock.model.dto.UserDTO;
import com.soundstock.model.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserEntity mapToUserEntity(User user);
    UserDTO mapToUserDTO(UserEntity userEntity);
    User mapToUser(UserEntity userEntity);
    User mapToUser(UserDTO userDTO);



}
