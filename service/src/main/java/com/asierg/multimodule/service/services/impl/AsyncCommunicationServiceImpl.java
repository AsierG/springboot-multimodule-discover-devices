package com.asierg.multimodule.service.services.impl;

import com.asierg.multimodule.domain.DiscoverError;
import com.asierg.multimodule.domain.Equipment;
import com.asierg.multimodule.domain.enums.ErrorType;
import com.asierg.multimodule.service.dto.DeviceDTO;
import com.asierg.multimodule.service.enums.DeviceType;
import com.asierg.multimodule.service.enums.RcpContentType;
import com.asierg.multimodule.service.enums.ReflashResultCode;
import com.asierg.multimodule.service.services.AsyncCommunicationService;
import com.asierg.multimodule.service.services.EquipmentService;
import com.asierg.multimodule.service.services.RcpService;
import com.asierg.multimodule.service.services.SnmpService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class AsyncCommunicationServiceImpl implements AsyncCommunicationService {

    private static final Logger stdLogger = LoggerFactory.getLogger(AsyncCommunicationServiceImpl.class);
    private static final Logger auditLogger = LoggerFactory.getLogger("audit-log");

    @Autowired
    private SnmpService snmpService;

    @Autowired
    private RcpService rcpService;

    @Autowired
    private EquipmentService equipmentService;

    @Async("processExecutor")
    public CompletableFuture<DeviceDTO> discoverIp(String ip) {
        stdLogger.info("IP {} information requested equipmentid, modelid, firmware", ip);
        DeviceDTO deviceDTO = discoverDevice(ip);
        try {
            if (deviceDTO != null) {
                processDeviceInformation(ip, deviceDTO);
            } else {
                processIpWithoutDevice(ip);
            }
        } catch (Exception e) {
            stdLogger.error("Error processing discovery data for Ip {}: {}", ip, e.getMessage());
            auditLogger.error("IP {} Error processing discovery data", ip);
        }
        auditLogger.info("IP {} discovery complete", ip);
        return CompletableFuture.completedFuture(deviceDTO);
    }

    private DeviceDTO discoverDevice(String ip) {
        DeviceDTO deviceDTO = null;
        try {
            deviceDTO = this.snmpService.getDevice(ip);
            auditLogger.info("IP {} SNMP access OK", ip);
        } catch (Exception e) {
            stdLogger.error("Error in Snmp discovery call for Ip {}: {}", ip, e.getMessage());
            auditLogger.error("IP {} SNMP access not OK", ip);
        }
        return deviceDTO;
    }

    private void processDeviceInformation(String ip, DeviceDTO deviceDTO) {
        List<Equipment> previousEquipments;
        auditLogger.info("IP {} information check OK", ip);
        if (DeviceType.TYPE_1.getType().equals(deviceDTO.getManufacturer()) ||
                DeviceType.TYPE_2.getType().equals(deviceDTO.getManufacturer())) {
            auditLogger.info("IP {} confirmed as a device", ip);
            equipmentService.saveOrUpdate(deviceDTO);
            previousEquipments = equipmentService.findAllByIpAddrAndSerialNumberIsNot(ip,
                    deviceDTO.getSerialNumber());
            if (CollectionUtils.isNotEmpty(previousEquipments)) {
                deactivateConflictingEquipments(ip, previousEquipments);
            } else {
                auditLogger.info("IPA {} not duplicated in the DB", ip);
            }
            auditLogger.info("IP {} DB information updated", ip);
        } else {
            auditLogger.info("IP {} host is not a device", ip);
            previousEquipments = equipmentService.findByIpAddr(ip);
            if (CollectionUtils.isNotEmpty(previousEquipments)) {
                deactivateConflictingEquipments(ip, previousEquipments);
            }
        }
    }

    private void processIpWithoutDevice(String ip) {
        List<Equipment> previousEquipments;
        auditLogger.info("IP {} hosts no device", ip);
        previousEquipments = equipmentService.findByIpAddr(ip);
        if (CollectionUtils.isNotEmpty(previousEquipments)) {
            deactivateMissingEquipments(ip, previousEquipments);
        }
    }

    private void deactivateMissingEquipments(String ip, List<Equipment> equipments) {
        for (Equipment equipment : equipments) {
            auditLogger.info("IP {} missing device (SN {}) disabled", equipment.getSerialNumber(), ip);
            equipment.setActive(false);
            equipmentService.saveOrUpdate(equipment);
        }
    }

    private void deactivateConflictingEquipments(String ip, List<Equipment> equipments) {
        auditLogger.info("IPA {} duplicated in the DB", ip);
        for (Equipment equipment : equipments) {
            equipment.setActive(false);
            equipment.setIpAddress(null);
            if (equipment.getDiscoverErrors() == null) {
                equipment.setDiscoverErrors(new ArrayList());
            }
            equipment.getDiscoverErrors().add(new DiscoverError(equipment, ErrorType.CONFLICTING_IP));
            equipmentService.saveOrUpdate(equipment);
            auditLogger.info("IP {} device (SN {}) disabled due to duplication in the DB", ip, equipment.getSerialNumber());
        }
    }

    @Async("processExecutor")
    public CompletableFuture<Boolean> reflashEquipment(String ip) {
        stdLogger.info("Received request to reflash {} ", ip);
        Boolean reflashFinished;
        try {
            RcpContentType rcpContentType = RcpContentType.REMOTE_CONF;
            DeviceDTO deviceDTO = rcpService.getDeviceData(ip, rcpContentType);

            if (deviceDTO == null) {
                rcpContentType = RcpContentType.USYSCOM_REMOTE_CONF;
                deviceDTO = rcpService.getDeviceData(ip, rcpContentType);
            }

            if (deviceDTO == null) {
                Equipment equipment = equipmentService.saveOrUpdate(deviceDTO);

                ReflashResultCode result = rcpService.reflashDevice(ip, rcpContentType, equipment.getFirmware());
                if (result.equals(ReflashResultCode.OK_RESPONSE)) {
                    stdLogger.info("Reflash {} complete", ip);
                } else {
                    stdLogger.info("No equipment on ip {}", ip);
                }
                reflashFinished = Boolean.TRUE;
            } else {
                reflashFinished = Boolean.FALSE;
            }
        } catch (Exception ie) {
            stdLogger.error("Error reflashing {}: {}", ip, ie.getMessage());
            reflashFinished = Boolean.FALSE;
        }
        return CompletableFuture.completedFuture(reflashFinished);
    }

}
