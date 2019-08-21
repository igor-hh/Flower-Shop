package com.accenture.flowershop.fe.controllers;

import com.accenture.flowershop.be.business.service.FlowerService;
import com.accenture.flowershop.be.entity.flower.Flower;
import com.accenture.flowershop.be.util.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private FlowerService flowerService;

    @GetMapping("/")
    public String indexPage(@ModelAttribute Filter filter,
                            @ModelAttribute("cartSuccess") String cartSuccess,
                            @ModelAttribute("addedFlower") String flowerId,
                            Model model) {
        try {
            List<Flower> flowers = flowerService.getFilteredFlowerList(new Filter(filter));
            model.addAttribute("flowers", flowers);
            model.addAttribute("filter", filter);
            model.addAttribute("cartSuccess", cartSuccess);
            model.addAttribute("addedFlower", flowerId);
            return "index";
        } catch (Exception e) {
            model.addAttribute("filterError", e.getMessage());
            return "index";
        }
    }

    @GetMapping("/403")
    public String accessDenied() {
        return "403";
    }
}
