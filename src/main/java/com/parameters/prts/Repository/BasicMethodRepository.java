package com.parameters.prts.Repository;

import com.parameters.prts.Model.BasicMethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicMethodRepository extends JpaRepository<BasicMethodEntity, Integer> {
}
