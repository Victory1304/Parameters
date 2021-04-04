package com.parameters.prts.Repository;

import com.parameters.prts.Model.ParameterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParameterRepository extends JpaRepository<ParameterEntity, Integer> {
    List<ParameterEntity> findByVidContaining(String title);
}
