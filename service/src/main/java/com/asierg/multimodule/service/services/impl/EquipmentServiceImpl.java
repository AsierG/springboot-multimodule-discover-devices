package com.asierg.multimodule.service.services.impl;

import com.asierg.multimodule.domain.DiscoverError;
import com.asierg.multimodule.domain.Equipment;
import com.asierg.multimodule.domain.Firmware;
import com.asierg.multimodule.domain.Model;
import com.asierg.multimodule.domain.enums.ErrorType;
import com.asierg.multimodule.service.dto.DeviceDTO;
import com.asierg.multimodule.service.repositories.EquipmentRepository;
import com.asierg.multimodule.service.repositories.FirmwareRepository;
import com.asierg.multimodule.service.repositories.ModelRepository;
import com.asierg.multimodule.service.services.EquipmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class EquipmentServiceImpl implements EquipmentService {

    private static final Logger auditLogger = LoggerFactory.getLogger("audit-log");

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private FirmwareRepository firmwareRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    public EquipmentServiceImpl() {
    }

    public List<Equipment> listAll() {
        List<Equipment> equipmentList = new ArrayList<>();
        this.equipmentRepository.findAll().forEach(equipmentList::add);
        return equipmentList;
    }

    public Equipment getById(String id) {
        return equipmentRepository.findOne(id);
    }

    @Transactional
    public Equipment saveOrUpdate(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    @Transactional
    public void delete(String id) {
        equipmentRepository.delete(id);
    }

    public Equipment findActiveByIpAddr(String ip) {
        return equipmentRepository.findByIpAddressAndActiveEquals(ip, true);
    }

    public List<Equipment> findByIpAddr(String ip) {
        return equipmentRepository.findAllByIpAddress(ip);
    }

    public List<Equipment> findAllByIpAddrAndSerialNumberIsNot(String ip, String serialNumber) {
        return equipmentRepository.findAllByIpAddressAndSerialNumberIsNot(ip, serialNumber);
    }

    @Override
    @Transactional
    public Equipment saveOrUpdate(DeviceDTO deviceDTO) {
        Equipment equipment = getById(deviceDTO.getSerialNumber());
        if (equipment == null) {
            auditLogger.info("IP {} (SN {}) not recorded in the DB", deviceDTO.getIpAddress(), deviceDTO.getSerialNumber());
            equipment = new Equipment();
            equipment.setSerialNumber(deviceDTO.getSerialNumber());
        } else {
            auditLogger.info("IP {} (SN {}) already in the DB", deviceDTO.getIpAddress(), deviceDTO.getSerialNumber());
        }
        equipment.setIpAddress(deviceDTO.getIpAddress());
        equipment.setActive(true);

        List<DiscoverError> errors = new ArrayList<>();
        Model model = modelRepository.findOne(deviceDTO.getProductIdentifier());
        if (model == null) {
            DiscoverError modelError = new DiscoverError(equipment, ErrorType.MODEL);
            errors.add(modelError);
            auditLogger.info("IP {} unrecorded model {} found (SN {})", deviceDTO.getIpAddress(),
                    deviceDTO.getProductIdentifier(), deviceDTO.getSerialNumber());
        }
        equipment.setModel(model);
        Firmware firmware = firmwareRepository.findOne(deviceDTO.getFirmwareVersion());
        if (firmware == null) {
            DiscoverError firmwareError = new DiscoverError(equipment, ErrorType.FIRMWARE);
            errors.add(firmwareError);
            auditLogger.info("IP {} unrecorded firmware {} found (SN {})", deviceDTO.getIpAddress(),
                    deviceDTO.getFirmwareVersion(), deviceDTO.getSerialNumber());
        }
        equipment.setFirmware(firmware);


        if (model != null && firmware != null) {
            if (!firmware.getFirmwareModels().contains(model)) {
                auditLogger.info("IP {} incompatible Firmware {} and Model {} found for device (SN {})",
                        deviceDTO.getIpAddress(), deviceDTO.getFirmwareVersion(), deviceDTO.getProductIdentifier(),
                        deviceDTO.getSerialNumber());
                DiscoverError firmwareError = new DiscoverError(equipment, ErrorType.INCOMPATIBLE_FIRMWARE);
                errors.add(firmwareError);
            }
        }

        equipment.setDiscoverErrors(errors);
        return saveOrUpdate(equipment);
    }

}
