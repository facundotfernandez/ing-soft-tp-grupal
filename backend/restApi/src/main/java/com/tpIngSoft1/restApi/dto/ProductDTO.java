package com.tpIngSoft1.restApi.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class ProductDTO {

    private String id;

    @NotBlank(message = "El nombre del producto es obligatoriO.")
    private String name;

    private List<VariantDTO> variants;
}