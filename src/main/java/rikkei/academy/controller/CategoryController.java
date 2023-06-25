package rikkei.academy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rikkei.academy.model.Category;
import rikkei.academy.service.category.CategoryServiceIMPL;

import java.util.List;

@Controller
@RequestMapping(value = {"/category"})
public class CategoryController {
    CategoryServiceIMPL categoryServiceIMPL = new CategoryServiceIMPL();

    @GetMapping("/showlist")
    public String showListCategory(Model model) {
        List<Category> list = categoryServiceIMPL.findAll();
        model.addAttribute("listCategory", list);
        return "category/list";
    }

    @GetMapping("/create")
    public String formCreate(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "category/create";
    }

    @PostMapping("/create")
    public String actionCreate(Category category, RedirectAttributes rs) {
        categoryServiceIMPL.save(category);
        rs.addFlashAttribute("validate", "CREATE CATEGORY SUCCESS !");
        return "redirect:/category/showlist";
    }

    @GetMapping("/update/{id}")
    public String formUpdate(@PathVariable int id, Model model) {
        Category category = categoryServiceIMPL.findById(id);
        model.addAttribute("category", category);
        return "category/edit";
    }

    @PostMapping("/update")
    public String actionUpdate(Category category, RedirectAttributes rs) {
        categoryServiceIMPL.save(category);
        rs.addFlashAttribute("validate", "UPDATE CATEGORY SUCCESS !");
        return "redirect:/category/showlist";
    }

    @GetMapping("/delete/{id}")
    public String formDelete(@PathVariable int id, Model model) {
        Category category = categoryServiceIMPL.findById(id);
        model.addAttribute("category", category);
        return "category/delete";
    }

    @PostMapping("/delete")
    public String actionDelete(Category category, RedirectAttributes rs){
        categoryServiceIMPL.delete(category.getIdCategory());
        rs.addFlashAttribute("validate", "DELETE CATEGORY SUCCESS !");
        return "redirect:/category/showlist";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam String search) {
        List<Category> category = categoryServiceIMPL.findByName(search);
        model.addAttribute("listCategory", category);
        model.addAttribute("search", search);
        return "category/list";
    }
}
