package com.rentspace.model.user;

import com.rentspace.exception.InvalidUserTypeException;

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
        throw new InvalidUserTypeException();
    }
}
