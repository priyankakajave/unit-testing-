package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("/api/v1/")
public class UserController {

	@Autowired
	private UserRepository customerRepository;

	// get all customers

	@GetMapping("/customers")
	public List<User> getAllCustomers() {
		return customerRepository.findAll();
	}

	@PostMapping("/sendCustomer")
	public User createCustomer(@Validated @RequestBody User newCustomer) {
		return customerRepository.save(newCustomer);
	}

	@PutMapping("/updateCustomer/{id}")
	public ResponseEntity<User> updateCustomer(@PathVariable(value = "id") Long userID,
			@Validated @RequestBody User newCustomer) throws ResourceNotFoundException {
		User updatedCustomer = customerRepository.findById(userID)
				.orElseThrow(() -> new ResourceNotFoundException("Customer is not avaiable:" + userID));
		updatedCustomer.setPassword(newCustomer.getPassword());
		updatedCustomer.setTransactionPassword(newCustomer.getTransactionPassword());
		customerRepository.save(updatedCustomer);

		return ResponseEntity.ok(updatedCustomer);
	}

	@DeleteMapping("/deleteCustomer/{id}")
	public Map<String, Boolean> deleteCustomer(@PathVariable(value = "id") Long userID)
			throws ResourceNotFoundException {
		User updatedCustomer = customerRepository.findById(userID)
				.orElseThrow(() -> new ResourceNotFoundException("Customer is not Available:" + userID));
		customerRepository.delete(updatedCustomer);
		Map<String, Boolean> response = new HashMap<>();
		response.put("Customer has been Deleted", Boolean.TRUE);
		return response;
	}

}
