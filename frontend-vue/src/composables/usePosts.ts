import {ref} from 'vue';

export function usePosts() {
    const posts = ref<any[]>([]);
    const paginatedPosts = ref<any[]>([]);
    const currentPage = ref(1);
    const postsPerPage = 8;
    const totalPages = ref(0);

    async function fetchPosts() {
        try {
            const response = await fetch('http://localhost:3001/posts');
            const data = await response.json();
            data.forEach((post: any) => {
                post.dateCreated = new Date(post.dateCreated).toLocaleString();
            });
            posts.value = data;
            totalPages.value = Math.ceil(posts.value.length / postsPerPage);
            paginatePosts();
        } catch (error) {
            console.error('Error fetching posts:', error);
        }
    }

    function paginatePosts() {
        const start = (currentPage.value - 1) * postsPerPage;
        const end = start + postsPerPage;
        paginatedPosts.value = posts.value.slice(start, end);
    }

    function goToPage(page: number) {
        if (page > 0 && page <= totalPages.value) {
            currentPage.value = page;
            paginatePosts();
        }
    }

    return {
        posts: paginatedPosts,
        currentPage,
        totalPages,
        fetchPosts,
        goToPage,
    };
}