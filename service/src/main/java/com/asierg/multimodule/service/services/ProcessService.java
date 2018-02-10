package com.asierg.multimodule.service.services;


import com.asierg.multimodule.service.dto.ProcessDTO;

public interface ProcessService {

    void launchCmdDiscoverProcess(String ipRangeInput);

    void launchCmdDiscoverProcess(String ipFrom, String ipTo);

    boolean launchWebDiscoverProcess(String ipRangeInput);

    void launchWebDiscoverProcess(String ipFrom, String ipTo);

    void launchCmdReflashProcess(String ipRangeInput);

    void launchCmdReflashProcess(String ipFrom, String ipTo);

    void launchWebReflashProcess(String ipRangeInput);

    void launchWebReflashProcess(String ipFrom, String ipTo);

    Integer getRunningFutures();

    ProcessDTO getProcessDto();

}
