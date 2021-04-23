package org.acme.services;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.acme.dto.ProductDto;
import org.acme.exceptions.BusinessError;
import org.acme.exceptions.BusinessErrorException;
import org.acme.models.Product;
import org.acme.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestScoped
public class ProductGetService {
    private static final Logger LOG = LoggerFactory.getLogger(ProductGetService.class.getName());

    @Inject
    ProductRepository productRepository;

    public ProductDto getProductService(int id) throws BusinessErrorException {

        LOG.debug("getProductService");

        LOG.debug("Request: id: " + id);

        if (id == 0) {
            LOG.debug("BusinessError");
            throw new BusinessErrorException(new BusinessError(2, "Please inform the product ID"));
        }

        Product productToGet = productRepository.findProductById(id).orElseThrow(() -> new BusinessErrorException(
                new BusinessError(3, "Product with id of " + id + " does not exist")));
        return new ProductDto(productToGet);

    }
}
