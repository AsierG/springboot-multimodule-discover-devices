package com.asierg.multimodule.web.controller;

import com.asierg.multimodule.domain.Firmware;
import com.asierg.multimodule.service.repositories.FirmwareRepository;
import com.asierg.multimodule.web.dto.FirmwareDto;
import org.apache.commons.collections4.IteratorUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/firmware")
public class FirmwareController {

    private FirmwareRepository firmwareRepository;

    @Autowired
    public FirmwareController(FirmwareRepository firmwareRepository) {
        this.firmwareRepository = firmwareRepository;
    }

    @GetMapping({"/findAll"})
    public ResponseEntity<List<FirmwareDto>> findFirmwares() {
        List<Firmware> firmwareList = IteratorUtils.toList(firmwareRepository.findAll().iterator());
        ModelMapper modelMapper = new ModelMapper();
        List<FirmwareDto> firmwareDTOList = modelMapper.map(firmwareList, new TypeToken<List<FirmwareDto>>() {
        }.getType());
        ResponseEntity<List<FirmwareDto>> responseEntity = new ResponseEntity<>(firmwareDTOList, HttpStatus.OK);
        return responseEntity;
    }


}
