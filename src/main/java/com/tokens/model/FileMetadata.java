package com.tokens.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FileMetadata {
	private String fileName;
	private String fileCreationTime;
	private int noOflinesProcessed;
	private Map<String, String> tokenMapping;
}
