/**
 * A service class to manage session-related  operations, such as authentication,
 * token handling and user session persistence using the local storage.
 */
export default class SessionSbService {
    private readonly RESOURCES_URL: string;// base url for api resources
    private readonly BROWSER_STORAGE_ITEM_NAME: string;// Key name for storing the token
    private _currentToken: string | null;//hold the current JWT token.
    /**
     * Constructor that initializes the sessionsSbService object.
     * @param resourcesURL The base url for authentication and session -related API calls.
     * @param browserStorageItemName the place wheree the current jwt token is stored
     */
    constructor(resourcesURL, browserStorageItemName) {
        this.RESOURCES_URL = resourcesURL;
        this.BROWSER_STORAGE_ITEM_NAME = browserStorageItemName;
        this._currentToken = null;
        // get  the token from the localstorage.
        this.getTokenFromBrowserStorage();
    }

    /**
     * function to get the  current jwt token from the browser storage.
     * @returns the retrieved token or null if not found
     */
    getTokenFromBrowserStorage() {
        const token = localStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME);
        this._currentToken = token;
        return token;
    }

    /**
     *saves or removes the JWT token in the browser's localstorage.
     * @param token
     */
    saveTokenIntoBrowserStorage(token) {
        if (token === null) {
            localStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME);
        } else {
            localStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME, token);
        }
        this._currentToken = token;
    }
// function to save the username into to storage
    saveUserNameIntoBrowserStorage(username){
        localStorage.setItem("username", username);
    }
// function to retrieve the username from the storage
    getUsernameFormBrowserStorage(){
        return localStorage.getItem("username");
    }
// helper function to clear the token that is located in the storage
    clearToken(){
        localStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME);
    }
//helper function to clear the username from the local storage
    clearUserName(){
        localStorage.removeItem("username");
    }

    /**
     * Function that handles logic related to signing in the current client.
     * If the function is successfully executed it stores the token and username into the localstorage.
     * @param email the client's email to signin with
     * @param password the client's password to authenticate the account.
     * @returns {Promise<boolean>} a boolean value that returns true if the account credentials match with one in the database
     * returns false if not.
     */
    async asyncSignIn(email, password) {
        //variable that holds the  client's credentials.
        const body = JSON.stringify({ email, password });
        // constructs the response object using fetch method.
        const response = await fetch(`${this.RESOURCES_URL}/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: body,
            credentials: "include",
        });
//checks if the credentials are correct by checking the response.
        if (response.ok) {
            const responseData: Account = await response.json();
            const token = responseData.jwtToken;
            const username = responseData.userName;

            if (token) {
                this.saveTokenIntoBrowserStorage(token);
                this.saveUserNameIntoBrowserStorage(username);
            }
            return true;
        }
        return false;
    }

    /**
     * Handles the logic for signing up using a registration object
     * @param registerForm the user's credentials
     * @returns {Promise<boolean>}  a boolean value that returns true if the account credentials are valid,
     * returns false if not.
     */
    async asyncSignUp(registerForm){
         const body = JSON.stringify(registerForm);
        const response = await fetch(`${this.RESOURCES_URL}/register`,{
            method:"POST",
            headers:{"Content-type": "application/json"},
            body:body,
        });
        return true;
    }
}