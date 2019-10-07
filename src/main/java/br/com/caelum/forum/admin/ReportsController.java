	package br.com.caelum.forum.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.caelum.forum.model.OpenTopicByCategory;
import br.com.caelum.forum.repository.OpenTopicByCategoryRepository;

@Controller
@RequestMapping("/admin/reports")
public class ReportsController {

    @Autowired
    private OpenTopicByCategoryRepository openTopicByCategoryRepository;

    @GetMapping("/open-topics-by-category")
    public String showOpenTopicsByCategoryReport(Model model) {

        List<OpenTopicByCategory> openTopics = 
            openTopicByCategoryRepository.findAllByCurrentMonth();
        model.addAttribute("openTopics", openTopics);
        return "report";
    }
}
