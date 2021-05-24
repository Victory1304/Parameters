package com.parameters.prts.Service;

import com.parameters.prts.Model.BasicMethodEntity;
import com.parameters.prts.Repository.BasicMethodRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class BasicMethodService extends CrudService<BasicMethodEntity, Integer> {
    private final BasicMethodRepository basicMethodRepository;

    public BasicMethodService(BasicMethodRepository basicMethodRepository) {
        this.basicMethodRepository = basicMethodRepository;
    }

    @Override
    protected BasicMethodRepository getRepository() {
        return basicMethodRepository;
    }
}
