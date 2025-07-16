package com.dee.auth_service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dee.auth_service.Entity.User;

public interface UserRepo extends JpaRepository<User, String>{

}
