package com.electronic.store.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    private String orderId;

    //PENDING DELIVERD,DISPATCHED
    //enum
    private String orderStatus;

    //paid unpaid
    //enum
    //boolean notpaid paid
    private String paymentStatus;

    private int orderAmount;
    @Column(length = 1000)
    private String billingAddress;

    private String billingPhone;

    private String billingName;
    private Date orderedDate;
    private Date deliveredDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_Id")
    private User user;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<OrderItem> ordersItem=new ArrayList<>();

}
