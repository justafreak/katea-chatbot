package ml.strikers.kateaserver.fulfilment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Price {

    public static final String KIND = "Prices";

    public static final String CURRENCY = "currency";
    public static final String VALUE = "value";

    private Double value;
    private Currency currency;

}
