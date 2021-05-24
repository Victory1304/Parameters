package com.parameters.prts.Service;

import com.parameters.prts.Model.LanguageEntity;
import com.parameters.prts.Repository.LanguageRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class LanguageService extends CrudService<LanguageEntity, Integer> {
    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    protected LanguageRepository getRepository() {
        return languageRepository;
    }
}
