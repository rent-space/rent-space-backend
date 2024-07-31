package com.rentspace.service;

import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.user.EventOwner;
import com.rentspace.model.user.PlaceOwner;
import com.rentspace.model.user.ServiceOwner;
import com.rentspace.repository.ServiceOwnerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import static com.rentspace.exception.ExceptionMessages.INVALID_SERVICE_OWNER_ID;
import static com.rentspace.exception.ExceptionMessages.SERVICE_OWNER_SEARCH_ERROR;

@Service
@AllArgsConstructor
public class ServiceOwnerService {

    private ServiceOwnerRepository serviceOwnerRepository;

    public void save(ServiceOwner model) { this.serviceOwnerRepository.save(model);}

    public ServiceOwner get(Long id) {
        return this.serviceOwnerRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException(INVALID_SERVICE_OWNER_ID + id));
    }

    public ServiceOwner getByServiceId(Long id) {
        ServiceOwner serviceOwner = serviceOwnerRepository.findByServiceId(id).get();

        if(serviceOwner == null){
            return new ServiceOwner();
        }else{
            return serviceOwner;
        }
    }
}
