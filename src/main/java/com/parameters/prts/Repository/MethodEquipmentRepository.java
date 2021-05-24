package com.parameters.prts.Repository;

import com.parameters.prts.Model.MethodEquipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MethodEquipmentRepository extends JpaRepository<MethodEquipmentEntity, Integer> {
}
