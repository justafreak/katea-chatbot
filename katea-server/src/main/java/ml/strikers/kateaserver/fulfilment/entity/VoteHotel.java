package ml.strikers.kateaserver.fulfilment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteHotel {

    public enum Action {
        upvote,downvote
    }

    FullfilmentHotelRequest fullfilmentHotelRequest;
    UUID hotelId;
    Action action;
}
