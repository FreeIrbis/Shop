package com.shop.controller.vc;

import com.shop.controller.dto.UserRegistrationDto;
import com.shop.repository.entity.EmailConfirmationToken;
import com.shop.repository.entity.User;
import com.shop.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
                                      BindingResult result, HttpServletRequest request){

        User existing = userService.findByEmail(userDto.getEmail());
        if (existing != null){
            result.rejectValue("email", null, "There is already an account registered with that email");
        }

        if (result.hasErrors()){
            return "registration";
        }

        userService.save(userDto, request);
        //return "redirect:/registration?success";
        return "redirect:/login?success_registration";
    }

    @Transactional
    @RequestMapping(value="/confirm", method = RequestMethod.GET)
    public String showConfirmationPage(ModelAndView modelAndView, @RequestParam("token") String token) {
        EmailConfirmationToken emailConfirmationToken = userService.getEmailConfirmationToken(token);

        if(emailConfirmationToken != null) {
            if(!emailConfirmationToken.getUsed()) {
                User user = userService.confirmEmail(emailConfirmationToken);
                if (user == null) { // No token found in DB
                    modelAndView.addObject("invalidToken", "Oops!  This is an invalid confirmation link.");
                } else { // Token found
                    //TODO modelAndView.addObject("confirmationToken", user.getConfirmationToken());
                }
                return "redirect:/login?success";
            } else  {
                return "confirm-account?tokenAlreadyUsed";
            }
        } else {
            //TODO
        }

        return "redirect:/login?success";
    }

    @RequestMapping(value="/confirmated-mail", method = RequestMethod.POST)
    public ModelAndView showConfirmationPage(ModelAndView modelAndView) {
        modelAndView.setViewName("/home");
        return modelAndView;
    }

}
