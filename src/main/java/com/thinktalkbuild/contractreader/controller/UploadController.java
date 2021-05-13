package com.thinktalkbuild.contractreader.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author kate
 */
@Controller
public class UploadController {

    @GetMapping("/")
    public String greeting(Model model) {
        return "upload";
    }

    @PostMapping("/upload-file")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {

        if (file.isEmpty()) {
            model.addAttribute("errormessage", "File was empty.");
            return "analysis";
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            
            String firstLine = reader.readLine();
            reader.close();
            
            model.addAttribute("contents", firstLine);

        } catch (Exception ex) {
            model.addAttribute("errormessage", "An error occurred processing the file.");
  
        }
        return "analysis";

    }
}
