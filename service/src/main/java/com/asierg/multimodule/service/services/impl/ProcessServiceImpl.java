package com.asierg.multimodule.service.services.impl;

import com.asierg.multimodule.domain.Operation;
import com.asierg.multimodule.domain.enums.InterfaceType;
import com.asierg.multimodule.domain.enums.OperationType;
import com.asierg.multimodule.service.dto.DeviceDTO;
import com.asierg.multimodule.service.dto.ProcessDTO;
import com.asierg.multimodule.service.enums.OperationStatus;
import com.asierg.multimodule.service.services.AsyncCommunicationService;
import com.asierg.multimodule.service.services.OperationService;
import com.asierg.multimodule.service.services.ProcessService;
import com.asierg.multimodule.service.utils.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
public class ProcessServiceImpl implements ProcessService {

    private static final Logger logger = LoggerFactory.getLogger(ProcessServiceImpl.class);

    private OperationService operationService;
    private AsyncCommunicationService asyncProcessService;

    @Autowired
    public ProcessServiceImpl(OperationService operationService, AsyncCommunicationService asyncProcessService) {
        this.operationService = operationService;
        this.asyncProcessService = asyncProcessService;
    }

    private boolean allFuturesStarted = false;
    private List<CompletableFuture<DeviceDTO>> listCompletableFuture = new ArrayList<>();

    public void launchCmdDiscoverProcess(String ipRangeInput) {
        this.launchDiscoverProcess(ipRangeInput, InterfaceType.COMMAND_LINE);
    }

    public void launchCmdDiscoverProcess(String ipFrom, String ipTo) {
        this.launchDiscoverProcess(ipFrom, ipTo, InterfaceType.COMMAND_LINE);
    }

    public boolean launchWebDiscoverProcess(String ipRangeInput) {
        boolean discoverLaunched = false;
        boolean operationIsAvailable = operationService.operationIsAvailable();
        if (operationIsAvailable) {
            operationService.startOperation(InterfaceType.WEB, OperationType.DISCOVER);
            allFuturesStarted = false;
            Set<String> ips = IpUtils.getIpsFromInputRange(ipRangeInput);
            listCompletableFuture = new ArrayList<>();
            for (String ip : ips) {
                listCompletableFuture.add(asyncProcessService.discoverIp(ip));
            }
            allFuturesStarted = true;
            discoverLaunched = true;
        }
        return discoverLaunched;
    }

    public void launchWebDiscoverProcess(String ipFrom, String ipTo) {
        this.launchDiscoverProcess(ipFrom, ipTo, InterfaceType.WEB);
    }

    private void launchDiscoverProcess(String ipRangeInput, InterfaceType interfaceType) {
        Set<String> ips = IpUtils.getIpsFromInputRange(ipRangeInput);
        this.discoverEquipments(ips, interfaceType);
    }

    private void launchDiscoverProcess(String ipFrom, String ipTo, InterfaceType interfaceType) {
        Set<String> ips = IpUtils.translateIpRange(ipFrom, ipTo);
        this.discoverEquipments(ips, interfaceType);
    }

    public void launchCmdReflashProcess(String ipRangeInput) {
        launchReflashProcess(ipRangeInput, InterfaceType.COMMAND_LINE);
    }

    public void launchCmdReflashProcess(String ipFrom, String ipTo) {
        launchReflashProcess(ipFrom, ipTo, InterfaceType.COMMAND_LINE);
    }

    public void launchWebReflashProcess(String ipRangeInput) {
        launchReflashProcess(ipRangeInput, InterfaceType.WEB);
    }

    public void launchWebReflashProcess(String ipFrom, String ipTo) {
        launchReflashProcess(ipFrom, ipTo, InterfaceType.WEB);
    }

    private void launchReflashProcess(String ipRangeInput, InterfaceType interfaceType) {
        Set<String> ips = IpUtils.getIpsFromInputRange(ipRangeInput);
        reflashEquipments(ips, interfaceType);
    }

    private void launchReflashProcess(String ipFrom, String ipTo, InterfaceType interfaceType) {
        Set<String> ips = IpUtils.translateIpRange(ipFrom, ipTo);
        reflashEquipments(ips, interfaceType);
    }

    private void discoverEquipments(Set<String> ips, InterfaceType interfaceType) {
//        auditLogger.info("Begin discovery process for {} Ip addresses", ips != null ? ips.size() : 0);
//        operationService.startOperation(interfaceType, OperationType.DISCOVER);
//        List<CompletableFuture<DeviceDTO>> listCompletableFuture = new ArrayList<>();
//        for (String ip : ips) {
//            listCompletableFuture.add(asyncProcessService.discoverIp(ip));
//        }
//        CompletableFuture.allOf((CompletableFuture[]) listCompletableFuture.toArray(new CompletableFuture[0])).join();
//        operationService.endOperation();
//        auditLogger.info("End discovery process");
    }

    private void reflashEquipments(Set<String> ips, InterfaceType interfaceType) {
        operationService.startOperation(interfaceType, OperationType.UPDATE);
        List<CompletableFuture<Boolean>> listCompletableFuture = new ArrayList<>();
        for (String ip : ips) {
            listCompletableFuture.add(asyncProcessService.reflashEquipment(ip));
        }
        CompletableFuture.allOf((CompletableFuture[]) listCompletableFuture.toArray(new CompletableFuture[0])).join();
        operationService.endOperation();
    }


    public List<CompletableFuture<DeviceDTO>> getListCompletableFuture() {
        return listCompletableFuture;
    }

    public void setListCompletableFuture(List<CompletableFuture<DeviceDTO>> listCompletableFuture) {
        this.listCompletableFuture = listCompletableFuture;
    }

    @Override
    public Integer getRunningFutures() {
        Integer futures = 0;
        if (allFuturesStarted && getListCompletableFuture() != null) {
            futures = getListCompletableFuture().size();
        }
        return futures;
    }

    @Override
    public ProcessDTO getProcessDto() {
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setFuturesSize(getListCompletableFuture().size());
        if (allFuturesStarted) {
            processDTO.setProcessRunning(Boolean.TRUE);
            int completed = 0;
            int canceled = 0;
            for (CompletableFuture<DeviceDTO> future : getListCompletableFuture()) {
                if (future.isDone()) {
                    completed++;
                    try {
                        DeviceDTO deviceDTO = future.get();
                        if (deviceDTO != null) {
                            processDTO.setFoundDevices(processDTO.getFoundDevices() + 1);
                        }
                    } catch (Exception e) {
                        logger.error("Error accesing in future.get(), {}", e);
                    }
                } else if (future.isCancelled()) {
                    canceled++;
                }
            }
            processDTO.setFinishedFutures(completed + canceled);
            processDTO.setCompletedFutures(completed);
            processDTO.setCanceledFutures(canceled);
            if (processDTO.getFuturesSize() == (completed + canceled)) {
                processDTO.setProcessRunning(Boolean.FALSE);
                Operation operation = operationService.endOperation();
                processDTO.setStart(operation.getStart());
                processDTO.setEnd(operation.getEnd());
            }
        } else {
            processDTO.setProcessRunning(Boolean.FALSE);
        }
        OperationStatus operationStatus = operationService.getOperationStatus();
        processDTO.setOperationStatus(operationStatus);

        return processDTO;
    }

}
