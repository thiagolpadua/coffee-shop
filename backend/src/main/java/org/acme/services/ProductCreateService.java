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
public class ProductCreateService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductCreateService.class.getName());

    @Inject
    ProductRepository productRepository;

    @Transactional(rollbackOn = Exception.class)
    public ProductDto CreateProductService(ProductDto request) throws BusinessErrorException {

        LOG.debug("CreateProductService");

        if (request.getName() == null || request.getName().equals("")) {

            LOG.debug("BusinessError");

            throw new BusinessErrorException(new BusinessError(2, "Please inform the product name"));

        }

        Product product = new Product();

        product.setName(request.getName());

        product.setPrice(request.getPrice());

        productRepository.persistProduct(product);

        ProductDto productDto = new ProductDto(product);

        return productDto;
    }
    
}
