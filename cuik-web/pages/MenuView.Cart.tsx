import { formatPrice } from '../utilities';

import {
    getCartTotal, groupCartLines, type CartCategory,
    type CartGroup, type CartLine
} from './MenuView.logic';

type CartProps = {
    cart: CartLine[];
    cartOpen: boolean;
    orderPlaced: boolean;
    onChangeQuantity: (id: string, delta: number) => void;
    onPlaceOrder: () => void;
};

type CartLineRowProps = {
    line: CartLine;
    onChangeQuantity: (id: string, delta: number) => void;
};

const CartLineRow = ({ line, onChangeQuantity }: CartLineRowProps) => (
    <li>
        <div>
            <span class="cart-item-name">{line.item.name}</span>
            <span class="cart-item-price">{formatPrice(line.item.price * line.quantity)}</span>
        </div>
        <div class="cart-item-actions">
            <button title="Quitar uno" onClick={() => onChangeQuantity(line.item.id, -1)}>−</button>
            <span>{line.quantity}</span>
            <button title="Agregar uno" onClick={() => onChangeQuantity(line.item.id, 1)}>+</button>
        </div>
    </li>
);

type CartCategorySectionProps = {
    category: CartCategory;
    onChangeQuantity: (id: string, delta: number) => void;
};

const CartCategorySection = ({ category, onChangeQuantity }: CartCategorySectionProps) => (
    <div class="cart-category">
        <h5>{category.name}</h5>
        <ul class="cart-items">
            {category.lines.map(line =>
                <CartLineRow key={line.item.id} line={line} onChangeQuantity={onChangeQuantity} />
            )}
        </ul>
    </div>
);

type CartGroupSectionProps = {
    group: CartGroup;
    onChangeQuantity: (id: string, delta: number) => void;
};

const CartGroupSection = ({ group, onChangeQuantity }: CartGroupSectionProps) => (
    <div class="cart-group">
        <h4>{group.menuName}</h4>
        {group.categories.map(category =>
            <CartCategorySection key={category.name} category={category} onChangeQuantity={onChangeQuantity} />
        )}
    </div>
);

export function MenuCart({ cart, cartOpen, orderPlaced, onChangeQuantity, onPlaceOrder }: CartProps) {
    const groups = groupCartLines(cart);
    const total = getCartTotal(cart);

    return (
        <aside class="cart" aria-hidden={!cartOpen}>
            <div class="cart-inner">
                <h3>Carrito</h3>
                {orderPlaced && <p class="green-text">¡Pedido realizado!</p>}
                {cart.length === 0
                    ? <p class="cart-empty">Tu carrito está vacío.</p>
                    : <>
                        <div class="cart-groups">
                            {groups.map(group =>
                                <CartGroupSection key={group.menuName} group={group} onChangeQuantity={onChangeQuantity} />
                            )}
                        </div>
                        <div class="cart-total">
                            <span>Total</span>
                            <strong>{formatPrice(total)}</strong>
                        </div>
                    </>
                }
                <button class="success cart-checkout" disabled={cart.length === 0} onClick={onPlaceOrder}>
                    Realizar pedido
                </button>
            </div>
        </aside>
    );
}
