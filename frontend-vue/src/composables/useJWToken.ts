import type {TokenPayload} from "@/types/TokenPayload";
import {ref} from "vue";

export function useJWToken() {
    const payload = ref<TokenPayload>();

    /**
     * Function that handles the logic for signing out the current client.
     * @returns {TokenPayload} - The payload of the token.
     * @param token
     */
    function getPayloadFromToken(token: string): TokenPayload {
        const payloadPart = token.split('.')[1];
        const decodedPayload = atob(payloadPart);
        const parsedPayload = JSON.parse(decodedPayload) as TokenPayload;
        payload.value = parsedPayload;

        return parsedPayload;
    }

    const token = localStorage.getItem("JWT_STORAGE_ITEM");
    if (token) {
        getPayloadFromToken(token);
    }

    return payload;
}