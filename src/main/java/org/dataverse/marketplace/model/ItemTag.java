package org.dataverse.marketplace.model;

import jakarta.persistence.*;

@Entity
@Table(name = "item_tag")
public class ItemTag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mkt_item_tag_id_seq")    
    @SequenceGenerator(
        name = "mkt_item_tag_id_seq", 
        sequenceName = "mkt_item_tag_id_seq", 
        allocationSize = 1)
    private Integer id;

    private String name;

    /* Getters & Setters */

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
