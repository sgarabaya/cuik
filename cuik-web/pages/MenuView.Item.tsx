import type { MenuItem } from '../api';
import { formatPrice } from '../utilities';

type MenuItemRowProps = {
    item: MenuItem;
    quantity: number;
    canOrder: boolean;
    onAdd: () => void;
    onChangeQuantity: (delta: number) => void;
};

export function MenuItemRow({ item, quantity, canOrder, onAdd, onChangeQuantity }: MenuItemRowProps) {
    return (
        <li class={item.available ? '' : 'unavailable'}>
            <div>
                <span class="menu-item-name">{item.name}</span>
                {item.description && <p class="menu-item-description">{item.description}</p>}
            </div>
            <div class="menu-item-aside">
                <span class="menu-item-price">{formatPrice(item.price)}</span>
                {canOrder &&
                    <div class="menu-item-actions">
                        <button
                            title="Quitar uno"
                            disabled={quantity === 0}
                            onClick={() => onChangeQuantity(-1)}
                        >−</button>
                        <span>{quantity}</span>
                        <button
                            title="Agregar uno"
                            onClick={onAdd}
                        >+</button>
                    </div>
                }
                {!item.available && <span class="menu-item-badge">No disponible</span>}
            </div>
        </li>
    );
}
