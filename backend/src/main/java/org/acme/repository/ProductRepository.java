package org.acme.repository;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.acme.exceptions.BusinessError;
import org.acme.exceptions.BusinessErrorException;
import org.acme.models.Product;

@RequestScoped
public class ProductRepository {

    @Inject
    EntityManager entityManager;

    public List<Product> findAll() {
        return entityManager.createNamedQuery("Product.findAll", Product.class).getResultList();
    }

    public void persistProduct(Product product) {
        entityManager.persist(product);
    }

    public Optional<Product> findProductById(int id) {
        Product product = entityManager.find(Product.class, id);
        return Optional.ofNullable(product);
    }

    public void removeProduct(int id) throws BusinessErrorException {
        Product product = findProductById(id).orElseThrow(
                () -> new BusinessErrorException(new BusinessError(3, "Product with id of " + id + " does not exist")));
        entityManager.remove(product);

    }

    public void updateProduct(Product product) throws BusinessErrorException {
        Product productToUpdate = findProductById(product.getId()).orElseThrow(() -> new BusinessErrorException(
                new BusinessError(3, "Product with id of " + product.getId() + " does not exist")));
        productToUpdate.setName(product.getName());
        productToUpdate.setPrice(product.getPrice());
    }
    
}
