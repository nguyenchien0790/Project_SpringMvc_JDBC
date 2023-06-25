package rikkei.academy.service.product;

import rikkei.academy.model.Product;
import rikkei.academy.service.IGenericService;

import java.util.List;

public interface IProductService extends IGenericService<Product> {

    List<Product> showByCatalog(int id);
    List<Product> findProductByPage(int page,int count);
    int countProduct();
}
