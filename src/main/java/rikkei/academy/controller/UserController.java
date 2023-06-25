package rikkei.academy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rikkei.academy.model.*;
import rikkei.academy.service.cart.CartServiceIMPL;
import rikkei.academy.service.cart.ICartService;
import rikkei.academy.service.cartdetai.CartDetailServiceIMPL;
import rikkei.academy.service.cartdetai.ICartDetailService;
import rikkei.academy.service.user.IUserService;
import rikkei.academy.service.user.UserServiceIMPL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
//    @ModelAttribute(name = "userLogin")
//    public User loginUser() {
//        return new User();
//    }

    IUserService userServiceIMPL = new UserServiceIMPL();
    ICartService cartServiceIMPL = new CartServiceIMPL();
    ICartDetailService cartDetailServiceIMPL = new CartDetailServiceIMPL();

    @GetMapping("/showlist")
    public String showListUser(Model model) {
        List<User> list = userServiceIMPL.findAll();
        model.addAttribute("listUser", list);
        return "/user/list";
    }

    @GetMapping("/account")
    public String formAccount(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "/account/reglog";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user,
                           @RequestParam("repeat") String repeat,
                           RedirectAttributes redirectAttributes
    ) {
        String notice = "";
        if (user.getName().length() == 0) {
            notice = "Can not empty name!";
        } else if (user.getEmail().length() == 0) {
            notice = "Can not empty email!";
        } else if (user.getPassword().length() == 0) {
            notice = "Can not empty password!";
        } else if (userServiceIMPL.existedByEmail(user.getEmail())) {
            notice = "Email existed !";
        } else if (user.getPassword().equals(repeat)) {
            userServiceIMPL.save(user);
            notice = "Register success !";
        } else {
            notice = "Password not match !";
        }
        redirectAttributes.addFlashAttribute("validate", notice);
        return "redirect:/user/account";
    }

    @PostMapping("/login")
    public String checkLogin(HttpServletRequest request, @ModelAttribute("user") User user,
                             RedirectAttributes redirectAttributes, Model model) {

        User userLogin = userServiceIMPL.checkLogin(user);
        String notice = "";
        if (userLogin == null) {
            notice = "Email or Password not match !";
        } else {
            request.getSession().setAttribute("userLogin", userLogin);
            if (checkAdmin(userLogin)) {
                return "redirect:/admin/manager";
            } else {
                return "redirect:/";
            }
        }
        redirectAttributes.addFlashAttribute("validate", notice);
        return "redirect:/user/account";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("userLogin");
        return "redirect:/";
    }

    private boolean checkAdmin(User user) {
        boolean isAdmin = false;
        for (Role role : user.getRoleSet()) {
            if (role.getName() == RoleName.AMIN) {
                isAdmin = true;
            }
        }
        return isAdmin;
    }


    @GetMapping("/cart")
    public String showCart(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("userLogin");
        if (currentUser != null) {
            Cart cart = cartServiceIMPL.findByUserId(currentUser.getId());
            if (cart == null) {
                cart = cartServiceIMPL.createNewCartForUser(currentUser);
            }
            List<CartDetail> listCartDetail = cartDetailServiceIMPL.findCartDetailByCart(cart);
            model.addAttribute("cartDetailList", listCartDetail);
            model.addAttribute("idCart", cart.getId());
            return "/user/cart";
        } else {
//            model.addAttribute("messageSignUp", "Vui lòng đăng nhập!");
            return "redirect:/";
        }
    }

    @GetMapping("/myprofile/{id}")
    public String myProfile(@PathVariable int id, Model model) {
        User user = userServiceIMPL.findById(id);
        model.addAttribute("userProfile",user);
        return "/user/myprofile";
    }

}
