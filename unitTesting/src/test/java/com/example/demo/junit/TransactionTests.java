package com.example.demo.junit;
//import java.sql.Date;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.controller.TransactionController;
import com.example.demo.model.Transaction;
import com.example.demo.repository.TransactionRepository;
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
@WebMvcTest(TransactionController.class)
public class TransactionTests {

    @Autowired
    private MockMvc mockMvc;

 

    @MockBean
    private TransactionRepository transactionRepository;

    java.sql.Date transactionDate1= new java.sql.Date(20230405);
    java.sql.Date transactionDate2= new java.sql.Date(20220405);
    
    @Test
    public void testGetAllTransactions() throws Exception {
        List<Transaction> transactions = new ArrayList<>();
        // Add Transaction instances to the list
       
       Transaction transaction1 = new Transaction(12345678L,13245678L,1000D,"y",transactionDate1);
       
       Transaction transaction2 = new Transaction(12325678L,13245678L,1000D,"y",transactionDate1);
        
        transactions.add(transaction1);
        transactions.add(transaction2);
        when(transactionRepository.findAll()).thenReturn(transactions);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(transactions.size()));
    }
    

    
    @Test
    public void testCreateTransaction() throws Exception {
     Transaction newTransaction = new Transaction(12345678L,13245678L,1000D,"y",transactionDate2);
      Transaction createTransaction = new Transaction(12345678L,13245678L,1000D,"y",transactionDate2);
       
    
       
        when(transactionRepository.save(any(Transaction.class))).thenReturn(createTransaction);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/sendTransaction")
                        .contentType(MediaType.APPLICATION_JSON)
                         .content(asJsonString(newTransaction)))
                .andExpect(status().isOk());

}
    
    
    public static String asJsonString(final Transaction newTransaction) {
    	
    	    try {
    	        final ObjectMapper mapper = new ObjectMapper();
    	        final String jsonContent = mapper.writeValueAsString(newTransaction);
    	        return jsonContent;
    	    } catch (Exception e) {
    	        throw new RuntimeException(e);
    	    }
    	}  
    	
	


	@Test
    public void testUpdateTransactionById() throws Exception {
    
      Transaction existingTransaction = new Transaction(12345678L,13245678L,1000D,"y",transactionDate1);
      Transaction updatedTransaction = new Transaction(12345678L,13245678L,1000D,"y",transactionDate2);

        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(existingTransaction));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(updatedTransaction);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/updateTransaction/{id}",12345678L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedTransaction)))
                .andExpect(status().isOk());
            
    }
    
  
    @Test
    public void testDeleteTransaction() throws Exception {
      
      Transaction existingTransaction = new Transaction(12345678L,13245678L,1000D,"y",transactionDate1);

        when(transactionRepository.findById(12345678L)).thenReturn(Optional.of(existingTransaction));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/deleteTransaction/{id}", 12345678L))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(transactionRepository, times(1)).delete(existingTransaction);
    }
    
    @Test
    public void find_byIdNotFound_404() throws Exception {
        mockMvc.perform(get("/api/v1/Transactions/87965438")).andExpect(status().isNotFound());
    }


}
