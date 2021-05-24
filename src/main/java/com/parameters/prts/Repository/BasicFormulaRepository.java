package com.parameters.prts.Repository;

import com.parameters.prts.Model.BasicFormulaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicFormulaRepository extends JpaRepository<BasicFormulaEntity, Integer> {
}
