import Logo from "../image/logo.png";
import Background from "../image/home_background.png";
import Dropdown from 'react-bootstrap/Dropdown';
import { useContext } from "react";
import CardContext from "../context/cards";

const Search = ({ location, type, onSubmit, handleSearchBar, setSearchType, setSearchLoc}) => {
    const CardContxt = useContext(CardContext);
    // const baseUrl = "http://localhost:8080/restaurants";

    const styles = {
        mainBg: {
            backgroundImage: `url(${Background})`,
            width: "100%",
            backgroundRepeat: "no-repeat",
            backgroundPosition: "center center",
            backgroundSize: "cover cover",
            height: "730px",
            top: "-200px",
            left: " -231px",
        },
        mainHome: {
            width: "704px",
            height: "317px",
            top: "297px",
            marginLeft: "100px",
            textAlign: "center",
        },
        mainLogo: {
            width: "319px",
            height: "250.37px",
            left: "192px",
            display: "block",
            marginLeft: "auto",
            marginRight: "auto",
        }
    }
    
    return (

        <div className="jumbotron d-flex align-items-center" style={styles.mainBg}>
            <div style={{ ...styles.mainHome }}>
                <img className="d-flex justify-content-center" src={Logo} alt="main_logo" style={{ ...styles.mainLogo }} />
                <Dropdown className="d-inline-flex me-3">
                    <Dropdown.Toggle variant="light" id="dropdown-basic">
                        Location
                    </Dropdown.Toggle>
                    <Dropdown.Menu onChange={()=>{console.log("loc")}}>
                        {
                            location.map((p, i) => (
                                <Dropdown.Item key={i} style={{ color: "#7E7E7E" }}>{p.country}
                                    <>
                                        {
                                            p.cities.map((c, i) => (
                                                <Dropdown.Item key={i} value={c}  >{c}</Dropdown.Item>
                                            ))
                                        }
                                    </>
                                </Dropdown.Item>
                            ))
                        }

                    </Dropdown.Menu>
                </Dropdown>
                <Dropdown className="d-inline-flex me-3">
                    <Dropdown.Toggle variant="light" id="dropdown-basic">
                        Restaurant
                    </Dropdown.Toggle>
                    <Dropdown.Menu>
                        {
                            type.map((p, i) => (
                                <Dropdown.Item key={i} value={p} onChange={() => {setSearchType(p)}} >{p}</Dropdown.Item>
                            ))
                        }

                    </Dropdown.Menu>
                </Dropdown>
                <form className="d-inline-flex" role="search" onSubmit={onSubmit}>
                    <input className="form-control me-2" value={CardContxt.searchInfo} onChange={(e) =>handleSearchBar(e.target.value)} type="search" placeholder="Type Restaurant..." aria-label="Search" />
                    <button className="btn btn-danger" type="submit">Search</button>
                </form>
            </div>
        </div>

    );
}

export default Search;