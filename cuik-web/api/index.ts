import { getToken, request, setToken } from './common';
import { toRole, type Role } from '../auth/roles';
import type { User, Employee, BaseEntity, Allergen, Menu, MenuItem } from './models';

export interface LoginInfo {
    id: string;
    name: string;
    email: string;
    role: Role;
}

//Base CRUD client
abstract class Service<T extends BaseEntity> {
    protected route: string;
    constructor(route: string) {
        this.route = route;
    }
    fetch() {
        return request<T[]>(this.route);
    }
    fetchById(id: string) {
        return request<T | null>(`${this.route}/${id}`);
    }
    create(el: Partial<T>) {
        return request<T | null>(this.route, { body: el, method: 'POST' });
    }
    update(el: Partial<T>) {
        return request<T>(`${this.route}/${el.id}`, { body: el, method: 'PUT' });
    }
    delete(el: T) {
        return request<boolean>(`${this.route}/${el.id}`, { method: 'DELETE' });
    }
}

class UserService extends Service<User> {
    constructor() { super("users"); }
}
class EmployeeService extends Service<Employee> {
    constructor() { super("employees"); }
}
class AllergenService extends Service<Allergen> {
    constructor() { super("allergens"); }
}
class MenuService extends Service<Menu> {
    constructor() { super("menus"); }
}
class MenuItemService extends Service<MenuItem> {
    constructor() { super("menu_items"); }
}

const parseToken = (token: string): LoginInfo | null => {
    try {
        const { id, name, email, role } = JSON.parse(atob(token.split('.')[1]));
        return { id, name, email, role: toRole(role) };
    } catch {
        return null;
    }
};

class AuthService {
    isLoggedIn() {
        const value = getToken();

        return value && value.length > 0;
    }

    getUser(): LoginInfo | null {
        const token = getToken();

        return token ? parseToken(token) : null;
    }

    async login(email: string, password: string) {
        const token = await request<string>('auth/login', { method: 'POST', body: { email, password } });
        setToken(token);
        return true;
    }

    logout() {
        setToken(null);
    }
}

// exports
export const authService = new AuthService();
export const userService = new UserService();
export const employeeService = new EmployeeService();
export const allergenService = new AllergenService();
export const menuService = new MenuService();
export const menuItemService = new MenuItemService();
export type { User, Employee, Allergen, Menu, MenuItem } from './models';
