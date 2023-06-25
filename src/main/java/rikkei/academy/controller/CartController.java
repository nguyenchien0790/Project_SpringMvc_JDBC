package rikkei.academy.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rikkei.academy.model.Cart;
import rikkei.academy.model.CartDetail;
import rikkei.academy.model.Product;
import rikkei.academy.model.User;
import rikkei.academy.service.cart.CartServiceIMPL;
import rikkei.academy.service.cartdetai.CartDetailServiceIMPL;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    CartServiceIMPL cartServiceIMPL = new CartServiceIMPL();
    CartDetailServiceIMPL cartDetailServiceIMPL = new CartDetailServiceIMPL();

    @GetMapping("/add")
    public String addToCart(HttpSession session, @RequestParam("idProduct") int idProduct) {
        User currentUser = (User) session.getAttribute("userLogin");
        if (currentUser == null) return "redirect:/";
        Cart cart = cartServiceIMPL.findByUserId(currentUser.getId());
        if (cart == null) {
            cart = cartServiceIMPL.createNewCartForUser(currentUser);
        }
        List<CartDetail> cartDetailByCart = cartDetailServiceIMPL.findCartDetailByCart(cart);
        int iCheck = checkProduct(cartDetailByCart, idProduct);
        if (iCheck == -1) {
            Product product = new Product();
            product.setId(idProduct);
            cartDetailServiceIMPL.save(new CartDetail(0, cart.getId(), product, 1));

        } else {
            CartDetail cartDetail = cartDetailByCart.get(iCheck);
            cartDetail.setQuantityBuy(cartDetail.getQuantityBuy() + 1);
            cartDetailServiceIMPL.save(cartDetail);
        }
        return "redirect:/";
    }

    @GetMapping("/remove")
    public String remove(@RequestParam(name = "idCartDetail") int idCartDetail) {
        cartDetailServiceIMPL.delete(idCartDetail);
        return "redirect:/user/cart";
    }

    @GetMapping("/checkout")
    public String checkout(@RequestParam(name = "idCart") int idCart, RedirectAttributes redirectAttributes) {
        String message = "";
        Cart cart = new Cart();
        cart.setId(idCart);
        List<CartDetail> cartDetailByCart = cartDetailServiceIMPL.findCartDetailByCart(cart);
        if (cartDetailByCart.size() == 0) {
            message = "No product";
        } else {
            cartServiceIMPL.changeStatusCart(idCart, true);
            message = "Checkout success!";
        }
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/";
    }


    private int checkProduct(List<CartDetail> cartDetailList, int id) {
        for (int i = 0; i < cartDetailList.size(); i++) {
            if (id == cartDetailList.get(i).getProduct().getId()) {
                return i;
            }
        }
        return -1;
    }


}
