package com.nnk.springboot.mapper;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * User Mapper
 */
@Mapper(componentModel = "spring")
public interface UserMapper extends IMapper<User, UserDto> {
    
    /**
     * UserDto To User
     *
     * @param userDto the userDto
     * @return the user
     */
    @Mapping(target = "password", ignore = true)
    User toDomain(UserDto userDto);

    /**
     * User to UserDto
     *
     * @param user the user
     * @return the userDto
     */
    @Mapping(target = "password", ignore = true)
    UserDto toDto(User user);
}
