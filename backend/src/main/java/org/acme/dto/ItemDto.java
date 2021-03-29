package org.acme.dto;

import java.math.BigDecimal;

import org.acme.models.Item;

public class ItemDto {

    public ItemDto() {
        
    }

    public ItemDto(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
    }    

    private int id;

    private String name;

    private BigDecimal price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
}
