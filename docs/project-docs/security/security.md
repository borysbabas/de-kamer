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
### JWT Tokens
#### Reasons for implementation
Jwt offers a stateless authentication mechanism. Unlike traditional session-based authentication, JWT allows user session
data to be encoded within the token itself,eliminating the need for server-side storage. 
#### How it works
When the client creates an account and signs in with their credentials a jwt token is generated and signed with a secret key. The token encodes
essentials user information and an expiration date. The client stores this token in the localstorage and includes it in the Authorization header.
For the next requests the server verifies  the token's signature. If the token is valid , the request can proceed. If not an 401 error is thrown indicating that the JWT is invalid of tampered with.


#### Actual implementation
In our application the JWT token is created and signed in the backend in the JWToken class.
This class implements JWT functionality by providing encoding,decoding and validation methods.
During token generation , user information such as <code>callName, accountId and role</code>
The token is signed with the HMAC SHA-512 algorithm, with a securely generated key derived from the passphrase.

<br>
the decode method validates incoming tokens by verifying the signature against the passphrase and checks the issuer's authenticity.

### BCrypt for password hashing
To ensure that our user details are handled with great care we need to make practices as brute forcing impractical.
This is done by hashing user's credentials before they are stored in the database through using the <code>BCrypt</code> algorithm. This implementation can be found in the AccountService.java file where you'll find the basic implementation that secures our client's details.
### Data transfer objects
We are using java classes as data transfer object to ensure only the necessary values are being sent back to the client. By doing so we 
reduce the risk of over-sharing sensitive information. Another great attribute of this practice is that it has great scalability when for instance issuing a different dataset the DTO stays the same.

### Encapsulation with Object-Oriented Programming (OOP)
By separating tasks through a layered architecture with services, controller and repository's we reduce the risk of attackers gaining access to critical data ensuring it is done through controlled interfaces.
It also helps with the cognitive complexity of code, making it more readable for fellow developers and defines clear boundaries.

### Avoidance of Password Retrieval
When navigating to the account details page we have ensured that the client and or malicious attacker can't acces the password as this isn't being sent back when making the desired request.
We have chose this particular approach as it reduces misuse or leaks.
Even administrators can't retrieve passwords. which makes the site overall more secure.

### Nav guard in vue
#### Reasons for implementation
To ensure that certain endpoints can't be accessed  by clients  even when handled in the backend by using jwt tokens. It is best to ensure the 
client can't access the endpoint altogether. That's why we use vue's nav guard.
#### How it works
When the client visits an endpoint the code checks if the target route is defined as secured.
Then it retrieves the JWT token from the localstorage. If there is none the client is considered unauthorized.
Thereafter it redirects the unauthorized client back to <code/forum/login</code>. If however the client is competent enough the navigation continues (next() is called).
It functions as a middleware to ensure the client is competent.
#### Actual implementation

In the code below you can see our implementation of the nav guard.
<code>router.beforeEach((to,from,next)=>{
if(to.matched.some(record=>record.meta.requiresAuth)){
const token = localStorage.getItem("JWT_STORAGE_ITEM")
if(!token){
return next({path:"/forum/login"})
}
}
next();
});</code>