package com.tpIngSoft1.restApi.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Document(collection = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    private String id;
    private String username;
    private LocalDateTime confirmationDate;
    private List<OrderItem> items;
    private String status;

    public Order(String username,String status, LocalDateTime date, List<OrderItem> items) {
        this.username = username;
        this.status = status;
        this.confirmationDate = date;
        this.items = items;
    }

    public List<OrderItem> getItems(){return this.items;};
    public void setStatus(String status) {this.status = status;}
    public void setConfirmationDate(LocalDateTime confirmationDate) {this.confirmationDate = confirmationDate;}
}
