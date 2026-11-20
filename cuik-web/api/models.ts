export interface BaseEntity {
    id: string;
    created?: Date;
}

export interface User extends BaseEntity {
    name: string;
    email: string;
    password?: string;
}

export interface Employee extends BaseEntity {
    name: string;
    email: string;
    password?: string;
    isAdmin?: boolean;
}

export interface Allergen extends BaseEntity {
    name: string;
}

export interface NutritionalFacts extends BaseEntity {
    portion: string;
    calories: number;
    protein: number;
    fats: number;
    isVegan: boolean;
    isGlutenFree: boolean;
}

export interface MenuItem extends BaseEntity {
    menuId?: string;
    name: string;
    category: string | null;
    description: string;
    price: number;
    available: boolean;
    estimatedPrepTime: number;
    nutritionalFacts: NutritionalFacts;
    allergens: Allergen[];
}

export interface Menu extends BaseEntity {
    name: string;
    isActive: boolean;
    items: MenuItem[];
}

export interface Purchase extends BaseEntity {
    userId: string;
    userName: string;
    userEmail: string;
    items: number;
    total: number;
}

export interface PurchaseItem {
    menuItemId: string;
    price: number;
    units: number;
}

export interface PurchasePayload {
    userId: string;
    items: PurchaseItem[];
}
