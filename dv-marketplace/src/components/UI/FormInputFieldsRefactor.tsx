import { useState } from "react";
import { types } from "storybook/internal/babel";

interface FormInputFieldProps {
    label: string;
    name: string;
    id: string;
    value?: string | string[] | undefined;
    htmlType?: string;
    datalist?:string;
}

type SelectOption = { 
    value: string; 
    label: string 
};
interface FormInputSelectProps extends FormInputFieldProps {
    options: SelectOption[];
    multiple?: boolean;
}

type InputType = {
    label: string;
    type: string;
    options?: SelectOption[];
    value?: string;
}

interface MultiValueInputProps {
    header: string;
    types: InputType[];
    id?: string;
    name?: string;
}

export const FormInputTextField = ({ label, name, id, value, datalist}: FormInputFieldProps) => {
    return (
        <div className="mb-3">
            <label htmlFor={id} className="form-label">{label}</label>
            <input type="text" className="form-control" id={id} name={name} defaultValue={value} list={datalist}/>  
        </div>
    );
}

export const FormInputTextArea = ({ label, name, id, value}: FormInputFieldProps) => {
    return (
        <div className="mb-3">
            <label htmlFor={id} className="form-label">{label}</label>
            <textarea className="form-control" id={id} name={name} defaultValue={value} />  
        </div>
    );
}

export const FormInputCheckbox = ({ label, name, id, value}: FormInputFieldProps) => {
    return (
        <div className="mb-3 form-check">
            <input type="checkbox" className="form-check-input" id={id} name={name} defaultChecked={value === "true"} />
            <label className="form-check-label" htmlFor={id}>{label}</label>
        </div>
    );
}

export const FormInput = ({ label, name, id, value, htmlType}: FormInputFieldProps) => {
    return (
        <div className="mb-3">
            <label htmlFor={id} className="form-label">{label}</label>
            <input type={htmlType} className="form-control" id={id} name={name} defaultValue={value} />  
        </div>
    );
}

export const FormInputSelect = ({label, name, id, value, options, multiple}: FormInputSelectProps) => {
    return (
    <div className="mb-3">
        <label htmlFor={id} className="form-label">{label}</label>
        <select
            className="form-select"
            id={id}
            name={name}
            defaultValue={value}
            {...(multiple ? { multiple: true } : {})}
        >
            {options.map((opt) => (
                <option key={opt.value} value={opt.value}>
                    {opt.label}
                </option>
            ))}
        </select>
    </div>
    )
};

export const MultiValueInput = ({header, types, id, name} : MultiValueInputProps) => {

    const [values, setValues] = useState<InputType[][]>([]);
    
    const handleAddValue = () => {
        setValues([...values, types]);
        console.log("Added new value", values);
    }

    const handleRemoveValue = (index: number) => {
        setValues(values.filter((_, i) => i !== index));
    }
    
    
    return (
        <div className="mb-3">
            <h5 className="form-label">{header}</h5>
            <hr />
            <div className="">

                {values.map((item, index) => (
                    <div key={`${id}-${index}`} className="mb-2 align-items-start flex-nowrap row">
                        {item.map((inputType, inputIndex) => (
                            <div key={`${inputType.label}-${inputType.type}-${inputIndex}`} className="col-md-5">
                                <FormInputTextField 
                                    label={inputType.label}
                                    name={`${id}`}
                                    id={`${id}-${inputIndex}`}
                                />
                            </div>
                        ))}
                       
                        <div className="col-md-2 align-items-center">
                            <button type="button" className="btn bi bi-trash" onClick={() => handleRemoveValue(index)}>
                            </button>
                        </div>
                    </div>
                ))}
                
            </div>
            <div>
                <button
                    type="button"
                    className="btn btn-primary bi bi-plus"
                    onClick={() => handleAddValue()}
                >
                </button>
            </div>
            <hr />
        </div>
    );
}

export const KeyPairInput = ({ label, id }: {label:string, id:string}) => {

    const inputTypes: InputType[] = [
        { label: "Key", type: "text" },
        { label: "Value", type: "text" }
    ];
    
    return (
        <MultiValueInput
            header={label}
            types={inputTypes}
            id={id} />
    );
}

export const SingleMultiValueInput = ({ label, id }: {label:string, id:string}) => {

    const inputTypes: InputType[] = [
        { label: "", type: "text" }
    ];
    
    return (
        <MultiValueInput
            header={label}
            types={inputTypes}
            id={id} />
    );
}