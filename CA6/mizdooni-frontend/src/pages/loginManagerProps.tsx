import Session from "../models/Session";


export interface loginManagerProps {
    session?: Session|null;
    login: (session: Session) => void;
}
