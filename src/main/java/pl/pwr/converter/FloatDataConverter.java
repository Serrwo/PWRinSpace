package pl.pwr.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.lang.reflect.Array;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Converter
public class FloatDataConverter implements AttributeConverter<Float[], String> {

    private static final DecimalFormat defaultDecimalFormat = new DecimalFormat("0.00");

    protected FloatDataConverter() {
        defaultDecimalFormat.setRoundingMode(RoundingMode.DOWN);
    }

    @Override
    public String convertToDatabaseColumn(Float[] values) {
        if(values == null) {
            return null;
        }
        var builder = new StringBuilder();
        for(int i = 0; i < values.length; i++) {
            if(values[i] != null) {
                builder.append(values[i]);
            }
            if(i + 1 < values.length) {
                builder.append(";");
            }
        }
        return builder.toString();
    }

    @Override
    public Float[] convertToEntityAttribute(String joined) {
        if(joined == null) {
            return null;
        }
        List<Float> values = new ArrayList<>();
        String[] floatStrings = joined.split(";");
        for(String str : floatStrings) {
            values.add(Float.parseFloat(str));
        }
        return values.toArray((Float[]) Array.newInstance(Float.class, 0));
    }
}
