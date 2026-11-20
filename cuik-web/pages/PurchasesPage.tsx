import { useEffect, useState } from "preact/hooks";
import { purchaseService } from "../api";
import { formatTimestamp, formatPrice, fuzzyFilter } from "../utilities";
import { DataTable } from "../components/DataTable";
import { useAuth } from "../auth/AuthContext";

export const PurchasesPage = () => {
    const { user } = useAuth();

    const isClient = user?.role === 'user';

    const [purchases, setPurchases] = useState<any[]>([]);
    const [search, setSearch] = useState<string>('');

    const updateProp = (fn: Function) => (ev: InputEvent) => fn((ev.target as HTMLInputElement).value);

    useEffect(() => {
        purchaseService.fetch()
            .then(v => {
                const sorted = v.sort((a, b) => a.created! < b.created! ? 1 : -1);
                setPurchases(sorted);
            })
            .catch(console.error);
    }, []);

    return <>
        <article>
            <header>
                <h2>Transacciones</h2>
                {isClient ? null :
                    <input
                        type="text"
                        style="width:14rem"
                        placeholder="Buscar por nombre o email..."
                        value={search}
                        onInput={updateProp(setSearch)}
                    />}
            </header>
            <div style="padding:.5rem">
                <DataTable
                    data={search ? fuzzyFilter(purchases, ['userName', 'userEmail'], search) : purchases}
                    columns={
                        isClient ? [
                            { header: 'Fecha', key: e => <span>{formatTimestamp(e.created!)}</span> },
                            { header: 'Items', key: 'items' },
                            { header: 'Total', key: e => <span>{formatPrice(e.total)}</span> },
                        ] : [
                            { header: 'Email Usuario', key: 'userEmail' },
                            { header: 'Fecha', key: e => <span>{formatTimestamp(e.created!)}</span> },
                            { header: 'Items', key: 'items' },
                            { header: 'Total', key: e => <span>{formatPrice(e.total)}</span> },
                        ]
                    }
                    renderActions={null}
                />
            </div>
        </article>
    </>;
};
