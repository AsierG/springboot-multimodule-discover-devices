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

    private ProcessService processService;

    @Autowired
    public DiscoverController(ProcessService processService) {
        this.processService = processService;
    }

    @GetMapping({"/start"})
    public Boolean startDiscover(@RequestParam("ipRange") String ipRange) {
        return processService.launchWebDiscoverProcess(ipRange);
    }

    @GetMapping({"/futures"})
    public Integer futures() {
        return processService.getRunningFutures();
    }

    @GetMapping({"/status"})
    public ProcessDTO processStatus() {
        return processService.getProcessDto();
    }

}
