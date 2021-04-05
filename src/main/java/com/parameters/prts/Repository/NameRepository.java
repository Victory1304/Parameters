package com.parameters.prts.Repository;

import com.parameters.prts.Model.NameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NameRepository extends JpaRepository<NameEntity, Integer> {
}
