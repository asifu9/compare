## Application Compare
Application to compare JSON/String base64 encoded binary data.

<hr/>
### Technologies used

**Environment**
 - *Java Development Kit (JDK) 1.8.x*
 - *Gradle 6.5.1*
 
**Framework**
 - *Spring Boot* 

---
### Generate Java Documentation
To generate the jav document run the below command
  ```sh
   ./gradlew clean javadoc
  ```

Result will be in the below location

   *build/docs/javadoc/index.html*

---
### Running test cases and code coverage
  Run below command to execute all test cases and code coverage report.
  ```sh
   ./gradlew clean test jacocoTestReport
  ```
  Code coverage report will be in this location.
 
   *build/jacocoHtml/index.html*
   
---
### Building and Running the application

Please install java and gradle before running below command.

Once you checkout this branch, you can run below command to compile and run the application.

 ```sh
  ./gradlew clean build && java -jar build/libs/diff-app-0.0.1-SNAPSHOT.jar
 ```

This command will do following things
1. Compilation of code
2. Runds unit and integration test
3. build and runs the application on port 8080

> **_NOTE:_**  If this still wont compile, then add lombok.jar to your class path and try to run once again.

---
### Assumptions for Input/Output and Data Source
 **Input:**
 ```
  Input to the API is assumed as below
   Step 1:  Base 64 endocoding ( string/json ) online tool: [Base64Coverter](https://www.base64encode.org/)
   Step 2:  Convert the encoded string to binary stream 
   
   Example: 
       Base64.encode( {"data":"data"} ) => ewogICJkYXRhIjoiZGF0YSIKfQ==
       Binary Stream ( "ewogICJkYXRhIjoiZGF0YSIKfQ==" ) => input to the API.
 ```      
 **Output:**
 ```
   Output is taken into a json format with two fields.
      1. Compare Result - 
            EQUALS - if both left and right values are equal.
            SIZE_DIFFERENCE - if both string length not matches.
            OFFSET_DIFFERENCE - if both string length is equal but contents are changes.
      2. list of Difference with offset and length
      
   Example 1:
        {
          "status" : "EQUALS"
          "difference" : null
        }
   Example 2:
        {
          "status" : "OFFSET_DIFFERENCE"
          "difference" : [
              {
                "offset" : 3
                "length" : 1
              }
            ]
        }
```
**Technical Details:**

   Created an In-Memory cache to store the data for left and right to compare.
   The configuration for the size of cache can be updated in this location:
   ```properties
    compare/src/main/resources/application.properties
   ```
 Currently it has been set to 10, and if the entries exceeds 10 then Last Recently used (LRU) 
 entry will be removed form cache.

---
### API Details

| Name | Url| Method | Body | Response |
| ------ | ------ | ----- | ----- | ----- |
| Create Left | http://\<hostname\>:8080/compare/api/v1/diff/{id}/left | POST | binary data of Base 64 encoded json/string | HttpStatus 201 |
| Create Right | http://\<hostname\>:8080/compare/api/v1/diff/{id}/right | POST | binary data of Base 64 encoded json/string | HttpStatus 201 |
| Get Result | http://\<hostname\>:8080/compare/api/v1/diff/{id} | GET | JSON Response |  JSON { "status" : "EQUALS", "difference" : null } |

---

### Invoking API
Below steps to invoke API.
 1. API to create (post) **left** data
 ```sh
   curl --location --request POST 'http://localhost:8080/compare/api/v1/diff/1/left' \
  --header 'Content-Type: application/octet-stream' \
  --data-binary '@sample.data'
  ```

 2. API to create (post) **right** data
  ```sh
   curl --location --request POST 'http://localhost:8080/compare/api/v1/diff/1/right' \
  --header 'Content-Type: application/octet-stream' \
  --data-binary '@sample.data'
  ```
3. API to check the result
  ```sh
   curl --location --request GET 'http://localhost:8080/compare/api/v1/diff/1'
  
  ```

### Improvements / Future Enhancements
 - Need to add Error code for each type of excpetion.
 - Proper logging.
 - Need to include code quality reports like sonar.
 - Segragate the comparing logic to different seperate micro service.

