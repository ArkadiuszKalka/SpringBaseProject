package arek.appdemo.user;

import java.util.Locale;

import javax.ws.rs.GET;
import javax.ws.rs.POST;

import arek.appdemo.validators.UserRegisterValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    private static final Logger LOG = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private UserService userService;

    @Autowired
    MessageSource messageSource;

    @GET
    @RequestMapping(value = "/register")
    public String registerForm(Model model) {
        User u = new User();
        model.addAttribute("user", u);
        return "register";
    }

    @POST
    @RequestMapping(value = "/adduser")
    public String registerAction(User user, BindingResult result, Model model, Locale locale) {

        String returnPage = null;

        User userExist = userService.findUserByEmail(user.getEmail());

        new UserRegisterValidator().validateEmailExist(userExist, result);

        new UserRegisterValidator().validate(user, result);

        if (result.hasErrors()) {
            returnPage = "register";
        } else {
            userService.saveUser(user);
            LOG.info("**** UTWORZONO USERA( " + user.getName());
            model.addAttribute("message", messageSource.getMessage("user.register.success", null, locale));
            model.addAttribute("user", new User());
            returnPage = "register";
        }

        return returnPage;
    }


    @POST
    @RequestMapping(value = "/activationlink/{activationCode}")
    public String activateAccount(@PathVariable("activationCode") String activationCode, Model model, Locale locale) {

        userService.updateUserActivation(1, activationCode);
        model.addAttribute("message", messageSource.getMessage("user.register.success", null, locale));

        return "index";
    }

}
