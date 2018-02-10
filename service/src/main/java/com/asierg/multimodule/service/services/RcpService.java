package com.asierg.multimodule.service.services;


import com.asierg.multimodule.domain.Firmware;
import com.asierg.multimodule.service.dto.DeviceDTO;
import com.asierg.multimodule.service.enums.RcpContentType;
import com.asierg.multimodule.service.enums.ReflashResultCode;

public interface RcpService {

    /**
     * Operación para obtener (mediante una única petición HTTP POST) la información del dispositivo
     *
     * @param ip             Dirección ip del dispositivo
     * @param rcpContentType Content-type de la petición POST
     * @return Datos del dispositivo
     */
    DeviceDTO getDeviceData(String ip, RcpContentType rcpContentType);

    /**
     * Operación para la actualización del firmware de un dispositivo
     *
     * @param ip             Dirección ip del dispositivo a actualizar
     * @param rcpContentType Content-type del mensaje HTTP que se enviará al dispositivo
     * @param firmware       Versión del firmware
     * @return Rspuesta del intento de actualización
     */
    ReflashResultCode reflashDevice(String ip, RcpContentType rcpContentType, Firmware firmware);

}
