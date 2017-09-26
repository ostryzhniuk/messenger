package andrii.services;

import andrii.dao.ChatDAO;
import andrii.dao.MessageDAO;
import andrii.dao.UserChatDAO;
import andrii.dto.ChatDTO;
import andrii.dto.MessageDTO;
import andrii.dto.MessageParametersDTO;
import andrii.entities.Chat;
import andrii.entities.Message;
import andrii.entities.User;
import andrii.entities.UserChat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    @Autowired
    private ChatDAO chatDAO;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageDAO messageDAO;

    @Autowired
    private UserChatDAO userChatDAO;

    @Transactional
    public Integer getChatId(Integer interlocutorId) {

        Integer currentUserId = userService.getCurrentUserId();
        Chat chat = chatDAO.getChat(currentUserId, interlocutorId);
        return chat == null ? -1 : chat.getId();
    }

    @Transactional
    public List<ChatDTO> getChats() {

        Integer currentUserId = userService.getCurrentUserId();
        List<ChatDTO> chatDTOList = convertToChatDTOList(chatDAO.getChats(currentUserId));
        chatDTOList.forEach(chatDTO ->
            chatDTO.setUnreadMessages(
                            messageDAO.getUnreadMessages(
                                    chatDTO.getId(),
                                    userService.getCurrentUserId()))
        );
        return chatDTOList;
    }

    public List<ChatDTO> convertToChatDTOList(List<Chat> chatList) {
        return chatList
                .stream()
                .map(chat -> ChatDTO.convertToDTO(chat))
                .collect(Collectors.toList());
    }

    public List<MessageDTO> convertToMessageDTOList(List<Message> messageList) {
        return messageList
                .stream()
                .map(message -> MessageDTO.convertToDTO(message))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<MessageDTO> getMessages(Integer chatId) {
        return convertToMessageDTOList(messageDAO.getMessages(chatId));
    }

    @Transactional
    public MessageDTO saveMessage(MessageParametersDTO messageParameters, Authentication authentication) {

        Integer currentUserId = userService.getUserId(authentication.getName());
        User user = userService.getUser(currentUserId);

        Message message = new Message
                .MessageBuilder(
                    user,
                    messageParameters.getBody(),
                    chatDAO.getChat(messageParameters.getChatId()))
                .build();

        messageDAO.save(message);

        return MessageDTO.convertToDTO(message);
    }

    @Transactional
    public void updateLastChatVisit(Integer chatId) {

        LocalDateTime dateTime = LocalDateTime.now();
        UserChat userChat = userChatDAO
                .getUserChat(
                        chatId,
                        userService.getCurrentUserId());

        userChat.setLastVisit(dateTime);
        userChatDAO.update(userChat);
    }
}
