package org.acme.services;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.acme.dto.ItemDto;
import org.acme.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestScoped
public class ItemListService {

    private static final Logger LOG = LoggerFactory.getLogger(ItemListService.class.getName());

    @Inject
    ItemRepository itemRepository;

    public ItemDto[] listItemService() {

        LOG.debug("listItemService");

        List<ItemDto> listItem = new ArrayList<>();

        itemRepository.findAll().forEach(item -> {
            listItem.add(new ItemDto(item));
        });

        ItemDto[] responseItemArray = listItem.toArray(new ItemDto[0]);

        return responseItemArray;
    }
    
}
