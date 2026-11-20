import type { Menu, MenuItem } from '../api';

import { getMenuCategories, getQuantity, type CartLine } from './MenuView.logic';
import { MenuItemRow } from './MenuView.Item';

type MenuContentProps = {
    selectedMenu?: Menu;
    cart: CartLine[];
    isAuthenticated: boolean;
    onAdd: (item: MenuItem, menu: Menu) => void;
    onChangeQuantity: (id: string, delta: number) => void;
};

export function MenuContent({
    selectedMenu, cart,
    isAuthenticated, onAdd,
    onChangeQuantity
}: MenuContentProps) {
    const menu = selectedMenu;
    const categories = getMenuCategories(menu?.items ?? []);
    const quantityOf = (id: string) => getQuantity(cart, id);

    return (
        <div class="menu-content">
            {!menu && <p>No hay menús disponibles.</p>}

            {menu && (
                <div class="menu-categories">
                    {categories.length === 0 && <p>Este menú no tiene items.</p>}
                    {categories.map(([name, items]) => (
                        <section class="menu-category" key={name}>
                            <h3>{name}</h3>
                            <ul class="menu-items">
                                {items.map(item => (
                                    <MenuItemRow
                                        key={item.id}
                                        item={item}
                                        quantity={quantityOf(item.id)}
                                        canOrder={isAuthenticated && item.available}
                                        onAdd={() => onAdd(item, menu)}
                                        onChangeQuantity={delta => onChangeQuantity(item.id, delta)}
                                    />
                                ))}
                            </ul>
                        </section>
                    ))}
                </div>
            )}
        </div>
    );
}
