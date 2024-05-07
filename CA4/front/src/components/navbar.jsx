import Logo from "../image/logo.png";
import { useContext } from "react";
import NavContext from "../context/nav";
import { useNavigate } from "react-router-dom";

const Navbar = () => {
  const ctxt = useContext(NavContext);
  const navigate = useNavigate();
  const styles = {
    logoContainer: {
      width: "65px",
      height: "45px",
      top: "8px",
      left: "0px"
    },
    navButton: {
      width: "130px",
      height: "40px",
      marginBottom: "10px",
      left: "25px",
      borderRadius: "12px",
      backgroundColor: "#ED3545",
      color: "#fff",
      border: "none"
    },
    navbarTitle: {
      float: "right",
      color: "#ED3545",
      lineHeight: "50px",
      marginLeft: "20px"
    }
  }

  return (
    <nav className="navbar navbar-default fixed-top navbar-light bg-light">
      <div className="container">
        <div className="navbar-brand" to="/">
          <img src={Logo} className="nav-img" style={styles.logoContainer} alt="logo" />
          <p className="navbar-title" style={styles.navbarTitle}>Reserve Table From Anywhere!</p>
        </div>
        <p style={{ padding: "0px", marginLeft: "400px" }}>Welcome, {ctxt.values.username}!</p>
        
          <button className="btn btn-danger" style={styles.navButton} type="button" 
          onClick={()=>{
            (ctxt.valuesReg.role == "client")?(navigate("/customer")):(navigate("/manager"));
          }}>
            Reserve Now!
          </button>
        

      </div>
    </nav>
  );
}

export default Navbar;