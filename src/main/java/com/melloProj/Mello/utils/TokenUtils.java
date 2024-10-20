package com.melloProj.Mello.utils;


import com.melloProj.Mello.models.user.MelloUser;
import com.melloProj.Mello.models.user.Token;
import com.melloProj.Mello.repositories.system.TokenRepository;
import com.melloProj.Mello.repositories.system.UserRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TokenUtils {
    private final TokenRepository tokenRepository;
    private final Integer dataOffset;
    private final SecureRandom secureRand;
    private final UserRepository userRepository;

    @Autowired
    public TokenUtils(TokenRepository tokenRepository, Environment env, UserRepository userRepository){
        this.tokenRepository = tokenRepository;
        dataOffset = Integer.parseInt(env.getProperty("app.scramble.dataOffset", "0"));
        secureRand = new SecureRandom(env.getProperty("app.scramble.seed", "7489509037124").getBytes(StandardCharsets.UTF_8));
        this.userRepository = userRepository;
    }
    
    public Token createToken(MelloUser user){
        Token res = new Token();

        res.setExpTime(OffsetDateTime.now().plusDays(dataOffset));
        res.setMelloUser(user.getId());

        List<String> parts = generatePayload(user);
        res
                .setTokenPart(String.valueOf(parts));
        System.out.println(res.getTokenPart());
        return tokenRepository.save(res);
    }

    public Boolean verifyToken(String tokenPart){
        Token currToken = findToken(tokenPart);

        return currToken!=null;
    }

    public String normilizeToken(String tokenPart){
        char[] arrayToken = tokenPart.toCharArray();

        String newToken = "";
        for (int i = 1; i < arrayToken.length - 1; i++) {

            newToken += arrayToken[i];
        }

        return newToken;
    }

    public MelloUser getProfileByToken(Token token){
        if(token == null) return null;
        return userRepository.findById(token.getMelloUser()).orElse(null);
    }

    public void extendTokenLifetime(Token token){
        token.setExpTime(OffsetDateTime.now().plusDays(dataOffset));
        tokenRepository.save(token);
    }

    public Token findToken(String tokenPart){
        System.out.println(tokenPart + "Token Part");
        for(var d: tokenRepository.findAll()){
            System.out.println(d.getTokenPart() + "One of token");
        }
        Token token = tokenRepository.findByTokenPart(tokenPart).orElse(null);
        return token;
    }

    public void deleteToken(Token token){
        tokenRepository.delete(token);
    }
    
    private List<String> generatePayload(MelloUser profile){
        if(profile == null) return null;
        List<String> res = new ArrayList<>();

        String publicPart = profile.getId().toString() + "-" + longToBase64(OffsetDateTime.now().toEpochSecond());
        while(!tokenRepository.findByTokenPart(publicPart).isEmpty())
            publicPart = profile.getId().toString() + "-" + longToBase64(OffsetDateTime.now().toEpochSecond());
        
        res.add(strToBase64(publicPart));

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
