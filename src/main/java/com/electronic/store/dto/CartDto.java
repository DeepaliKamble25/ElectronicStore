package com.electronic.store.dto;


import com.electronic.store.model.CartItem;
import com.electronic.store.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {

    private String cartId;

    private Date createdDate;
    @NotBlank(message = "compulsory need deatail of user !!!")
    private User user;

    List<CartItem> items=new ArrayList<>();




}
