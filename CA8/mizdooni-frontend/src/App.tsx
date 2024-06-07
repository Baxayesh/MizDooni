import * as configs from "./configs";
import Login from "./pages/Login";
import CallbackManager from "./pages/CallbackManager";
import { useEffect, useState } from "react";
import Session from "./models/Session";
import { Navigate, Routes, Route } from "react-router-dom";
import SessionStorage from "./models/SessoinStorage";
import Home from "./pages/Home";
import { ToastContainer } from "react-toastify";

function App() {
  const [session, setSession] = useState<Session | null>(null);

  useEffect(() => {
    const storedSession = SessionStorage.instance.getSession();
    if (storedSession) {
      setSession(storedSession);
    }
  }, []);

  function login(session: Session) {
    SessionStorage.instance.saveSession(session);
    setSession(session);
  }

  function logout() {
    SessionStorage.instance.removeSession();
    setSession(null);
  }

  return (
    <>
      <ToastContainer />
      <Routes>
        <Route path="/" element={Home(session, logout)} />
        <Route path="/login" element={Login(session, login)} />
        <Route
          path={configs.googleOauth2RedirectPath}
          element={<CallbackManager login={login}/>}
        />
        <Route path="*" element={<Navigate to="/" />} />
      </Routes>
    </>
  );
}

export default App;
