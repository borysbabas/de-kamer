import type {Account} from "@/types/Account";

export default class SessionSbService {
    constructor(resourcesURL, browserStorageItemName) {
        this.RESOURCES_URL = resourcesURL;
        this.BROWSER_STORAGE_ITEM_NAME = browserStorageItemName;
        this._currentToken = null;
        this.getTokenFromBrowserStorage();
    }

    getTokenFromBrowserStorage() {
        const token = localStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME);
        this._currentToken = token;
        return token;
    }

    saveTokenIntoBrowserStorage(token) {
        if (token === null) {
            localStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME);
        } else {
            localStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME, token);
        }
        this._currentToken = token;
    }

    saveUserNameIntoBrowserStorage(username){
        console.log("username: ", username);
        localStorage.setItem("username", username);
    }

    getUsernameFormBrowserStorage(){
        return localStorage.getItem("username");
    }

    clearToken(){
        localStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME);
    }

    clearUserName(){
        localStorage.removeItem("username");
    }

    async asyncSignIn(email, password) {
        const body = JSON.stringify({ email, password });
        const response = await fetch(`${this.RESOURCES_URL}/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: body,
            credentials: "include",
        });

        if (response.ok) {
            const responseData: Account = await response.json();
            const token = responseData.jwtToken;
            const username = responseData.userName;

            if (token) {
                console.log(token)
                this.saveTokenIntoBrowserStorage(token);
                this.saveUserNameIntoBrowserStorage(username);
            }
            return true;
        }
        return false;
    }

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