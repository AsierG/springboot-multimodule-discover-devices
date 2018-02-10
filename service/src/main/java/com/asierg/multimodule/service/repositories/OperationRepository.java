package com.asierg.multimodule.service.repositories;

import com.asierg.multimodule.domain.Operation;
import com.asierg.multimodule.domain.enums.OperationType;
import org.springframework.data.repository.CrudRepository;

public interface OperationRepository extends CrudRepository<Operation, Long> {

    Operation findByOperationType(OperationType operationType);

}
