package com.parameters.prts.Repository;

import com.parameters.prts.Model.PrimaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrimaryRepository extends JpaRepository<PrimaryEntity, Integer> {
}
