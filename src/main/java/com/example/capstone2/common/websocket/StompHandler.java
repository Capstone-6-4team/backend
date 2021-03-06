package com.example.capstone2.common.websocket;

import com.example.capstone2.chat.entity.ChatMessage;
import com.example.capstone2.chat.service.ChatMessageService;
import com.example.capstone2.common.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
@Slf4j
public class StompHandler implements ChannelInterceptor {

    private final JwtService jwtService;
    private final ChatMessageService chatMessageService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String jwt = accessor.getFirstNativeHeader("Authorization");
        System.out.println("session id : " + accessor.getSessionId());

        System.out.println("accessor.getDestination() = " + accessor.getDestination());
        if(StringUtils.hasText(jwt) && jwtService.validateAccessToken(jwt)) {
            Authentication authentication = jwtService.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            accessor.setUser(authentication);

            String destination = accessor.getDestination();
            if(accessor.getCommand() == StompCommand.SUBSCRIBE && destination.contains("private")) {
                String[] split = destination.split("/");
                Long roomId = Long.parseLong(split[split.length - 1]);
                if(!chatMessageService.hasReservation(authentication.getName(), roomId)) {
                    throw new AccessDeniedException("???????????? ????????? ????????? ????????????.");
                };
            }

        } else {
            log.info("[StompHandler] ????????? JWT ????????? ????????????.");
            throw new AccessDeniedException("???????????? ?????? ???????????????");
        }
        return MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());
    }
}
