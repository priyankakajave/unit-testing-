package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Beneficiary;
import com.example.demo.model.User;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary,Long> {

	List<Beneficiary> findByAccountNo(String accountNo);
//	public Customer findByCustomerID(String CustomerID);
}
