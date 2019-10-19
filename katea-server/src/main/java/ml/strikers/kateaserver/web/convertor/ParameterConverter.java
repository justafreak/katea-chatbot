package ml.strikers.kateaserver.web.convertor;

import com.google.common.collect.Maps;
import com.google.protobuf.ListValue;
import com.google.protobuf.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ParameterConverter {


    //Need custom conversion because Jackson doesn't know how to serialize ProtoBuf
    public static Map<String, Object> convert(Map<String, Value> fieldsMap) {

        return Optional.ofNullable(fieldsMap)
                .orElseGet(HashMap::new)
                .entrySet()
                .stream()
                .map(ParameterConverter::convert)
                .flatMap(Optional::stream)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static Optional<Map.Entry<String, Object>> convert(Map.Entry<String, Value> entry) {
        return convert(entry.getValue())
                .map(value -> Maps.immutableEntry(entry.getKey(), value));

    }

    public static Optional<Object> convert(Value value) {
        switch (value.getKindCase()) {
            case BOOL_VALUE:
                return Optional.of(value.getBoolValue());
            case STRING_VALUE:
                return value.getStringValue().trim().isBlank()
                        ? Optional.empty() : Optional.of(value.getStringValue().trim());
            case LIST_VALUE:
            return Optional.of(convert(value.getListValue()));

        }
        return Optional.empty();
    }

    public static List<Object> convert(ListValue listValue) {
        return listValue.getValuesList()
                .stream()
                .map(ParameterConverter::convert)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }


}
