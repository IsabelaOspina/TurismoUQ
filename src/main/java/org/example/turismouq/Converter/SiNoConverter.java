package org.example.turismouq.Converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SiNoConverter implements AttributeConverter<Boolean, String> {

    // De Java a Oracle: true -> 'SI', false -> 'NO'
    @Override
    public String convertToDatabaseColumn(Boolean value) {
        if (value == null) return null;
        return value ? "SI" : "NO";
    }

    // De Oracle a Java: 'SI' -> true, 'NO' -> false
    @Override
    public Boolean convertToEntityAttribute(String dbValue) {
        if (dbValue == null) return null;
        return "SI".equalsIgnoreCase(dbValue);
    }
}
