import { useState } from "preact/hooks";
import { employeeService, type Employee } from "../api";
import { Checkbox } from "../components/Checkbox";

export const EditEmployee = (props: { entry?: Employee, close: Function }) => {
    const [name, setName] = useState<string>(props.entry?.name ?? '');
    const [email, setEmail] = useState<string>(props.entry?.email ?? '');
    const [isAdmin, setIsAdmin] = useState<boolean>(props.entry?.isAdmin ?? false);
    const [password, setPassword] = useState<string>('');
    const [password2, setPassword2] = useState<string>('');

    const [error, setError] = useState<string>('');

    const save = async () => {
        if (password !== password2) {
            setError('Las contraseñas deben ser iguales');
            return;
        }

        const entry: Partial<Employee> = { name, email };
        if (props.entry) {
            entry.id = props.entry.id;

            if (password.length > 0)
                entry.password = password;

            if (isAdmin !== props.entry.isAdmin)
                entry.isAdmin = isAdmin;

            await employeeService.update(entry);
        }
        else {
            entry.password = password;
            entry.isAdmin = isAdmin;
            await employeeService.create(entry);
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
            <h2>{props.entry ? 'Editar' : 'Crear'}</h2>
            <form>
                <input type="text" class="full-width" placeholder="Nombre" onInput={updateName} value={name} />
                <input type="email" class="full-width" placeholder="Email" onInput={updateEmail} value={email} />

                <Checkbox
                    label="Es Admin"
                    value={isAdmin}
                    setValue={setIsAdmin}
                />

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
