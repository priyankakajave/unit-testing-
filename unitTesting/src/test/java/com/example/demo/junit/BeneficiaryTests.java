package com.example.demo.junit;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.controller.BeneficiaryController;
import com.example.demo.model.Beneficiary;
//import com.example.demo.model.User;
import com.example.demo.repository.BeneficiaryRepository;
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
@WebMvcTest(BeneficiaryController.class)
public class BeneficiaryTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BeneficiaryRepository beneficiaryRepository;

  

    @Test
    public void testGetAllBeneficiary() throws Exception {
        List<Beneficiary> beneficiaries = new ArrayList<>();
        // AddBeneficiary instances to the list
        
        Beneficiary beneficiary1 = new Beneficiary(1L,"1234","2345","5678","7896");

        Beneficiary beneficiary2 = new Beneficiary(2L,"1234","2345","5678","7896");
        beneficiaries.add(beneficiary1);
        beneficiaries.add(beneficiary2);
        when(beneficiaryRepository.findAll()).thenReturn(beneficiaries);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beneficiaries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(beneficiaries.size()));
    }
    
    
    @Test
    public void testCreateBeneficiary() throws Exception {
        
       Beneficiary newBeneficiary = new Beneficiary(1L,"1234","2345","5678","7896");
       Beneficiary createBeneficiary = new Beneficiary(1L,"1234","2345","5678","7896");
       
    
       
        when(beneficiaryRepository.save(any(Beneficiary.class))).thenReturn(createBeneficiary);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/sendBeneficiary")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newBeneficiary)))
                .andExpect(status().isOk());

}
    
    
    public static String asJsonString(final Beneficiary newBeneficiary) {
    	
    	    try {
    	        final ObjectMapper mapper = new ObjectMapper();
    	        final String jsonContent = mapper.writeValueAsString(newBeneficiary);
    	        return jsonContent;
    	    } catch (Exception e) {
    	        throw new RuntimeException(e);
    	    }
    	}  
    	
	


	@Test
    public void testUpdateBeneficiaryById() throws Exception {
       
       Beneficiary existingBeneficiary = new Beneficiary(1L,"1234","2345","5678","7896");
       Beneficiary updatedBeneficiary = new Beneficiary(1L,"1234","2345","5678","999");

        when(beneficiaryRepository.findById(anyLong())).thenReturn(Optional.of(existingBeneficiary));
        when(beneficiaryRepository.save(any(Beneficiary.class))).thenReturn(updatedBeneficiary);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/updateBeneficiary/{id}","12345678")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedBeneficiary)))
                .andExpect(status().isOk());
             
    }
    
  
    @Test
    public void testDeleteBeneficiary() throws Exception {
       
       Beneficiary existingBeneficiary = new Beneficiary(1L,"1234","2345","5678","7896");

        when(beneficiaryRepository.findById(1L)).thenReturn(Optional.of(existingBeneficiary));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/deleteBeneficiary/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(beneficiaryRepository, times(1)).delete(existingBeneficiary);
    }
    
    @Test
    public void find_byIdNotFound_404() throws Exception {
        mockMvc.perform(get("/api/v1/beneficiarys/98765432q")).andExpect(status().isNotFound());
    }


}
