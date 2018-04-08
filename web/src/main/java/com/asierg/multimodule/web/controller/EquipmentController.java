package com.asierg.multimodule.web.controller;

import com.asierg.multimodule.domain.Equipment;
import com.asierg.multimodule.service.dto.DeviceDTO;
import com.asierg.multimodule.service.services.EquipmentService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {

    private EquipmentService equipmentService;

    @Autowired
    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping({"/find"})
    public ResponseEntity<List<DeviceDTO>> findEquipments() {

        List<Equipment> equipmentList = equipmentService.listAll();
        ModelMapper modelMapper = new ModelMapper();
        List<DeviceDTO> deviceDTOList = modelMapper.map(equipmentList, new TypeToken<List<DeviceDTO>>() {
        }.getType());
        ResponseEntity<List<DeviceDTO>> responseEntity = new ResponseEntity<>(deviceDTOList, HttpStatus.OK);
        return responseEntity;
    }


}
