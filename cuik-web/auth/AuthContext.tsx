import { createContext, type ComponentChildren } from 'preact';
import { useContext, useState } from 'preact/hooks';

import { authService, type LoginInfo } from '../api';

type AuthValue = {
    user: LoginInfo | null;
    login: (email: string, password: string) => Promise<void>;
    logout: () => void;
};

const AuthContext = createContext<AuthValue | null>(null);

export function AuthProvider({ children }: { children: ComponentChildren }) {
    const [user, setUser] = useState<LoginInfo | null>(() => authService.getUser());

    const login = async (email: string, password: string) => {
        await authService.login(email, password);
        setUser(authService.getUser());
    };

    const logout = () => {
        authService.logout();
        setUser(null);
    };

    return <AuthContext.Provider value={{ user, login, logout }}>{children}</AuthContext.Provider>;
}

export function useAuth(): AuthValue {
    const value = useContext(AuthContext);

    if (!value) throw new Error('useAuth must be used inside <AuthProvider>');

    return value;
}
