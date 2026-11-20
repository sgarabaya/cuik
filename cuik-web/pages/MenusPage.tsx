import { useEffect, useState } from 'preact/hooks';
import { CirclePower, Square, SquareCheck, SquarePen, Trash } from 'lucide-preact';

import { Menu, menuService } from '../api';
import { Allergen, MenuItem } from '../api/models';

import { DataTable } from '../components/DataTable';
import { SquareButton } from '../components/SquareButton';

import {
    Beef, Eggs, Gluten,
    Milk, Nuts, Peanuts,
    Sesame, Shellfish, Soy
} from '../components/icons';

import { normalizeString } from '../utilities';

import { MenuItemEdit } from './MenuItemEdit';
import { MenuEdit } from './MenuEdit';

const Allergens = ({ items }: { items: Allergen[] }) => {
    return <div style="display:flex;flex-flow:wrap;max-width:5rem;justify-content:space-evenly">
        <Beef /><Eggs /><Gluten />
        <Milk /><Nuts /><Peanuts />
        <Sesame /><Shellfish /><Soy />
    </div>;
};

export function MenusPage() {
    const [entries, setEntries] = useState<Menu[]>([]);
    const [selectedMenu, setSelectedMenu] = useState<Menu | null>(null);
    const [selectedMenuItem, setSelectedMenuItem] = useState<MenuItem | null>(null);

    useEffect(() => {
        menuService
            .fetch()
            .then(e => {
                e.sort((a, b) => normalizeString(a.name) < normalizeString(b.name) ? -1 : 1);
                setEntries(e);
                setSelectedMenu(e[0]);
            })
            .catch(console.error);
    }, []);


    const [isEditingMenu, setIsEditingMenu] = useState<boolean>(false);
    const [isEditingItem, setIsEditingItem] = useState<boolean>(false);
    const closeDialog = () => { setIsEditingMenu(false); setIsEditingItem(false); }

    const handleCreateMenu = () => {
        setSelectedMenu(null);
        setIsEditingMenu(true);
    };
    const handleEditMenu = (e: Menu) => {
        setSelectedMenu(e);
        setIsEditingMenu(true);
    };

    const handleCreateItem = () => {
        setSelectedMenuItem(null);
        setIsEditingItem(true);
    };

    const handleEditItem = (e: MenuItem) => {
        setSelectedMenuItem(e);
        setIsEditingItem(true);
    };

    const handleDeleteItem = (e: MenuItem) => { };

    return <>
        <article style="max-width:calc(var(--max-width) + 100px)">
            <header>
                <h2>Menus</h2>
            </header>
            <div class="columns">
                <aside>
                    <ul class="list full-height">
                        {entries.map(e => <li
                            class={e.id === selectedMenu?.id ? 'selected' : ''}
                            onClick={() => setSelectedMenu(e)}
                        >
                            <span class="align-center">
                                {e.name} <CirclePower class={"small " + (e.isActive ? 'green-text' : 'red-text')} />
                            </span>
                        </li>)}
                        <li class="plus" onClick={handleCreateMenu}>+</li>
                    </ul>
                </aside>
                <div class={"flex justify-center " + (selectedMenu ? "" : "align-center")}>
                    {selectedMenu ?
                        <div class="full-width">
                            <section class="controls" style="margin-bottom:.5rem">
                                <div>
                                    <h3>Menu: {selectedMenu.name}</h3>
                                </div>
                                <div style="display:flex;gap:.5rem">
                                    <button onClick={() => handleEditMenu(selectedMenu)}>Editar</button>
                                    <button class="success" onClick={handleCreateItem}>Crear Item</button>
                                </div>
                            </section>
                            <DataTable
                                data={(selectedMenu?.items ?? [])}
                                renderActions={e => <div class="flex">
                                    <SquareButton icon={<SquarePen />} tooltip='Editar' onClick={() => handleEditItem(e)} />
                                    <SquareButton icon={<Trash />} tooltip='Eliminar' class="cancel" onClick={() => handleDeleteItem(e)} />
                                </div>}
                                columns={[
                                    { header: 'Nombre', key: 'name' },
                                    { header: 'Categoria', key: 'category' },
                                    { header: 'Precio', key: e => <span class="number">${e.price}</span> },
                                    { header: 'Disponible', key: e => e.available ? <SquareCheck /> : <Square />, class: 'center' },
                                    { header: 'Alérgenos', key: e => <Allergens items={e.allergens ?? []} />, class: 'center' }
                                ]}
                            />
                        </div>
                        : <h3>Seleccione un menu para ver el contenido</h3>
                    }
                </div>
            </div>
        </article>
        {isEditingItem && selectedMenu !== null ?
            <MenuItemEdit menuId={selectedMenu.id} entry={selectedMenuItem ?? undefined} close={closeDialog} />
            : null
        }
        {isEditingMenu ?
            <MenuEdit entry={selectedMenu ?? undefined} close={closeDialog} />
            : null
        }
    </>;
}