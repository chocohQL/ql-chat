package com.ql.www.controller;

import com.ql.www.domain.entity.ChatHistory;
import com.ql.www.domain.model.Response;
import com.ql.www.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author chocoh
 */
@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @GetMapping("/listChatHistory")
    public Response listChatHistory(@RequestParam("userId") String userId,
                                    @RequestParam("targetId") String targetId,
                                    @RequestParam("type") Integer type) {
        return Response.success(chatService.listChatHistory(userId, targetId, type));
    }

    @GetMapping("/lisConversation")
    public Response lisConversation(@RequestParam("userId") String userId) {
        return Response.success(chatService.lisConversation(userId));
    }

    @PostMapping("/sendChatHistory")
    public Response sendChatHistory(@RequestBody ChatHistory chatHistory) {
        return Response.success(chatService.sendChatHistory(chatHistory));
    }

    @PostMapping("/deleteChatHistory")
    public Response deleteChatHistory(@RequestParam("id") Long id) {
        return Response.success(chatService.deleteChatHistory(id));
    }
}
