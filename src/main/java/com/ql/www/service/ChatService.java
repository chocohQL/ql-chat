package com.ql.www.service;

import com.ql.www.domain.entity.ChatHistory;
import com.ql.www.domain.vo.ChatHistoryInfo;
import com.ql.www.domain.vo.ConversationInfo;

import java.util.List;

/**
 * @author chocoh
 */
public interface ChatService {
    /**
     * 获取聊天记录集合
     * @param userId 用户id
     * @param targetId 目标用户id
     * @param type 会话类型
     */
    List<ChatHistoryInfo> listChatHistory(String userId, String targetId, Integer type);

    /**
     * 获取会话集合
     * @param userId 用户id
     */
    List<ConversationInfo> listConversation(String userId);

    /**
     * 发送聊天记录
     * @param chatHistory 聊天记录
     */
    ChatHistoryInfo sendChatHistory(ChatHistory chatHistory);

    /**
     * 删除聊天记录
     * @param id 聊天记录id
     */
    int deleteChatHistory(Long id);

    /**
     * 创建会话
     * @param userId 用户id
     * @param targetId 目标用户id
     * @param type 会话类型
     */
    void createConversation(Long userId, Long targetId, Integer type);
}
