import React, {useContext} from 'react';
import Input from '../components/input';
import { useNavigate } from 'react-router-dom';
import '../css/loginReg.css';
import NavContext from '../context/nav';
const Login = () => {

  const navigate = useNavigate();
  const ctx = useContext(NavContext);

  return (
    <div className="container mt-5">
      <div className="reg-in-log float-start text-center col-5 mt-5 ms-5" onClick={() => { navigate(`/register`) }}>Register</div>
      <div className="log-in-log float-start text-center col-5 mt-5">Login</div>
      <form className="login-form col-4 mx-auto" onSubmit={(e) => ctx.handleSubmit(e)}>
        <Input type="text" name='username' value={ctx.values?.username} label='Username' onChange={(e) => ctx.handleChange(e)} />
        <Input type="password" name='password' value={ctx.values?.password} label='Password' onChange={(e) => ctx.handleChange(e)} />
        <button className="btn btn-danger col-12">Login</button>
      </form>
    </div>);
}

export default Login;
