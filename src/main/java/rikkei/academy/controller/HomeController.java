package rikkei.academy.controller;

import org.hibernate.loader.plan.build.internal.returns.ScalarReturnImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.model.Product;
import rikkei.academy.model.User;
import rikkei.academy.service.product.IProductService;
import rikkei.academy.service.product.ProductServiceIMPL;
import rikkei.academy.service.user.IUserService;
import rikkei.academy.service.user.UserServiceIMPL;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    IProductService productService = new ProductServiceIMPL();

    @ModelAttribute(name = "userLogin")
    public String loginUser(HttpSession session) {
        User user = (User) session.getAttribute("userLogin");
        System.out.println(user);
        return user==null?null:user.getName();
    }

    private int count = 12;
    @GetMapping
    public String listNewProduct(Model model, @RequestParam(required = false) String page) {
        if (page == null) page = "1";
        if(!page.matches("\\d+") || Integer.parseInt(page) <= 0) return "error404";
        List<Product> list = productService.findProductByPage(Integer.parseInt(page),count);
        int proCount = productService.countProduct();
        int lastPage = (int) Math.ceil((double)proCount / count);
        if (Integer.parseInt(page) > lastPage){
            return "error404";
        }else {
            model.addAttribute("lastPage",lastPage);
        }
        model.addAttribute("listProduct",list);
        model.addAttribute("search","");
        return "home";
//        List<Product> list = productService.findAll();
//        model.addAttribute("listAllProduct",list);
//        return "home";
    }
}
