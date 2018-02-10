package com.asierg.multimodule.web.controller;

import com.asierg.multimodule.domain.Equipment;
import com.asierg.multimodule.service.dto.DeviceDTO;
import com.asierg.multimodule.service.repositories.EquipmentRepository;
import org.apache.commons.collections4.IteratorUtils;
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
@RequestMapping("/update")
public class UpdateController {

    private static final Logger logger = LoggerFactory.getLogger(UpdateController.class);

    @Autowired
    private EquipmentRepository equipmentRepository;

    @GetMapping({"/search"})
    public ResponseEntity<List<DeviceDTO>> search(@RequestParam("firmware") String firmware,
                                                  @RequestParam("model") String model,
                                                  @RequestParam("cidr") String cidr) {

        logger.info("firmware {}, model {}, cidr {}", firmware, model, cidr);
        List<Equipment> equipments = IteratorUtils.toList(equipmentRepository.findAll().iterator());

        ModelMapper modelMapper = new ModelMapper();
        List<DeviceDTO> deviceDTOList = modelMapper.map(equipments, new TypeToken<List<DeviceDTO>>() {
        }.getType());
        ResponseEntity<List<DeviceDTO>> responseEntity = new ResponseEntity<>(deviceDTOList, HttpStatus.OK);
        return responseEntity;
    }

}
