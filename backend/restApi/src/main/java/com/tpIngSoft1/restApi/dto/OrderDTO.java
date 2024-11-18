package com.tpIngSoft1.restApi.dto;

import com.tpIngSoft1.restApi.domain.OrderItem;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Data
public class OrderDTO {
    @Id
    private String id;

    @NotBlank(message = "El nombre de usuario es obligatorio.")
    private String username;

    @NotEmpty(message = "Es necesario proporcionar una lista de productos.")
    private List<OrderItem> items;

    //@NotBlank(message = "El estado de la orden es obligatorio.")
    private String status;

    //@NotBlank(message = "La fecha de confirmación de la orden es obligatorio.")
    private LocalDateTime confirmationDate;

    public String getUsername(){return username;};

    public String getStatus(){return status;};

    public LocalDateTime getConfirmationDate(){return confirmationDate;};

    public List<OrderItem> getItems(){return items;};
}
