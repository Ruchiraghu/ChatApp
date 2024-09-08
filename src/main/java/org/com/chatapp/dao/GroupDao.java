package org.com.chatapp.dao;
import org.com.chatapp.entities.GroupChat;

import java.util.List;

public interface GroupDao {
    void addGroup(GroupChat group);
    GroupChat getGroup(int id);
    GroupChat getGroupByName(String name);
    List<GroupChat> getAllGroups();
}
