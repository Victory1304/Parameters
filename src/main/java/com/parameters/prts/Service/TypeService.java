package com.parameters.prts.Service;

import com.parameters.prts.Model.TypeEntity;
import com.parameters.prts.Repository.TypeRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class TypeService extends CrudService<TypeEntity, Integer> {
    private final TypeRepository typeRepository;

    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    protected TypeRepository getRepository() {
        return typeRepository;
    }
}
