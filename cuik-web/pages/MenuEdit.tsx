import { Menu } from "../api";

export interface MenuEditProps {
    entry?: Menu;
    close: Function;
}

export const MenuEdit = ({ entry, close }: MenuEditProps) => {
    return <h1>hi!</h1>;
}

