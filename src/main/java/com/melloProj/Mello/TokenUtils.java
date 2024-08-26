package com.melloProj.Mello;


import com.melloProj.Mello.models.MelloUser;
import com.melloProj.Mello.models.Token;
import com.melloProj.Mello.repositories.TokenRepo;
import com.melloProj.Mello.repositories.UserRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class TokenUtils {
    private final TokenRepo tokenRepo;
    private final Integer dataOffset;
    private final SecureRandom secureRand;
    private final UserRepository userRepository;

    @Autowired
    public TokenUtils(TokenRepo tokenRepo, Environment env, UserRepository userRepository){
        this.tokenRepo = tokenRepo;
        dataOffset = Integer.parseInt(env.getProperty("app.scramble.dataOffset", "0"));
        secureRand = new SecureRandom(env.getProperty("app.scramble.seed", "7489509037124").getBytes(StandardCharsets.UTF_8));
        this.userRepository = userRepository;
    }
    
    public Token createToken(MelloUser user){
        Token res = new Token(); 

        res.setExpTime(OffsetDateTime.now().plusDays(dataOffset));
        res.setProfile(user.getId());

        List<String> parts = generatePayload(user);
        res.setPublicPart(parts.get(0));
        res.setPrivatePart(parts.get(1));
        return tokenRepo.save(res);
    }

    public Boolean verifyToken(String tokenPart){
        Token currToken = findToken(tokenPart);
        
        if(currToken.getExpTime().isBefore(OffsetDateTime.now())) {
            deleteToken(currToken);
            return false;
        }

        extendTokenLifetime(currToken);
        return true;
    }

    public MelloUser getProfileByToken(Token token){
        if(token == null) return null;
        return userRepository.findById(token.getProfile()).orElse(null);
    }

    public void extendTokenLifetime(Token token){
        token.setExpTime(OffsetDateTime.now().plusDays(dataOffset));
        tokenRepo.save(token);
    }

    public Token findToken(String tokenPart){
        List<Token> currToken = tokenRepo.findByPublicPart(tokenPart);
        if(currToken.isEmpty()) currToken = tokenRepo.findByPrivatePart(tokenPart);
        if(currToken.isEmpty()) return null;
        return currToken.get(0);
    }

    public void deleteToken(Token token){
        tokenRepo.delete(token);
    }
    
    private List<String> generatePayload(MelloUser profile){
        if(profile == null) return null;
        List<String> res = new ArrayList<>();

        String publicPart = profile.getId().toString() + "-" + longToBase64(OffsetDateTime.now().toEpochSecond());
        while(!tokenRepo.findByPublicPart(publicPart).isEmpty()) 
            publicPart = profile.getId().toString() + "-" + longToBase64(OffsetDateTime.now().toEpochSecond());
        
        res.add(strToBase64(publicPart));
        


        String privatePart = longToBase64(secureRand.nextLong());
        while(!tokenRepo.findByPrivatePart(privatePart).isEmpty()) 
            privatePart = longToBase64(secureRand.nextLong());

        res.add(privatePart);

        return res;
    }

    public static String longToBase64(long val){
        return bytesToBase64(longToBytes(val));
    }

    public static String strToBase64(String str){
        return bytesToBase64(str.getBytes(StandardCharsets.UTF_8));
    }

    public static String bytesToBase64(byte[] b){
        return Base64.encodeBase64String(b);
    }

    public static String base64ToStr(String base64){
        return new String(Base64.decodeBase64(base64), StandardCharsets.UTF_8);
    }

    public static String bytesToString(byte[] b){
        return new String(b, StandardCharsets.UTF_8);
    }

    public static byte[] longToBytes(long l) {
        byte[] result = new byte[8];
        for (int i = 7; i >= 0; i--) {
            result[i] = (byte)(l & 0xFF);
            l >>= 8;
        }
        return result;
    }
    
    public static long bytesToLong(final byte[] b) {
        long result = 0;
        for (int i = 0; i < 8; i++) {
            result <<= 8;
            result |= (b[i] & 0xFF);
        }
        return result;
    }

}
