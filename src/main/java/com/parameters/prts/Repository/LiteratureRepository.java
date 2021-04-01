package com.parameters.prts.Repository;

import com.parameters.prts.Model.LiteratureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiteratureRepository extends JpaRepository<LiteratureEntity, Long> {
}
