import { useEffect, useState } from 'preact/hooks';
import { SquarePen, Trash } from 'lucide-preact';

import { userService, type User } from '../api';

import { EditUser } from './UsersPage.Edit';

import { SquareButton } from '../components/SquareButton';
import { DataTable } from '../components/DataTable';
import { fuzzyFilter, normalizeString } from '../utilities';

export function UsersPage() {
    const [users, setUsers] = useState<User[]>([]);
    const [showModal, setShowModal] = useState(false);
    const [selectedUser, setSelectedUser] = useState<User>();

    const [search, setSearch] = useState<string>('');

    useEffect(() => {
        userService.fetch()
            .then(v => {
                const sorted = v.sort((a, b) => normalizeString(a.name) < normalizeString(b.name) ? -1 : 1);
                setUsers(sorted);
            })
            .catch(console.error);
    }, []);

    const handleNewUser = () => {
        setSelectedUser(undefined);
        setShowModal(true);
    };

    const handleEditUser = (user: User) => {
        setSelectedUser(user);
        setShowModal(true);
    };

    const handleDeleteUser = async (user: User) => {
        if (!confirm(`Eliminar a ${user.name}?`))
            return;

        if (await userService.delete(user))
            window.location.reload();
    };

    const closeDialog = () => {
        setShowModal(false);
        setSelectedUser(undefined);
    };

    return (
        <>
            <article>
                <header>
                    <h2>Usuarios</h2>
                    <div class="controls">
                        <input
                            type="text"
                            style="width:14rem"
                            placeholder="Buscar por nombre o email..."
                            value={search}
                            onInput={(e) => setSearch((e.target as HTMLInputElement).value)}
                        />
                        <button onClick={handleNewUser}>Crear</button>
                    </div>
                </header>
                <div style="padding:.5rem">
                    <DataTable
                        data={search ? fuzzyFilter(users, ['name', 'email'], search) : users}
                        columns={[
                            { header: 'ID', key: 'id' },
                            { header: 'Nombre', key: 'name' },
                            { header: 'Email', key: 'email' }
                        ]}
                        renderActions={user => (
                            <div class="flex">
                                <SquareButton icon={<SquarePen />} tooltip='Editar' onClick={() => handleEditUser(user)} />
                                <SquareButton icon={<Trash />} tooltip='Eliminar' class="cancel" onClick={() => handleDeleteUser(user)} />
                            </div>
                        )}
                    />
                </div>
            </article>
            {showModal ? <EditUser user={selectedUser} close={closeDialog} /> : null}
        </>
    );
}