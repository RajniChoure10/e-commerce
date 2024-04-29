package com.dollop.app.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="ordertab")
public class Order {
	
   @Id
	private String orderId;
    private String orderStatus;
    private Double orderAmount;
    private String paymentStatus;
    private String bilingAddress;
    private String bilingPhone;
    private String bilingName;
    private Date orderDate;
    private Date deliverDate;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_Id")
    private User user;
    
    @OneToMany(mappedBy="order",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<OrderItem> orderItem =new ArrayList<>();
}
