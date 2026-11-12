import { render } from 'preact';
import { Router, Route, Switch } from 'wouter-preact';
import { useHashLocation } from 'wouter-preact/use-hash-location';

import { Navbar } from './components/Navbar';

import { UsersPage } from './pages/UsersPage';
import { EmployeesPage } from './pages/EmployeesPage';
const MenusPage = (props: any) => <h1>Menus</h1>;
const TablesPage = (props: any) => <h1>Tables</h1>;
const PurchasesPage = (props: any) => <h1>Purchases</h1>;

const Home = () => <article class="flex align-center justify-center">
    <section>
        <h1>Admin System</h1>
        <p>Welcome to the admin dashboard. Please select a section from the menu above.</p>
    </section>
</article>;

function App() {
    return (
        <Router hook={useHashLocation}>
            <Navbar />
            <main>
                <Switch>
                    <Route path="/" component={Home} />
                    <Route path="/users" component={UsersPage} />
                    <Route path="/employees" component={EmployeesPage} />
                    <Route path="/menus" component={MenusPage} />
                    <Route path="/tables" component={TablesPage} />
                    <Route path="/purchases" component={PurchasesPage} />
                </Switch>
            </main>
        </Router>
    );
}

document.addEventListener('DOMContentLoaded', () => {
    const currentTheme = localStorage.getItem('theme') || (window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light');
    document.documentElement.setAttribute('data-theme', currentTheme);

    render(<App />, document.body);
});