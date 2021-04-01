package com.parameters.prts.Service;

import com.parameters.prts.Model.LiteratureEntity;
import com.parameters.prts.Repository.LiteratureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LiteratureService {
    private final LiteratureRepository literatureRepository;

    public LiteratureService(LiteratureRepository literatureRepository) {
        this.literatureRepository = literatureRepository;
    }

    public List<LiteratureEntity> findAll() {
        return literatureRepository.findAll();
    }

    public Optional<LiteratureEntity> findById(Long id) {
        return literatureRepository.findById(id);
    }

    public LiteratureEntity save(LiteratureEntity lit) {
        if (lit.getId() != null && lit.getId() > 0) {
            return literatureRepository.save(lit);
        }
        return literatureRepository.save(new LiteratureEntity(lit));
    }

    public void delete(Long id) {
        literatureRepository.deleteById(id);
    }
}
