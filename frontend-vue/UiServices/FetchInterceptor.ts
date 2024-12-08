import fetchIntercept from "fetch-intercept";

export class FetchInterceptor {
    sessionService;
    unregister;

    constructor(sessionService) {
        this.sessionService = sessionService;

        this.request = this.request.bind(this);
        this.response = this.response.bind(this);
        this.responseError = this.responseError.bind(this);

        this.unregister = fetchIntercept.register(this);
    }

    request(url, options = {}) {
        const token = this.sessionService?.getTokenFromBrowserStorage();
        if(url.includes("api.wikimedia.org")){
            return [url,options]
        }
        if (!token) {
            return [url, options];
        }

        const newOptions = { ...options };
        newOptions.headers = {
            ...(options.headers || {}),
            Authorization: `Bearer ${token}`,
        };

        return [url, newOptions];
    }

    response(response) {
        this.tryRecoverNewJWToken(response);

        if (response.status >= 400 && response.status < 600) {
            this.handleErrorInResponse(response);
        }

        return response;
    }

    responseError(error) {
        console.error("Fetch response error: ", error);
        return Promise.reject(error);
    }

    tryRecoverNewJWToken(response) {
        const newToken = response.headers?.get("Authorization");
        if (newToken) {
            this.sessionService.saveTokenIntoBrowserStorage(newToken);
        }
    }

    handleErrorInResponse(response) {
        console.error("API error response: ", response);

        if (response.status === 401) {
            console.log("Unauthorized 401")
        }
    }
}
