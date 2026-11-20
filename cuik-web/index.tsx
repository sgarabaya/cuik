import { render } from 'preact';
import { Redirect, Route, Router, Switch } from 'wouter-preact';
import { useHashLocation } from 'wouter-preact/use-hash-location';

import { AuthProvider } from './auth/AuthContext';
import { canAccess, routes, viewFor } from './routes';

import { Navbar } from './components/Navbar';
import { authService } from './api';

function App() {
    const user = authService.getUser();

    return (
        <Router hook={useHashLocation}>
            <AuthProvider>
                <Navbar />
                <main>
                    <Switch>
                        {routes.map(route => {
                            const View = viewFor(route, user?.role ?? null);
                            const allowed = canAccess(user?.role ?? null, route) && !(route.guestOnly && user);

                            return (
                                <Route key={route.path} path={route.path}>
                                    {allowed ? <View /> : <Redirect to="/" />}
                                </Route>
                            );
                        })}
                        <Route><Redirect to="/" /></Route>
                    </Switch>
                </main>
            </AuthProvider>
        </Router>
    );
}

document.addEventListener('DOMContentLoaded', () => {
    const currentTheme = localStorage.getItem('theme') || (window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light');
    document.documentElement.setAttribute('data-theme', currentTheme);

    render(<App />, document.body);
});
