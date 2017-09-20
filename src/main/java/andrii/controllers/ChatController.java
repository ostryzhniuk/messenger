package andrii.controllers;

import andrii.dto.ChatDTO;
import andrii.dto.MessageDTO;
import andrii.dto.MessageParametersDTO;
import andrii.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ChatController {

    @Autowired
    private ChatService chatService;

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

    @MessageMapping("/message")
    @SendTo("/topic/greetings")
    public MessageDTO sendMessage(MessageParametersDTO message,
                                  @AuthenticationPrincipal Authentication authentication) {
        return chatService.saveMessage(message, authentication);
    }

}
