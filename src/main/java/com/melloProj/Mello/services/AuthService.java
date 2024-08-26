package com.melloProj.Mello.services;

//@Service
public class AuthService {
    /*@Autowired
    private ProfileRepo profileRepo;
    @Autowired
    private ProfileAttributesRepo profileAttributesRepo;
    @Autowired
    private ChatRepo chatRepo;

    public Long canAccessContent(Profile user, String contentType, Long contentID){
        switch (contentType){
            case "JOB_TIER":
            case "FREELANCE_SUBCATEGORY":
            case "FREELANCE_CATEGORY":
            case "REFERENCE_LIST":
                return contentID;

            case "MESSAGE_CONTENT":
            case "POST_ACTIVITY":
            case "FILE":
                if(!contentInfo.getOnDeletion()) return contentID;
            case "MESSAGE":
            case "CHAT":
                Optional<Chat> chatCheck = chatRepo.findById(contentInfo.getContentID());
                if(chatCheck.isEmpty()) return -2L; // //illegal argument
                    //throw new RuntimeException("Error: illegal access attempt, check backup data for content", new IllegalArgumentException("Profile: " + user.getId() + " content type: "+ contentType + " contentID: " + contentID));
                Chat chat = chatCheck.get();

                if(!Objects.equals(chat.getProfile1().getId(), user.getId()) && !Objects.equals(chat.getProfile2().getId(), user.getId()) )
                    return -3L; // user has no access to
                    //throw new RuntimeException("Error: illegal access attempt, probably, should take some action", new IllegalArgumentException("Profile: " + user.getId() + " content type: "+ contentType + " contentID: " + contentID));
                if(isUserBlocked(creator, user)) return -4L; //user is blocked
                return contentID;

            case "FREELANCE_OFFER":
            case "POST":
            case "PROFILE": if(postCheck(user, contentInfo)) return contentID;
            default: if(defaultCheck(user)) return contentID;
        }

        return -1L;
    }
    public Boolean isUserBlocked(Profile user1, Profile user2){
        ProfileAttributes user1Info = profileAttributesRepo.findProfileAttributesByOnProfileAndForProfile(user1, user2).get(0);
        ProfileAttributes user2Info = profileAttributesRepo.findProfileAttributesByOnProfileAndForProfile(user2, user1).get(0);
        return user1Info.getIsBlocked() || user2Info.getIsBlocked();
    }
    public Boolean canCreateContent(Profile user, String contentType, Long onContentID){
        String role = user.getProfileRole();
        if(role.equals("GUEST") || !Objects.equals(contentType, "PROFILE")) return false;
        switch (contentType){
            case "JOB_TIER":
            case "FREELANCE_OFFER":
            case "REFERENCE_LIST":
            case "FILE":
            case "PROFILE":
            case "MESSAGE_CONTENT":
            case "POST":
                return true;

            case "CHAT":
            case "MESSAGE":
            case "POST_ACTIVITY":
                if(canAccessContent(user, contentType, onContentID) >= 0) return true;

            case "FREELANCE_SUBCATEGORY":
            case "FREELANCE_CATEGORY":

            default: onlyAdmin(role);
        }
        return false;
    }
    public Boolean canCreateContent(Profile user, String contentType){
        return canCreateContent(user, contentType, -1L);
    }

    public Boolean onlyAdmin(String role){
        return role.equals("ADMIN");
    }
    public Long canAccessContent(Long profileID, String contentType, Long contentID){
        Profile user;
        if(profileID < 0) {
            user = getGuestProfile();

        }
            else{
            Optional<Profile> profileCheck = profileRepo.findById(profileID);
            if(profileCheck.isEmpty()) return -1L;
            user = profileCheck.get();
        }
        return canAccessContent(user, contentType,contentID);
    }

    private boolean defaultCheck(Profile userProfile){
        String role = userProfile.getProfileRole();
        return Objects.equals(role, "MODERATOR") || Objects.equals(role, "ADMIN");
    }
    private boolean postCheck(Profile userProfile, SystemInfo contentSystemInfo){

        String userProfileRole = userProfile.getProfileRole();

        if(Objects.equals(userProfileRole, "MODERATOR") &&
                Objects.equals(userProfileRole, "ADMIN")
        ) return true;

        if(Objects.equals(contentSystemInfo.getCreator(), userProfile) && !contentSystemInfo.getOnDeletion()) return true;

        if(
                (Objects.equals(contentSystemInfo.getAccessMode(), "PUBLIC")
                        || Objects.equals(contentSystemInfo.getAccessMode(), "UNLISTED")) &&
                        !contentSystemInfo.getOnDeletion()
        ) return true;

        Profile creatorProfile = contentSystemInfo.getCreator();
        ProfileAttributes userAndCreatorRelations = profileAttributesRepo.findProfileAttributesByOnProfileAndForProfile(userProfile, creatorProfile).get(0);
        ProfileAttributes creatorAndUserRelations = profileAttributesRepo.findProfileAttributesByOnProfileAndForProfile(creatorProfile, userProfile).get(0);

        return !creatorAndUserRelations.getIsBlocked() && userAndCreatorRelations.getIsFollowing() &&
                userProfileRole.equals("FOLLOWERS_ONLY");
    } */
}
