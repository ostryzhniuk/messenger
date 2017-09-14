package andrii.controllers;

import andrii.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/chat/id")
    public Integer getChatId(@RequestParam(value= "interlocutor") Integer interlocutorId) {
        return chatService.getChatId(interlocutorId);
    }

}
