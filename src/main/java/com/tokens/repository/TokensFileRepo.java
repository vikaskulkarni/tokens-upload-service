package com.tokens.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tokens.model.TokensStore;

@Repository
public interface TokensFileRepo extends JpaRepository<TokensStore, Long> {

}
