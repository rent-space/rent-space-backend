package com.rentspace.model.user;

import com.rentspace.exception.ApiRequestException;
import static com.rentspace.exception.ExceptionMessages.USER_TYPE_NOT_SUPPORTED;


public enum UserType {

    EVENT_OWNER,
    PLACE_OWNER,
    SERVICE_OWNER;

    public Class<? extends AppUser> toClass() {
        if (this == UserType.EVENT_OWNER) {
            return EventOwner.class;
        } else if (this == UserType.PLACE_OWNER) {
            return PlaceOwner.class;
        } else if (this == UserType.SERVICE_OWNER) {
            return ServiceOwner.class;
        }
        throw new ApiRequestException(USER_TYPE_NOT_SUPPORTED);
    }
}
