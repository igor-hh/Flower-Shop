package com.accenture.flowershop.be.entity.Order;

import com.accenture.flowershop.be.entity.Flower.Flower;
import com.accenture.flowershop.be.entity.Order.Order;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "flower_id")
    private Flower flower;

    private Integer quantity;

    public OrderItem() {
    }

    public OrderItem(Order order, Flower flower, Integer quantity) {
        this.order = order;
        this.flower = flower;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Flower getFlower() {
        return flower;
    }

    public void setFlower(Flower flower) {
        this.flower = flower;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
