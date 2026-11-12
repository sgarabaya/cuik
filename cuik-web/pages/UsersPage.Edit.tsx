import { useState } from "preact/hooks";
import { userService, type User } from "../api";

export const EditUser = (props: { user?: User, close: Function }) => {
    const [name, setName] = useState<string>(props.user?.name ?? '');
    const [email, setEmail] = useState<string>(props.user?.email ?? '');
    const [password, setPassword] = useState<string>('');
    const [password2, setPassword2] = useState<string>('');

    const [error, setError] = useState<string>('');

    const save = async () => {
        if (password !== password2) {
            setError('Las contraseñas deben ser iguales');
            return;
        }

        const user: Partial<User> = { name, email };
        if (props.user) {
            user.id = props.user.id;

            if (password.length > 0)
                user.password = password;

            await userService.update(user);
        }
        else {
            user.password = password;
            await userService.create(user);
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

    const updatePassword2 = (ev: InputEvent) => {
        setPassword2((ev.target as HTMLInputElement).value);
    };

    return <dialog open>
        <article style="max-width:350px" class={error.length > 0 ? 'red-border' : ''}>
            <h2>{props.user ? 'Editar Usuario' : 'Crear Usuario'}</h2>
            <form>
                <input type="text" name="name" class="full-width" placeholder="Nombre" onInput={updateName} value={name} />
                <input type="email" name="email" class="full-width" placeholder="Email" onInput={updateEmail} value={email} />
                <input type="password" name="password" class="full-width" placeholder="Contraseña" onInput={updatePassword} />
                {
                    password.length > 0 ? (
                        <input type="password" name="password-2" class="full-width" placeholder="Confirmar contraseña" onInput={updatePassword2} value={password2} />
                    ) : null
                }
            </form>

            {error.length > 0
                ? <>
                    <span class="red-text">{error}</span>
                    <br /><br />
                </>
                : null
            }

            <div class="controls-bar">
                <button class="cancel" onClick={() => props.close()}>Cerrar</button>
                <button class="success" onClick={save}>Guardar</button>
            </div>
        </article>
    </dialog>;
};
