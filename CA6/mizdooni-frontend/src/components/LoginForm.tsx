import { useState } from "react";
import TextInput from "./TextInput";
import { getMizdooni } from "../mizdooni";
import { ToastContainer, toast } from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import type { Error } from '../contracts/error'
import { handleError, saveToken } from "../utils";

function LoginForm() {
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
          saveToken(response.data.token || "");
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
