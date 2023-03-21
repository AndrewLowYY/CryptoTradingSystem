package com.aquariux.cryptotradingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aquariux.cryptotradingsystem.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
