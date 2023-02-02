package com.thinktalkbuild.contractreader.ui.controller;
import com.thinktalkbuild.contractreader.ui.model.Analysis;
import com.thinktalkbuild.contractreader.ui.service.AnalyserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author kate
 */
@Controller
@Slf4j
public class UserController {

    @Autowired
    private AnalyserService analyserService;

    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserDetails user, Model model) {
        model.addAttribute("auth", user);
        return "home";
    }

    @GetMapping("/user")
    public String user(HttpServletRequest request,
                       @AuthenticationPrincipal OAuth2User principal,
                       Model model,
                       @AuthenticationPrincipal(expression = "idToken") OidcIdToken idToken) {
        log.info("user info {}", principal);
        log.info("token {}", idToken.getTokenValue());
        if(principal != null){
            model.addAttribute("name", principal.getAttribute("name"));
            model.addAttribute("email", principal.getAttribute("email"));
            model.addAttribute("id", principal.getName());
        }
        if(idToken != null){
            model.addAttribute("idtoken", idToken.getTokenValue());
        }
        return "user";
    }

}
