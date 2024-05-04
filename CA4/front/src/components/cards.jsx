import MapMarker from "./../image/mapMarker.svg";
import Fstar from "./../image/starFull.svg";
import Estar from "./../image/starEmpty.svg";
import {Link} from "react-router-dom";
// import { useNavigate } from "react-router-dom";
const Cards = ({
    name,
    openTime,
    closeTime,
    type,
    image,
    review,
    loc,
    date,
    score
}) => {

    const styles = {
        card: {
            height: "auto",
            position: " relative",
            borderStyle: "groove",
            borderRadius: "15px 15px",

        },

        restImage: {
            position: " relative",
            borderRadius: "15px 15px 0px 0px",
            width: "100%",
            height: "150px",
            padding: "0px",
            margin: "0px",

        },
        ratingBox: {
            position: "absolute",
            width: "80%",
            marginTop: "-140px",
            backgroundColor: "#FFF6F7",
            borderRadius: "0px 15px 15px 0px",
        },
        restaurantName: {
            fontSize: "13px",
            fontWeight: "400",
            lineHeight: "17px",
            letterSpacing: "0em",
            textAlign: "left",
            color: " #303030",
        },
        reviews: {
            fontSize: " 10px",
            fontWeight: " 500",
            lineHeight: "12px",
            letterSpacing: " 0em",
            textAlign: "left",
            color: " #7E7E7E",

        },
        restaurantType: {

            fontSize: "11px",
            fontWeight: "300",
            lineHeight: "12px",
            letterSpacing: "0em",
            textAlign: "left",
        },
        restaurantLocation: {

            fonSize: "10px",
            fontWeight: "400",
            lineHeight: "12px",
            letterSpacing: " 0em",
            textAlign: "left",
        },
        openOrCloseTime: {
            fontSize: "10px",
            fontWeight: "400",
            lineHeight: "12px",
            letterSpacing: "0em",
            textAlign: "left",
        },
        cardLink:{
            textDecoration: "none",
            color:"#303030",
           
        }

    }


    return (
        <>
            <div className="col-md-2 col-xs-4">
            <Link to={`/restaurant`} style={{...styles.cardLink}} state={{name}}>
                <div style={styles.card}>
                    <img className="img-responsive" style={{ ...styles.restImage }} src={image} alt='rest' />
                    <div className="d-flex justify-content-between" style={{ ...styles.ratingBox }}>
                        &nbsp;
                        {
                            (score == 5) ? (
                                <>
                                    <img src={Fstar} alt="star" />
                                    <img src={Fstar} alt="star" />
                                    <img src={Fstar} alt="star" />
                                    <img src={Fstar} alt="star" />
                                    <img src={Fstar} alt="star" />
                                </>
                            ) : (4 < score < 5) ? (
                                <>
                                    <img src={Fstar} alt="star" />
                                    <img src={Fstar} alt="star" />
                                    <img src={Fstar} alt="star" />
                                    <img src={Fstar} alt="star" />
                                    <img src={Estar} alt="star" />
                                </>
                            ) : (3 < score <= 4) ? (
                                <>
                                    <img src={Fstar} alt="star" />
                                    <img src={Fstar} alt="star" />
                                    <img src={Fstar} alt="star" />
                                    <img src={Estar} alt="star" />
                                    <img src={Estar} alt="star" />
                                </>
                            ) : (2 < score <= 3) ? (
                                <>
                                    <img src={Fstar} alt="star" />
                                    <img src={Fstar} alt="star" />
                                    <img src={Estar} alt="star" />
                                    <img src={Estar} alt="star" />
                                    <img src={Estar} alt="star" />
                                </>
                            ) : (1 < score <= 2) ? (
                                <>
                                    <img src={Fstar} alt="star" />
                                    <img src={Estar} alt="star" />
                                    <img src={Estar} alt="star" />
                                    <img src={Estar} alt="star" />
                                    <img src={Estar} alt="star" />
                                </>
                            ) : (
                                <>
                                    <img src={Estar} alt="star" />
                                    <img src={Estar} alt="star" />
                                    <img src={Estar} alt="star" />
                                    <img src={Estar} alt="star" />
                                    <img src={Estar} alt="star" />
                                </>
                            )
                        }
                        &nbsp;
                    </div>
                    <p style={{ ...styles.restaurantName }}>&nbsp;{name}</p>
                    <p style={{ ...styles.reviews }}>&nbsp;{review} reviews</p>
                    <p style={{ ...styles.restaurantType }}>&nbsp;{type}</p>
                    <p style={{ ...styles.restaurantLocation }}>
                        &nbsp; <img src={MapMarker} alt="mapMarker" />&nbsp;
                        {loc}
                    </p>

                    <div className="d-inline-flex" style={{ ...styles.openOrCloseTime }}>&nbsp;
                        {(openTime < date.toLocaleTimeString() < closeTime) ?
                            (<>
                                <p style={{ color: "#2B9407" }}>Open</p>&nbsp;
                                <p style={{ fontSize: "20px", lineHeight: "0px" }}> . </p>&nbsp;
                                <p>Closes at {closeTime}</p>
                            </>)
                            :
                            (<>
                                <p style={{ color: "#ED3545" }}>Closed</p>&nbsp;
                                <p style={{ fontSize: "20px", lineHeight: "0px" }}> . </p>&nbsp;
                                <p>Opens at {openTime} </p>
                            </>)
                        }

                    </div>
                </div>
                </Link>
            </div>
        </>
    );
}

export default Cards;