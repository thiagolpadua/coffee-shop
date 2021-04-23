package org.acme.services;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.acme.dto.ProductDto;
import org.acme.exceptions.BusinessError;
import org.acme.exceptions.BusinessErrorException;
import org.acme.models.Product;
import org.acme.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestScoped
public class ProductUpdateService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductUpdateService.class.getName());

    @Inject
    ProductRepository productRepository;

    @Transactional(rollbackOn = Exception.class)
    public ProductDto updateProductService(ProductDto request) throws BusinessErrorException {

        LOG.debug("UpdateProductService");

        if (request.getId() == 0) {
            LOG.debug("BusinessError");
            throw new BusinessErrorException(new BusinessError(2, "Please inform the product ID"));            
        }

        Product product = new Product();
        product.setId(request.getId());
        product.setName(request.getName());
        product.setPrice(request.getPrice());

        productRepository.updateProduct(product);

        return new ProductDto(product);

    }
    
}
