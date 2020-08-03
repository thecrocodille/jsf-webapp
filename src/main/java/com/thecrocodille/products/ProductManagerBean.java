package com.thecrocodille.products;

import com.thecrocodille.products.domain.ProductEntity;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@LocalBean
@Stateless
public class ProductManagerBean {

    @PersistenceContext(unitName = "examplePU")
    private EntityManager entityManager;

    //CRUD

    public boolean create(Product product){
        if(!checkValid(product)){
            return false;
        }
         ProductEntity existingProduct = entityManager.find(ProductEntity.class, product.getId());
        if(existingProduct != null){
            return false;
        }

        existingProduct = new ProductEntity();
        existingProduct.fromDto(product);
        entityManager.persist(existingProduct);
        return true;
    }

    public Product read(long id){
        ProductEntity productEntity = entityManager.find(ProductEntity.class, id);
        if(productEntity == null){
            return null;
        }
        return productEntity.toDto();
    }

    public boolean update(Product product){
        if(!checkValid(product)){
            return false;
        }
        ProductEntity existingProduct = entityManager.find(ProductEntity.class, product.getId());
        if(existingProduct == null){
            return false;
        }

        existingProduct.fromDto(product);
        entityManager.merge(existingProduct);
        return true;
    }

    public boolean delete(long id){
        ProductEntity existingEntity = entityManager.find(ProductEntity.class, id);
        if(existingEntity == null){
            return false;
        }

        entityManager.remove(existingEntity);
        return true;
    }

    public List<Product> readList(int offset, int limit){
        if(offset < 0 || limit < 1){
            return Collections.emptyList();
        }
        TypedQuery<ProductEntity> query = entityManager.createQuery(
                "select entity from ProductEntity entity",
                ProductEntity.class);

        query.setFirstResult(offset);
        query.setMaxResults(limit);

        List<ProductEntity> productEntities = query.getResultList();
        if(productEntities == null || productEntities.isEmpty()){
            return Collections.emptyList();
        }

        List<Product> result = new ArrayList<Product>(productEntities.size());
        for(ProductEntity productEntity : productEntities){
            result.add(productEntity.toDto());
        }

        return result;
    }

    public boolean checkValid(Product product){
        return product != null &&
                !StringUtils.isEmpty(product.getName()) &&
                product.getPrice() > 0;
    }
}
