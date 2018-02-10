package com.asierg.multimodule.service.repositories;

import com.asierg.multimodule.domain.Firmware;
import org.springframework.data.repository.CrudRepository;

public interface FirmwareRepository extends CrudRepository<Firmware, String> {


}
