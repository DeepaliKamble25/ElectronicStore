package com.electronic.store.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cart")
public class Cart {

    private String cardId;
    private Date cardCreatedDate;

    @OneToOne
    private User user;
    //mapping of cart item
    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<CartItem> items = new ArrayList<>();


}
