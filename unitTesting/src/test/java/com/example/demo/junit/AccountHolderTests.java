package com.example.demo.junit;
//mport java.sql.Date;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.controller.AccountHolderController;
import com.example.demo.model.AccountHolder;
import com.example.demo.repository.AccountHolderRepository;
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
@WebMvcTest(AccountHolderController.class)
public class AccountHolderTests {

    @Autowired
    private MockMvc mockMvc;

  

    @MockBean
    private AccountHolderRepository accountHolderRepository;

    java.sql.Date birthDate1= new java.sql.Date(20010405);
    java.sql.Date birthDate2= new java.sql.Date(20010405);
    
    @Test
    public void testGetAllAccountHolders() throws Exception {
        List<AccountHolder> accHolder = new ArrayList<>();
        // Add instances to the list
       
       AccountHolder accHolder1 = new AccountHolder("12345","Kishor","Sharma","112233445566","Dhananjay","abc,hyderabad","abc,hyderabad",birthDate1,"9867543210","sharma@gmail.com","234565786543","1E23455678","Engineer",5000d);
       
       AccountHolder accHolder2 = new AccountHolder("12346","Kishori","Sharma","112233445567","Dhananjay","abc,hyderabad","abc,hyderabad",birthDate1,"9887543210","isharma@gmail.com","294565786543","1E2455678","Engineer",5000d);
       
        accHolder.add(accHolder1);
        accHolder.add(accHolder2);
        when(accountHolderRepository.findAll()).thenReturn(accHolder);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(accHolder.size()));
    }
    

    
    @Test
    public void testCreateAccountHolder() throws Exception {

     AccountHolder newAccountHolder = new AccountHolder("12345","Kishor","Sharma","112233445566","Dhananjay","abc,hyderabad","abc,hyderabad",birthDate1,"9867543210","sharma@gmail.com","234565786543","1E23455678","Engineer",5000d);
    	AccountHolder createAccountHolder = new AccountHolder("12345","Kishor","Sharma","112233445566","Dhananjay","abc,hyderabad","abc,hyderabad",birthDate1,"9867543210","sharma@gmail.com","234565786543","1E23455678","Engineer",5000d);
       
    
       
        when(accountHolderRepository.save(any(AccountHolder.class))).thenReturn(createAccountHolder);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/sendUser")
                        .contentType(MediaType.APPLICATION_JSON)
                         .content(asJsonString(newAccountHolder)))
                .andExpect(status().isOk());

}
    
    
    public static String asJsonString(final AccountHolder newAccountHolder) {
    	
    	    try {
    	        final ObjectMapper mapper = new ObjectMapper();
    	        final String jsonContent = mapper.writeValueAsString(newAccountHolder);
    	        return jsonContent;
    	    } catch (Exception e) {
    	        throw new RuntimeException(e);
    	    }
    	}  
    	
	


	@Test
    public void testUpdateAccountHolderById() throws Exception {
     
      AccountHolder existingAccountHolder = new AccountHolder("12345","Kishor","Sharma","11223344","Dhananjay","abc,hyderabad","abc,hyderabad",birthDate1,"9867543210","sharma@gmail.com","234565786543","1E23455678","Engineer",5000d);
      AccountHolder updatedAccountHolder = new AccountHolder("12345","Kishor","Sharma","11223344","Dhananjay","ab,banglore","abc,hyderabad",birthDate1,"9867543210","sharma@gmail.com","234565786543","1E23455678","Engineer",5000d);

        when(accountHolderRepository.findById(anyString())).thenReturn(Optional.of(existingAccountHolder));
        when(accountHolderRepository.save(any(AccountHolder.class))).thenReturn(updatedAccountHolder);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/updateUser/{id}","12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedAccountHolder)))
                .andExpect(status().isOk());
             
    }
    
  
    @Test
    public void testDeleteAccount() throws Exception {
       
      AccountHolder existingUser = new AccountHolder("12345","Kishor","Sharma","11223344","Dhananjay","abc,hyderabad","abc,hyderabad",birthDate1,"9867543210","sharma@gmail.com","234565786543","1E23455678","Engineer",5000d);

        when(accountHolderRepository.findById("12345")).thenReturn(Optional.of(existingUser));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/deleteUser/{id}", "12345"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(accountHolderRepository, times(1)).delete(existingUser);
    }
    
    @Test
    public void find_byIdNotFound_404() throws Exception {
        mockMvc.perform(get("/api/v1/Users/12387")).andExpect(status().isNotFound());
    }


}
