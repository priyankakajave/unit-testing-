package com.example.demo.junit;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.controller.UserController;
import com.example.demo.model.User;
//import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
//import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

//import net.minidev.json.JSONObject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@ExtendWith({SpringExtension.class, MockitoExtension.class})
@WebMvcTest(UserController.class)
public class UserTests {

    @Autowired
    private MockMvc mockMvc;

  
    @MockBean
    private UserRepository userRepository;

    @Test
    public void testGetAllUsers() throws Exception {
        List<User> user = new ArrayList<>();
        // Add User instances to the list
        User user1 = new User(1L, "1234567","abHcd@123","762435");
        
        User user2 = new User(2L, "2234567","aSbcd@123","762435");
        
        user.add(user1);
        user.add(user2);
        when(userRepository.findAll()).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(user.size()));
    }
    
    
    @Test
    public void testCreateUser() throws Exception {
        
      User newUser = new User(1L, "1234567","abWcd@123","762435");
       User createUser = new User(2L, "1434567","abWcd@123","762435");
       
    
       
        when(userRepository.save(any(User.class))).thenReturn(createUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/sendCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newUser)))
                .andExpect(status().isOk());

}
    
    
    public static String asJsonString(final User newUser) {
    	
    	    try {
    	        final ObjectMapper mapper = new ObjectMapper();
    	        final String jsonContent = mapper.writeValueAsString(newUser);
    	        return jsonContent;
    	    } catch (Exception e) {
    	        throw new RuntimeException(e);
    	    }
    	}  
    	
	


	@Test
    public void testUpdateUserById() throws Exception {
       
       User existingUser = new User(1L, "1234567","abcA@123","762435");
       User updatedUser = new User(1L, "1234567","123@Abcd","762435");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/updateCustomer/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedUser)))
                .andExpect(status().isOk());
           
    }
    
  
    @Test
    public void testDeleteUser() throws Exception {
      
       User existingUser = new User(3L, "1234567","abcAd@123","762435");

        when(userRepository.findById(3L)).thenReturn(Optional.of(existingUser));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/deleteCustomer/{id}", 3L))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(userRepository, times(1)).delete(existingUser);
    }
    
    @Test
    public void find_byIdNotFound_404() throws Exception {
        mockMvc.perform(get("/api/v1/customers/8")).andExpect(status().isNotFound());
    }


}
