import {ref} from "vue";
import type {Account} from "@/types/Account";
import {useJWToken} from "@/composables/useJWToken";

export function useAccount() {
    const adminAccount = ref<Account>();
    const isLoading = ref(false);
    const success = ref(false);

    function getAccount() {
        isLoading.value = true;
        const response = fetch(import.meta.env.VITE_API_URL + `/account/details`, {
            method: "GET",
        });

        response.then((response) => {
            if (!response.ok) {
                isLoading.value = false;
                success.value = false;
                return;
            } else {
                success.value = true;
            }

            response.json().then((account: Account) => {
                adminAccount.value = account;
                isLoading.value = false;
            });
        });
    }

    const payload = useJWToken();
    if (payload.value) {
        getAccount();
    }

    return {adminAccount, success};
}