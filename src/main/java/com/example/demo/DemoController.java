package com.example.demo;

import  com.example.demo.DemoForm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class DemoController {

    private final DemoRepository demoRepository;

    @GetMapping("/demoForm")
    public String showDemoForm(@ModelAttribute("demoForm") DemoForm form) {
        return "demoForm";
    }

    @PostMapping("/demos")
    public String createDemo(DemoForm demoForm,
                             Model model) {
        try {
            demoRepository.insert(demoForm.getTitle(), demoForm.getContent(), demoForm.getPeriod(), demoForm.getImportance());
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
        return "redirect:/";
    }
}
