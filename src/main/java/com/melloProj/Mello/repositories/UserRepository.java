package com.melloProj.Mello.repositories;

import com.melloProj.Mello.models.List;
import com.melloProj.Mello.models.MelloUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<MelloUser, Integer> {

}
