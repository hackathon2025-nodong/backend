package org.foreignworker.hackatone.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.foreignworker.hackatone.domain.user.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    @GetMapping("/login")
    public String login(){return "/login";}

}
