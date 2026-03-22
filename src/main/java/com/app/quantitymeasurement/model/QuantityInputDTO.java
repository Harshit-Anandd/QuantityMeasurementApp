package com.app.quantitymeasurement.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuantityInputDTO {

    @NotNull(message = "First quantity is required")
    @Valid
    private QuantityDTO thisQuantity;

    @NotNull(message = "Second quantity is required")
    @Valid
    private QuantityDTO thatQuantity;

    @Valid
    private QuantityDTO targetUnit;
}