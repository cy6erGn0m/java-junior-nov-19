package ru.levelp.tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.levelp.junior.dao.AccountsRepository;
import ru.levelp.junior.dao.TransactionsRepository;
import ru.levelp.junior.entities.Account;
import ru.levelp.junior.entities.Transaction;
import ru.levelp.junior.web.WebConfig;
import ru.levelp.junior.web.security.SecurityConfig;

import javax.servlet.Filter;
import java.util.Date;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, SecurityConfig.class, WebConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@WebAppConfiguration
public class DashboardControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    @Qualifier("springSecurityFilterChain")
    private Filter securityFilter;

    @Autowired
    private AccountsRepository accounts;

    @Autowired
    private TransactionsRepository transactions;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilter(securityFilter)
                .apply(springSecurity())
                .build();

        Account account = new Account("test", "ppp");
        accounts.save(account);
        transactions.save(new Transaction(new Date(), 10, account, account));
    }

    @Test
    public void unauthorizedShouldNotBeAllowedToVisitDashboard() throws Exception {
        // unauthorized user should be redirected to /
        mockMvc.perform(MockMvcRequestBuilders.get("/dashboard"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "test", roles = "USER")
    public void authorizedUserShouldSeeDashboard() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dashboard"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("transactions", "accountId"))
                .andReturn();
    }
}
