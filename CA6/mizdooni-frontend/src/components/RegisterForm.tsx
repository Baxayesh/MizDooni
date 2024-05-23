import { ReactNode, useState } from "react";
import TextInput from "./TextInput";
import { getMizdooni } from "../mizdooni";
import { handleError } from "../utils";
import { TokenModel } from "../contracts";

interface RegisterFormProps{
  login: (token: TokenModel) => void
}

function RegisterForm({login}: RegisterFormProps) : ReactNode{
  
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");
  const [country, setCountry] = useState("");
  const [city, setCity] = useState("");
  const [isClient, setIsClient] = useState(true);

  function submit() {
    getMizdooni()
      .signup({
        username: username,
        password: password,
        email: email,
        country: country,
        city: city,
        role: isClient ? "client" : "manager",
      })
      .then((response) => {
        login(response.data)
      }, handleError);
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
      <TextInput
        id="email"
        value={email}
        type="email"
        onValueChanged={setEmail}
        label="Email : "
      />
      <TextInput
        id="country"
        value={country}
        onValueChanged={setCountry}
        label="Country : "
      />
      <TextInput
        id="city"
        value={city}
        onValueChanged={setCity}
        label="City : "
      />
      
      <div className="d-flex justify-content-around">
        <div className="mt-2 d-flex">I am a new </div>
        <div className="mb-3 d-flex">
          <input
            onChange={() => setIsClient(!isClient)}
            value="client"
            id="client"
            name="role"
            type="radio"
            checked={isClient}
          />
          <label className="p-2" htmlFor="Role">
            Customer
          </label>
        </div>
        <div className="mb-3 d-flex">
          <input
            onChange={() => setIsClient(!isClient)}
            value="manager"
            id="manager"
            name="role"
            type="radio"
            checked={!isClient}
          />
          <label className="p-2" htmlFor="Role">
            Manager
          </label>
        </div>
      </div>

      <button
        className="btn btn-danger col-12 d-flex justify-content-center"
        onClick={(e)=> {submit()}}
      >
        Register
      </button>
    </div>
  );
}

export default RegisterForm;
