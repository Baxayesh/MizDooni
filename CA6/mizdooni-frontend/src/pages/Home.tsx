import { ReactNode } from "react";
import Session from "../models/Session";
import { Navigate } from "react-router-dom";
import { UserRole } from "../models/UserRole";
import ManagerHome from "./ManagerHome";
import ClientHome from "./ClientHome";

export default function Home(session: Session|null, logout: ()=>void): ReactNode {

    if(!session){
        return <Navigate to='/login' />
    }

    if(session.user.role == UserRole.Manager){
        return ManagerHome(session, logout);
    }else{
        return ClientHome(session, logout);
    }
}