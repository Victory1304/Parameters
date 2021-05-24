package com.parameters.prts.Service;

import com.parameters.prts.Model.SystemEntity;
import com.parameters.prts.Repository.SystemRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class SystemService extends CrudService<SystemEntity, Integer> {
    private final SystemRepository systemRepository;

    public SystemService(SystemRepository systemRepository) {
        this.systemRepository = systemRepository;
    }

    @Override
    protected SystemRepository getRepository() {
        return systemRepository;
    }
}
