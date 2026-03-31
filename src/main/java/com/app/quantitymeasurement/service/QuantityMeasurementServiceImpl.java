package com.app.quantitymeasurement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.model.IMeasurable;
import com.app.quantitymeasurement.model.LengthUnit;
import com.app.quantitymeasurement.model.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.model.QuantityModel;
import com.app.quantitymeasurement.model.TemperatureUnit;
import com.app.quantitymeasurement.model.VolumeUnit;
import com.app.quantitymeasurement.model.WeightUnit;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;

import java.util.List;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    @Autowired
    private QuantityMeasurementRepository repository;

    @Override
    public QuantityMeasurementDTO compareQuantities(QuantityDTO thisQuantity, QuantityDTO thatQuantity) {
        QuantityMeasurementDTO dto = createBaseDTO(thisQuantity, thatQuantity, "compare");
        try {
            QuantityModel<IMeasurable> thisModel = convertDtoToModel(thisQuantity);
            QuantityModel<IMeasurable> thatModel = convertDtoToModel(thatQuantity);
            boolean result = thisModel.compare(thatModel);
            dto.setResultString(String.valueOf(result));
            saveToRepository(dto);
            return dto;
        } catch (Exception e) {
            dto.setError(true);
            dto.setErrorMessage(e.getMessage());
            saveToRepository(dto);
            throw new QuantityMeasurementException("compare Error: " + e.getMessage());
        }
    }

    @Override
    public QuantityMeasurementDTO convertQuantity(QuantityDTO thisQuantity, QuantityDTO thatQuantity) {
        QuantityMeasurementDTO dto = createBaseDTO(thisQuantity, thatQuantity, "convert");
        try {
            QuantityModel<IMeasurable> thisModel = convertDtoToModel(thisQuantity);
            IMeasurable targetUnit = getUnit(thatQuantity.getUnit(), thatQuantity.getMeasurementType());
            double convertedValue = targetUnit.fromBaseUnit(thisModel.getBaseValue());
            dto.setResultValue(convertedValue);
            saveToRepository(dto);
            return dto;
        } catch (Exception e) {
            dto.setError(true);
            dto.setErrorMessage(e.getMessage());
            saveToRepository(dto);
            throw new QuantityMeasurementException("convert Error: " + e.getMessage());
        }
    }

    @Override
    public QuantityMeasurementDTO addQuantities(QuantityDTO thisQuantity, QuantityDTO thatQuantity) {
        return performArithmetic(thisQuantity, thatQuantity, "add");
    }
    
    public QuantityMeasurementDTO addQuantitiesWithTargetUnit(QuantityDTO thisQuantity, QuantityDTO thatQuantity, QuantityDTO targetQuantity) {
    	return performArithmetic(thisQuantity, thatQuantity, "add");
    }

    @Override
    public QuantityMeasurementDTO subtractQuantities(QuantityDTO thisQuantity, QuantityDTO thatQuantity) {
        return performArithmetic(thisQuantity, thatQuantity, "subtract");
    }

    @Override
    public QuantityMeasurementDTO divideQuantities(QuantityDTO thisQuantity, QuantityDTO thatQuantity) {
        return performArithmetic(thisQuantity, thatQuantity, "divide");
    }

    @Override
    public List<QuantityMeasurementDTO> getOperationHistory(String operation) {
        List<QuantityMeasurementEntity> entities = repository.findSuccessfulByOperation(operation);
        return QuantityMeasurementDTO.fromEntityList(entities);
    }

    @Override
    public List<QuantityMeasurementDTO> getMeasurementsByType(String measurementType) {
        List<QuantityMeasurementEntity> entities = repository.findByThisMeasurementType(measurementType);
        return QuantityMeasurementDTO.fromEntityList(entities);
    }

    @Override
    public long getOperationCount(String operation) {
        return repository.countByOperationAndIsErrorFalse(operation);
    }

    @Override
    public List<QuantityMeasurementDTO> getErrorHistory() {
        List<QuantityMeasurementEntity> entities = repository.findByIsErrorTrue();
        return QuantityMeasurementDTO.fromEntityList(entities);
    }

    private QuantityMeasurementDTO performArithmetic(QuantityDTO thisQuantity, QuantityDTO thatQuantity, String operation) {
        QuantityMeasurementDTO dto = createBaseDTO(thisQuantity, thatQuantity, operation);
        try {
            if (!thisQuantity.getMeasurementType().equals(thatQuantity.getMeasurementType())) {
                throw new QuantityMeasurementException(
                        "Cannot perform arithmetic between different measurement categories: "
                                + thisQuantity.getMeasurementType() + " and " + thatQuantity.getMeasurementType()
                );
            }

            QuantityModel<IMeasurable> thisModel = convertDtoToModel(thisQuantity);
            QuantityModel<IMeasurable> thatModel = convertDtoToModel(thatQuantity);

            double thisBase = thisModel.getBaseValue();
            double thatBase = thatModel.getBaseValue();
            double resultBase;

            switch (operation) {
                case "add" -> resultBase = thisBase + thatBase;
                case "subtract" -> resultBase = thisBase - thatBase;
                case "divide" -> {
                    if (thatBase == 0) throw new ArithmeticException("Divide by zero");
                    resultBase = thisBase / thatBase;
                }
                default -> throw new QuantityMeasurementException("Unknown operation: " + operation);
            }

            double resultValue = thisModel.getUnit().fromBaseUnit(resultBase);
            dto.setResultValue(resultValue);
            dto.setResultUnit(thisQuantity.getUnit());
            dto.setResultMeasurementType(thisQuantity.getMeasurementType());
            saveToRepository(dto);
            return dto;
        } catch (QuantityMeasurementException e) {
            dto.setError(true);
            dto.setErrorMessage(e.getMessage());
            saveToRepository(dto);
            throw new QuantityMeasurementException(operation + " Error: " + e.getMessage());
        } catch (Exception e) {
            dto.setError(true);
            dto.setErrorMessage(e.getMessage());
            saveToRepository(dto);
            throw e;
        }
    }

    private QuantityMeasurementDTO createBaseDTO(QuantityDTO thisQuantity, QuantityDTO thatQuantity, String operation) {
        QuantityMeasurementDTO dto = new QuantityMeasurementDTO();
        dto.setThisValue(thisQuantity.getValue());
        dto.setThisUnit(thisQuantity.getUnit());
        dto.setThisMeasurementType(thisQuantity.getMeasurementType());
        dto.setThatValue(thatQuantity.getValue());
        dto.setThatUnit(thatQuantity.getUnit());
        dto.setThatMeasurementType(thatQuantity.getMeasurementType());
        dto.setOperation(operation);
        return dto;
    }

    private void saveToRepository(QuantityMeasurementDTO dto) {
        QuantityMeasurementEntity entity = dto.toEntity();
        repository.save(entity);
    }

    private QuantityModel<IMeasurable> convertDtoToModel(QuantityDTO dto) {
        IMeasurable unit = getUnit(dto.getUnit(), dto.getMeasurementType());
        return new QuantityModel<>(dto.getValue(), unit);
    }

    private IMeasurable getUnit(String unitName, String measurementType) {
        return switch (measurementType) {
            case "LengthUnit" -> LengthUnit.valueOf(unitName);
            case "VolumeUnit" -> VolumeUnit.valueOf(unitName);
            case "WeightUnit" -> WeightUnit.valueOf(unitName);
            case "TemperatureUnit" -> TemperatureUnit.valueOf(unitName);
            default -> throw new QuantityMeasurementException("Unknown measurement type: " + measurementType);
        };
    }
}