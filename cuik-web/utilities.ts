import Fuse from 'fuse.js';

export function fuzzyFilter<T>(entries: T[], keys: string[], query: string): T[] {
    const fuse = new Fuse(entries, {
        keys: keys.map(k => ({ name: k, weight: 1 })),
        threshold: 0.1,
        distance: 500,
        ignoreDiacritics: true,
        includeScore: true,
        ignoreLocation: true,
        minMatchCharLength: 2,
    });

    //fuse returns in proper order by default
    const results = fuse.search(query);

    return results.map(r => r.item);
}

// Localization
export const formatPrice = (price: number): string =>
    `$ ${new Intl.NumberFormat('es-AR', { maximumFractionDigits: 2 }).format(price)}`;

export const formatDate = (date: Date): string => Intl.DateTimeFormat('es-AR').format(date);

export function normalizeString(str: string): string {
    return str
        .normalize("NFD")
        .replace(/[\u0300-\u036f]/g, "")
        .toLowerCase()
        .replace(/[^a-z0-9]/g, "");
}

export const compareText = (a: string, b: string): number => {
    const left = normalizeString(a);
    const right = normalizeString(b);
    return left < right ? -1 : left > right ? 1 : 0;
};
