package com.app.quantitymeasurement.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.quantitymeasurement.model.QuantityInputDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quantities")
@Tag(name = "Quantity Measurements", description = "REST API for quantity measurement operations")
public class QuantityMeasurementController {

    @Autowired
    private IQuantityMeasurementService service;

    @PostMapping("/compare")
    @Operation(summary = "Compare two quantities")
    public ResponseEntity<QuantityMeasurementDTO> compareQuantities(@Valid @RequestBody QuantityInputDTO input) {
        QuantityMeasurementDTO result = service.compareQuantities(
                input.getThisQuantityDTO(), input.getThatQuantityDTO());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/convert")
    @Operation(summary = "Convert a quantity to another unit")
    public ResponseEntity<QuantityMeasurementDTO> convertQuantity(@Valid @RequestBody QuantityInputDTO input) {
        QuantityMeasurementDTO result = service.convertQuantity(
                input.getThisQuantityDTO(), input.getThatQuantityDTO());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    @Operation(summary = "Add two quantities")
    public ResponseEntity<QuantityMeasurementDTO> addQuantities(@Valid @RequestBody QuantityInputDTO input) {
        QuantityMeasurementDTO result = service.addQuantities(
                input.getThisQuantityDTO(), input.getThatQuantityDTO());
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/add-with-target-unit")
    @Operation(summary = "Add two quantities with a target unit")
    public ResponseEntity<QuantityMeasurementDTO> addQuantitiesWithTargetUnit(@Valid @RequestBody QuantityInputDTO input) {
    	QuantityMeasurementDTO result = service.addQuantitiesWithTargetUnit(input.getThisQuantityDTO(), input.getThatQuantityDTO(), input.getTargetQuantityDTO());
    	return ResponseEntity.ok(result);
    }

    @PostMapping("/subtract")
    @Operation(summary = "Subtract two quantities")
    public ResponseEntity<QuantityMeasurementDTO> subtractQuantities(@Valid @RequestBody QuantityInputDTO input) {
        QuantityMeasurementDTO result = service.subtractQuantities(
                input.getThisQuantityDTO(), input.getThatQuantityDTO());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/divide")
    @Operation(summary = "Divide two quantities")
    public ResponseEntity<QuantityMeasurementDTO> divideQuantities(@Valid @RequestBody QuantityInputDTO input) {
        QuantityMeasurementDTO result = service.divideQuantities(
                input.getThisQuantityDTO(), input.getThatQuantityDTO());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/history/operation/{operation}")
    @Operation(summary = "Get operation history by operation type")
    public ResponseEntity<List<QuantityMeasurementDTO>> getOperationHistory(@PathVariable String operation) {
        List<QuantityMeasurementDTO> history = service.getOperationHistory(operation.toLowerCase());
        return ResponseEntity.ok(history);
    }

    @GetMapping("/history/type/{type}")
    @Operation(summary = "Get measurements by measurement type")
    public ResponseEntity<List<QuantityMeasurementDTO>> getMeasurementsByType(@PathVariable String type) {
        List<QuantityMeasurementDTO> measurements = service.getMeasurementsByType(type);
        return ResponseEntity.ok(measurements);
    }

    @GetMapping("/count/{operation}")
    @Operation(summary = "Get successful operation count")
    public ResponseEntity<Long> getOperationCount(@PathVariable String operation) {
        long count = service.getOperationCount(operation.toLowerCase());
        return ResponseEntity.ok(count);
    }

    @GetMapping("/history/errored")
    @Operation(summary = "Get error history")
    public ResponseEntity<List<QuantityMeasurementDTO>> getErrorHistory() {
        List<QuantityMeasurementDTO> errors = service.getErrorHistory();
        return ResponseEntity.ok(errors);
    }
    
    
}