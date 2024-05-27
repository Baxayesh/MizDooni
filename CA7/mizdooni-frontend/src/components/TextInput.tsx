

interface TextInputProps {

    value: string
    label: string
    type?: 'text'|'email'|'password',
    id: string
    placeholder?: string
    onValueChanged: (newValue: string)=>void
}

function TextInput({value, label, type='text', id, placeholder='', onValueChanged } : TextInputProps){

    return (
        <div className="mb-3">
            <label htmlFor={id} className="form-label">{label}</label>
            <input type={type} className="form-control" value={value} id={id} placeholder={placeholder} onChange={(event)=>onValueChanged(event.target.value)}/>
        </div>
    );
}

export default TextInput