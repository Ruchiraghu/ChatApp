package org.com.chatapp.service;

import org.com.chatapp.entities.GroupChat;
import org.com.chatapp.dao.GroupDao;

import java.util.List;

public class GroupService {
    private final GroupDao groupDao;

    public GroupService(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    public void createGroup(GroupChat groupChat) {
        groupDao.addGroup(groupChat);
    }

    public GroupChat getGroup(Long id)  {
        return groupDao.getGroup(id);
    }

    public void addUsersToGroup(Long groupId, List<Long> userIds) {
        groupDao.addUsersToGroup(groupId, userIds);
    }
}
