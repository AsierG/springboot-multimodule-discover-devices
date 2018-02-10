package com.asierg.multimodule.service.services;


import com.asierg.multimodule.service.dto.DeviceDTO;

public interface SnmpService {

    DeviceDTO getDevice(String ip);

}
