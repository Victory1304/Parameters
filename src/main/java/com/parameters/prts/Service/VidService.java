package com.parameters.prts.Service;

import com.parameters.prts.Model.VidEntity;
import com.parameters.prts.Repository.VidRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class VidService extends CrudService<VidEntity, Integer> {
    private final VidRepository vidRepository;

    public VidService(VidRepository vidRepository) {
        this.vidRepository = vidRepository;
    }

    @Override
    protected VidRepository getRepository() {
        return vidRepository;
    }
}
