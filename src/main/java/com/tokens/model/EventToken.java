package com.tokens.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter @Setter @ToString @Builder
public class EventToken {
    private long timestamp;
    private long fileId;
}
