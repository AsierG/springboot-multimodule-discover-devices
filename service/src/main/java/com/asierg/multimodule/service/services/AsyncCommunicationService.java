package com.asierg.multimodule.service.services;

import com.asierg.multimodule.service.dto.DeviceDTO;

import java.util.concurrent.CompletableFuture;

public interface AsyncCommunicationService {

    CompletableFuture<DeviceDTO> discoverIp(String ip);

    CompletableFuture<Boolean> reflashEquipment(String ip);

}
