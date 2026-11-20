import { useEffect, useState } from "preact/hooks";
import { CircleCheck, CircleX, SquarePen, Trash } from "lucide-preact";

import { Employee, employeeService } from "../api";

import { DataTable } from "../components/DataTable";
import { fuzzyFilter, normalizeString } from "../utilities";
import { SquareButton } from "../components/SquareButton";

import { EditEmployee } from "./EmployeesPage.Edit";

export function EmployeesPage() {
    const [data, setData] = useState<Employee[]>([]);
    const [showModal, setShowModal] = useState(false);
    const [selectedEmployee, setSelectedEmployee] = useState<Employee | undefined>();

    const [search, setSearch] = useState<string>('');
    const [role, setRole] = useState<boolean | null>(null);

    useEffect(() => {
        employeeService.fetch()
            .then(v => {
                const sorted = v.sort((a, b) => normalizeString(a.name) < normalizeString(b.name) ? -1 : 1);
                setData(sorted);
            })
            .catch(console.error);
    }, []);

    const closeDialog = () => { setShowModal(false); setSelectedEmployee(undefined); };

    const handleCreate = () => { setSelectedEmployee(undefined); setShowModal(true); };
    const handleUpdate = (entry: Employee) => { setSelectedEmployee(entry); setShowModal(true); };
    const handleDelete = async (entry: Employee) => {
        if (!confirm(`Eliminar a ${entry.name}?`))
            return;
        if (await employeeService.delete(entry))
            window.location.reload();
    };

    const handleSetRole = (ev: InputEvent) => {
        const value = (ev.target as HTMLInputElement).value;
        setRole(({ all: null, employee: false, admin: true })[value]!);
    };

    const getFilteredEntries = () => {
        let filtered = data;

        if (role !== null)
            filtered = filtered.filter(e => e.isAdmin === role);

        if (search)
            filtered = fuzzyFilter(filtered, ['name', 'email'], search);

        return filtered;
    };

    return (
        <>
            <article>
                <header>
                    <h2>Empleados</h2>
                    <div class="controls">
                        <div style="display:flex">
                            <input
                                type="text"
                                style="width:220px"
                                placeholder="Buscar por nombre o email..."
                                value={search}
                                onInput={(e) => setSearch((e.target as HTMLInputElement).value)}
                            />
                            <select style="width:105px" onInput={handleSetRole}>
                                <option value="all" selected={role === null}>Todos</option>
                                <option value="employee" selected={role === false}>Empleado</option>
                                <option value="admin" selected={role === true}>Admin</option>
                            </select>
                        </div>

                        <button onClick={handleCreate}>Crear</button>
                    </div>
                </header>
                <div style="padding:.5rem">
                    <DataTable
                        data={getFilteredEntries()}
                        columns={[
                            { header: 'ID', key: 'id' },
                            { header: 'Nombre', key: 'name' },
                            { header: 'Email', key: 'email' },
                            { header: 'Es Admin', key: e => (e.isAdmin === true ? <CircleCheck style="color:var(--success)" /> : <CircleX style="color:var(--danger)" />) }
                        ]}
                        renderActions={entry => (
                            <div class="flex">
                                <SquareButton icon={<SquarePen />} tooltip='Editar' onClick={() => handleUpdate(entry)} />
                                <SquareButton icon={<Trash />} tooltip='Eliminar' class="cancel" onClick={() => handleDelete(entry)} />
                            </div>
                        )}
                    />
                </div>
            </article>
            {showModal ? <EditEmployee entry={selectedEmployee} close={closeDialog} /> : null}
        </>
    );
}