const Input = ({ classNm, checked, type, name, value, label, onChange }) => {
    return (
      <div className="mb-3">
        <label htmlFor={name}>{label}</label>
        <input
          onChange={onChange}
          value={value}
          id={name}
          name={name}
          // className="form-control px-4 py-3 rounded-full"
          type={type}
          checked={checked}
          className={classNm}
        />
      </div>
    );
  };
  
  export default Input;