## Security
### Introduction
This markdown file contains all the knowledge you need,  to understand what our current authentication/security process looks like,
why we have chosen these specific technologies, how it works,  and how it is implemented in our current application.
### Fetch-interceptor
#### Reasons for implementation
To ensure client side HTTP request are monitored and handled correctly
we are using a fetch interceptor from the npm library "fetch-intercept".
This ensures us secure and consistent handling of API request and responses, an automatic attachment of JWTs in requests
and it provides a centralized place to handle errors and refresh authentication tokens.
#### Steps to installation
For this to work you need to first tell your npm to install this library by running:  <br>
<code>npm install fetch-intercept --save</code>
<br>
<br>Then once this is completed you need to manually implement this library by importing it in your file:
<br><code>import fetchIntercept from 'fetch-intercept';</code>
<br>
<br>
The final step to this process will be to initialize the fetch interceptor within your code by registering the interceptor:<br> <code>        this.unregister = fetchIntercept.register(this);
</code>
 <br>
<br>
#### How it works
Now you will be able to create custom functions for every outgoing and incoming request e.g.(request,response). Before the actual
network request is made the request method is called, which allows you to modify it.
The response howeveris triggered after the server responds but before the response is sent back to the calling code.
#### Actual implementation
The fetch interceptor is registered globally and binds methods for request and response handling. In our application requests are intercepted to attach a <code>Bearer</code> token to the <code>Authorization</code>Header ensuring we have token based authentication.
In the <code>FetchInterceptor.ts</code> file we have also whitelisted some third-party APIs to bypass the interceptor.
<br>
<br>
We have also structured it in a way that refreshes the token once expired. Additionally , the interceptor detects specific error statuses, such as a<code>401 Unauthorized</code> clearing the session and redirecting the client to the login page.

