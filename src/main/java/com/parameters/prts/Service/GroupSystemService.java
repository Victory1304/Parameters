package com.parameters.prts.Service;

import com.parameters.prts.Model.GroupSystemEntity;
import com.parameters.prts.Repository.GroupSystemRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class GroupSystemService extends CrudService<GroupSystemEntity, Integer> {
    private final GroupSystemRepository groupSystemRepository;

    public GroupSystemService(GroupSystemRepository groupSystemRepository) {
        this.groupSystemRepository = groupSystemRepository;
    }

    @Override
    protected GroupSystemRepository getRepository() {
        return groupSystemRepository;
    }
}
