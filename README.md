API allows to insert, get, update, archive and invert feature toggles.
Feature Toggles have:
ID
DisplayName
TechnicalName
ExpiresOn
Description
Inverted
CustomerIds

Feature Toggles can be inserted, get, update, archive, inverted via REST services.

Requirement to run (Java version): 11


Definition of Architecture:
API has been developed in REST-ful architecture to provide services with JSON output.
Spring Boot (2.3.4 release) framework has been used to benefit it's rich, lightweight features, Spring data, REST and etc. 
Maven has been used to easily add and resolve dependencies, automatically build application. 
FeatureToggle model has been set to manage data in API.
H2 Database is used to store feature toggles.
spring-boot-starter-data-jpa (2.3.3 release) is used to benefit its CRUD operations and persistence layer to access and persist to DB.
All services have been tested using JUnit (4.13 version).
org.mockito.Mockito framework used to mock data in tests.

REST-ful services in API:
URL: /api/
Input: none
Output: none
Type: Any
Use: To check API is working

URL: /api/featuretoggle/{id}
Input: id (int)
Output: FeatureToggleDTO
Type: GET
Use: Get feature toggle by ID

URL: /api/featureToggle
Input: none
Output: List<FeatureToggleDTO>
Type: GET
Use: To get all feature toggles

URL: /api/featuretoggle
Input: id (int)
Output: FeatureToggleDTO
Type: POST
Use: create feature toggle

URL: /api/featuretoggle/{id}
Input: id (int), FeatureToggleDTO
Output: FeatureToggleDTO
Type: PUT
Use: update feature toggle

URL: /api/featuretoggle/archive/{id}
Input: id (int)
Output: FeatureToggleDTO
Type: PUT
Use: Archive feature toggle

URL: /api/featuretoggle/{id}/{inverted}
Input: id (int), inverted (boolean)
Output: FeatureToggleDTO
Type: PUT
Use: Invert feature toggle