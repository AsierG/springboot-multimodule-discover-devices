package com.asierg.multimodule.service.services.impl;

import com.asierg.multimodule.domain.Operation;
import com.asierg.multimodule.domain.enums.InterfaceType;
import com.asierg.multimodule.domain.enums.OperationType;
import com.asierg.multimodule.service.enums.OperationStatus;
import com.asierg.multimodule.service.repositories.OperationRepository;
import com.asierg.multimodule.service.services.OperationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OperationServiceImpl implements OperationService {

    private static final Logger logger = LoggerFactory.getLogger(OperationServiceImpl.class);

    @Autowired
    private OperationRepository operationRepository;

    @Override
    public Operation getOperation() {
        return operationRepository.findOne(Operation.OPERATION_ID);
    }

    @Override
    public boolean operationIsAvailable() {
        OperationStatus operationStatus = getOperationStatus();
        if (OperationStatus.AVAILABLE.equals(operationStatus) || OperationStatus.FINALIZED.equals(operationStatus)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public OperationStatus getOperationStatus() {
        Operation operation = getOperation();
        logger.info("Operation: {}", operation);
        OperationStatus operationStatus = OperationStatus.NOT_AVAILABLE;
        if (operation != null) {
            if (operation.getStart() == null && operation.getEnd() == null) {
                operationStatus = OperationStatus.AVAILABLE;
            } else if (operation.getStart() != null && operation.getEnd() == null) {
                operationStatus = OperationStatus.PROCESSING;
            } else {
                operationStatus = OperationStatus.FINALIZED;
            }
        }
        return operationStatus;
    }

    @Override
    public Operation startOperation(InterfaceType interfaceType, OperationType operationType) {
        Operation operation = operationRepository.findOne(Operation.OPERATION_ID);
        operation.setLauncherInterface(interfaceType);
        operation.setOperationType(operationType);
        operation.setStart(new Date());
        operation.setEnd(null);
        return operationRepository.save(operation);
    }

    @Override
    public Operation endOperation() {
        Operation operation = operationRepository.findOne(Operation.OPERATION_ID);
        operation.setEnd(new Date());
        return operationRepository.save(operation);
    }

}
