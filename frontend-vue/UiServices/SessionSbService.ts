
export default class SessionSbService {
    constructor(resourcesURL, browserStorageItemName) {
        this.RESOURCES_URL = resourcesURL;
        this.BROWSER_STORAGE_ITEM_NAME = browserStorageItemName;
        this._currentToken = null;
        this.getTokenFromBrowserStorage();
    }

    getTokenFromBrowserStorage() {
        const token = window.sessionStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME);
        this._currentToken = token;
        return token;
    }

    saveTokenIntoBrowserStorage(token) {
        if (token === null) {
            window.sessionStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME);
            window.localStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME)
        } else {
            window.sessionStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME, token);
            window.localStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME, token)
        }
        this._currentToken = token;
    }
    clearToken(){
        window.localStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME)
        window.sessionStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME)
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
            const token = response.headers.get("Authorization");
            if (token) {
                const correctedToken = token.slice(6);
                this.saveTokenIntoBrowserStorage(correctedToken);
            }
            return true;
        }
        return false;
    }
}