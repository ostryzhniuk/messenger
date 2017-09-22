package andrii.services;

import andrii.dao.ChatDAO;
import andrii.dao.MessageDAO;
import andrii.dto.ChatDTO;
import andrii.dto.MessageDTO;
import andrii.dto.MessageParametersDTO;
import andrii.entities.Chat;
import andrii.entities.Message;
import andrii.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Integer getChatId(Integer interlocutorId) {

        Integer currentUserId = userService.getCurrentUserId();
        Chat chat = chatDAO.getChat(currentUserId, interlocutorId);
        return chat == null ? -1 : chat.getId();
    }

    public List<ChatDTO> getChats() {

        Integer currentUserId = userService.getCurrentUserId();
        return convertToChatDTOList(chatDAO.getChats(currentUserId));
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
}
