package com.nnk.springboot.mapper;

import com.nnk.springboot.entity.User;
import com.nnk.springboot.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * User Mapper
 */
@Mapper(componentModel = "spring")
public interface UserMapper {


    /**
     * UserDto To User
     *
     * @param userDto the userDto
     * @return the user
     */
    @Mapping(target = "password", ignore = true)
    User toUser(UserDto userDto);

    /**
     * User to UserDto
     *
     * @param user the user
     * @return the userDto
     */
    @Mapping(target = "password", ignore = true)
    UserDto toUserDto(User user);
}
