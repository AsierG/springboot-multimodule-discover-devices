package com.asierg.multimodule.web.controller;

import com.asierg.multimodule.domain.Model;
import com.asierg.multimodule.service.repositories.ModelRepository;
import com.asierg.multimodule.web.dto.ModelDto;
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
@RequestMapping("/model")
public class ModelController {

    @Autowired
    private ModelRepository modelRepository;

    @GetMapping({"/findAll"})
    public ResponseEntity<List<ModelDto>> findFirmwares() {
        List<Model> modelList = IteratorUtils.toList(modelRepository.findAll().iterator());
        ModelMapper modelMapper = new ModelMapper();
        List<ModelDto> modelDTOList = modelMapper.map(modelList, new TypeToken<List<ModelDto>>() {
        }.getType());
        ResponseEntity<List<ModelDto>> responseEntity = new ResponseEntity<>(modelDTOList, HttpStatus.OK);
        return responseEntity;
    }


}
