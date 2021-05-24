package com.parameters.prts.Service;

import com.parameters.prts.Model.BasicFormulaEntity;
import com.parameters.prts.Repository.BasicFormulaRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class BasicFormulaService extends CrudService<BasicFormulaEntity, Integer> {
    private final BasicFormulaRepository basicFormulaRepository;


    public BasicFormulaService(BasicFormulaRepository basicFormulaRepository) {
        this.basicFormulaRepository = basicFormulaRepository;
    }

    @Override
    protected BasicFormulaRepository getRepository() {
        return basicFormulaRepository;
    }
}
