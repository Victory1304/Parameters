package com.parameters.prts.Service;

import com.parameters.prts.Model.SourceEntity;
import com.parameters.prts.Repository.SourceRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class SourceService extends CrudService<SourceEntity, Integer> {
    private final SourceRepository sourceRepository;

    public SourceService(SourceRepository sourceRepository) {
        this.sourceRepository = sourceRepository;
    }

    @Override
    protected SourceRepository getRepository() {
        return sourceRepository;
    }
}
