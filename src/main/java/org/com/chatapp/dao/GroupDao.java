package org.com.chatapp.dao;
import org.com.chatapp.entities.GroupChat;

import java.util.List;

public interface GroupDao {
    void addGroup(GroupChat groupChat);
    GroupChat getGroup(Long id);
    GroupChat getGroupByName(String name);
    List<GroupChat> getAllGroups();
    void updateGroup(GroupChat groupChat);
    void addUsersToGroup(Long groupId, List<Long> userIds);
}
