import { useContext } from "react";
import Navbar from "../components/navbar";
import Search from "../components/search";
import About from "../components/about";
import Cards from "../components/cards";
import CardContext from './../context/cards';
import { useNavigate } from "react-router-dom";
import NavContext from "../context/nav";

const Home = () => {
 
    const navigate = useNavigate();

    const ctx = useContext(NavContext);

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

            <Navbar />
            <CardContext.Provider
                value={{ 
                searchInfo: ctx.searchInfo,
                searchType: ctx.searchType,
                searchLoc: ctx.searchLoc
                
                }}
            >
                <Search 
                        location={ctx.location} 
                        type={ctx.restType} 
                        onSubmit={() => navigate("/searchResult")} 
                        handleSearchBar={ctx.handleSearchBar}
                        setSearchType={ctx.setSearchType}
                        setSearchLoc={ctx.setSearchLoc}
                        />
            </CardContext.Provider>
            


            <div className="row container-fluid mt-3">
                <div className="col-md-12 col-xs-12">
                    <p style={{ ...styles.cardsTitle }}>
                        Top Restaurants in Mizdooni
                    </p>
                </div>
            </div>
            <div className="row d-flex justify-content-between container-fluid">
                {
                    ctx.data.map((p, i) => (
                        <Cards
                            name={p.Name}
                            openTime={p.OpenTime}
                            closeTime={p.CloseTime}
                            type={p.Type}
                            image={p.ImageUri}
                            review={p.rating.reviewCount}
                            loc={p.restaurantAddress.city}
                            key={i}
                            date={ctx.date}
                            score={p.rating.overallScore}
                            
                        />
                    ))
                }
            </div>
            <div className="row container-fluid mt-3">
                <div className="col-md-12 col-xs-12">
                    <p style={{ ...styles.cardsTitle }}>
                        Top Restaurants in Mizdooni
                    </p>
                </div>
            </div>
            <div className="row d-flex justify-content-between container-fluid">
            
                {
                    ctx.ratingData.map((p, i) => (
                       
                            <Cards
                            name={p.Name}
                            openTime={p.OpenTime}
                            closeTime={p.CloseTime}
                            type={p.Type}
                            image={p.ImageUri}
                            review={p.rating.reviewCount}
                            loc={p.restaurantAddress.city}
                            key={i}
                            date={ctx.date}
                        />
                        
                    ))
                }
            </div>
            <About />



        </>
    );
}

export default Home;