export interface CheckboxProps {
    value: boolean;
    setValue: Function;
    label: string;
}

export function Checkbox({ value, setValue, label }: CheckboxProps) {
    const onInput = (e: InputEvent) => setValue((e.target as HTMLInputElement).checked);

    return <label class="checkbox" style="width:100%">
        <input type="checkbox" checked={value} onInput={onInput} />
        <div class="wrapper">
            <span>{label}</span>
            <div class="switch">
                <div class="knob"></div>
            </div>
        </div>
    </label>;
}