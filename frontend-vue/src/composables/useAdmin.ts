import type {Account} from "@/types/Account";
import {ref} from "vue";

export function getAllAccounts() {
    const accounts = ref<Account[]>();
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

        console.log(accounts.value);
    }

    void getAccounts();

    return {accounts, isLoading};
}