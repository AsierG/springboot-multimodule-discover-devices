package com.asierg.multimodule.service.services;

import com.asierg.multimodule.domain.Equipment;
import com.asierg.multimodule.service.dto.DeviceDTO;

import java.util.List;

public interface EquipmentService extends CRUDService<Equipment, String> {

    Equipment saveOrUpdate(DeviceDTO deviceDTO);

    Equipment findActiveByIpAddr(String ip);

    List<Equipment> findByIpAddr(String ip);

    List<Equipment> findAllByIpAddrAndSerialNumberIsNot(String ip, String serialNumber);

}