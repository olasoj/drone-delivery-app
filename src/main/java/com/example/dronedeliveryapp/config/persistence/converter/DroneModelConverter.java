package com.example.dronedeliveryapp.config.persistence.converter;

import com.example.dronedeliveryapp.drone.model.DroneModel;
import jakarta.persistence.AttributeConverter;
import org.springframework.util.Assert;

public class DroneModelConverter implements AttributeConverter<DroneModel, String> {

    @Override
    public String convertToDatabaseColumn(DroneModel droneModel) {
        Assert.notNull(droneModel, "DroneModel cannot be null");
        return droneModel.getName();
    }

    @Override
    public DroneModel convertToEntityAttribute(String s) {
        return DroneModel.assemble(s);
    }
}
