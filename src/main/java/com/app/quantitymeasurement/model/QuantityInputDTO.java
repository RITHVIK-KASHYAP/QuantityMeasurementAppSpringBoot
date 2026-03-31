package com.app.quantitymeasurement.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuantityInputDTO {

    @NotNull
    @Valid
    private QuantityDTO thisQuantityDTO;

    @NotNull
    @Valid
    private QuantityDTO thatQuantityDTO;
    
    @Valid
    @Schema(nullable = true)
    private QuantityDTO targetQuantityDTO;
}