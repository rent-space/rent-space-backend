package com.rentspace.exception;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


public class ExceptionMessages {

    public static final String USER_TYPE_NOT_SUPPORTED = "Este tipo de usuário não é suportado.";

    public static final String EMAIL_ALREADY_EXISTS = "Já existe um usuário com este e-mail no sistema.";

    public static final String INVALID_PLACE_OWNER_ID = "Não existe um proprietário de espaço com o id: ";

    public static final String INVALID_SERVICE_OWNER_ID = "Não existe um proprietário de serviço com o id: ";

    public static final String INVALID_PLACE_ID = "Não existe um espaço com o id: ";

    public static final String PLACE_OWNER_SEARCH_ERROR = "Não foi possível encontrar o proprietário do espaço referente";

}
