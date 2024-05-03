import React, { useState } from 'react';
import Form from 'react-bootstrap/Form';
import Input from '../components/input';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const Register = () => {

  
    const [values, setValues] = useState({});
    
    const navigate = useNavigate();

    async function handleSubmit (event) {
        event.preventDefault();
        // Add your registration logic here
        // const response = await axios.post('https://reqres.in/api/register',  );
        const response = await axios.post('http://localhost:8080/users',  values );
        navigate("/");
        console.log(response);
        // console.log('Registration submitted:', { username, email, country, city, password, role });
    };

    const handleChange = (e) => {
        const { value, name } = e.target;
        setValues({ ...values, [name]: value });
      }

    return (
        <>
            <div className="reg-in-reg float-start text-center col-5 mt-5 ms-5">Register</div>
            <div className="log-in-reg float-start text-center col-5 mt-5" onClick={()=>{navigate(`/login`)}}>Login</div>
            <div className="col-4 mx-auto">
                <Form className='reg-form' onSubmit={(e) => handleSubmit(e)}>
                    <Input type="text" name='username' value={values?.username} label='Username' onChange={(e) => handleChange(e)} />
                    <Input type="email" name='email' value={values?.email} label='Email' onChange={(e) => handleChange(e)} />
                    <Input type="text" name='country' value={values?.country} label='Country' onChange={(e) => handleChange(e)} />
                    <Input type="text" name='city' value={values?.city} label='City' onChange={(e) => handleChange(e)} />
                    <Input type="password" name='password' value={values?.password} label='Password' onChange={(e) => handleChange(e)} />
                    {/* <Input checked={role === 'customer'} type="radio" name='role' value="customer" label='Customer' onChange={(event) => setRole(event.target.value)} />
                <Input checked={role === 'manager'} type="radio" name='role' value="manager" label='Manager' onChange={(event) => setRole(event.target.value)} /> */}
                    <div className="d-flex justify-content-around">
                        <div className="mt-2 d-flex">I am a new </div>
                        <div className="mb-3 d-flex">
                            <input
                                onChange={(e) => handleChange(e)}
                                value="customer"
                                id='customer'
                                name='role'
                                type="radio"
                                checked={values?.role === "customer"}
                            />
                            <label className="p-2" htmlFor='Role'>Customer</label>
                        </div>
                        <div className="mb-3 d-flex">
                            <input
                                onChange={(e) => handleChange(e)}
                                value="manager"
                                id='manager'
                                name='role'
                                type="radio"
                                checked={values?.role === "manager"}
                            />
                            <label className="p-2" htmlFor='Role'>Manager</label>
                        </div>
                    </div>

                    <button className="btn btn-danger col-12">Register</button>
                </Form>
            </div>
        </>
    );
};

export default Register;



