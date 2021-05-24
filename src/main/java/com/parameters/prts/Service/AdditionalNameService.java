package com.parameters.prts.Service;

import com.parameters.prts.Model.AdditionalNameEntity;
import com.parameters.prts.Repository.AdditionalNameRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class AdditionalNameService extends CrudService<AdditionalNameEntity, Integer> {
    private final AdditionalNameRepository additionalNameRepository;


    public AdditionalNameService(AdditionalNameRepository additionalNameRepository) {
        this.additionalNameRepository = additionalNameRepository;
    }

    @Override
    protected AdditionalNameRepository getRepository() {
        return additionalNameRepository;
    }
}
