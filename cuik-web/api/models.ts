export interface User {
    id: string;
    name: string;
    created: Date;
    email: string;
    password?: string;
}