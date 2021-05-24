package com.parameters.prts.Service;

import com.parameters.prts.Model.BasicNameIndicatorEntity;
import com.parameters.prts.Repository.BasicNameIndicatorRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class BasicNameIndicatorService extends CrudService<BasicNameIndicatorEntity, Integer> {
    private final BasicNameIndicatorRepository basicNameIndicatorRepository;


    public BasicNameIndicatorService(BasicNameIndicatorRepository basicNameIndicatorRepository) {
        this.basicNameIndicatorRepository = basicNameIndicatorRepository;
    }

    @Override
    protected BasicNameIndicatorRepository getRepository() {
        return basicNameIndicatorRepository;
    }
}
