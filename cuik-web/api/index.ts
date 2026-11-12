import { request } from './common';
import type { User, Employee, BaseEntity } from './models';

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
export const userService = new UserService();

class EmployeeService extends Service<Employee> {
    constructor() { super("employees"); }
}
export const employeeService = new EmployeeService();

export type { User, Employee } from './models';
