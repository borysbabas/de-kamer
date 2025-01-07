import {onMounted, ref} from "vue";
import type {Post} from "@/types/Post";

export function usePostDetails(postId: unknown) {
    const isLoading = ref(false);
    const loadedPost = ref<Post>();

    async function fetchPost() {
        isLoading.value = true;
        const response = await fetch(import.meta.env.VITE_API_URL + `/posts/${postId}`, {
            method: "GET",
        });

        if (!response.ok) {
            isLoading.value = false;
            return {isLoading, loadedPost}
        }

        const post: Post = await response.json();

        isLoading.value = false;
        loadedPost.value = post;

        return {isLoading, loadedPost}
    }

    onMounted(()=> {
        void fetchPost();
    })

    return {isLoading, loadedPost};
}