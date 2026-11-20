import { useEffect, useState } from 'preact/hooks';

import { useAuth } from '../auth/AuthContext';
import { menuService, type Menu, type MenuItem } from '../api';

import {
    addToCart,
    changeQuantity,
    getActiveMenus,
    getCartCount,
    type CartLine
} from './MenuView.logic';
import { MenuHeader } from './MenuView.Header';
import { MenuContent } from './MenuView.Content';
import { MenuCart } from './MenuView.Cart';

export function MenuView() {
    const { user } = useAuth();
    const [menus, setMenus] = useState<Menu[]>([]);
    const [selectedId, setSelectedId] = useState<string | null>(null);

    const [cart, setCart] = useState<CartLine[]>([]);
    const [cartOpen, setCartOpen] = useState(false);
    const [orderPlaced, setOrderPlaced] = useState(false);

    useEffect(() => {
        menuService.fetch()
            .then(result => {
                const active = getActiveMenus(result);
                setMenus(active);
                setSelectedId(active[0]?.id ?? null);
            })
            .catch(console.error);
    }, []);

    useEffect(() => { if (!user) setCart([]); }, [user]);

    const selectedMenu = menus.find(menu => menu.id === selectedId);

    const addItem = (item: MenuItem, menu: Menu) => {
        setCart(lines => addToCart(lines, item, menu));
        setOrderPlaced(false);
    };

    const updateItemQuantity = (id: string, delta: number) =>
        setCart(lines => changeQuantity(lines, id, delta));

    const placeOrder = () => {
        //TODO: llamar POST /api/purchase
        setCart([]);
        setOrderPlaced(true);
    };

    return (
        <article class="menu-view" style="max-width:calc(var(--max-width) + 100px)">
            <MenuHeader
                menus={menus}
                selectedMenu={selectedMenu}
                isAuthenticated={!!user}
                itemCount={getCartCount(cart)}
                onSelectMenu={setSelectedId}
                onToggleCart={() => setCartOpen(open => !open)}
            />
            <div class={`menu-layout${user && cartOpen ? ' cart-open' : ''}`}>
                <MenuContent
                    selectedMenu={selectedMenu}
                    cart={cart}
                    isAuthenticated={!!user}
                    onAdd={addItem}
                    onChangeQuantity={updateItemQuantity}
                />

                {user && <MenuCart
                    cart={cart}
                    cartOpen={cartOpen}
                    orderPlaced={orderPlaced}
                    onChangeQuantity={updateItemQuantity}
                    onPlaceOrder={placeOrder}
                />}
            </div>
        </article>
    );
}
