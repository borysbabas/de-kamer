import type {AdminViewAccount} from "@/types/Account";
import {ref} from "vue";

export function getAllAccounts() {
    const accounts = ref<AdminViewAccount[]>();
    const isLoading = ref(false);

    async function getAccounts() {
        isLoading.value = true;
        const response = await fetch(import.meta.env.VITE_API_URL + "/admin/accounts", {
            method: "GET",
        });

        if (!response.ok) {
            isLoading.value = false;
            return {accounts, isLoading};
        }

        accounts.value = await response.json();
    }

    async function banAccount(accountId: number) {
        isLoading.value = true;
        const response = await fetch(import.meta.env.VITE_API_URL + "/admin/account/ban/" + accountId, {
            method: "PUT",
        });

        if (!response.ok) {
            isLoading.value = false;
            return {accounts, isLoading};
        }

        await getAccounts();
    }

    void getAccounts();

    return {accounts, isLoading, banAccount};
}