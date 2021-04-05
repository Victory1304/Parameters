package com.parameters.prts.Service;

import com.parameters.prts.Model.GroupEntity;
import com.parameters.prts.Repository.GroupRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class GroupService extends CrudService<GroupEntity, Integer> {
    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    protected GroupRepository getRepository() {
        return groupRepository;
    }
}
