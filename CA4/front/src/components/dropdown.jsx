import { NavLink } from "react-router-dom";

const Dropdown = ({props}) => {
    return ( 
        <>
            <div className="btn-group d-inline-flex">
                    <button type="button" className="btn btn-light dropdown-toggle" data-toggle="dropdown"
                        aria-expanded="false">
                        Location
                    </button>
                    <ul className="dropdown-menu">
                        <li><NavLink className="dropdown-item" to="#">Action</NavLink></li>
                        <li><NavLink className="dropdown-item" to="#">Another action</NavLink></li>
                        <li><NavLink className="dropdown-item" to="#">Something else here</NavLink></li>
                        <li><NavLink className="dropdown-item" to="#">Separated NavLink</NavLink></li>
                    </ul>
                </div>
        </>
     );
}
 
export default Dropdown;