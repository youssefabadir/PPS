package com.pps.controller.domain.user;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public ModelAndView loginForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView registerForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid @ModelAttribute("user") UserDto userDto,
                           BindingResult result,
                           Model model) {
        UserEntity existingUser = userService.getUserByEmail(userDto.getEmail());
        ModelAndView modelAndView = new ModelAndView();

        if (existingUser != null) {
            result.rejectValue("email", "409", "This email already exists");
            model.addAttribute("user", userDto);
            modelAndView.setViewName("register");
            return modelAndView;
        }

        userService.createUser(userDto);
        modelAndView.setViewName("redirect:/register?success");
        return modelAndView;
    }

    @PostMapping("/registerUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User has been created",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "This email already exists",
                    content = @Content) })
    public ResponseEntity<String> register(@Valid @ModelAttribute("user") UserDto userDto) {
        UserEntity existingUser = userService.getUserByEmail(userDto.getEmail());
        if (existingUser != null) {
            return ResponseEntity.badRequest().body("This email already exists");
        }

        userService.createUser(userDto);
        return ResponseEntity.status(201).body("User has been created");
    }
}
