package com.parameters.prts.Service;

import com.parameters.prts.Model.ParameterEntity;
import com.parameters.prts.Repository.ParameterRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class ParameterService extends CrudService<ParameterEntity, Integer> {
    private final ParameterRepository parameterRepository;

    public ParameterService(ParameterRepository parameterRepository) {
        this.parameterRepository = parameterRepository;
    }

    @Override
    protected ParameterRepository getRepository() {
        return parameterRepository;
    }
}
