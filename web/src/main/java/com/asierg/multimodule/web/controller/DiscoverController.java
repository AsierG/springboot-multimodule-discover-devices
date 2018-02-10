package com.asierg.multimodule.web.controller;

import com.asierg.multimodule.service.dto.ProcessDTO;
import com.asierg.multimodule.service.services.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/discover")
public class DiscoverController {

    @Autowired
    private ProcessService processService;

    @GetMapping({"/start"})
    public Boolean startDiscover(@RequestParam("ipRange") String ipRange) {
        return processService.launchWebDiscoverProcess(ipRange);
    }

    @GetMapping({"/futures"})
    public Integer futures() {
        Integer futures = processService.getRunningFutures();
        return futures;
    }

    @GetMapping({"/status"})
    public ProcessDTO processStatus() {
        ProcessDTO processDTO = processService.getProcessDto();
        return processDTO;
    }

}
