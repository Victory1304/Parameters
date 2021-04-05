package com.parameters.prts.Service;

import com.parameters.prts.Model.NameEntity;
import com.parameters.prts.Repository.NameRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class NameService extends CrudService<NameEntity, Integer> {
    private final NameRepository nameRepository;

    public NameService(NameRepository nameRepository) {
        this.nameRepository = nameRepository;
    }

    @Override
    protected NameRepository getRepository() {
        return nameRepository;
    }
}
