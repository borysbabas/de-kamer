## Logging and monitoring

### Logging
To log what happens on the backend we use the log4j-api framework. This framework allows for easily adding log 
messages to the console and could also be used to log to a file. To use the log4j, you simply add the following code:

```java
// Import the necessary logger classes

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class LoggerExample {
    // Make a logger object
    private static final Logger LOGGER = LogManager.getLogger();
    
    public void exampleMethod() {
        // Call the logger object with the wanted log level (trace/debug/info/warning/error/fatal)
        LOGGER.error("An error message");
    }
}
```

### Monitoring
To monitor these logs, they currently only are outputted in the console like so: <br>

```
2025-01-14T14:35:02.681+01:00  ERROR 18212 --- [backend] [nio-3001-exec-5] .LoggerExample : "An error message"
```
The logs can also be saved in a file, but in order to do this log4j-core should be added to the project and configured.

[Next page: Troubleshooting](troubleshooting.md) | [Back to title page](/README.md) | [Previous page:
Deployment](deployment.md)