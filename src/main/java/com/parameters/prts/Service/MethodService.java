package com.parameters.prts.Service;

import com.parameters.prts.Model.MethodEntity;
import com.parameters.prts.Repository.MethodRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class MethodService extends CrudService<MethodEntity, Integer> {
    private final MethodRepository methodRepository;

    public MethodService(MethodRepository methodRepository) {
        this.methodRepository = methodRepository;
    }

    @Override
    protected MethodRepository getRepository() {
        return methodRepository;
    }
}
