package com.melloProj.Mello.services;


import com.melloProj.Mello.models.FileEntity;
import com.melloProj.Mello.models.MelloUser;
import com.melloProj.Mello.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private UserRepository profileRepo;
    @Autowired
    private FileEntityService fileEntityService;
    @Autowired
    public ProfileAttributesRepo profileAttributesRepo;

    public MelloUser SignUp(String email, String password) {
        List<MelloUser> users = profileRepo.findByEmail(email);
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

            profile.setMail(email);
            profile.setPassword(password);

            FileEntity profilePic = fileEntityService.makeRandomAvatar();
            profile.setProfilePic(profilePic.getId());

            return profileRepo.save(profile);
        }
        return null;
    }
    public MelloUser SignIn(String email, String password){
        List<MelloUser> profiles = profileRepo.findByMailAndPassword(email, password);
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
        List<MelloUser> profiles = profileRepo.findByEmail(email);
        profiles.forEach(profile -> {
            if(!profile.getIsDeleted())
                profile.setIsDeleted(true);
            else profile.setIsDeleted(null);
            
            profileRepo.save(profile);
        });
    }
    public void delete(MelloUser user){
        List<MelloUser> profiles = profileRepo.findByMail(user.getMail());
        delete(user.getMail());
    }
    public MelloUser restore(String email, String password) {
        List<MelloUser> profiles = profileRepo.findByMailAndPassword(email, password);
        if(profiles.size() == 0) return null;
        for(MelloUser profile : profiles) if(profile.getIsDeleted()) {
            profile.setIsDeleted(false);
            return profileRepo.save(profile);
        }
        return null;
    }
    public MelloUser getProfileByID(Long id){
        Optional<MelloUser> profile =  profileRepo.findById(id);
        return profile.orElse(null);
        //throw new RuntimeException("Error: illegal profile request on profile " + id + "! Check for data in backups");
    }
    public MelloUser updateProfile(MelloUser profile){
        if(profile == null) return null;
        return profileRepo.save(profile);
    }

    public Boolean isDeleted(MelloUser user){
        return user.getIsDeleted();
    }
}
