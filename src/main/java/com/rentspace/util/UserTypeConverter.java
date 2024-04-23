package com.rentspace.util;

import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.user.*;

import static com.rentspace.exception.ExceptionMessages.USER_TYPE_NOT_SUPPORTED;

public class UserTypeConverter {

    public static Class<? extends AppUser> toClass(UserType type) {
        if (type == UserType.EVENT_OWNER) {
            return EventOwner.class;
        } else if (type == UserType.PLACE_OWNER) {
            return PlaceOwner.class;
        } else if (type == UserType.SERVICE_OWNER) {
            return ServiceOwner.class;
        }
        throw new ApiRequestException(USER_TYPE_NOT_SUPPORTED);
    }

    public static UserType toEnum(Class<? extends AppUser> userClass) {
        if (userClass.equals(EventOwner.class)) {
            return UserType.EVENT_OWNER;
        } else if (userClass.equals(PlaceOwner.class)) {
            return UserType.PLACE_OWNER;
        } else if (userClass.equals(ServiceOwner.class)) {
            return UserType.SERVICE_OWNER;
        }
        throw new ApiRequestException(USER_TYPE_NOT_SUPPORTED);
    }

}
