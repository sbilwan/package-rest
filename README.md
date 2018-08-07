# Tools
> Spring Boot

> gradle

> MongoDB  

> Docker

# Assumptions 
1. Products will never be changed and will always be provided to the API
2. Since products are immutable then it is safe to use the Mongodb and persist the product details within package.
3. This API doesn't use HATEOS so there is no HAL or JSON-API specs used. 
4. Package price can be provided as one of the input in POST call however, API does make a check and compare the input price with cumulative products prices and whichever is bigger is chosen as Package price.
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

This expects a JSON body 

id : The operation has id field but it is useless in this operation as API generate an unique id and assign it to the package whilst persisting.
name: 
 
`Request Body`

>{
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
 
 ''

