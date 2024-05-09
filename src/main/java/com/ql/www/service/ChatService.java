package com.ql.www.service;

import com.ql.www.domain.entity.ChatHistory;
import com.ql.www.domain.vo.ChatHistoryInfo;
import com.ql.www.domain.vo.ConversationInfo;

import java.util.List;

/**
 * @author chocoh
 */
public interface ChatService {
    List<ChatHistoryInfo> listChatHistory(String userId, String targetId, Integer type);

    List<ConversationInfo> lisConversation(String userId);

    ChatHistoryInfo sendChatHistory(ChatHistory chatHistory);

    int deleteChatHistory(Long id);
}
