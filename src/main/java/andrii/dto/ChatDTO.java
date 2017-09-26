package andrii.dto;

import andrii.entities.Chat;
import org.modelmapper.ModelMapper;

public class ChatDTO {

    private Integer id;
    private String name;
    private Long unreadMessages;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUnreadMessages() {
        return unreadMessages;
    }

    public void setUnreadMessages(Long unreadMessages) {
        this.unreadMessages = unreadMessages;
    }

    public Chat convertToEntity() {
        return new ModelMapper().map(this, Chat.class);
    }

    public static ChatDTO convertToDTO(Chat chat) {
        return new ModelMapper().map(chat, ChatDTO.class);
    }
}
