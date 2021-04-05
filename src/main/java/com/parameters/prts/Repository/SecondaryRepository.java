package com.parameters.prts.Repository;

import com.parameters.prts.Model.SecondaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecondaryRepository extends JpaRepository<SecondaryEntity, Integer> {
}
