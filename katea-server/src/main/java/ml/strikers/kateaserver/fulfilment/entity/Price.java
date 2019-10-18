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

    private Double value;
    private Currency currency;

}
