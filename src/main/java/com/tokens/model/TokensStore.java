package com.tokens.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tokens_store")
public class TokensStore {

	public TokensStore() {
	}

	@Id
	@GeneratedValue(generator = "token_generator")
	@SequenceGenerator(name = "token_generator", sequenceName = "token_sequence", initialValue = 1)
	private Long id;

	private String token_file_name;
	private String token_file_type;

	@Column(columnDefinition = "bytea")
	private byte[] token_file_content;

	public TokensStore(String token_file_name, String token_file_type, byte[] token_file_content) {
		this.token_file_name = token_file_name;
		this.token_file_type = token_file_type;
		this.token_file_content = token_file_content;
	}
}
