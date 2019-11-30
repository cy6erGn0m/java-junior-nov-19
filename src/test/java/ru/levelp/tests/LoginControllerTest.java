package ru.levelp.tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.levelp.junior.dao.AccountsRepository;
import ru.levelp.junior.entities.Account;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@WebAppConfiguration
public class LoginControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private AccountsRepository accounts;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testWrongCredentials() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/login")
                        .param("login", "aaaaa")
                        .param("password", "bbbbb")
        ).andExpect(view().name("mainPage"))
                .andExpect(model().attribute("login", "login"))
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    public void testCorrectCredentials() throws Exception {
        Account testAccount = new Account("test1", "pass1");
        accounts.save(testAccount);

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/login")
                        .param("login", "test1")
                        .param("password", "pass1")
        ).andExpect(view().name("redirect:/dashboard"))
            .andReturn();

        Assert.assertEquals(testAccount.getId(),
                result.getRequest().getSession().getAttribute("accountId"));
    }
}
