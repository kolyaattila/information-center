package com.information.center.authservice.convert;

import com.information.center.authservice.entity.User;
import com.information.center.authservice.model.UserRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserConverter {

  User toUser(UserRequest request);

}
