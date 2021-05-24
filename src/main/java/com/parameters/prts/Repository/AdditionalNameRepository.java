package com.parameters.prts.Repository;

import com.parameters.prts.Model.AdditionalNameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditionalNameRepository extends JpaRepository<AdditionalNameEntity, Integer> {
}
