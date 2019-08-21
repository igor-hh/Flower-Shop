package com.accenture.flowershop.be.entity.order;

import com.accenture.flowershop.be.entity.user.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal totalPrice;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date closeDate;

    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User owner;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderItem> orderItems;

    @PrePersist
    public void prePersist() {
        setCreationDate(new Date());
    }

    public Order() {
    }

    public Order(User owner) {
        this.owner = owner;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
