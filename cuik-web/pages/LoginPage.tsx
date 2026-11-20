import { useState } from 'preact/hooks';
import { useLocation } from 'wouter-preact';

import { useAuth } from '../auth/AuthContext';

export function LoginPage() {
    const [, navigate] = useLocation();
    const { login } = useAuth();
    const [email, setEmail] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const [error, setError] = useState<string | null>(null);

    const updateProp = (fn: Function) => (ev: InputEvent) => fn((ev.target as HTMLInputElement).value);
    const onKeyDown = (ev: KeyboardEvent) => { if (ev.key === 'Enter') doLogin(); };

    const doLogin = async () => {
        if (!email || email.length === 0)
            return setError('Ingrese todos los datos');

        if (!password || password.length === 0)
            return setError('Ingrese todos los datos');

        try {
            await login(email, password);
            navigate('/');
            window.location.reload();
        } catch (err) {
            console.error(err);
            setError(String(err));
        }
    };

    return <div class="login">
        <article>
            <h2 style="margin:0">Ingresar</h2>
            <input type="email" placeholder="Email" value={email} onInput={updateProp(setEmail)} onKeyDown={onKeyDown} />
            <input type="password" placeholder="Contraseña" onInput={updateProp(setPassword)} onKeyDown={onKeyDown} />
            <button class="success" onClick={doLogin}>Entrar</button>
            {error ? <span class="error">{error}</span> : null}
        </article>
    </div>;
}