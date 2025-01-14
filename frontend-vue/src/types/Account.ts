export type Account = {
    id: number;
    email?: string;
    password?: string;
    jwtToken?: string;
    userName: string;
    role: string;
    dateOfBirth?: string;
}