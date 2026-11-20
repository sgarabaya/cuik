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
    `$${new Intl.NumberFormat('es-AR', { maximumFractionDigits: 2 }).format(price)}`;

const pad = (n: number) => String(n).padStart(2, '0');

const parseApiDate = (value: string | Date): Date =>
    typeof value === 'string'
        ? new Date(/[zZ]|[+-]\d{2}:?\d{2}$/.test(value) ? value : `${value}Z`)
        : value;

export function formatTimestamp(value: string | Date): string {
    const d = parseApiDate(value);

    return `${d.getFullYear()}-${pad(d.getMonth() + 1)}-${pad(d.getDate())}`
        + `T${pad(d.getHours())}:${pad(d.getMinutes())}:${pad(d.getSeconds())}`;
}

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
