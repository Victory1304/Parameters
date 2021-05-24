package com.parameters.prts.Repository;

import com.parameters.prts.Model.SystemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemRepository extends JpaRepository<SystemEntity, Integer> {
}
