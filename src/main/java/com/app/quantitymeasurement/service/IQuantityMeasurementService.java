package com.app.quantitymeasurement.service;

import java.util.List;

import com.app.quantitymeasurement.model.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementDTO;

public interface IQuantityMeasurementService {

    QuantityMeasurementDTO compareQuantities(QuantityDTO thisQuantity, QuantityDTO thatQuantity);

    QuantityMeasurementDTO convertQuantity(QuantityDTO thisQuantity, QuantityDTO thatQuantity);

    QuantityMeasurementDTO addQuantities(QuantityDTO thisQuantity, QuantityDTO thatQuantity);
    
    QuantityMeasurementDTO addQuantitiesWithTargetUnit(QuantityDTO thisQuantity, QuantityDTO thatQuantity, QuantityDTO targetQuantity);

    QuantityMeasurementDTO subtractQuantities(QuantityDTO thisQuantity, QuantityDTO thatQuantity);

    QuantityMeasurementDTO divideQuantities(QuantityDTO thisQuantity, QuantityDTO thatQuantity);

    List<QuantityMeasurementDTO> getOperationHistory(String operation);

    List<QuantityMeasurementDTO> getMeasurementsByType(String measurementType);

    long getOperationCount(String operation);

    List<QuantityMeasurementDTO> getErrorHistory();
}
