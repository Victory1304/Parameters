package com.parameters.prts.Service;

import com.parameters.prts.Model.PrimaryEntity;
import com.parameters.prts.Repository.PrimaryRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class PrimaryService extends CrudService<PrimaryEntity, Integer> {
    private final PrimaryRepository primaryRepository;

    public PrimaryService(PrimaryRepository primaryRepository) {
        this.primaryRepository = primaryRepository;
    }

    @Override
    protected PrimaryRepository getRepository() {
        return primaryRepository;
    }
}
