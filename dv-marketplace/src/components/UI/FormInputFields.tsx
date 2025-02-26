

interface FormInputFieldProps {
    label: string;
    name: string;
    id: string;
    value: string | undefined;
}

export const FormInputTextField = ({ label, name, id, value}: FormInputFieldProps) => {
    return (
        <div className="mb-3">
            <label htmlFor={id} className="form-label">{label}</label>
            <input type="text" className="form-control" id={id} name={name} value={value} />  
        </div>
    );
}

export const FormInputTextArea = ({ label, name, id, value}: FormInputFieldProps) => {
    return (
        <div className="mb-3">
            <label htmlFor={id} className="form-label">{label}</label>
            <textarea className="form-control" id={id} name={name} value={value} />  
        </div>
    );
}
