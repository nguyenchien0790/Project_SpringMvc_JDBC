package rikkei.academy.service.cartdetai;

import rikkei.academy.model.Cart;
import rikkei.academy.model.CartDetail;
import rikkei.academy.service.IGenericService;

import java.util.List;

public interface ICartDetailService extends IGenericService<CartDetail> {
    List<CartDetail> findCartDetailByCart(Cart cart);

}
