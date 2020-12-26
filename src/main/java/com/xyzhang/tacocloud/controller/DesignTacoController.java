package com.xyzhang.tacocloud.controller;

import com.xyzhang.tacocloud.data.IngredientRepository;
import com.xyzhang.tacocloud.objects.Ingredient;
import com.xyzhang.tacocloud.objects.Ingredient.Type;
import com.xyzhang.tacocloud.objects.Taco;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(i -> ingredients.add(i));

        Type[] types = Ingredient.Type.values();
        for (Type type: types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients,type));
        }
        return "DesignTaco";
    }
    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
    // 程序清单2.4 使用@PostMapping处理POST请求
    @PostMapping
    public String processDesign(@Valid Taco design, Errors errors) {
        if (errors.hasErrors()) {
            return "DesignTaco";
        }

        // save the taco design...
        log.info("Processing design:" + design);
        return "redirect:/orders/current";
    }
}
