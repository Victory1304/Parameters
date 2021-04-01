package com.parameters.prts.Repository;

import com.parameters.prts.Model.VidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VidRepository extends JpaRepository<VidEntity, Long> {
}
