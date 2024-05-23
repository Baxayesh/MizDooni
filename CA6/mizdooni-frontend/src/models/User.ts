import { UserRole } from "./UserRole";

export default interface User {

    role: UserRole;
    username: string;
    email: string;

}