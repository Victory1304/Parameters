package com.parameters.prts.Repository;

import com.parameters.prts.Model.SourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceRepository extends JpaRepository<SourceEntity, Integer> {
}
