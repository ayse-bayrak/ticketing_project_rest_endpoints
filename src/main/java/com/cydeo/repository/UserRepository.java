package com.cydeo.repository;

import com.cydeo.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findAllByIsDeletedOrderByFirstNameDesc(Boolean deleted);

    User findByUserNameAndIsDeleted(String username, Boolean deleted);


    @Transactional
    void deleteByUserName(String username);

    List<User> findByRoleDescriptionIgnoreCaseAndIsDeleted(String description, Boolean deleted);

}
