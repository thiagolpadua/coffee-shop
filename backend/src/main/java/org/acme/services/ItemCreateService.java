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
public class ItemCreateService {

    private static final Logger LOG = LoggerFactory.getLogger(ItemCreateService.class.getName());

    @Inject
    ItemRepository itemRepository;

    @Transactional(rollbackOn = Exception.class)
    public ItemDto CreateItemService(ItemDto request) throws BusinessErrorException {

        LOG.debug("CreateItemService");

        if (request.getName() == null || request.getName().equals("")) {

            LOG.debug("BusinessError");

            throw new BusinessErrorException(new BusinessError(2, "Please inform the item name"));

        }

        Item item = new Item();

        item.setName(request.getName());

        item.setPrice(request.getPrice());

        itemRepository.persistItem(item);

        ItemDto itemDto = new ItemDto(item);

        return itemDto;
    }
    
}
