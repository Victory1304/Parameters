package com.parameters.prts.Service;

import com.parameters.prts.Model.FormulaEntity;
import com.parameters.prts.Repository.FormulaRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class FormulaService extends CrudService<FormulaEntity, Integer> {
    private final FormulaRepository formulaRepository;

    public FormulaService(FormulaRepository formulaRepository) {
        this.formulaRepository = formulaRepository;
    }

    @Override
    protected FormulaRepository getRepository() {
        return formulaRepository;
    }
}
