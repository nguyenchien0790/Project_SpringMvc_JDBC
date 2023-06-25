package rikkei.academy.service.cart;

import rikkei.academy.model.Cart;
import rikkei.academy.model.User;
import rikkei.academy.service.IGenericService;

public interface ICartService extends IGenericService<Cart> {
    Cart findByUserId(int id);
    void changeStatusCart(int id,boolean pay);
    Cart createNewCartForUser(User user);

}
