package com.asierg.multimodule.service.services;

import com.asierg.multimodule.domain.Operation;
import com.asierg.multimodule.domain.enums.InterfaceType;
import com.asierg.multimodule.domain.enums.OperationType;
import com.asierg.multimodule.service.enums.OperationStatus;

public interface OperationService {

    Operation getOperation();

    boolean operationIsAvailable();

    Operation startOperation(InterfaceType interfaceType, OperationType operationType);

    Operation endOperation();

    OperationStatus getOperationStatus();

}
