export default class SessionSbService {
    private readonly RESOURCES_URL: string;
    private readonly BROWSER_STORAGE_ITEM_NAME: string;
    private _currentToken: string | null;

    constructor(resourcesURL: string, browserStorageItemName: string) {
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

    saveTokenIntoBrowserStorage(token: string) {
        if (token === null) {
            localStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME);
        } else {
            localStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME, token);
        }
        this._currentToken = token;
    }

    saveUserNameIntoBrowserStorage(username: string){
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

    async asyncSignIn(email: string, password: string) {
        const body = JSON.stringify({ email, password });
        const response = await fetch(`${this.RESOURCES_URL}/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: body,
            credentials: "include",
        });

        if (response.ok) {
            const responseData: any = await response.json();
            const token = responseData.jwtToken;
            const username = responseData.userName;

            if (token) {
                this.saveTokenIntoBrowserStorage(token);
                this.saveUserNameIntoBrowserStorage(username);
            }
            location.href="/forum";
            return true;
        }
        return false;
    }

    async asyncSignUp(registerForm: any){
        const body = JSON.stringify(registerForm);
        const response = await fetch(`${this.RESOURCES_URL}/register`,{
            method:"POST",
            headers:{"Content-type": "application/json"},
            body:body,
        });
        return response.ok;
    }
}