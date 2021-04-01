package com.parameters.prts.Service;

import com.parameters.prts.Model.ParameterEntity;
import com.parameters.prts.Repository.ParameterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParameterService {
    private final ParameterRepository parameterRepository;

    public ParameterService(ParameterRepository parameterRepository) {
        this.parameterRepository = parameterRepository;
    }

    public List<ParameterEntity> findAll() {
        return parameterRepository.findAll();
    }

    public Optional<ParameterEntity> findById(Long id) {
        return parameterRepository.findById(id);
    }

    public ParameterEntity save(ParameterEntity parameter) {
        if (parameter.getId() != null && parameter.getId() > 0) {
            return parameterRepository.save(parameter);
        }
        return parameterRepository.save(new ParameterEntity(parameter));
    }

    public void delete(Long id) {
        parameterRepository.deleteById(id);
    }
}
