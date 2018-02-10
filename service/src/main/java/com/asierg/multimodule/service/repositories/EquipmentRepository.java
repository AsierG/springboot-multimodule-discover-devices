package com.asierg.multimodule.service.repositories;

import com.asierg.multimodule.domain.Equipment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EquipmentRepository extends CrudRepository<Equipment, String> {

    Equipment findByIpAddressAndActiveEquals(String ip, Boolean active);

    List<Equipment> findAllByIpAddress(String ip);

    List<Equipment> findAllByIpAddressAndSerialNumberIsNot(String ip, String serialNumber);

    @Query(value = "SELECT * FROM Equipment e INNER JOIN DISCOVER_ERROR d ON e.serial_number=d.equipment_serial_number " +
            "WHERE d.id = :id", nativeQuery = true)
    Equipment findByDiscoverErrorId(@Param("id") long id);


}
