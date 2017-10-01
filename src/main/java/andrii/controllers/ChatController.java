package andrii.controllers;

import andrii.dto.ChatDTO;
import andrii.dto.MessageDTO;
import andrii.dto.MessageParametersDTO;
import andrii.dto.UserDTO;
import andrii.services.ChatService;
import andrii.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping("/chat/id")
    public Integer getChatId(@RequestParam(value= "interlocutor") Integer interlocutorId) {
        return chatService.getChatId(interlocutorId);
    }

    @GetMapping("/chats")
    public List<ChatDTO> getUserChats() {
        return chatService.getChats();
    }

    @GetMapping("/messages/{chatId}")
    public List<MessageDTO> getMessages(@PathVariable("chatId") Integer chatId) {
        return chatService.getMessages(chatId);
    }

    @PutMapping("/message/last")
    public void updateChatLastVisit(@RequestBody Integer messageId) {
        chatService.updateLastReadMessage(messageId);
    }

    @MessageMapping("/message")
    public void sendMessage(MessageParametersDTO message,
                                  @AuthenticationPrincipal Authentication authentication) {

        MessageDTO messageDTO = chatService.saveMessage(message, authentication);
        UserDTO userDTO = userService.getChatParticipant(
                message.getChatId(),
                authentication.getName(),
                false);

        messagingTemplate
                .convertAndSendToUser(
                        userDTO.getEmail(),
                        "/queue/reply",
                        messageDTO);

        messagingTemplate
                .convertAndSendToUser(
                    authentication.getName(),
                    "/queue/return",
                    messageDTO);
    }

    @GetMapping("/unreadMessages/count/{chatId}")
    public Long getCountUnreadMessages(@PathVariable("chatId") Integer chatId) {
        return chatService.getCountUnreadMessages(chatId);
    }

}
