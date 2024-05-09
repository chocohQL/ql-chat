package com.ql.www.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ql.www.domain.entity.ChatHistory;
import com.ql.www.domain.entity.Conversation;
import com.ql.www.domain.model.Message;
import com.ql.www.domain.vo.ChatHistoryInfo;
import com.ql.www.domain.vo.ConversationInfo;
import com.ql.www.domain.vo.UserInfo;
import com.ql.www.mapper.ChatHistoryMapper;
import com.ql.www.mapper.ConversationMapper;
import com.ql.www.service.ChatService;
import com.ql.www.service.UserService;
import com.ql.www.service.socket.WebSocketService;
import com.ql.www.utils.TimeUtil;
import com.ql.www.common.WebSocketMsgType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chocoh
 */
@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatHistoryMapper chatHistoryMapper;
    @Autowired
    private ConversationMapper conversationMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private WebSocketService webSocketService;

    @Override
    public List<ChatHistoryInfo> listChatHistory(String userId, String targetId, Integer type) {
        List<ChatHistoryInfo> chatHistoryInfos = new ArrayList<>();
        if (type == 0) {
            // 获取自己发送的聊天记录
            chatHistoryInfos.addAll(getUserChatHistory(userId, targetId));
            // 获取对方发送的聊天记录
            chatHistoryInfos.addAll(getUserChatHistory(targetId, userId));
        }
        // 根据时间排序
        return chatHistoryInfos.stream()
                .sorted(Comparator.comparing(ChatHistoryInfo::getCreateTime))
                .collect(Collectors.toList());
    }

    @Override
    public List<ConversationInfo> lisConversation(String userId) {
        List<ConversationInfo> conversationInfos = new ArrayList<>();
        conversationMapper.selectList(new LambdaQueryWrapper<Conversation>().eq(Conversation::getUserId, userId))
                .forEach(conversation -> {
                    if (conversation.getType() == 0) {
                        UserInfo userInfo = userService.getUserInfo(userService.getById(conversation.getTargetId()));
                        ConversationInfo conversationInfo = ConversationInfo.builder()
                                .name(userInfo.getUsername())
                                .avatar(userInfo.getAvatar())
                                .previewTimeFormat(TimeUtil.formatConversationTime(conversation.getPreviewTime()))
                                .build();
                        BeanUtils.copyProperties(conversation, conversationInfo);
                        conversationInfos.add(conversationInfo);
                    }
                });
        // 根据时间排序
        return conversationInfos.stream()
                .sorted(Comparator.comparing(ConversationInfo::getPreviewTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public ChatHistoryInfo sendChatHistory(ChatHistory chatHistory) {
        // 新增聊天记录
        chatHistory.setCreateTime(LocalDateTime.now());
        chatHistoryMapper.insert(chatHistory);
        ChatHistoryInfo chatHistoryInfo = getChatHistoryInfo(chatHistory, userService.getUserInfo(userService.getById(chatHistory.getUserId())));
        if (chatHistory.getType() == 0) {
            // 更新会话
            updateFriendConversation(chatHistory);
            // 发送websocket消息
            webSocketService.sendMessage(Message.getInstance(WebSocketMsgType.SEND_CHAT_HISTORY, chatHistoryInfo), chatHistory.getTargetId());
        }
        // 返回记录
        return chatHistoryInfo;
    }

    @Override
    public int deleteChatHistory(Long id) {
        ChatHistory chatHistory = chatHistoryMapper.selectById(id);
        if (chatHistory.getType() == 0) {
            if (chatHistory.getUserId().equals(StpUtil.getLoginIdAsLong())) {
                // 更新聊天记录
                chatHistory.setContent(null);
                chatHistory.setType(1);
                chatHistoryMapper.updateById(chatHistory);
                // 更新会话
                updateFriendConversation(chatHistory);
                // 发送websocket消息
                webSocketService.sendMessage(Message.getInstance(WebSocketMsgType.DELETE_CHAT_HISTORY, chatHistory.getId()), chatHistory.getTargetId());
                return 1;
            }
        }
        return 0;
    }

    private void updateFriendConversation(ChatHistory chatHistory) {
        // 更新对象为本用户或者目标用户的会话
        List<Conversation> conversations = conversationMapper.selectList(new LambdaQueryWrapper<Conversation>()
                .eq(Conversation::getType, 0)
                .and(q -> q
                        .eq(Conversation::getUserId, chatHistory.getUserId())
                        .eq(Conversation::getTargetId, chatHistory.getTargetId())
                        .or()
                        .eq(Conversation::getUserId, chatHistory.getTargetId())
                        .eq(Conversation::getTargetId, chatHistory.getUserId())));
        conversations.forEach(i -> {
            i.setPreviewMessage(chatHistory.getContent());
            i.setPreviewTime(chatHistory.getCreateTime());
            i.setPreviewType(chatHistory.getType());
            conversationMapper.updateById(i);
        });
    }

    private List<ChatHistoryInfo> getUserChatHistory(String userId, String targetId) {
        List<ChatHistoryInfo> chatHistoryInfos = new ArrayList<>();
        UserInfo userInfo = userService.getUserInfo(userService.getById(userId));
        chatHistoryMapper.selectList(new LambdaQueryWrapper<ChatHistory>()
                        .eq(ChatHistory::getUserId, userId)
                        .eq(ChatHistory::getTargetId, targetId))
                .forEach(chatHistory -> chatHistoryInfos.add(getChatHistoryInfo(chatHistory, userInfo)));
        return chatHistoryInfos;
    }

    private ChatHistoryInfo getChatHistoryInfo(ChatHistory chatHistory, UserInfo userInfo) {
        ChatHistoryInfo chatHistoryInfo = new ChatHistoryInfo();
        BeanUtils.copyProperties(chatHistory, chatHistoryInfo);
        chatHistoryInfo.setUserInfo(userInfo);
        return chatHistoryInfo;
    }
}
