import { useEffect, useState } from 'preact/hooks';
import { DataTable } from '../components/DataTable';
import { User } from '../api/models';
import { createUser, fetchUsers, updateUser } from '../api';

const EditUser = (props: { user?: User, close: Function }) => {
    const [name, setName] = useState<string>(props.user?.name ?? '');
    const [email, setEmail] = useState<string>(props.user?.email ?? '');
    const [password, setPassword] = useState<string>('');

    const save = async () => {
        const user: Partial<User> = { name, email };
        if (props.user) {
            user.id = props.user.id;

            if (password.length > 0)
                user.password = password;

            await updateUser(user);
        }
        else {
            await createUser({ name, email });
            user.password = password;
        }

        window.location.reload();
    };

    const updateName = (ev: InputEvent) => {
        setName((ev.target as HTMLInputElement).value);
    };

    const updateEmail = (ev: InputEvent) => {
        setEmail((ev.target as HTMLInputElement).value);
    };

    const updatePassword = (ev: InputEvent) => {
        setPassword((ev.target as HTMLInputElement).value);
    };

    return <dialog open>
        <article style="max-width:600px">
            <h2>Crear Usuario</h2>
            <form>
                <input type="text" name="name" class="full-width" placeholder="Nombre" onInput={updateName} />
                <input type="email" name="email" class="full-width" placeholder="Email" onInput={updateEmail} />
                <input type="password" name="password" class="full-width" placeholder="Password" onInput={updatePassword} />
            </form>

            <div class="controls-bar">
                <button class="cancel" onClick={() => props.close()}>Cerrar</button>
                <button class="success" onClick={save}>Guardar</button>
            </div>
        </article>
    </dialog>;
};

export function UsersPage() {
    const [users, setUsers] = useState<User[]>([]);
    const [showModal, setShowModal] = useState(false);

    const [search, setSearch] = useState<string>('');

    const loadUsers = async () => {
        try {
            setUsers(await fetchUsers());
        } catch (e) {
            console.error(e);
        }
    };

    useEffect(() => { loadUsers(); }, []);

    const handleNewUser = () => setShowModal(true);
    const closeDialog = () => setShowModal(false);

    return (
        <>
            <article>
                <header>
                    <h2>Usuarios
                        <button type="button" class="float-right" onClick={handleNewUser}>Crear Usuario</button>
                    </h2>
                    <div class="controls">
                        <input
                            type="text"
                            style="width:14rem"
                            placeholder="Buscar por nombre o email..."
                            value={search}
                            onInput={(e) => setSearch((e.target as HTMLInputElement).value)}
                        />
                    </div>
                </header>
                <div style="padding:.5rem">
                    <DataTable
                        data={users}
                        columns={[
                            { header: 'ID', key: 'id' },
                            { header: 'Nombre', key: 'name' },
                            { header: 'Email', key: 'email' }
                        ]}
                        renderActions={user => (
                            <>
                                {<span>No actions</span>}
                            </>
                        )}
                    />
                </div>
            </article>
            {
                showModal
                    ? <EditUser close={closeDialog} />
                    : null
            }
        </>
    );
}