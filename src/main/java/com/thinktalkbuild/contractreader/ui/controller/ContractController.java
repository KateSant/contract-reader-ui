package com.thinktalkbuild.contractreader.ui.controller;
import com.thinktalkbuild.contractreader.ui.model.dto.ContractMetadata;
import com.thinktalkbuild.contractreader.ui.service.ContractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

/**
 *
 * @author kate
 */
@Controller
@Slf4j
public class ContractController {

    @Autowired
    private ContractService contractService;

    @GetMapping("/add-contract")
    public String addContractForm() {
        return "add-contract";
    }

    @PostMapping("/add-contract")
    public String addContractPost(@RequestParam("name") String name,
                                  @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                  Model model,
                                  @AuthenticationPrincipal(expression = "idToken") OidcIdToken idToken) {

        ContractMetadata contract = new ContractMetadata();
        contract.setName(name);
        contract.setStartDate(startDate);

        try{
            contractService.postToAddEndpoint(contract, idToken.getTokenValue());
            model.addAttribute("result", "success");
        }catch(Exception e){
            log.error("Exception POSTing: [{}]", e.getMessage());
            model.addAttribute("result", "fail");
        }

        return "add-contract";

    }

    @GetMapping("/my-contracts")
    public String listContracts(Model model,
                                @AuthenticationPrincipal(expression = "idToken") OidcIdToken idToken) throws Exception {
        contractService.getContractMetadata(idToken.getTokenValue());
        return "list-contracts";
    }

}
