package com.melloProj.Mello.repositories.system;


import com.melloProj.Mello.models.user.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Long> {
    public List<Token> findByMelloUser(Long profile);

    public List<Token> findByToken(String token);
}
