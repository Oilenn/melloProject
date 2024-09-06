package com.melloProj.Mello.services.user;


import com.melloProj.Mello.TokenUtils;
import com.melloProj.Mello.models.user.MelloUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    @Autowired
    public TokenUtils tokenUtils;
    
    public MelloUser getUserByToken(String token){
        return tokenUtils.getProfileByToken(tokenUtils.findToken(token));
    }
}