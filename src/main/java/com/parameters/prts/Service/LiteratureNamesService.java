package com.parameters.prts.Service;

import com.parameters.prts.Model.LiteratureNamesEntity;
import com.parameters.prts.Repository.LiteratureNamesRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class LiteratureNamesService extends CrudService<LiteratureNamesEntity, Integer> {
    private final LiteratureNamesRepository literatureNamesRepository;

    public LiteratureNamesService(LiteratureNamesRepository literatureNamesRepository) {
        this.literatureNamesRepository = literatureNamesRepository;
    }

    @Override
    protected LiteratureNamesRepository getRepository() {
        return literatureNamesRepository;
    }
}
