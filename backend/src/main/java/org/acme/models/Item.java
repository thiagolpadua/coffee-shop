package org.acme.models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ITEM")
@NamedNativeQueries({
    @NamedNativeQuery(
        name = "Item.findAll",
        query = "SELECT * " +
                "FROM  ITEM " ,
            resultClass = Item.class                
    ),
})

public class Item {

    @Id
    @SequenceGenerator(
            name = "ITEM_SEQUENCE",
            sequenceName = "ITEM_ID_SEQUENCE",
            allocationSize = 1,
            initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_SEQUENCE")
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME", length = 80)
    private String name;

    @Column(name = "PRICE")
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
