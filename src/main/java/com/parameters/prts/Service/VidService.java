package com.parameters.prts.Service;

import com.parameters.prts.Model.VidEntity;
import com.parameters.prts.Repository.VidRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VidService {
    private final VidRepository vidRepository;

    public VidService(VidRepository vidRepository) {
        this.vidRepository = vidRepository;
    }

    public List<VidEntity> findAll() {
        return vidRepository.findAll();
    }

    public Optional<VidEntity> findById(Long id) {
        return vidRepository.findById(id);
    }

    public VidEntity save(VidEntity vid) {
        if (vid.getId() != null && vid.getId() > 0) {
            return vidRepository.save(vid);
        }
        return vidRepository.save(new VidEntity(vid));
    }

    public void delete(Long id) {
        vidRepository.deleteById(id);
    }
}
