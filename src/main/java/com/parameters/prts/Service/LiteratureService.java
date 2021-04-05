package com.parameters.prts.Service;

import com.parameters.prts.Model.LiteratureEntity;
import com.parameters.prts.Repository.LiteratureRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class LiteratureService extends CrudService<LiteratureEntity, Integer> {
    private final LiteratureRepository literatureRepository;

    public LiteratureService(LiteratureRepository literatureRepository) {
        this.literatureRepository = literatureRepository;
    }

    @Override
    protected LiteratureRepository getRepository() {
        return literatureRepository;
    }
}
