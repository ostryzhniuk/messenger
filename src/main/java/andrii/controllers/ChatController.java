package andrii.controllers;

import andrii.dto.ChatDTO;
import andrii.dto.MessageDTO;
import andrii.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public String sendMessage(String message) throws Exception {
        return message;
    }

}
