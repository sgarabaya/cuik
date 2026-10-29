import { useEffect, useRef, useState } from "preact/hooks";
import { Loader } from "./Loader";

export interface DropdownProps<T> {
    getLabel: (item: T) => string;
    onSelect: (items: T[]) => void;
    placeholder?: string;
    value?: string;
    values: T[];
}

export function Dropdown<T>(props: DropdownProps<T>) {
    const lbl = props.getLabel;
    const [filter, setFilter] = useState<string | null>(props.value ?? null);
    const [isOpen, setOpen] = useState<boolean>(false);
    const [filteredEntries, setFilteredEntries] = useState<T[]>(props.values);
    const [index, setIndex] = useState<number>(-1);

    // Handle clicking outside to close dropdown
    const containerRef = useRef<HTMLDivElement>(null);
    useEffect(() => {
        const handleClickOutside = (event: MouseEvent) => {
            if (containerRef.current && !containerRef.current.contains(event.target as Node)) {
                setOpen(false);
            }
        };
        document.addEventListener('mousedown', handleClickOutside);
        return () => document.removeEventListener('mousedown', handleClickOutside);
    }, []);

    useEffect(() => {
        props.onSelect(filteredEntries);
    }, [filteredEntries]);

    const doFilter = (f: string | null, close: boolean = false) => {
        setFilter(f);
        setIndex(-1); //no selected entry if we change the filter
        setFilteredEntries(
            f === null
                ? props.values
                : props.values.filter(e => lbl(e).includes(f))
        );

        if (close) setOpen(false);
    };

    const handleInput = (ev: Event) => doFilter((ev.target as HTMLInputElement).value);
    const handleFocus = () => setOpen(true);
    const handleKeyDown = (ev: KeyboardEvent) => {
        if (!isOpen) return;

        const elCount = filteredEntries.length;

        if (ev.key === "ArrowDown") {
            ev.preventDefault();
            setIndex(prev => prev + 1 % elCount);
        } else if (ev.key === "ArrowUp") {
            ev.preventDefault();
            setIndex(prev => (prev - 1 + elCount) % elCount);
        } else if (ev.key === "Enter") {
            if (index >= 0 && filteredEntries[index]) {
                ev.preventDefault();
                doFilter(lbl(filteredEntries[index]), true);
            }
        } else if (ev.key === "Escape") {
            setOpen(false);
            setFilter('');
            doFilter('', true);
        }
    };

    const Entry = ({ element, i }: { element: T, i: number }) => {
        const value = lbl(element);
        const key = `${i}-${value}`;
        const handleClick = () => doFilter(value, true);
        return <li key={key} class={i === index ? 'active' : ''} onClick={handleClick}>{value}</li>;
    };

    return <div ref={containerRef} class="dropdown">
        <input
            type="text"
            value={filter ?? ''}
            onInput={handleInput}
            onKeyDown={handleKeyDown}
            onFocus={handleFocus}
            placeholder={props.placeholder}
        />
        {isOpen &&
            <ul>
                {filteredEntries.map((e, i) => <Entry element={e} i={i} />)}
            </ul>
        }
    </div>;
}

export interface AsyncDropdownProps<T> {
    getLabel: (item: T) => string;
    onSelect: (items: T[]) => void;
    placeholder?: string;
    value?: string;
    loadingFn: () => Promise<T[]>;
}

export function AsyncDropdown<T>(props: AsyncDropdownProps<T>) {
    return <Loader fn={props.loadingFn}>{
        data => data ? <Dropdown
            placeholder={props.placeholder}
            values={data}
            getLabel={props.getLabel}
            onSelect={props.onSelect}
        /> : <input type="text" disabled placeholder={props.placeholder} />
    }</Loader>;
}
