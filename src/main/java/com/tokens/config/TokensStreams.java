package com.tokens.config;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface TokensStreams {
	String OUTPUT = "tokens-out";

	@Output(OUTPUT)
	MessageChannel outboundTokens();
}