interface FormInputFieldProps {
    label: string;
    name: string;
    id: string;
    value?: string | string[] | undefined;
    onChange: (e: React.ChangeEvent<any>) => void;
}

type TextFieldProps = FormInputFieldProps & {
    datalist?: string;
};

type TypeFieldProps = FormInputFieldProps & {
    htmlType?: string;
};

type SelectOption = { value: string; label: string };

type SelectProps = FormInputFieldProps & {
    options: SelectOption[];
    multiple?: boolean;
};

type CheckBoxProps = FormInputFieldProps & {
    checked: boolean;
};

export const FormInputTextField = ({ label, name, id, value, onChange, datalist }: TextFieldProps) => {
    return (
        <div className="mb-3">
            {label && <label htmlFor={id} className="form-label">{label}</label>}
            <input type="text" className="form-control" id={id} name={name} value={value ?? ""} onChange={onChange} list={datalist} />
        </div>
    );
}

export const FormInputTextArea = ({ label, name, id, onChange, value }: FormInputFieldProps) => {
    return (
        <div className="mb-3">
            <label htmlFor={id} className="form-label">{label}</label>
            <textarea className="form-control" id={id} name={name} value={value ?? ""} onChange={onChange} />
        </div>
    );
}

export const FormInputCheckbox = ({ label, name, id, checked, onChange }: CheckBoxProps) => {
    return (
        <div className="mb-3 form-check">
            <input type="checkbox" className="form-check-input" id={id} name={name} checked={checked} onChange={onChange} />
            <label className="form-check-label" htmlFor={id}>{label}</label>
        </div>
    );
}

export const FormInput = ({ label, name, id, value, onChange, htmlType }: TypeFieldProps) => {
    return (
        <div className="mb-3">
            <label htmlFor={id} className="form-label">{label}</label>
            <input type={htmlType} className="form-control" id={id} name={name} value={value ?? ""} onChange={onChange} />
        </div>
    );
}


export const FormInputSelect = ({
    id,
    name,
    label,
    value,
    onChange,
    options,
    multiple = false,
}: SelectProps) => (
    <div className="mb-3">
        <label htmlFor={id} className="form-label">{label}</label>
        <select
            className="form-select"
            id={id}
            name={name}
            value={value}
            onChange={onChange}
            multiple={multiple}
        >
            {options.map((opt) => (
                <option key={opt.value} value={opt.value}>
                    {opt.label}
                </option>
            ))}
        </select>
    </div>
);



export function createFormChangeHandler<T>(setState: React.Dispatch<React.SetStateAction<T>>) {
    return (e: React.ChangeEvent<any>) => {
        const { name, type, value, checked, multiple, options } = e.target;

        let finalValue: any = value;

        if (type === 'checkbox') {
            finalValue = checked;
        } else if (type === 'number') {
            finalValue = value === "" ? "" : Number(value);
        } else if (multiple) {
            finalValue = Array.from(options as HTMLOptionsCollection)
                .filter((option: HTMLOptionElement) => option.selected)
                .map((option: HTMLOptionElement) => option.value);
        }

        setState((prev) => updateNestedField(prev, name, finalValue));
    };
}

function updateNestedField(obj: any, path: string, value: any): any {
    const keys = path.replace(/\[(\d+)\]/g, '.$1').split('.');
    const result = { ...obj };
    let current = result;

    for (let i = 0; i < keys.length - 1; i++) {
        const key = keys[i];
        const nextKey = keys[i + 1];
        const isArrayIndex = /^\d+$/.test(nextKey);

        if (!(key in current)) {
            current[key] = isArrayIndex ? [] : {};
        }

        // clone arrays/objects to preserve immutability
        if (Array.isArray(current[key])) {
            current[key] = [...current[key]];
        } else {
            current[key] = { ...current[key] };
        }

        current = current[key];
    }

    current[keys[keys.length - 1]] = value;
    return result;
}


