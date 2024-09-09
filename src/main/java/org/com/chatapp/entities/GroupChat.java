package org.com.chatapp.entities;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "chat_group")
public class GroupChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_groups",  // Join table name
            joinColumns = @JoinColumn(name = "groupId"),  // Matches column name in GroupChat entity
            inverseJoinColumns = @JoinColumn(name = "user_id")  // Matches column name in User entity
    )
    private Set<User> users;

    // Constructors, getters, and setters
    public GroupChat() { }

    public GroupChat(Long id, String name, Set<User> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

}
