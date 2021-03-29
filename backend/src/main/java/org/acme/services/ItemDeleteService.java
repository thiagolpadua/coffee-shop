package org.acme.services;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.acme.exceptions.BusinessError;
import org.acme.exceptions.BusinessErrorException;
import org.acme.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestScoped
public class ItemDeleteService {

    private static final Logger LOG = LoggerFactory.getLogger(ItemDeleteService.class.getName());

    @Inject
    ItemRepository itemRepository;

    @Transactional(rollbackOn = Exception.class)
    public void deleteItemService(int id) throws BusinessErrorException {

        LOG.debug("deleteItemService");

        LOG.debug("Request: id: " + id);

        if (id == 0) {
            LOG.debug("BusinessError");
            throw new BusinessErrorException(new BusinessError(2, "Please inform the item ID"));
        }

        itemRepository.removeItem(id);

    }
    
}
