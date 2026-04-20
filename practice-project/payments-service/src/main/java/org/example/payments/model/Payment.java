package org.example.payments.model;

import jakarta.persistence.*;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "status")
    private String status;

    public Payment() {

    }

    public Payment(String clientId, Integer amount, String status) {
        this.clientId = clientId;
        this.amount = amount;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getClientId() {
        return clientId;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
