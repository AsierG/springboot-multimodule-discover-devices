package com.asierg.multimodule.service.repositories;

import com.asierg.multimodule.domain.DiscoverError;
import com.asierg.multimodule.domain.enums.ErrorType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DiscoverErrorRepository extends CrudRepository<DiscoverError, Long> {

    List<DiscoverError> findAllByEquipmentSerialNumber(String equipmentSerialNumber);

    List<DiscoverError> findAllByEquipmentSerialNumberAndErrorType(String equipmentSerialNumber, ErrorType errorType);

    Long countByEquipmentSerialNumber(String equipmentSerialNumber);

}
