package com.parameters.prts.Service;

import com.parameters.prts.Model.TypeEntity;
import com.parameters.prts.Repository.TypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeService {
    private final TypeRepository typeRepository;

    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public List<TypeEntity> findAll() {
        return typeRepository.findAll();
    }

    public Optional<TypeEntity> findById(Long id) {
        return typeRepository.findById(id);
    }

    public TypeEntity save(TypeEntity type) {
        if (type.getId() != null && type.getId() > 0) {
            return typeRepository.save(type);
        }
        return typeRepository.save(new TypeEntity(type));
    }

    public void delete(Long id) {
        typeRepository.deleteById(id);
    }
}
