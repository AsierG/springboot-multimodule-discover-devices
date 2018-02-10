package com.asierg.multimodule.web.controller;

import com.asierg.multimodule.service.dto.DeviceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PruebaController {

    private static final Logger logger = LoggerFactory.getLogger(PruebaController.class);

    @GetMapping({"/device"})
    public DeviceDTO device() {
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setFirmwareVersion("22222");
        deviceDTO.setManufacturer("PRODUCT123");
        deviceDTO.setSerialNumber("4444");
        return deviceDTO;
    }

    @PostMapping("/pruebaPost")
    public ResponseEntity<DeviceDTO> pruebaPost(@RequestBody DeviceDTO deviceDTODto) {
        logger.info("deviceDTODto " + deviceDTODto);
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setFirmwareVersion("1.2223");
        deviceDTO.setManufacturer("manufac");
        deviceDTO.setSerialNumber("121212");
        ResponseEntity<DeviceDTO> responseEntity = new ResponseEntity<>(deviceDTO, HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value = "/ipRange/{startIp}/{endIp}")
    @ResponseBody
    public List<String> method7(@PathVariable("startIp") String startIp, @PathVariable("endIp") String endIp) {
        List<String> ips = new ArrayList<>();
        ips.add("1");
        ips.add("2");
        return ips;
    }

}
