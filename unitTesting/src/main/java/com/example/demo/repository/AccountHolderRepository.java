package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.AccountHolder;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder,String> {
    public AccountHolder findByUserID(String userID);
    
}
