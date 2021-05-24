package com.parameters.prts.Repository;

import com.parameters.prts.Model.FormulaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormulaRepository extends JpaRepository<FormulaEntity, Integer> {
}
