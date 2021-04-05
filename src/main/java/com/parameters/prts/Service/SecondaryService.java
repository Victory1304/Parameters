package com.parameters.prts.Service;

import com.parameters.prts.Model.SecondaryEntity;
import com.parameters.prts.Repository.SecondaryRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class SecondaryService extends CrudService<SecondaryEntity, Integer> {
    private final SecondaryRepository secondaryRepository;

    public SecondaryService(SecondaryRepository secondaryRepository) {
        this.secondaryRepository = secondaryRepository;
    }

    @Override
    protected SecondaryRepository getRepository() {
        return secondaryRepository;
    }
}
