import { Fragment } from 'preact';
import { Link, useLocation } from 'wouter-preact';
import { LogOut, UserKey } from 'lucide-preact';

import { useAuth } from '../auth/AuthContext';
import { canAccess, routes } from '../routes';

type NavbarItemProps = { route: string, label: string };

export function Navbar() {
    const [location] = useLocation();
    const { user, logout } = useAuth();
    const calcClass = (path: string) => location === path ? 'navbar-active' : '';

    const visible = routes.filter(route => route.label && canAccess(user?.role ?? null, route));

    if (!user)
        return null;

    return (
        <nav>
            <div class="items">
                {visible.map((route, index) => (
                    <Fragment key={route.path}>
                        {index > 0 && <span class="nav-separator">/</span>}
                        <Link href={route.path} class={calcClass(route.path)}>{route.label}</Link>
                    </Fragment>
                ))}
            </div>
            {
                user && <div class="flex align-center">
                    <span>{user.email}</span>
                    <a onClick={logout} class="interactive"><LogOut /></a>
                </div>
            }
        </nav>
    );
}
