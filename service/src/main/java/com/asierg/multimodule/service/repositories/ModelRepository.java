package com.asierg.multimodule.service.repositories;

import com.asierg.multimodule.domain.Model;
import org.springframework.data.repository.CrudRepository;

public interface ModelRepository extends CrudRepository<Model, String> {
}
