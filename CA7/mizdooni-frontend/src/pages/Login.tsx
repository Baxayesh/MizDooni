import { ReactNode, useEffect, useState } from "react";
import LoginWithGoogleBottom from "../components/LoginWithGoogleBottom";
import LoginForm from "../components/LoginForm";
import RegisterForm from "../components/RegisterForm";
import { TokenModel } from "../contracts";
import { Navigate, useNavigate } from "react-router-dom";
import Session, { toSession } from "../models/Session";
import { showError } from "../utils";

export default function Login(session: Session|null, login: (session: Session)=>void): ReactNode {

  const [showLogin, setShowLogin] = useState(false);
  const natigate = useNavigate();

  useEffect(()=>{
    if(session){
      natigate('/')
    }
  }, [session, natigate])
  
  function handleSuccessfullLogin(token: TokenModel) {
    var session = toSession(token)
    if(session){
      login(session)
    }else{
      showError('Invalid Response from server, try again')
    }
    
  }

  return (
    <div className="container-fluit" style={{ margin: 25 }}>
      <ul className="nav nav-tabs justify-content-center">
        <li className="nav-item">
          <div
            className={"nav-link" + (showLogin ? " active" : "")}
            onClick={() => setShowLogin(true)}
          >
            Login
          </div>
        </li>
        <li className="nav-item">
          <div
            className={"nav-link" + (!showLogin ? " active" : "")}
            onClick={() => setShowLogin(false)}
          >
            Register
          </div>
        </li>
        <li className="nav-item">
          <LoginWithGoogleBottom />
        </li>
      </ul>
      {showLogin
        ? <LoginForm login={handleSuccessfullLogin} />
        : <RegisterForm login={handleSuccessfullLogin} />}
    </div>
  );
}