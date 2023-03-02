package com.thinktalkbuild.contractreader.ui.controller;
import com.thinktalkbuild.contractreader.ui.model.dto.ContractMetadata;
import com.thinktalkbuild.contractreader.ui.service.AddContractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

        ContractMetadata contract = new ContractMetadata();
        contract.setName(name);
        try{
            addContractService.postToAddEndpoint(contract, idToken.getTokenValue());
            model.addAttribute("result", "success");
        }catch(Exception e){
            log.error("Exception POSTing: [{}]", e.getMessage());
            model.addAttribute("result", "fail");
        }

        return "add-contract";

    }

}
