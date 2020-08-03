package com.thecrocodille.products;

import com.thecrocodille.products.domain.ProductEntity;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ProductListBean implements Serializable {

    @EJB
    private ProductManagerBean productManagerBean;

    private Product newProduct = new Product();
    private long idForDelete;
    private Product productToEdit;

    public List<Product> getProducts(){
        return productManagerBean.readList(0, 100);
    }

    public Product getNewProduct() {
        return newProduct;
    }

    public void createNewProduct(){
        productManagerBean.create(newProduct);
        newProduct = new Product();
    }

    public void deleteProduct(){
        productManagerBean.delete(idForDelete);
    }

    public void updateProduct(){
        productManagerBean.update(productToEdit);
    }

    public long getIdForDelete() {
        return idForDelete;
    }

    public void setIdForDelete(long idForDelete) {
        this.idForDelete = idForDelete;
    }

    public Product getProductToEdit() {
        return productToEdit;
    }

    public void setProductToEdit(Product productToEdit) {
        this.productToEdit = productToEdit;
    }
}
