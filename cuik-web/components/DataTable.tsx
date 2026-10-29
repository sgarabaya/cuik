import { JSX } from 'preact';

interface Column<T> {
    header: string;
    key: string | ((item: T) => string);
}

interface DataTableProps<T extends { id: string }> {
    data: T[];
    columns: Column<T>[];
    renderActions: (item: T) => JSX.Element;
}

export function DataTable<T extends { id: string }>(props: DataTableProps<T>) {
    const { data, columns, renderActions } = props;

    return (
        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        {columns.map(col => (
                            <th key={typeof col.key === 'string' ? col.key : col.header}>{col.header}</th>
                        ))}
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    {data.map((item) => (
                        <tr key={item.id}>
                            {columns.map((col, index) => (
                                <td key={index}>
                                    {typeof col.key === 'function' ? col.key(item) : (item[col.key as keyof T] as string)}
                                </td>
                            ))}
                            <td>{renderActions(item)}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}
