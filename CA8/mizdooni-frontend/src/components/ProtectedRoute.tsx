import { ReactNode } from "react";
import Session from "../models/Session";
import { UserRole } from "../models/UserRole";
import { Navigate, Route } from "react-router-dom";


export default function ProtectedElement(session: Session|null, component: ReactNode, allowedRole: UserRole|null = null): ReactNode{

    if(session){
        if(!allowedRole || session.user.role == allowedRole){
            return component;
        }   
    }

    return (<Navigate to='/login'/>);
}

