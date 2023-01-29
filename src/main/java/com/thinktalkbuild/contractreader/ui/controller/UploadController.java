package com.thinktalkbuild.contractreader.ui.controller;
import com.thinktalkbuild.contractreader.ui.model.Analysis;
import com.thinktalkbuild.contractreader.ui.service.AnalyserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Map;

/**
 *
 * @author kate
 */
@Controller
@Slf4j
public class UploadController {

    @Value("${spring.security.oauth2.client.registration.google.clientSecret}")
    private String clientSecret;

    @Autowired
    private AnalyserService analyserService;

    @GetMapping("/")
    public String home(@AuthenticationPrincipal OAuth2User principal, Model model) {
        log.info("client secret = {}", clientSecret);
        model.addAttribute("auth", principal);
        return "home";
    }

    @GetMapping("/user")
    public String user(@AuthenticationPrincipal OAuth2User principal, Model model) {
        log.info("user info {}", principal);
        model.addAttribute("name", principal.getAttribute("name"));
        model.addAttribute("email", principal.getAttribute("email"));
        model.addAttribute("id", principal.getName());
        return "user";
    }

    @GetMapping("/upload-form")
    public String uploadForm(@AuthenticationPrincipal OAuth2User principal) {
        return "upload";
    }



    @PostMapping("/upload-file")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model, @AuthenticationPrincipal OAuth2User principal) {
   
        try {

            Analysis a = analyserService.postToAnalysisEngine(file);

            model.addAttribute("filename", a.getFileName());
            model.addAttribute("summary", a.getSummary());
            model.addAttribute("obligationsByParty", a.getObligationsByParty());
            model.addAttribute("durations", a.getDurations());
            model.addAttribute("raw", "");

            return "analysis";

        } catch (Exception ex) {
            log.error("Error from engine endpoint [{}]", ex.getMessage());
            model.addAttribute("errormessage", "Failed to process file.");
            return "error";
        }


    }
}
