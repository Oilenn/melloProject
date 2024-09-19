package com.melloProj.Mello.repositories.system;

import com.melloProj.Mello.models.user.MelloUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<MelloUser, Long> {
    List<MelloUser> findByMailAndPassword(String mail, Integer password);

    List<MelloUser> findByMail(String mail);
}
