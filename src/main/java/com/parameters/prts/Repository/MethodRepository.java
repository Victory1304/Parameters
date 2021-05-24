package com.parameters.prts.Repository;

import com.parameters.prts.Model.MethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MethodRepository extends JpaRepository<MethodEntity, Integer> {
}
