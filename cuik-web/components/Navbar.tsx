import { Link, useLocation } from 'wouter-preact';

type NavbarItemProps = { route: string, label: string };

export function Navbar() {
    const [location] = useLocation();
    const calcClass = (path: string) => location === path ? 'navbar-active' : '';

    const NavbarItem = ({ route, label }: NavbarItemProps) => <>
        <Link href={route} class={calcClass(route)}>{label}</Link>
        <span class="nav-separator">/</span>
    </>;

    return (
        <nav>
            <NavbarItem route="/" label="Inicio" />
            <NavbarItem route="/users" label="Usuarios" />
            <NavbarItem route="/employees" label="Empleados" />
            <NavbarItem route="/menus" label="Menus" />
            <NavbarItem route="/tables" label="Mesas" />
            <NavbarItem route="/purchases" label="Transacciones" />
        </nav>
    );
}
