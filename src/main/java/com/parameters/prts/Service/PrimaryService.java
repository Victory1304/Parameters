package com.parameters.prts.Service;

import com.parameters.prts.Model.PrimaryEntity;
import com.parameters.prts.Repository.PrimaryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrimaryService {
    private final PrimaryRepository primaryRepository;

    public PrimaryService(PrimaryRepository primaryRepository) {
        this.primaryRepository = primaryRepository;
    }

    public List<PrimaryEntity> findAll() {
        return primaryRepository.findAll();
    }

    public Optional<PrimaryEntity> findById(Long id) {
        return primaryRepository.findById(id);
    }

    public PrimaryEntity save(PrimaryEntity primary) {
        if (primary.getId() != null && primary.getId() > 0) {
            return primaryRepository.save(primary);
        }
        return primaryRepository.save(new PrimaryEntity(primary));
    }

    public void delete(Long id) {
        primaryRepository.deleteById(id);
    }
}
