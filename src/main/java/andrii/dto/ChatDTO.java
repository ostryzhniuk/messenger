package andrii.dto;

import andrii.entities.Chat;
import org.modelmapper.ModelMapper;

public class ChatDTO {

    private Integer id;
    private Long unreadMessages;
    private UserDTO user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUnreadMessages() {
        return unreadMessages;
    }

    public void setUnreadMessages(Long unreadMessages) {
        this.unreadMessages = unreadMessages;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Chat convertToEntity() {
        return new ModelMapper().map(this, Chat.class);
    }

    public static ChatDTO convertToDTO(Chat chat) {
        return new ModelMapper().map(chat, ChatDTO.class);
    }
}
