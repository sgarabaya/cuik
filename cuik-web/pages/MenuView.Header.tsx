import { ShoppingCart, UserKey } from 'lucide-preact';
import { Link } from 'wouter-preact';

import type { Menu } from '../api';

type MenuHeaderProps = {
    menus: Menu[];
    selectedMenu?: Menu;
    isAuthenticated: boolean;
    itemCount: number;
    onSelectMenu: (id: string) => void;
    onToggleCart: () => void;
};

export function MenuHeader({
    menus,
    selectedMenu,
    isAuthenticated,
    itemCount,
    onSelectMenu,
    onToggleCart
}: MenuHeaderProps) {
    return (
        <header class="menu-header">
            <div>
                <h1>Menú</h1>
                {menus.length > 1 &&
                    <div class="menu-tabs">
                        {menus.map(menu => (
                            <button
                                class={menu.id === selectedMenu?.id ? 'active' : ''}
                                onClick={() => onSelectMenu(menu.id)}
                            >{menu.name}</button>
                        ))}
                    </div>
                }
            </div>
            {isAuthenticated
                ? <button class="cart-toggle" title="Carrito" onClick={onToggleCart}>
                    <ShoppingCart />
                    <span class="cart-count">{itemCount}</span>
                </button>
                : <Link href="/login" class="icon-button"><abbr title="Ingresar"><UserKey /></abbr></Link>
            }
        </header>
    );
}
