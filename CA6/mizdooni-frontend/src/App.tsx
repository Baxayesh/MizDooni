import { ToastContainer } from "react-toastify";
import Login from "./pages/login";
import 'react-toastify/dist/ReactToastify.css';

function App() {
  return (
   <>
    <Login />
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
    </>
  );
}

export default App
