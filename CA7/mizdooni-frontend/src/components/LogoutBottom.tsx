import { useNavigate } from "react-router-dom";
import AuthenticationManager from "../models/SessoinStorage";

export default function LogoutBottom(logout: ()=>void){

    function handleLogout(){
        logout()
    }

    return (
        <button className="btn btn-danger btn-sm col-1 rounded-5" onClick={handleLogout}> logout </button>
    );
}