package com.rentspace.service;

import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.user.ServiceOwner;
import com.rentspace.repository.ServiceOwnerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import static com.rentspace.exception.ExceptionMessages.INVALID_SERVICE_OWNER_ID;

@Service
@AllArgsConstructor
public class ServiceOwnerService {

    private ServiceOwnerRepository serviceOwnerRepository;

    public void save(ServiceOwner model) { this.serviceOwnerRepository.save(model);}

    public ServiceOwner get(Long id) {
        return this.serviceOwnerRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException(INVALID_SERVICE_OWNER_ID + id));
    }
}
