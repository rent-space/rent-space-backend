package com.rentspace.model.user;

import com.rentspace.exception.ApiRequestException;
import static com.rentspace.exception.ExceptionMessages.USER_TYPE_NOT_SUPPORTED;


public enum UserType {

    EVENT_OWNER,
    PLACE_OWNER,
    SERVICE_OWNER;
}
