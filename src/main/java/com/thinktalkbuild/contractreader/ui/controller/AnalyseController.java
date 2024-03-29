package com.thinktalkbuild.contractreader.ui.controller;
import com.thinktalkbuild.contractreader.ui.model.analysis.Analysis;
import com.thinktalkbuild.contractreader.ui.service.AnalyserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author kate
 */
@Controller
@Slf4j
public class AnalyseController {

    @Autowired
    private AnalyserService analyserService;

    @GetMapping("/upload-form")
    public String uploadForm(@AuthenticationPrincipal OAuth2User principal) {
        return "upload";
    }



    @PostMapping("/upload-file")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             Model model,
                             @AuthenticationPrincipal(expression = "idToken") OidcIdToken idToken) {
   
        try {

            Analysis a = analyserService.postToAnalysisEngine(file, idToken.getTokenValue());

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
