import { ReactNode, useState } from "react";
import LoginWithGoogleBottom from "../components/LoginWithGoogleBottom";
import LoginForm from "../components/LoginForm";
import RegisterForm from "../components/RegisterForm";

function Login(): ReactNode {
  const [showLogin, setShowLogin] = useState(true);

  return (
    <div className="container-fluit" style={{ margin: 25 }}>
      <ul className="nav nav-tabs justify-content-center">
        <li className="nav-item">
          <a
            className={"nav-link" + (showLogin ? " active" : "")}
            onClick={() => setShowLogin(true)}
            href="#"
          >
            Login
          </a>
        </li>
        <li className="nav-item">
          <a
            className={"nav-link" + (!showLogin ? " active" : "")}
            onClick={() => setShowLogin(false)}
            href="#"
          >
            Register
          </a>
        </li>
        <li className="nav-item">
          <LoginWithGoogleBottom />
        </li>
      </ul>
      {showLogin ? <LoginForm /> : <RegisterForm />}
    </div>
  );
}

export default Login;
