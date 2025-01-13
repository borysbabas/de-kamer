import fetchIntercept from "fetch-intercept";

type FetchOptions = {
    method?: string;
    headers?: {};
    Authorization?: string;
}

/**
 * A Utility class that intercepts and enhances HTTP request and responses.
 * This class uses the `fetch-interceptor` library to intercept HTTP requests and responses.
 * allowing token based authentication , error handling  and token refreshing for API calls.
 */
export class FetchInterceptor {
    sessionService;//Service to manage user session , including token storage.
    unregister;//functionality to unregister the fetch interceptor.
    /**
     * Constructs the FetchInterceptor class and registers it with the fetch-intercept.
     * @param{Object} sessionService is a service for managing session tokens and holding user information.
     */
    constructor(sessionService) {
        this.sessionService = sessionService;
//Bind instance methods to ensure they maintain the correct context using the singleton principle.
        this.request = this.request.bind(this);
        this.response = this.response.bind(this);
        this.responseError = this.responseError.bind(this);

        this.unregister = fetchIntercept.register(this);
    }

    /**
     * Intercepts outgoing HTTP request to alter or add to the request.
     * This method adds a Bearer token to the Authorization header for authenticated API calls
     * unless the request is directed to certain whitelisted endpoints.
     * @param url the current url where the request is being called from
     * @param options the fetch options object including headers and other settings.
     * @returns Array the updated url with the options object.
     */
    request(url, options: FetchOptions = {}) {
        const token = this.sessionService?.getTokenFromBrowserStorage();
        if(url.includes("api.wikimedia.org")|| url.includes("https://opendata.cbs.nl")){
            return [url,options]
        }
        //if no token is found proceed with the request
        if (!token) {
            return [url, options];
        }
        //add the Bearer token to the request.
        const newOptions = { ...options };
        const newOptions: FetchOptions = { ...options };
        newOptions.headers = {
            ...(options.headers || {}),
            Authorization: `Bearer ${token}`,
        };

        return [url, newOptions];
    }

    /**
     * Intercepts HTTP responses to handle token refreshing and error handling
     * @param response the HTTP response
     * @returns the response object
     */
    response(response) {
        // tries to recover token from local storage.
        this.tryRecoverNewJWToken(response);

        if (response.status >= 400 && response.status < 600) {
            this.handleErrorInResponse(response);
        }

        return response;
    }
//handle response error.
    responseError(error) {
        console.error("Fetch response error: ", error);
        return Promise.reject(error);
    }

    /**
     * function that recovers a new token from the headers
     *
     * @param response the HTTP response object.
     */
    tryRecoverNewJWToken(response) {
        const newToken = response.headers?.get("Authorization");
        if (newToken) {
            this.sessionService.saveTokenIntoBrowserStorage(newToken);
        }
    }

    /**
     *Handles error reponses , logging details and managing specific error codes.
     * if the reponse returns a 401 the client gets send back to the login page and by doing so also clears the session
     * @param response the HTTP response object containing the error.
     */
    handleErrorInResponse(response) {
        console.error("API error response: ", response);

        if (response.status === 401) {
            console.error("Unauthorized 401")
            this.sessionService.clearToken()
            this.sessionService.clearUserName()
            window.location.href = "/forum/login";
        }
    }
}
