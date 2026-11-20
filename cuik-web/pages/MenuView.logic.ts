import type { Menu, MenuItem } from '../api';
import { normalizeString } from '../utilities';

export type CartLine = {
    item: MenuItem;
    menuId: string;
    menuName: string;
    category: string;
    quantity: number;
};

export type CartCategory = { name: string, lines: CartLine[] };

export type CartGroup = { menuName: string, categories: CartCategory[] };

export type MenuCategory = [name: string, items: MenuItem[]];

const DEFAULT_CATEGORY = 'Otros';

export function getActiveMenus(menus: Menu[] | null | undefined): Menu[] {
    return (menus ?? [])
        .filter(menu => menu.isActive)
        .sort((a, b) => normalizeString(a.name).localeCompare(normalizeString(b.name)));
}

export const getCategoryName = (item: MenuItem): string =>
    item.category?.trim() || DEFAULT_CATEGORY;

export function getMenuCategories(items: MenuItem[]): MenuCategory[] {
    const categories: MenuCategory[] = [];
    const byName = new Map<string, MenuItem[]>();

    for (const item of items) {
        const name = getCategoryName(item);
        let categoryItems = byName.get(name);

        if (!categoryItems) {
            categoryItems = [];
            byName.set(name, categoryItems);
            categories.push([name, categoryItems]);
        }

        categoryItems.push(item);
    }

    return categories.sort(([a], [b]) => normalizeString(a).localeCompare(normalizeString(b)));
}

export function groupCartLines(lines: CartLine[]): CartGroup[] {
    const groups: CartGroup[] = [];
    const groupsByMenu = new Map<string, CartGroup>();

    for (const line of lines) {
        let group = groupsByMenu.get(line.menuId);

        if (!group) {
            group = { menuName: line.menuName, categories: [] };
            groupsByMenu.set(line.menuId, group);
            groups.push(group);
        }

        let category = group.categories.find(entry => entry.name === line.category);

        if (!category) {
            category = { name: line.category, lines: [] };
            group.categories.push(category);
        }

        category.lines.push(line);
    }

    return groups;
}

export const getCartCount = (lines: CartLine[]): number =>
    lines.reduce((total, line) => total + line.quantity, 0);

export const getCartTotal = (lines: CartLine[]): number =>
    lines.reduce((sum, line) => sum + line.item.price * line.quantity, 0);

export const getQuantity = (lines: CartLine[], itemId: string): number =>
    lines.find(line => line.item.id === itemId)?.quantity ?? 0;

export function addToCart(lines: CartLine[], item: MenuItem, menu: Menu): CartLine[] {
    if (lines.some(line => line.item.id === item.id))
        return lines.map(line => line.item.id === item.id
            ? { ...line, quantity: line.quantity + 1 }
            : line);

    return [...lines, {
        item,
        menuId: menu.id,
        menuName: menu.name,
        category: getCategoryName(item),
        quantity: 1
    }];
}

export function changeQuantity(lines: CartLine[], itemId: string, delta: number): CartLine[] {
    return lines
        .map(line => line.item.id === itemId ? { ...line, quantity: line.quantity + delta } : line)
        .filter(line => line.quantity > 0);
}
