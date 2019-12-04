package ru.levelp.junior.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.levelp.junior.dao.AccountsRepository;
import ru.levelp.junior.entities.Account;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    private AccountsRepository accounts;

    @Autowired
    private PasswordEncoder encoder;

//    @PostMapping(path = "/login")
    public String processLogin(
            HttpSession session,
            @RequestParam String login,
            @RequestParam String password,
            ModelMap model) {

//        Account found = accounts.findByLoginAndEncryptedPassword(login, encoder.encode(password));
        Account found = accounts.findByLogin(login);
        if (found == null || !encoder.matches(password, found.getEncryptedPassword())) {
            model.addAttribute("login", "login");
            return "mainPage";
        }

        session.setAttribute("accountId", found.getId());

        return "redirect:/dashboard";
    }

    @GetMapping("/register")
    public String registrationPage() {
        return "registration";
    }

    @PostMapping("/register")
    public String registrationForm(
            @Validated
            @ModelAttribute("form") RegistrationFormBean form,
            BindingResult result
    ) { // CSRF
        if (form.getPasswordConfirmation() == null || !form.getPasswordConfirmation().equals(form.getPassword())) {
            result.addError(new FieldError("form", "passwordConfirmation",
                    "Confirmation doesn't match."));
        }

        try {
            accounts.save(new Account(form.getLogin(), encoder.encode(form.getPassword())));
        } catch (Exception e) {
            result.addError(new FieldError("form", "login",
                    "User with this login is already registered"));
        }

        if (result.hasErrors()) {
            return "registration";
        }
        return "redirect:/";
    }

    @ModelAttribute("form")
    public RegistrationFormBean newFormBean() {
        RegistrationFormBean bean = new RegistrationFormBean();
        bean.setLogin("");
        bean.setPassword("");
        bean.setPasswordConfirmation("");
        return bean;
    }
}
