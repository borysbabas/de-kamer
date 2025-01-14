[Back to title page](/README.md) | [Previous page:
Logging and monitoring](logging.md)
### Overzicht
This file contains guidelines and actions to be taken to solve any problem
that might occur when starting this application.

### Steps to solve Common problems
#### Check logs
Spring Boot offers a wide variety of logging, error messages and stacktraces.
Logs are usually located in the default logs folder or can be configured through means of
<code>application.properties</code>.
#### Check configuration files
Files such as <code>application.properties</code> contain crucial settings for database configuration
, ports , environments etc.
#### Restart the application/ reload maven project
Sometimes temporary issues can be resolved by simply restarting the entire application.
If however you have injected some new dependencies into the pom.xml file you can use the built in feature within intellij 
idea to reload from disk. This will reduce the probability of your application failing.
#### Test endpoints
To ensure that the project functions as intended you can navigate through the project structure and find under the test folder
some unit tests that cover almost all restfull API endpoints. You can execute these before starting the actual application and 
discover where the problem lies.

#### Cloned project from gitlab.
Once started in your new IntelliJ idea environment don't forget to assign your correct JDK.
You can find this setting by first clicking the navbar top right, then you navigate and click on Project structure... and finally
assign your downloaded SDK under Project setting --> project --> SDK.
#### Fixing 401 unauthorized error
You will  get this error most likely when interacting with the frontend application while performing actions that require a JWT token.
To fix this error simply navigate to the /backend/src/java/com.example.backend/authentication folder, here you will find any code related
that might throw this error as an exception.
Furthermore we make use of a filter to monitor any request made for secured endpoints you can partially disable this by just altering the 
code to proceed with the request and not act upon it.
#### issues with CORS.
Since we were assigned to develop a backend usning springboot, during production it is possible to encounter an cors error. However this is easily manageable by defining a global CORS configuration
You can find the file that manages the cors by navigating to <code> /backend/src/java/com.example.backend/config/ApiConfig</code>. 
#### Contact information
If you find yourself in the situation where none of these tips and steps helped with solving your problem please feel free to contact us by sending an email
to our designated email addresses 

<br>
Tom: <em>tom.pirovano@hva.nl</em>
<br>
Borys:<em>borys.babas@hva.nl</em>
<br>
Senna:<em>senna.garrelts@hva.nl</em>
<br>
Sjoerd:  <em>sjoerd.zwaan@hva.nl</em>
<br>
Mathijs:<em>mathijs.langhorst@hva.nl</em>

