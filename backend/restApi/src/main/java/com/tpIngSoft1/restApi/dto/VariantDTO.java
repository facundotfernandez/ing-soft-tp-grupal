package com.tpIngSoft1.restApi.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class VariantDTO {

    private String vid;

    @NotNull(message = "El stock es obligatorio.")
    private Integer stock;

    @NotBlank(message = "El tipo es obligatorio.")
    private String type;

    @NotBlank(message = "El color es obligatorio.")
    private String color;

    @NotBlank(message = "El tamaño es obligatorio.")
    private String size;

    @NotBlank(message = "El material es obligatorio.")
    private String material;

    @NotBlank(message = "El modelo es obligatorio.")
    private String model;

    private Map<String, String> specs;
}
