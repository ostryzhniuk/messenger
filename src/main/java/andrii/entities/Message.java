package andrii.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @Column(nullable = false)
    private String body;

    @Column
    private LocalDateTime time;

    public static class MessageBuilder {

        private User user;
        private Chat chat;
        private String body;
        private LocalDateTime time;

        public MessageBuilder(User user, String body, Chat chat) {
            this.user = user;
            this.chat = chat;
            this.body = body;
            this.time = LocalDateTime.now();
        }

        public Message build(){
            return new Message(this);
        }

    }

    public Message() {
    }

    public Message(MessageBuilder messageBuilder) {
        this.user = messageBuilder.user;
        this.chat = messageBuilder.chat;
        this.body = messageBuilder.body;
        this.time = messageBuilder.time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
