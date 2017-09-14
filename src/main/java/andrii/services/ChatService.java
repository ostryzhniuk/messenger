package andrii.services;

import andrii.dao.ChatDAO;
import andrii.dto.ChatDTO;
import andrii.entities.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    @Autowired
    private ChatDAO chatDAO;

    @Autowired
    private UserService userService;

    public Integer getChatId(Integer interlocutorId) {

        Integer currentUserId = userService.getCurrentUser().getId();
        Chat chat = chatDAO.getChat(currentUserId, interlocutorId);
        return chat == null ? null : chat.getId();
    }

    public List<ChatDTO> getChats() {

        Integer currentUserId = userService.getCurrentUser().getId();
        List<ChatDTO> chatDTOList = convertToDTOList(chatDAO.getChats(currentUserId));
        return chatDTOList;
    }

    public List<ChatDTO> convertToDTOList(List<Chat> chatList) {
        return chatList
                .stream()
                .map(chat -> ChatDTO.convertToDTO(chat))
                .collect(Collectors.toList());
    }

}
