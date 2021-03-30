package org.acme.repository;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.acme.exceptions.BusinessError;
import org.acme.exceptions.BusinessErrorException;
import org.acme.models.Item;

@RequestScoped
public class ItemRepository {

    @Inject
    EntityManager entityManager;

    public List<Item> findAll() {
        return entityManager.createNamedQuery("Item.findAll", Item.class).getResultList();
    }

    public void persistItem(Item item) {
        entityManager.persist(item);
    }

    public Optional<Item> findItemById(int id) {
        Item item = entityManager.find(Item.class, id);
        return Optional.ofNullable(item);
    }

    public void removeItem(int id) throws BusinessErrorException {
        Item item = findItemById(id).orElseThrow(
                () -> new BusinessErrorException(new BusinessError(3, "Item with id of " + id + " does not exist")));
        entityManager.remove(item);

    }

    public void updateItem(Item item) throws BusinessErrorException {
        Item itemToUpdate = findItemById(item.getId()).orElseThrow(() -> new BusinessErrorException(
                new BusinessError(3, "Item with id of " + item.getId() + " does not exist")));
        itemToUpdate.setName(item.getName());
        itemToUpdate.setPrice(item.getPrice());
    }
    
}
