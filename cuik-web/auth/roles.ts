export type Role = 'admin' | 'employee' | 'user';

export const Roles: Role[] = ['admin', 'employee', 'user'];

export const toRole = (value?: string): Role => {
    if (value === 'admin' || value === 'employee') return value;
    return 'user';
};
