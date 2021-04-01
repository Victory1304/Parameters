package com.parameters.prts.Service;

import com.parameters.prts.Model.NameEntity;
import com.parameters.prts.Repository.NameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NameService {
    private final NameRepository nameRepository;

    public NameService(NameRepository nameRepository) {
        this.nameRepository = nameRepository;
    }

    public List<NameEntity> findAll() {
        return nameRepository.findAll();
    }

    public Optional<NameEntity> findById(Long id) {
        return nameRepository.findById(id);
    }

    public NameEntity save(NameEntity name) {
        if (name.getId() != null && name.getId() > 0) {
            return nameRepository.save(name);
        }
        return nameRepository.save(new NameEntity(name));
    }

    public void delete(Long id) {
        nameRepository.deleteById(id);
    }
}
