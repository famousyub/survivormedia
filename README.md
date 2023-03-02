# survivor-social-network
A Java REST API sample using Spring Boot, Spring Data, MongoDB, Maven. Implementing the 
problem described in https://gist.github.com/akitaonrails/711b5553533d1a14364907bbcdbee677

## Frameworks and tools used

For this project I decided to use Spring Boot because it makes taking your project from the ground up
a lot faster. And time was an important thing I took into consideration due to the short amount of time
I had to focus on this project. Also used Spring Data MongoDB to handle most of the repository work.

MongoDB was selected because it is light, and runs easy with [syncleous](https://github.com/Syncleus/maven-mongodb-plugin) 
maven plugin. To run this tool one may simply run _mongodb-embedded.bat_. That starts running the mongo db service on 
localhost in port 37037. If you decide to give a look on the data persisted you can find some tools like [Robo Mongo](https://robomongo.org/) to help you with that.

## End-Points Description

Here follows a list of the end-points by type of data that is returned.

The base URL to all end-points is:

```
/v1/app
```

## Survivor

The following end-points for survivor make use of the same fields here listed:

```
name : string 
age : int
gender : string
lastLocation : double[longitude, latitude]
inventory : {
    water : int
    food : int
    medication : int
    ammunition : int
}
```


* Request all of the survivors in the data-base

```
GET /survivors
```

**Curl example**
```
$ curl 'http://survivalnetwork.org/v1/app/survivors/' -i
```
**HTTP request example**
```
GET /v1/app/survivors/ HTTP/1.1
Host: survivalnetwork.org
```

**Example Response 200 OK**
```
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
Content-Length: 32338

{
  "meta" : null,
  "data" : [ {
    "id" : "58eaaba92b43926980b5d5fe",
    "name" : "Ms. De Rivia",
    "age" : 23,
    "gender" : "female",
    "contaminationReports" : 1,
    "lastLocation" : [ -49.264789, -16.686882 ],
    "inventory" : {
      "water" : 8,
      "food" : 1,
      "medication" : 1,
      "ammunition" : 2
    }
  }, {
    "id" : "58eaabaa2b43926980b5d600",
    "name" : "Poison Wayne",
    "age" : 23,
    "gender" : "dont-ask",
    "contaminationReports" : 0,
    "lastLocation" : [ 139.691706, 35.689487 ],
    "inventory" : {
      "water" : 0,
      "food" : 6,
      "medication" : 0,
      "ammunition" : 5
    }
  }]
}
```

* Request a single survivor by the ID

```
GET /survivors/{id}
```
**Curl example**
```
$ curl 'http://survivalnetwork.org/v1/app/survivors/58eaabaa2b43926980b5d650' -i
```
**HTTP request example**
```
GET /v1/app/survivors/58eaabaa2b43926980b5d650 HTTP/1.1
Host: survivalnetwork.org
```

**Example Response 200 OK**
```
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
Content-Length: 360

{
  "meta" : null,
  "data" : {
    "id" : "58eaabaa2b43926980b5d650",
    "name" : "Wolverine Jackman",
    "age" : 56,
    "gender" : "dont-ask",
    "contaminationReports" : 0,
    "lastLocation" : [ -46.633908, -23.550324 ],
    "inventory" : {
      "water" : 6,
      "food" : 8,
      "medication" : 9,
      "ammunition" : 2
    }
  }
}
```

* Creates a new Survivor

OBS: None of the fields can be null.

```
POST /survivors
```
**Curl example**
```
$ curl 'http://survivalnetwork.org/v1/app/survivors/' -i -X POST -H 'Content-Type: application/json' -d '{
  "name" : "Jane Doe",
  "age" : 24,
  "gender" : "female",
  "lastLocation" : [ -23.55052, -46.633309 ],
  "inventory" : {
    "water" : 0,
    "food" : 5,
    "medication" : 0,
    "ammunition" : 10
  }
}'
```
**HTTP request example**
```
POST /v1/app/survivors/ HTTP/1.1
Content-Type: application/json
Host: survivalnetwork.org
Content-Length: 219

{
  "name" : "Jane Doe",
  "age" : 24,
  "gender" : "female",
  "lastLocation" : [ -23.55052, -46.633309 ],
  "inventory" : {
    "water" : 0,
    "food" : 5,
    "medication" : 0,
    "ammunition" : 10
  }
}
```

**Example Response 201 CREATED**
```
HTTP/1.1 201 Created
Content-Type: application/json;charset=UTF-8
Content-Length: 316

{
  "meta" : null,
  "data" : {
    "id" : "58eefc0c52f05008a83e42e6",
    "name" : "Jane Doe",
    "age" : 24,
    "gender" : "female",
    "lastLocation" : [ -23.55052, -46.633309 ],
    "inventory" : {
      "water" : 0,
      "food" : 5,
      "medication" : 0,
      "ammunition" : 10
    }
  }
}
```

* Report an infected survivor

```
POST /survivors/{id}/report
```
**Curl example**
```
$ curl 'http://survivalnetwork.org/v1/app/survivors/58eaabaa2b43926980b5d678/report' -i -X POST -d 'infectedName=Ms.+De+Rivia'
```
**HTTP request example**
```
POST /v1/app/survivors/58eaabaa2b43926980b5d678/report HTTP/1.1
Host: survivalnetwork.org
Content-Type: application/x-www-form-urlencoded

infectedName=Ms.+De+Rivia
```

**Example Response 200 OK**
```
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
Content-Length: 82

{
  "meta" : {
    "message" : "Infected survivor succesfully reported."
  }
}
```

* Updates a survivor

```
PATCH /survivors/{id}
```
**Curl example**
```
$ curl 'http://survivalnetwork.org/v1/app/survivors/58eaabaa2b43926980b5d650' -i -X PATCH -H 'Content-Type: application/json' -d '{
  "lastLocation" : [ -23.550324, -46.633908 ]
}'
```
**HTTP request example**
```
PATCH /v1/app/survivors/58eaabaa2b43926980b5d650 HTTP/1.1
Content-Type: application/json
Host: survivalnetwork.org
Content-Length: 51

{
  "lastLocation" : [ -23.550324, -46.633908 ]
}
```

**Example Response 200 OK**
```
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
Content-Length: 60

{
  "meta" : {
    "message" : "Survivor updated."
  }
}
```

## Trade

The following end-point for trading make use of the following fields

```
buyerId : string
buyerTrade : {
    water : int
    food : int
    medication : int
    ammunition : int
}
sellerTrade : {
    water : int
    food : int
    medication : int
    ammunition : int
}
```

* Perform a trade between two survivors

OBS: None of the fields can be null.

```
POST /survivors/{id}/trade
```
**Curl example**
```
$ curl 'http://survivalnetwork.org/v1/app/survivors/58eaabaa2b43926980b5d678/trade' -i -X POST -H 'Content-Type: application/json' -d '{
  "buyerId" : "58eaabaa2b43926980b5d650",
  "buyerTrade" : {
    "water" : 0,
    "food" : 2,
    "medication" : 0,
    "ammunition" : 0
  },
  "sellerTrade" : {
    "water" : 0,
    "food" : 0,
    "medication" : 3,
    "ammunition" : 0
  }
}'
```
**HTTP request example**
```
POST /v1/app/survivors/58eaabaa2b43926980b5d678/trade HTTP/1.1
Content-Type: application/json
Host: survivalnetwork.org
Content-Length: 259

{
  "buyerId" : "58eaabaa2b43926980b5d650",
  "buyerTrade" : {
    "water" : 0,
    "food" : 2,
    "medication" : 0,
    "ammunition" : 0
  },
  "sellerTrade" : {
    "water" : 0,
    "food" : 0,
    "medication" : 3,
    "ammunition" : 0
  }
}
```

**Example Response 200 OK**
```
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
Content-Length: 59

{
  "meta" : {
    "message" : "Trade succesful."
  }
}
```

## Reports

The following end-points are used to obtain stats and reports about the survivos and their items

* Get report on percentage of infected survivors

```
GET /reports/infected
```
**Curl example**
```
$ curl 'http://survivalnetwork.org/v1/app/reports/infected' -i
```
**HTTP request example**
```
GET /v1/app/reports/infected HTTP/1.1
Host: survivalnetwork.org
```

**Example Response 200 OK**
```
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
Content-Length: 65

{
  "meta" : null,
  "data" : {
    "percentage" : 9.0
  }
}
```

* Get report on percentage of non infected survivors

```
GET /reports/not_infected
```
**Curl example**
```
$ curl 'http://survivalnetwork.org/v1/app/reports/not_infected' -i
```
**HTTP request example**
```
GET /v1/app/reports/not_infected HTTP/1.1
Host: survivalnetwork.org
```

**Example Response 200 OK**
```
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
Content-Length: 66

{
  "meta" : null,
  "data" : {
    "percentage" : 91.0
  }
}
```

* Get report on average amount of items per survivor

```
GET /reports/inventory
```
**Curl example**
```
$ curl 'http://survivalnetwork.org/v1/app/reports/inventory' -i
```
**HTTP request example**
```
GET /v1/app/reports/inventory HTTP/1.1
Host: survivalnetwork.org
```

**Example Response 200 OK**
```
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
Content-Length: 216

{
  "meta" : null,
  "data" : {
    "waterPerSurvivor" : 5.0,
    "ammunitionPerSurvivor" : 4.09,
    "foodPerSurvivor" : 4.51,
    "medicationPerSurvivor" : 4.82
  }
}
```

* Get report on points lost due to infected survivors

```
GET /reports/inventory
```
**Curl example**
```
$ curl 'http://survivalnetwork.org/v1/app/reports/points_lost' -i
```
**HTTP request example**
```
GET /v1/app/reports/points_lost HTTP/1.1
Host: survivalnetwork.org
```

**Example Response 200 OK**
```
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
Content-Length: 65

{
  "meta" : null,
  "data" : {
    "pointsLost" : 361
  }
}
```
