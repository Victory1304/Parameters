package com.parameters.prts.Repository;

import com.parameters.prts.Model.BasicNameIndicatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicNameIndicatorRepository extends JpaRepository<BasicNameIndicatorEntity, Integer> {
}
