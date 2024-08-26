package com.melloProj.Mello.repositories;

import com.melloProj.Mello.models.MelloUser;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<MelloUser, Long> {
    List<MelloUser> findByMailAndPassword(String mail, String password);

    List<MelloUser> findByMail(String mail);
}
