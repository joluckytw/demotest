# Demotest  
### Postman 測資  
DemotestAPI.postman_collection.json  

### 測試sql  
demotest.sql  
### H2-console  
http://localhost:8080/h2-console/  

### 單元測試指令  
### GetALL    
mvn -Dtest="CurrencyControllerTest#testGetAllCurrencies" test  
### Update  
mvn -Dtest="CurrencyControllerTest#testUpdateCurrency" test  
### Create  
mvn -Dtest="CurrencyControllerTest#testCreateCurrency" test  
### GetID  
mvn -Dtest="CurrencyControllerTest#testGetCurrency" test  
### Delete  
mvn -Dtest="CurrencyControllerTest#testDeleteCurrency" test  