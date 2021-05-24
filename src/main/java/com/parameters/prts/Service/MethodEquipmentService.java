package com.parameters.prts.Service;

import com.parameters.prts.Model.MethodEquipmentEntity;
import com.parameters.prts.Repository.MethodEquipmentRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class MethodEquipmentService extends CrudService<MethodEquipmentEntity, Integer> {
    private final MethodEquipmentRepository methodEquipmentRepository;

    public MethodEquipmentService(MethodEquipmentRepository methodEquipmentRepository) {
        this.methodEquipmentRepository = methodEquipmentRepository;
    }

    @Override
    protected MethodEquipmentRepository getRepository() {
        return methodEquipmentRepository;
    }
}
