export interface BaseEntity {
    id: string;
    created: Date;
}

export interface User extends BaseEntity {
    name: string;
    email: string;
    password?: string;
}

export interface Employee extends BaseEntity {
    name: string;
    email: string;
    password?: string;
    isAdmin?: boolean;
}
