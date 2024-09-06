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
    @Column(name = "sender_id", insertable = false, updatable = false)
    private Long senderId;
    @ManyToOne
    @JoinColumn(name = "sender_id", insertable = false, updatable = false)
    private User sender;
    @Column(name = "receiver_id", insertable = false, updatable = false)
    private Long receiverId;
    @ManyToOne
    @JoinColumn(name = "receiver_id", insertable = false, updatable = false)
    private User receiver;
    private LocalDateTime timestamp;
    public Message(){}

    public Message(Long id, String content, User sender, User receiver, Long senderId, Long receiverId, LocalDateTime timestamp) {
        this.id = id;
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.senderId = senderId;
        this.receiverId = receiverId;
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

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
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
