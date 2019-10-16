#Short Documentation

#Build Application
```
./gradlew build
```

#Run Jar 
```
go to: /build/libs
run: java -jar -Dspring.profiles.active=local katea-server-0.0.1-SNAPSHOT.jar
```

####POST - http://strikers.ml:8080/dialog/webhook
```
{
  "responseId": "31c076fd-3f7f-425c-9c72-2e82939ebedc-f6406966",
  "queryResult": {
    "queryText": "Hi!",
    "parameters": {},
    "languageCode": "en"
  }
}
```

The response should be:

```
{
    "fulfillmentText": "RECEIVED: {}",
    "responseId": "31c076fd-3f7f-425c-9c72-2e82939ebedc-f6406966",
    "queryResult": {
        "queryText": "Hi!",
        "parameters": {},
        "languageCode": "en"
    }
}
```

####GET - http://strikers.ml:8080/dialog

The response should be:

```
[
    {
        "id": "d3d46211-fa0c-44d2-939b-2252facb1ec0",
        "name": "The Colonnade",
        "imageUrl": "https://r-cf.bstatic.com/xdata/images/hotel/square200/122358182.jpg?k=04522139bfae775f531554f2be8a966e14f11880e12c76f58aa7ec31269eb2d2&o=",
        "url": "https://www.booking.com/hotel/gb/the-colonnade-london.en-gb.html",
        "rating": 6.6,
        "city": "London",
        "price": {
            "value": 716,
            "currency": "RON"
        }
    },
    {
        "id": "7a4ce2b7-7c43-48d8-a7d0-e254e555b479",
        "name": "Britannia International Hotel Canary Wharf",
        "imageUrl": "https://q-cf.bstatic.com/xdata/images/hotel/square200/50756453.jpg?k=18558635c66b534dabc514ace7a15f69c7c363dc79acb6d3c206bc7e4004f20b&o=",
        "url": "https://www.booking.com/hotel/gb/britannia-londoninternational.en-gb.html",
        "rating": 6.9,
        "city": "London",
        "price": {
            "value": 651,
            "currency": "RON"
        }
    }
                        [...]
]
```
