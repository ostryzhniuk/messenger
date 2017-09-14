package andrii.services;

import andrii.dao.ChatDAO;
import andrii.entities.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
