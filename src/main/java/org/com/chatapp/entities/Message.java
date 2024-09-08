package org.com.chatapp.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)  // Remove insertable = false, updatable = false
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)  // Remove insertable = false, updatable = false
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupChat groupChat;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Column(columnDefinition = "TEXT")
    private String message;
    private LocalDateTime timestamp;

    public Message() {}

    public Message(Long id,String content, User sender, User receiver, LocalDateTime timestamp) {
        this.id = id;
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public GroupChat getGroupChat() {
        return groupChat;
    }

    public void setGroupChat(GroupChat groupChat) {
        this.groupChat = groupChat;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", timestamp=" + timestamp +
                '}';
    }
}
