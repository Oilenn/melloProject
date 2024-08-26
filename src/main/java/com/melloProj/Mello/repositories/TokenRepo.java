package com.melloProj.Mello.repositories;


import com.melloProj.Mello.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepo extends JpaRepository<Token, Long> {
    public List<Token> findByPublicPart(String publicPart);
    public List<Token> findByPrivatePart(String privatePart);
    public List<Token> findByProfile(Long profile);
}
