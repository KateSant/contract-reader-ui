package com.thinktalkbuild.contractreader.ui.controller;
import com.thinktalkbuild.contractreader.ui.model.Analysis;
import com.thinktalkbuild.contractreader.ui.service.AddContractService;
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
public class AddController {

    @Autowired
    private AddContractService addContractService;

    @GetMapping("/add-contract")
    public String addContractForm() {
        return "add-contract";
    }

    @PostMapping("/add-contract")
    public String addContractPost(@RequestParam("name") String name,
                             Model model,
                             @AuthenticationPrincipal(expression = "idToken") OidcIdToken idToken) {

        addContractService.postToAddEndpoint(name, idToken.getTokenValue());

        model.addAttribute("result", "success");
        return "add-contract";

    }

}
