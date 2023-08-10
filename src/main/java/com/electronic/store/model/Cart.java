package com.electronic.store.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cart")
public class Cart {

    private int cardId;
    private Date cardCreatedDate;

    @OneToOne
    private User user;
    //mapping of cart item
    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<CartItem> items = new ArrayList<>();


}
