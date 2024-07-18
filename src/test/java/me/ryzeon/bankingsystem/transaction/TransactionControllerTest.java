package me.ryzeon.bankingsystem.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.ryzeon.bankingsystem.transaction.domain.model.aggregates.Transaction;
import me.ryzeon.bankingsystem.transaction.domain.services.TransactionCommandService;
import me.ryzeon.bankingsystem.transaction.interfaces.res.TransactionController;
import me.ryzeon.bankingsystem.transaction.interfaces.res.resources.CreateDepositTransactionResource;
import me.ryzeon.bankingsystem.transaction.interfaces.res.resources.CreateWithdrawalTransactionResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionCommandService transactionCommandService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createDepositTransaction_ReturnsOk() throws Exception {
        CreateDepositTransactionResource resource = new CreateDepositTransactionResource("123456789", 100.0);
        when(transactionCommandService.handle(any())).thenReturn(Optional.of(new Transaction()));

        mockMvc.perform(post("/transactions/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource)))
                .andExpect(status().isOk());
    }

    @Test
    void createDepositTransactionWithNegativeNumber() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new CreateDepositTransactionResource("", -100.0); // This should throw IllegalArgumentException
        });

        assertTrue(exception.getMessage().contains("Amount must be greater than 0"));
    }

    @Test
    void createDepositTransactionWithEmptyAccountNumber() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new CreateDepositTransactionResource("", 100.0); // This should throw IllegalArgumentException
        });

        assertTrue(exception.getMessage().contains("Account number must not be empty"));
    }

    @Test
    void createDepositTransactionWithNullAccountNumber() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new CreateDepositTransactionResource(null, 100.0); // This should throw IllegalArgumentException
        });

        assertTrue(exception.getMessage().contains("Account number must not be empty"));
    }

    @Test
    void createWithdrawTransactionWithNegativeNumber() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new CreateWithdrawalTransactionResource("", -100.0); // This should throw IllegalArgumentException
        });

        assertTrue(exception.getMessage().contains("Amount must be greater than 0"));
    }

    @Test
    void createWithdrawTransactionWithEmptyAccountNumber() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new CreateWithdrawalTransactionResource("", 100.0); // This should throw IllegalArgumentException
        });

        assertTrue(exception.getMessage().contains("Account number must not be empty"));
    }

    @Test
    void createWithdrawTransactionWithNullAccountNumber() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new CreateWithdrawalTransactionResource(null, 100.0); // This should throw IllegalArgumentException
        });

        assertTrue(exception.getMessage().contains("Account number must not be empty"));
    }

    @Test
    void createWithdrawTransaction_ReturnsOk() throws Exception {
        CreateWithdrawalTransactionResource resource = new CreateWithdrawalTransactionResource("123456789", 50.0);
        when(transactionCommandService.handle(any())).thenReturn(Optional.of(new Transaction()));

        mockMvc.perform(post("/transactions/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource)))
                .andExpect(status().isOk());
    }

    @Test
    void createDepositTransaction_Failure_ReturnsBadRequest() throws Exception {
        CreateDepositTransactionResource resource = new CreateDepositTransactionResource("123456789", 100.0);
        when(transactionCommandService.handle(any())).thenReturn(Optional.empty());

        mockMvc.perform(post("/transactions/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createWithdrawTransaction_Failure_ReturnsBadRequest() throws Exception {
        CreateWithdrawalTransactionResource resource = new CreateWithdrawalTransactionResource("123456789", 50.0);
        when(transactionCommandService.handle(any())).thenReturn(Optional.empty());

        mockMvc.perform(post("/transactions/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource)))
                .andExpect(status().isBadRequest());
    }
}
