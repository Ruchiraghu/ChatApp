package org.com.chatapp.service;

import org.com.chatapp.entities.GroupChat;
import org.com.chatapp.dao.GroupDao;

import java.util.List;

public class GroupService {
    private GroupDao groupDao;

    public GroupService(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    public void createGroup(GroupChat group) {
        groupDao.addGroup(group);
    }

    public GroupChat getGroup(int id) {
        return groupDao.getGroup(id);
    }

    public GroupChat getGroupByName(String name) {
        return groupDao.getGroupByName(name);
    }

    public List<GroupChat> getAllGroups() {
        return groupDao.getAllGroups();
    }
}
