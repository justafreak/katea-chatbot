package ml.strikers.kateaserver.fulfilment.convertor;

import com.google.cloud.dialogflow.v2.QueryResult;
import ml.strikers.kateaserver.fulfilment.entity.Fulfilment;

public class QueryResultConverter {

    public static Fulfilment covertResultToFulfilment(QueryResult queryResult) {
        Fulfilment fulfilment = new Fulfilment();
        fulfilment.setFulfilmentSimpleResponse(queryResult.getFulfillmentText());
        return fulfilment;
    }

}
