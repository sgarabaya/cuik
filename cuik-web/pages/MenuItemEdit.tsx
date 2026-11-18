import { useState } from "preact/hooks";
import { MenuItem, menuItemService } from "../api";
import { Checkbox } from "../components/Checkbox";

export interface MenuItemEditProps {
    entry?: MenuItem;
    menuId: string,
    close: Function;
}

export const MenuItemEdit = ({ entry, menuId, close }: MenuItemEditProps) => {
    const [name, setName] = useState<string>(entry?.name ?? '');
    const [category, setCategory] = useState<string>(entry?.category ?? '');
    const [description, setDescription] = useState<string>(entry?.description ?? '');
    const [price, setPrice] = useState<number>(entry?.price ?? 0);
    const [available, setAvailable] = useState<boolean>(entry?.available ?? false);
    const [estimatedPrepTime, setEstimatedPrepTime] = useState<number>(entry?.estimatedPrepTime ?? 5);

    const [error, setError] = useState<string>('');

    const save = async () => {
        if (price === 0) {
            setError('El precio de un item no puede ser 0');
            return;
        }

        const item: Partial<MenuItem> = {
            menuId,
            name, category, description,
            price, available, estimatedPrepTime
        };

        if (entry) {
            item.id = entry.id;
            await menuItemService.update(item);
        }
        else
            await menuItemService.create(item);

        window.location.reload();
    };

    const updateProp = (fn: Function) => {
        return (ev: InputEvent) => fn((ev.target as HTMLInputElement).value);
    };

    return <dialog open>
        <article style="max-width:450px" class={error.length > 0 ? 'red-border' : ''}>
            <h2>{entry ? 'Editar' : 'Crear'}</h2>
            <form>
                <input type="text" class="full-width" placeholder="Nombre" onInput={updateProp(setName)} value={name} />
                <div class="controls" style="gap:.5rem">
                    <input
                        type="number"
                        class="full-width"
                        placeholder="Precio"
                        onInput={updateProp(setPrice)} value={price}
                    />
                    <Checkbox value={entry?.available ?? false}
                        label="Disponible"
                        setValue={setAvailable}
                    />
                </div>
                <input type="text" class="full-width" placeholder="Categoria" onInput={updateProp(setCategory)} value={category} />
                <textarea rows={6} value={description} lang="es" onInput={updateProp(setDescription)} />

                <div class="prep-time">
                    <span>Tiempo de preparacion estimado:</span>
                    <input type="range"
                        min={5}
                        max={120}
                        step={5}
                        value={estimatedPrepTime}
                        onInput={updateProp(setEstimatedPrepTime)}
                    />
                    <span>{estimatedPrepTime} minutos</span>
                </div>
            </form>

            {error.length > 0
                ? <>
                    <span class="red-text">{error}</span>
                    <br /><br />
                </>
                : null
            }

            <div class="controls-bar">
                <button class="cancel" onClick={() => close()}>Cerrar</button>
                <button class="success" onClick={save}>Guardar</button>
            </div>
        </article>
    </dialog>;
};
