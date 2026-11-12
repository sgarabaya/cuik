import { JSX } from 'preact/jsx-runtime';
import { MouseEventHandler } from 'preact';

export type SquareButtonProps = { icon: JSX.Element, tooltip: string, class?: string, onClick: MouseEventHandler<HTMLButtonElement> };
export const SquareButton = (props: SquareButtonProps) => (
    <button
        class={"square-button " + (props.class ?? '')}
        onClick={props.onClick}
    >
        <abbr title={props.tooltip}>{props.icon}</abbr>
    </button>
);

