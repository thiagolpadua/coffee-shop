package org.acme.services;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.acme.dto.ProductDto;
import org.acme.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestScoped
public class ProductListService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductListService.class.getName());

    @Inject
    ProductRepository productRepository;

    public ProductDto[] listProductService() {

        LOG.debug("listProductService");

        List<ProductDto> listProduct = new ArrayList<>();

        productRepository.findAll().forEach(product -> {
            listProduct.add(new ProductDto(product));
        });

        ProductDto[] responseProductArray = listProduct.toArray(new ProductDto[0]);

        return responseProductArray;
    }
    
}
