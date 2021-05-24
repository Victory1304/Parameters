package com.parameters.prts.Repository;

import com.parameters.prts.Model.GroupSystemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupSystemRepository extends JpaRepository<GroupSystemEntity, Integer> {
}
