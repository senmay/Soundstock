package com.soundstock.mapper;

import com.soundstock.model.User;
import com.soundstock.model.dto.UserDTO;
import com.soundstock.model.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    UserEntity mapToUserEntity(User user);
    UserDTO mapToUserDTO(UserEntity userEntity);
    User mapToUser(UserEntity userEntity);
    User mapToUser(UserDTO userDTO);
    List<UserDTO> mapToUserDTOList(List<User> users);
    List<User> mapToUserList(List<UserEntity> users);
}
