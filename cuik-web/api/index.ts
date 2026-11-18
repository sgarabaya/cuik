import { request } from './common';
import type { User, Employee, BaseEntity, Allergen, Menu, MenuItem } from './models';

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

// exports
export const userService = new UserService();
export const employeeService = new EmployeeService();
export const allergenService = new AllergenService();
export const menuService = new MenuService();
export const menuItemService = new MenuItemService();
export type { User, Employee, Allergen, Menu, MenuItem } from './models';
