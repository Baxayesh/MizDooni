import { ReactNode } from "react";
import LogoutBottom from "../components/LogoutBottom";
import Session from "../models/Session";
  
export default function ManagerHome(session: Session|null, logout: ()=>void): ReactNode {
    
  return (
    <>
      <h2>Welcome Manager</h2>
      <div><pre>{JSON.stringify(session, null, 4)}</pre></div>
      {LogoutBottom(logout)}
    </>
  );
}