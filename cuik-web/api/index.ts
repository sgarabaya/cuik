import { User } from "./models";

export async function fetchUsers(): Promise<User[]> {
    const response = await fetch('/api/users');

    if (response.ok)
        return await response.json() as User[];

    return [];
}

export async function createUser(user: Partial<User>): Promise<string | null> {
    const response = await fetch('/api/users', {
        method: 'POST',
        body: JSON.stringify(user),
        headers: { 'Content-Type': 'application/json' }
    });

    if (response.ok)
        return await response.json() as string;

    console.error(await response.text());
    return null;
}

export async function updateUser(user: Partial<User>): Promise<boolean> {
    const response = await fetch('/api/users', {
        method: 'PUT',
        body: JSON.stringify(user),
        headers: { 'Content-Type': 'application/json' }
    });

    if (response.ok)
        return true;

    console.error(await response.text());
    return false;
}

export async function deleteUser(id: string): Promise<boolean> {
    const response = await fetch(`/api/users/${id}`, {
        method: 'DELETE'
    });

    if (response.ok)
        return true;

    console.error(await response.text());
    return false;
}
