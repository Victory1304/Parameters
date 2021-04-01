package com.parameters.prts.Service;

import com.parameters.prts.Model.SecondaryEntity;
import com.parameters.prts.Repository.SecondaryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SecondaryService {
    private final SecondaryRepository secondaryRepository;

    public SecondaryService(SecondaryRepository secondaryRepository) {
        this.secondaryRepository = secondaryRepository;
    }

    public List<SecondaryEntity> findAll() {
        return secondaryRepository.findAll();
    }

    public Optional<SecondaryEntity> findById(Long id) {
        return secondaryRepository.findById(id);
    }

    public SecondaryEntity save(SecondaryEntity secondary) {
        if (secondary.getId() != null && secondary.getId() > 0) {
            return secondaryRepository.save(secondary);
        }
        return secondaryRepository.save(new SecondaryEntity(secondary));
    }

    public void delete(Long id) {
        secondaryRepository.deleteById(id);
    }
}
