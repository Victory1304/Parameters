package com.parameters.prts.Service;

import com.parameters.prts.Model.GroupEntity;
import com.parameters.prts.Repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<GroupEntity> findAll() {
        return groupRepository.findAll();
    }

    public Optional<GroupEntity> findById(Long id) {
        return groupRepository.findById(id);
    }

    public GroupEntity save(GroupEntity group) {
        if (group.getId() != null && group.getId() > 0) {
            return groupRepository.save(group);
        }
        return groupRepository.save(new GroupEntity(group));
    }

    public void delete(Long id) {
        groupRepository.deleteById(id);
    }
}
