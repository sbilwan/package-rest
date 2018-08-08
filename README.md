# Tools
> Spring Boot

> gradle

> MongoDB  

> Docker

# Assumptions 
1. Products will never change and will always be provided to the API.
2. Since products are immutable then it is safe to use the Mongodb and persist the product details within package.
3. This API doesn't use HATEOS so there is no HAL or JSON-API specs used. 
4. Package price can be provided as one of the input in POST call. However, API does make a check and compare the input price with cumulative products prices and whichever is bigger is chosen as Package price.
5. Package price are in USD and whilst persisting packages, price is converted into Euros.
6. API make use of Pagination provide by Spring to get all the packages.
7. To get a package by its id, when passed a currency, the package price is converted to the currency and returned in response.
 

# Build the API 
To build the jar from the project, run the below steps.
1. Download the source code from the stash in a directory.
2. In the code directory of the user machine, create a jar using below command 

`Windows`
> gradlew clean bootRepackage

`Linux`

> ./gradlew clean bootRepackage

3. Go the location build/libs of the directory and run the command
> java -jar package-api.jar

# Endpoints of the API with the sample Request and Response.

1. POST 

This expects a JSON body and returns the id of the saved package if successful.

id : The operation has id field but it is useless in this operation as API generate an unique id and assign it to the package whilst persisting.

name: Name of the package.

products: This is the array of the products with similar structure as defined in the https://product-service.herokuapp.com/api/v1/products.

price: This is the placeholder for the price of the package. This is ideally the sum total of all the prices in products array. However, any price more than the total of products price will take precedence.
 
`Request Body`

```{
 	"id": "37149933-9f74-49bc-8f5f-df9bb19b62ba",
 	"name": "TinaPackage",
 	"products": [{
 			"id": "601",
 			"name": "Shoes",
 			"price": 340
 		},
 		{
 			"id": "602",
 			"name": "Knives",
 			"price": 400
 		}
 	],
 	"price": 800
 }
 ```

 `Endpoint`
 > http://localhost:8080/packages
 
`Response`

> 9f507f2d-d85f-43f1-aa44-b7780daef750

2. GET

This operation returns all the packages in the DB. 

`Endpoint`

> http://localhost:8080/packages

To see the paginated list of the packages, use below endpoint

> http://localhost:8080/packages?size=10&page=0

Bear in mind the page number starts from 0 and size denotes number of packages to be included in the resultset.

`Response`

```{
       "content": [
           {
               "id": "ded8c4d7-6ae0-408c-acf3-48f963009819",
               "name": "PaulaPackage",
               "products": [
                   {
                       "id": "001",
                       "name": "helmet",
                       "price": 300
                   },
                   {
                       "id": "002",
                       "name": "sword",
                       "price": 500
                   }
               ],
               "price": 800
           },
           {
               "id": "a0b6008b-60ff-4b78-9e27-7be5f10832f5",
               "name": "AmandaPackage",
               "products": [
                   {
                       "id": "1001",
                       "name": "shield",
                       "price": 500
                   },
                   {
                       "id": "1002",
                       "name": "axe",
                       "price": 500
                   }
               ],
               "price": 1000
           },
           {
               "id": "87759c58-bd72-4144-8d47-bb627e133854",
               "name": "VeronicaPackage",
               "products": [
                   {
                       "id": "501",
                       "name": "ArmGuard",
                       "price": 200
                   },
                   {
                       "id": "502",
                       "name": "Bows",
                       "price": 900
                   }
               ],
               "price": 1100
           },
           {
               "id": "37149933-9f74-49bc-8f5f-df9bb19b62ba",
               "name": "TinaPackage",
               "products": [
                   {
                       "id": "601",
                       "name": "Shoes",
                       "price": 340
                   },
                   {
                       "id": "602",
                       "name": "Knives",
                       "price": 400
                   }
               ],
               "price": 800
           }
       ],
       "totalElements": 8,
       "totalPages": 2,
       "last": false,
       "first": true,
       "numberOfElements": 4,
       "sort": null,
       "size": 4,
       "number": 0
   }
```

3. GET a package with package id 

The operation will fetch a package with the given id and also the currency in which the package price was asked..

`Endpoint`

http://localhost:8080/packages/37149933-9f74-49bc-8f5f-df9bb19b62ba?cur=GBP

`Response`
```
{
    "id": "37149933-9f74-49bc-8f5f-df9bb19b62ba",
    "name": "TinaPackage",
    "products": [
        {
            "id": "601",
            "name": "Shoes",
            "price": 340
        },
        {
            "id": "602",
            "name": "Knives",
            "price": 400
        }
    ],
    "price": 927.24
}
```

4. PUT 

The operation expects a Json body that will be the updated package and an id of the package and returns the updated package in Json.

`Request Body`
```
{
	"id": "37149933-9f74-49bc-8f5f-df9bb19b62ba",
	"name": "TinaPackage",
	"products": [{
			"id": "601",
			"name": "Shoes",
			"price": 340
		},

		{
			"id": "602",
			"name": "Knives",
			"price": 400
		}
	],
	"price": 800
}
```

`Endpoint`

> http://localhost:8080/packages/37149933-9f74-49bc-8f5f-df9bb19b62ba

`Response Body`

```
{
    "id": "37149933-9f74-49bc-8f5f-df9bb19b62ba",
    "name": "TinaPackage",
    "products": [
        {
            "id": "601",
            "name": "Shoes",
            "price": 340
        },
        {
            "id": "602",
            "name": "Knives",
            "price": 400
        }
    ],
    "price": 800
}
```
5. DELETE

The operation expects and id of the package that needs to be deleted. Bear in mind this actually deletes the package not soft delete it.

`Endpoint`

> http://localhost:8080/packages/37149933-9f74-49bc-8f5f-df9bb19b62ba

`Response Body`

```
{
    "id": "37149933-9f74-49bc-8f5f-df9bb19b62ba",
    "name": "TinaPackage",
    "products": [
        {
            "id": "601",
            "name": "Shoes",
            "price": 340
        },
        {
            "id": "602",
            "name": "Knives",
            "price": 400
        }
    ],
    "price": 800
}
```
#Dockerizing the app

1. Build the docker image of the app using command

> docker build -t "packageapi:latest" .

2. Run the built image in a docker container

> docker container run --publish 8080:8080 --name PackageApiContainer packageapi:latest




