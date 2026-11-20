import type { FunctionComponent } from 'preact';

import { Roles, type Role } from './auth/roles';
import { UsersPage } from './pages/UsersPage';
import { EmployeesPage } from './pages/EmployeesPage';
import { MenusPage } from './pages/MenusPage';
import { LoginPage } from './pages/LoginPage';
import { MenuView } from './pages/MenuView';

export type Viewer = Role | 'guest';
type ComponentFor = Partial<Record<Viewer, FunctionComponent>>;

export type AppRoute = {
    path: string;
    label?: string;
    component: FunctionComponent;
    componentFor?: ComponentFor;
    allowedRoles: Role[];
    isPublic?: boolean;
    guestOnly?: boolean;
};

const Home = () => <h1 class="logo">Cuik</h1>;
const PurchasesView = () => <h1>Historial</h1>;
const PurchasesPage = () => <h1>Purchases</h1>;

type Optionals = { isPublic?: boolean, guestOnly?: boolean, componentFor?: ComponentFor };
const _route = (path: string, label: string | undefined, component: FunctionComponent, allowedRoles: Role[], opts?: Optionals) => {
    return { path, label, component, allowedRoles, ...(opts ?? {}) };
};

export const routes: AppRoute[] = [
    _route('/', 'Inicio', MenuView, Roles, { isPublic: true, componentFor: { admin: Home, employee: Home } }),
    _route('/users', 'Usuarios', UsersPage, ['admin', 'employee']),
    _route('/employees', 'Empleados', EmployeesPage, ['admin']),
    _route('/menus', 'Menus', MenusPage, ['admin', 'employee']),
    _route('/purchases', 'Transacciones', PurchasesPage, ['admin', 'employee']),

    _route('/history', 'Historial', PurchasesView, ['user']),
    _route('/login', undefined, LoginPage, [], { isPublic: true, guestOnly: true }),
];

export const canAccess = (role: Role | null, route: AppRoute) => route.isPublic || (role !== null && route.allowedRoles.includes(role));
export const viewFor = (route: AppRoute, role: Role | null) => route.componentFor?.[role ?? 'guest'] ?? route.component;
