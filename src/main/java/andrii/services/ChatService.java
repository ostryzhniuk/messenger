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
import org.springframework.stereotype.Service;

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

    public Integer getChatId(Integer interlocutorId) {

        Integer currentUserId = userService.getCurrentUserId();
        Chat chat = chatDAO.getChat(currentUserId, interlocutorId);
        return chat == null ? null : chat.getId();
    }

    public List<ChatDTO> getChats() {

        Integer currentUserId = userService.getCurrentUserId();
        List<ChatDTO> chatDTOList = convertToChatDTOList(chatDAO.getChats(currentUserId));
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

    public List<MessageDTO> getMessages(Integer chatId) {
        return convertToMessageDTOList(messageDAO.getMessages(chatId));
    }

    public MessageDTO saveMessage(MessageParametersDTO messageParameters) {
        Message message = new Message();
//        Integer currentUserId = userService.getCurrentUserId();
        User user = userService.getUserById(4, false).convertToEntity();

        message.setUser(user);
        message.setChat(chatDAO.getChat(messageParameters.getChatId()));
        message.setBody(messageParameters.getBody());
        message.setTime(LocalDateTime.now());

        messageDAO.save(message);

        return MessageDTO.convertToDTO(message);
    }
}
