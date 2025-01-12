export type Post = {
    postId: number;
    title: string;
    description: string;
    dateCreated: Date;
    account: {
        dateOfBirth: Date;
        email: string;
        id: number;
        role: string;
        userName: string;
    };
}