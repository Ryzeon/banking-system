package me.ryzeon.bankingsystem.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.ryzeon.bankingsystem.account.domain.model.aggregates.Account;
import me.ryzeon.bankingsystem.account.domain.model.commands.CreateAccountCommand;
import me.ryzeon.bankingsystem.account.domain.model.queries.GetAccountHolderByAccountNumberQuery;
import me.ryzeon.bankingsystem.account.domain.services.AccountCommandService;
import me.ryzeon.bankingsystem.account.domain.services.AccountQueryService;
import me.ryzeon.bankingsystem.account.interfaces.res.AccountController;
import me.ryzeon.bankingsystem.account.interfaces.res.resources.CreateAccountResource;
import me.ryzeon.bankingsystem.account.interfaces.res.resources.UpdateAccountDetailsResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @MockBean
    private AccountCommandService accountCommandService;

    @MockBean
    private AccountQueryService accountQueryService;

    @Autowired
    private MockMvc mockMvc;

    final CreateAccountResource createAccountTestResource = new CreateAccountResource("123456789", 1000.00, "John", "Doe", "john.doe@example.com", "987654321");

    final UpdateAccountDetailsResource updateAccountDetailsResource = new UpdateAccountDetailsResource("Jane", "Roe", "jane.roe@example.com", "123456789");

    @Test
    void createAccount_ReturnsCreated() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResource = objectMapper.writeValueAsString(createAccountTestResource);

        given(accountCommandService.handle((CreateAccountCommand) any())).willReturn(Optional.of(new Account()));

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonResource))
                .andExpect(status().isCreated());
    }

    @Test
    void createAccount_ReturnsBadRequest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResource = objectMapper.writeValueAsString(createAccountTestResource);

        given(accountCommandService.handle((CreateAccountCommand) any())).willReturn(Optional.empty());

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonResource))
                .andExpect(status().isBadRequest());
    }


    @Test
    void updateAccountDetails_ReturnsOk() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResource = objectMapper.writeValueAsString(updateAccountDetailsResource);

        mockMvc.perform(put("/accounts/details/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonResource))
                .andExpect(status().isOk());
    }

    @Test
    void getAccount_ReturnsOk() throws Exception {
        given(accountQueryService.handle((GetAccountHolderByAccountNumberQuery) any())).willReturn(Optional.of(new Account()));

        mockMvc.perform(get("/accounts/{accountNumber}", "123"))
                .andExpect(status().isOk());
    }

    @Test
    void getAccount_ReturnsNotFound() throws Exception {
        given(accountQueryService.handle((GetAccountHolderByAccountNumberQuery) any())).willReturn(Optional.empty());

        mockMvc.perform(get("/accounts/{accountNumber}", "123"))
                .andExpect(status().isNotFound());
    }

    @Test
    void closeAccount_ReturnsOk() throws Exception {
        mockMvc.perform(post("/accounts/close/{accountNumber}", "123"))
                .andExpect(status().isOk());
    }
}