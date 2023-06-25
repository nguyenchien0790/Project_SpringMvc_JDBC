package rikkei.academy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rikkei.academy.model.Product;
import rikkei.academy.service.category.CategoryServiceIMPL;
import rikkei.academy.service.category.ICategoryService;
import rikkei.academy.service.product.IProductService;
import rikkei.academy.service.product.ProductServiceIMPL;


import java.util.List;

@Controller
@RequestMapping(value = {"/product"})
public class ProductController {
    private int count = 8;
    ICategoryService categoryService = new CategoryServiceIMPL();
    IProductService productService = new ProductServiceIMPL();

    @GetMapping("/showlist")
    public String showListProduct(Model model,@RequestParam(required = false) String page) {
        if (page == null) page = "1";
        if(!page.matches("\\d+") || Integer.parseInt(page) <= 0) return "error404";
        List<Product> list = productService.findProductByPage(Integer.parseInt(page),count);
//        int prodCount = productServiceIMPL.findAll().size();
        int prodCount = productService.countProduct();
        int lastPage = (int) Math.ceil((double)prodCount / count);
        if (Integer.parseInt(page) > lastPage) {
            return "error404";
        } else {
            model.addAttribute("lastPage", lastPage);
        }
        model.addAttribute("listProduct", list);
        model.addAttribute("search", "");
        return "product/list";
    }

    @GetMapping("/{id}")
    public String  detailProduct(@PathVariable int id, Model model){
        Product product = productService.findById(id);
        model.addAttribute("productDetail",product);
        return "product/detail";
    }

    @GetMapping("/create")
    public String formCreate(Model model){
        Product product = new Product();
        model.addAttribute("categoryList" , categoryService.findAll());
        model.addAttribute("product",product);
        return "product/create";
    }

    @PostMapping("/create")
    public String actionCreate(@ModelAttribute("product") Product product,
                               @RequestParam("categoryId") String id,
                               RedirectAttributes rs){
        product.setCategory(categoryService.findById(Integer.parseInt(id)));
        productService.save(product);
        rs.addFlashAttribute("validate","CREATE PRODUCT SUCCESS !");
        return "redirect:/product/showlist";
    }

    @GetMapping("/delete/{id}")
    public String formDelete(@PathVariable int id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "product/delete";
    }

    @PostMapping("/delete")
    public String actionDelete(Product product, RedirectAttributes rs){
        productService.delete(product.getId());
        rs.addFlashAttribute("validate", "DELETE PRODUCT SUCCESS !");
        return "redirect:/product/showlist";
    }

    @GetMapping("/update/{id}")
    public String formUpdate(@PathVariable int id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        model.addAttribute("categoryList",categoryService.findAll());
        return "product/edit";
    }

    @PostMapping("/update")
    public String formUpdate(@ModelAttribute("product") Product product,
                             @RequestParam("categoryId") String id,
                             RedirectAttributes rs) {
        product.setCategory(categoryService.findById(Integer.parseInt(id)));
        productService.save(product);
        rs.addFlashAttribute("validate", "UPDATE PRODUCT SUCCESS !");
        return "redirect:/product/showlist";
    }

    @GetMapping("/search")
    public String searchProduct(
            Model model,
            @RequestParam String search
    ) {
        List<Product> list = productService.findByName(search);
        model.addAttribute("listProduct", list);
        model.addAttribute("search", search);
        model.addAttribute("lastPage", 1);
        return "product/list";
    }
}
