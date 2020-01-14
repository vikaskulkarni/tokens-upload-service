package com.tokens.service;

import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import com.tokens.config.TokensStreams;
import com.tokens.model.EventToken;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TokensUploadService {
	private final TokensStreams tokensStreams;

	public TokensUploadService(TokensStreams tokensStreams) {
		this.tokensStreams = tokensStreams;
	}

	public void sendFileUploadEvent(final EventToken token) {
		log.info("Sending file upload started event {}", token);
		MessageChannel messageChannel = tokensStreams.outboundTokens();
		messageChannel.send(MessageBuilder.withPayload(token)
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());
	}

}
