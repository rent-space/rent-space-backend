package com.rentspace.exception;

public class ExceptionMessages {

    public static final String USER_TYPE_NOT_SUPPORTED = "Este tipo de usuário não é suportado.";

    public static final String EMAIL_ALREADY_EXISTS = "Já existe um usuário com este e-mail no sistema.";

    public static final String USER_NOT_FOUND = "Não existe um usuário com o id: ";

    public static final String INVALID_PLACE_OWNER_ID = "Não existe um proprietário de espaço com o id: ";

    public static final String INVALID_SERVICE_OWNER_ID = "Não existe um proprietário de serviço com o id: ";

    public static final String INVALID_PLACE_ID = "Não existe um espaço com o id: ";

    public static final String INVALID_SERVICE_ID = "Não existe um serviço com o id: ";

    public static final String SERVICE_NOT_RELATED_TO_SPACE = "O serviço informado não é relacionado diretamente ao espaço. Id do serviço: ";

    public static final String INVALID_PAYMENT_FORMAT = "A forma de pagamento informada é inválida";

    public static final String PLACE_OWNER_SEARCH_ERROR = "Não foi possível encontrar o proprietário do espaço de id: ";

    public static final String INVALID_RESERVATION_PERIOD_OF_TIME = "Não é possível utilizar este horário para reserva";

    public static final String PEOPLE_INVOLVED_EXCEED_MAXIMUM_CAPACITY = "O número de pessoas envolvidas excede o máximo permitido pelo espaço definido";

    public static final String EVENT_OWNER_SEARCH_ERROR = "Não foi possível encontrar o proprietário do evento de id: ";

    public static final String SERVICE_OWNER_SEARCH_ERROR = "Não foi possível encontrar o proprietário do serviço de id: ";

    public static final String SERVICE_IS_EXCLUSIVE = "Este serviço é exclusivo de um espaço diferente do endereço informado na solicitação";

    public static final String USER_EMAIL_NOT_FOUND = "Não foi encontrado o usuário com email: ";

    public static final String RESERVATION_NOT_FOUND = "Não foi encontrada uma reserva com o id: ";

    public static final String RESERVATION_USER_NOT_FOUND = "Não foi encontrado um usuário que possua uma reserva de id: ";

    public static final String INVALID_STATUS_UPDATE = "A atualização de status é inválida para a reserva de id: ";
}
