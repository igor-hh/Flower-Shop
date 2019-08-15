package com.accenture.flowershop.be.controllers;

import com.accenture.flowershop.be.entity.Flower.Flower;
import com.accenture.flowershop.be.business.service.FlowerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

import static org.apache.logging.log4j.util.Strings.isNotBlank;


@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private FlowerService flowerService;

    @GetMapping("/")
    public String indexPage(@RequestParam(required = false) String findString,
                            @RequestParam(required = false) Double priceFrom,
                            @RequestParam(required = false) Double priceTo,
                            @ModelAttribute("cartSuccess") String cartSuccess,
                            @ModelAttribute("addedFlower") String flowerId,
                            Model model) {

        List<Flower> flowers;

        if (priceFrom == null) {
            priceFrom = Double.MIN_VALUE;
        }
        if (priceTo == null) {
            priceTo = Double.MAX_VALUE;
        }
        if (!isNotBlank(findString)) {
            flowers = flowerService.findByPrices(new BigDecimal(priceFrom), new BigDecimal(priceTo));
        } else {
            flowers = flowerService.findByNameAndPrices(findString, new BigDecimal(priceFrom), new BigDecimal(priceTo));
        }

        logger.info("User applied following filters: name: \"{}\" priceFrom: \"{}\" priceTo: \"{}\"", findString, priceFrom, priceTo);

        if (priceFrom == Double.MIN_VALUE) {
            priceFrom = null;
        }
        if (priceTo == Double.MAX_VALUE) {
            priceTo = null;
        }

        model.addAttribute("flowers", flowers);
        model.addAttribute("findString", findString);
        model.addAttribute("priceFrom", priceFrom);
        model.addAttribute("priceTo", priceTo);
        model.addAttribute("cartSuccess", cartSuccess);
        model.addAttribute("addedFlower", flowerId);

        return "index";
    }

    @GetMapping("/403")
    public String accessDenied() {

        return "403";
    }
}
