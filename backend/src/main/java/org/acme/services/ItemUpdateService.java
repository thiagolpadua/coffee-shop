package org.acme.services;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.acme.dto.ItemDto;
import org.acme.exceptions.BusinessError;
import org.acme.exceptions.BusinessErrorException;
import org.acme.models.Item;
import org.acme.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestScoped
public class ItemUpdateService {

    private static final Logger LOG = LoggerFactory.getLogger(ItemUpdateService.class.getName());

    @Inject
    ItemRepository itemRepository;

    @Transactional(rollbackOn = Exception.class)
    public ItemDto updateItemService(ItemDto request) throws BusinessErrorException {

        LOG.debug("UpdateItemService");

        if (request.getId() == 0) {
            LOG.debug("BusinessError");
            throw new BusinessErrorException(new BusinessError(2, "Please inform the item ID"));            
        }

        Item item = new Item();
        item.setId(request.getId());
        item.setName(request.getName());
        item.setPrice(request.getPrice());

        itemRepository.updateItem(item);

        return new ItemDto(item);

    }
    
}
