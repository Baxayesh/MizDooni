import { useState, useEffect, useContext } from "react";
import Navbar from "../components/navbar";
import Search from "../components/search";
import axios from 'axios';
import About from "../components/about";
import Cards from "../components/cards";
import CardContext from './../context/cards';
import { useNavigate } from "react-router-dom";
import NavContext from "../context/nav";
// import SearchContext from './../context/search.js';

const Home = () => {
    // const [data, setData] = useState([]);
    // const [ratingData, setRatingData] = useState([]);
    // const [date, setDate] = useState(new Date());
    // const [location, setLocation] = useState([]);
    // const [restType, setRestType] = useState([]);
    // const [searchInfo, setSearchInfo] = useState([]);
    const navigate = useNavigate();
    // const CardContext = useContext(CardContext);

    // const baseUrl = "http://localhost:8080/restaurants";

    // useEffect(() => {
    //     axios.get(baseUrl + "?recommendBy=userLocation")
    //         .then((res) => {

    //             setData(res.data)
    //         })
    //         .catch((err) => { console.log(err) });
    // }, [])

    // useEffect(() => {
    //     axios.get(baseUrl + "?recommendBy=rating")
    //         .then((res) => {

    //             setRatingData(res.data)
    //         })
    //         .catch((err) => { console.log(err) });
    // }, [])

    // useEffect(() => {
    //     setDate(new Date());
    // }, [])

    // useEffect(() => {
    //     axios.get("http://localhost:8080/locations")
    //         .then((res) => {
    //             setLocation(res.data)
    //         })
    //         .catch((err) => { console.log(err) });
    // }, [])

    // useEffect(() => {
    //     axios.get("http://localhost:8080/foodTypes")
    //         .then((res) => {
    //             setRestType(res.data)
    //         })
    //         .catch((err) => { console.log(err) });
    // }, [])


    // const handleChange = (e) => {
    //     const { value, name } = e.target;
    //     setSearchInfo({ ...searchInfo, [name]: value });
        
    // }



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

            {/* <Navbar /> */}
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