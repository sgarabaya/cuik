const BASE: string = '/api';
const TIMEOUT_MS = 10_000;
const TOKEN_KEY = 'token';

export class ApiError extends Error {
    constructor(public status: number, message: string) {
        super(message);
        this.name = 'ApiError';
    }
}

export type Options = Omit<RequestInit, 'body'> & { body?: unknown, method?: 'GET' | 'POST' | 'PUT' | 'DELETE' };

// throws ApiError for non-2xx responses
export async function request<T>(path: string, options: Options = {}): Promise<T> {
    const headers = new Headers(options.headers);

    if (options.body !== undefined)
        headers.set('Content-Type', 'application/json');

    const token = getToken();
    if (token)
        headers.set('Authorization', `Bearer ${token}`);

    const response = await fetch(`${BASE}/${path}`, {
        ...options,
        headers,
        signal: options.signal ?? AbortSignal.timeout(TIMEOUT_MS),
        body: options.body === undefined ? undefined : JSON.stringify(options.body)
    });

    if (!response.ok) {
        const payload = await response.json().catch(() => null);
        throw new ApiError(response.status, payload?.error ?? response.statusText);
    }

    // DELETE and null responses are returned as 204 No Content.
    if (response.status === 204)
        return undefined as T;

    const text = (await response.text())?.trim();
    return (text ? JSON.parse(text) : undefined) as T;
}

// Builds a query string, skipping undefined/null values
export function query(params: Record<string, unknown>): string {
    const search = new URLSearchParams();

    for (const [key, value] of Object.entries(params))
        if (value !== undefined && value !== null)
            search.set(key, String(value));

    const result = search.toString();
    return result ? `?${result}` : '';
}

export const getToken = (): string | null => localStorage.getItem(TOKEN_KEY);
export const setToken = (token: string | null): void => {
    if (token) localStorage.setItem(TOKEN_KEY, token);
    else localStorage.removeItem(TOKEN_KEY);
};
