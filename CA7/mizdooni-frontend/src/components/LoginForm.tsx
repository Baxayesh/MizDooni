import { useState } from "react";
import TextInput from "./TextInput";
import { getMizdooni } from "../mizdooni";
import 'react-toastify/dist/ReactToastify.css';
import { handleError } from "../utils";
import { TokenModel } from "../contracts";

interface LoginFormProps{
  login: (token: TokenModel) => void
}

function LoginForm({login}: LoginFormProps) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  function submit() {
    getMizdooni()
      .login({
        username: username,
        password: password,
      })
      .then(
        (response) => {
          login(response.data);
        },
        handleError
      );
  }


  return (
    <div className="login-form col-4 mx-auto">
      <TextInput
        id="username"
        value={username}
        onValueChanged={setUsername}
        label="Username : "
      />
      <TextInput
        id="password"
        type="password"
        value={password}
        onValueChanged={setPassword}
        label="Password : "
      />
      <button
        className="btn btn-danger col-12 d-flex justify-content-center"
        onClick={submit}
      >
        Login
      </button>
    </div>
  );
}

export default LoginForm;
