package com.asierg.multimodule.service.services;

import java.util.List;

public interface CRUDService<T, ID> {

    List<T> listAll();

    T getById(ID id);

    T saveOrUpdate(T model);

    void delete(ID id);
}
