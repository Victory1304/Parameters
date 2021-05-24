package com.parameters.prts.Service;

import com.parameters.prts.Model.EquipmentEntity;
import com.parameters.prts.Repository.EquipmentRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class EquipmentService extends CrudService<EquipmentEntity, Integer> {
    private final EquipmentRepository equipmentRepository;

    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    protected EquipmentRepository getRepository() {
        return equipmentRepository;
    }
}
