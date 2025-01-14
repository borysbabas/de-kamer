import {onMounted, ref} from "vue";
import type {Post} from "@/types/Post";

/**
 * Function that handles the logic for fetching the details of a post.
 * @returns {isLoading, loadedPost}
 * @param postId the id of the post to fetch
 */
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

/**
 * Function that handles the logic for deleting a post.
 * @returns {isDeleting, deleteError, deletePost}
 * @param postId the id of the post to delete
 */
export function usePostDeletion(postId: unknown) {
    const isDeleting = ref(false);
    const deleteError = ref<string | null>(null);

    async function deletePost() {
        isDeleting.value = true;
        const response = await fetch(import.meta.env.VITE_API_URL + `/account/posts/delete/${postId}`, {
            method: "DELETE",
        });

        if (!response.ok) {
            isDeleting.value = false;
            deleteError.value = "Error deleting post";
            return {isDeleting, deleteError}
        } else {
            deleteError.value = null;
        }

        isDeleting.value = false;

        return {isDeleting, deleteError}
    }

    return {isDeleting, deleteError, deletePost};
}