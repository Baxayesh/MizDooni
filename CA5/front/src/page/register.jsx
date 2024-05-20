import React, { useContext } from 'react';
import Form from 'react-bootstrap/Form';
import Input from '../components/input';
import { useNavigate, Link } from 'react-router-dom';
import NavContext from '../context/nav';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';


const Register = () => {


    const ctx = useContext(NavContext);
    const navigate = useNavigate();

    const notify = () => {
        toast.success('Signed Up!', {
            position: "top-right",
            autoClose: 3000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
            theme: "colored",

        });
    }

    return (
        <div className='container mt-5'>
            <div className="reg-in-reg float-start text-center col-5 mt-5 ms-5">Register</div>
            <div className="log-in-reg float-start text-center col-5 mt-5" onClick={() => { navigate(`/login`) }}>Login</div>
            <div className="col-4 mx-auto">
                <Form className='reg-form' onSubmit={(e) => ctx.handleSubmitReg(e)}>
                    <Input type="text" name='username' value={ctx.valuesReg.username} label='Username' onChange={(e) => ctx.handleChangeReg(e)} />
                    <Input type="email" name='email' value={ctx.valuesReg.email} label='Email' onChange={(e) => ctx.handleChangeReg(e)} />
                    <Input type="text" name='country' value={ctx.valuesReg.country} label='Country' onChange={(e) => ctx.handleChangeReg(e)} />
                    <Input type="text" name='city' value={ctx.valuesReg.city} label='City' onChange={(e) => ctx.handleChangeReg(e)} />
                    <Input type="password" name='password' value={ctx.valuesReg.password} label='Password' onChange={(e) => ctx.handleChangeReg(e)} />
                    <div className="d-flex justify-content-around">
                        <div className="mt-2 d-flex">I am a new </div>
                        <div className="mb-3 d-flex">
                            <input
                                onChange={(e) => ctx.handleChangeReg(e)}
                                value="client"
                                id='client'
                                name='role'
                                type="radio"
                                checked={ctx.valuesReg.role === "client"}
                            />
                            <label className="p-2" htmlFor='Role'>Customer</label>
                        </div>
                        <div className="mb-3 d-flex">
                            <input
                                onChange={(e) => ctx.handleChangeReg(e)}
                                value="manager"
                                id='manager'
                                name='role'
                                type="radio"
                                checked={ctx.valuesReg.role === "manager"}
                            />
                            <label className="p-2" htmlFor='Role'>Manager</label>
                        </div>
                    </div>

                    <button className="btn btn-danger col-12" onClick={notify} ><Link to="/" className="text-decoration-none" style={{ color: "white" }}>Register</Link></button>
                    <ToastContainer
                        position="top-right"
                        autoClose={3000}
                        hideProgressBar={false}
                        newestOnTop={false}
                        closeOnClick
                        rtl={false}
                        pauseOnFocusLoss
                        draggable
                        pauseOnHover
                        theme="colored"
                    />
                </Form>
            </div>
        </div>
    );
};

export default Register;



