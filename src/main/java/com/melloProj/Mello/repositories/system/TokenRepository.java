package com.melloProj.Mello.repositories.system;


import com.melloProj.Mello.models.user.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    public List<Token> findByMelloUser(Long profile);

    public Optional<Token> findByTokenPart(String token);
}
