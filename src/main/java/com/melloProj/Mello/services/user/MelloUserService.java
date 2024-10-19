package com.melloProj.Mello.services.user;


import com.melloProj.Mello.models.user.MelloUser;
import com.melloProj.Mello.repositories.system.UserRepository;
import com.melloProj.Mello.services.FileEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MelloUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileEntityService fileEntityService;

    public MelloUser SignUp(String username, String email, String password) {
        List<MelloUser> users = userRepository.findByMail(email);
        Boolean isExist = false;
        for (MelloUser user:
             users) {
            if (user.getIsDeleted() == true) {
                isExist = true;
                break;
            }
        }
        if(!isExist){
            MelloUser profile = new MelloUser();

            int hashedPassword = password.hashCode();

            profile.setNickname(username);
            profile.setMail(email);
            profile.setPassword(hashedPassword);

            System.out.println(profile.getMail());
            return userRepository.save(profile);
        }
        return null;
    }
    public MelloUser SignIn(String email, String password){
        int hashedPassword = password.hashCode();

        List<MelloUser> profiles = userRepository.findByMailAndPassword(email, hashedPassword);
        if(profiles.size() == 0)return null; // account not found
        MelloUser latestProfile = null;
        for(MelloUser profile : profiles) {
            if(profile.getIsDeleted() == false) {
                latestProfile = profile;
                break;
            }
            if(profile.getIsDeleted() == true) latestProfile = profile;
        }
        return latestProfile; //if account gets deleted we'll still return user and will parse state on client
    }
    public void delete(String email){
        List<MelloUser> profiles = userRepository.findByMail(email);
        profiles.forEach(profile -> {
            if(!profile.getIsDeleted())
                profile.setIsDeleted(true);
            else profile.setIsDeleted(null);
            
            userRepository.save(profile);
        });
    }
    public void delete(MelloUser user){
        List<MelloUser> profiles = userRepository.findByMail(user.getMail());
        delete(user.getMail());
    }
    public MelloUser restore(String email, String password) {
        int hashedPassword = password.hashCode();

        List<MelloUser> profiles = userRepository.findByMailAndPassword(email, hashedPassword);
        if(profiles.size() == 0) return null;
        for(MelloUser profile : profiles) if(profile.getIsDeleted()) {
            profile.setIsDeleted(false);
            return userRepository.save(profile);
        }
        return null;
    }
    public MelloUser getProfileByID(Long id){
        Optional<MelloUser> profile =  userRepository.findById(id);
        return profile.orElse(null);
        //throw new RuntimeException("Error: illegal profile request on profile " + id + "! Check for data in backups");
    }
    public MelloUser updateProfile(MelloUser profile){
        if(profile == null) return null;
        return userRepository.save(profile);
    }

    public Boolean isDeleted(MelloUser user){
        return user.getIsDeleted();
    }
}
