package andrii.dto;

import andrii.entities.Chat;
import andrii.entities.Message;
import andrii.entities.User;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

public class MessageDTO {

    private Integer id;
    private UserDTO user;
    private ChatDTO chat;
    private String body;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public ChatDTO getChat() {
        return chat;
    }

    public void setChat(ChatDTO chat) {
        this.chat = chat;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Message convertToEntity() {
        return new ModelMapper().map(this, Message.class);
    }

    public static MessageDTO convertToDTO(Message message) {
        return new ModelMapper().map(message, MessageDTO.class);
    }

}
