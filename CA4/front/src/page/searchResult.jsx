import Cards from "../components/cards";
import { useContext } from "react";
import NavContext from "../context/nav";


const SearchResult = () => {
    const ctxt = useContext(NavContext);
    
    console.log("search info:",ctxt.searchInfo)
    // console.log("search loc:",ctxt.searchLoc)
    // console.log("search type:",ctxt.searchType)

    

    const styles = {
        cardsTitle: {
            fontSize: "16px",
            fontWeight: "400",
            lineHeight: " 19px",
            letterSpacing: "0em",
            textAlign: "left",
        }
    }
    return (
        <>
            <div className="row container-fluid" style={{ marginTop: "100px" }}>
                <div className="col-md-12 col-xs-12">
                    <p style={styles.cardsTitle}>
                        Results for # Restaurant Name:
                    </p>
                </div>
                
            </div>
            <div className="row d-flex justify-content-between container-fluid">
                    {
                        ctxt.resultName.map((p, i) => (
                            <Cards
                                name={p.Name}
                                openTime={p.OpenTime}
                                closeTime={p.CloseTime}
                                type={p.Type}
                                image={p.ImageUri}
                                review={p.rating.reviewCount}
                                loc={p.restaurantAddress.city}
                                key={i}
                                date={ctxt.date}
                                score={p.rating.overallScore}
                            />
                        ))
                    }
                </div>

        </>
    );
}

export default SearchResult;