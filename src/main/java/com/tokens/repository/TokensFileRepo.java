package com.tokens.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tokens.model.TokensStore;

@Repository
public interface TokensFileRepo extends JpaRepository<TokensStore, Long> {

	// @Query("SELECT t.token_file_content FROM TokensStore t where t.externalFileId
	// = :externalFileId")

	@Query("SELECT new TokensStore(t.token_file_name, t.externalFileId, t.token_file_type,\r\n"
			+ "t.token_file_content) from TokensStore t where t.externalFileId = :externalFileId")
	TokensStore findByExternalId(@Param("externalFileId") String externalFileId);

}
