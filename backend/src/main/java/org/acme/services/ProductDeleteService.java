package org.acme.services;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.acme.exceptions.BusinessError;
import org.acme.exceptions.BusinessErrorException;
import org.acme.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestScoped
public class ProductDeleteService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductDeleteService.class.getName());

    @Inject
    ProductRepository productRepository;

    @Transactional(rollbackOn = Exception.class)
    public void deleteProductService(int id) throws BusinessErrorException {

        LOG.debug("deleteProductService");

        LOG.debug("Request: id: " + id);

        if (id == 0) {
            LOG.debug("BusinessError");
            throw new BusinessErrorException(new BusinessError(2, "Please inform the product ID"));
        }

        productRepository.removeProduct(id);

    }
    
}
