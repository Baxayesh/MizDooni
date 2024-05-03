import { useContext, useEffect } from "react";
import { useLocation } from "react-router-dom";
import NavContext from "../context/nav";
import axios from "axios";
import Time from "../image/Time.png";
import REVIEW from "../image/carbon_review.png";
import TYP from "../image/Vector_Dizy.png";
import MM from "../image/mapMarker.svg";
import "../css/styles.css";
import DatePicker from 'react-date-picker';
import 'react-date-picker/dist/DatePicker.css';
import 'react-calendar/dist/Calendar.css';
import Dropdown from 'react-bootstrap/Dropdown';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

const Restaurant = () => {
    const ctx = useContext(NavContext);
    const { state } = useLocation();

    useEffect(() => {
        axios.get(`http://localhost:8080/restaurants/${state.name}`)
            .then((res) => {

                ctx.setRest(res.data)
            })
            .catch((err) => { console.log(err) });
    }, []);
    console.log(ctx.rest)

    // const {country, city, street} = ctx.rest.restaurantAddress;

    return (
        <>
            <div id="sec1" className="container" style={{ marginTop: "100px" }}>
                <section className="row">
                    <div className="col-md-6 col-xs-12">
                        <div className="container">
                            <img className="restaurant-image" src={ctx.rest.ImageUri} alt="rest" />
                            <div className="rest-name-container">
                                <p className="rest-name d-flex justify-content-start">{ctx.rest.Name}</p>
                                <button className="btn btn-success d-flex justify-content-end" style={{ marginLeft: "300px" }} >Open!</button>
                            </div>
                        </div>
                        <div>
                            <p className="rest-info-heading">
                                <img src={Time} alt="time" /> &nbsp; From {ctx.rest.OpenTime} to {ctx.rest.CloseTime} &nbsp;
                                {/* <img src={REVIEW} alt="review" /> &nbsp; {ctx.rest.rating.reviewCount} Reviews &nbsp;&nbsp; */}
                                <img src={TYP} alt="type" />&nbsp; {ctx.rest.Type}
                            </p>
                            <p className="rest-loc">
                                <img src={MM} alt="mapMarker" /> &nbsp;&nbsp;
                                {/* {ctx.rest.restaurantAddress.country}, {ctx.rest.restaurantAddress.city}, {ctx.rest.restaurantAddress.street} */}
                            </p>
                            <p>
                                {ctx.rest.Description}
                            </p>
                        </div>

                    </div>
                    <div className="col-md-6 col-xs-12" style={{ right: -"50px" }}>
                        <h5 style={{ marginTop: "20px" }}>Reserve Table</h5>
                        <div className="d-flex justify-content-around">
                            For
                            <Dropdown onSelect={(e)=>{ctx.setPeople(e.traget.value)}}>
                                <Dropdown.Toggle variant="light" id="number"></Dropdown.Toggle>
                                <Dropdown.Menu>
                                    <Dropdown.Item value={1}>1</Dropdown.Item>
                                    <Dropdown.Item value={2}>2</Dropdown.Item>
                                    <Dropdown.Item value={3}>3</Dropdown.Item>
                                    <Dropdown.Item value={4}>4</Dropdown.Item>
                                    <Dropdown.Item value={5}>5</Dropdown.Item>
                                    <Dropdown.Item value={6}>6</Dropdown.Item>
                                    <Dropdown.Item value={7}>7</Dropdown.Item>
                                    <Dropdown.Item value={8}>8</Dropdown.Item>
                                    <Dropdown.Item value={9}>9</Dropdown.Item>
                                    <Dropdown.Item value={10}>10</Dropdown.Item>
                                    <Dropdown.Item value={11}>11</Dropdown.Item>
                                    <Dropdown.Item value={12}>12</Dropdown.Item>
                                </Dropdown.Menu>
                            </Dropdown>
                            people, on date
                            <DatePicker onChange={ctx.setResDate} value={ctx.resDate} />
                        </div>
                        <p className="avaliable-times">Available Times</p>
                        <Container>
                            <Row>
                                <Col sm={3}>
                                    {
                                        (ctx.times !== 0) ? 
                                        
                                            (ctx.times.map((p,i)=>(
                                                <button className="btn btn-outline-danger" key={i}>{p}</button>
                                            ))) : (
                                                <p>No Table is Available on this date.</p>
                                            )
                                        
                                    }
                                </Col>
                            </Row>
                        </Container>
                        <div className="d-flex justify-content-around">
                            <button className="btn btn-outline-danger">11:00 PM</button>
                            <button className="btn btn-danger">12:00 PM</button>
                            <button className="btn btn-outline-danger">13:00 PM</button>
                            <button className="btn btn-outline-danger">14:00 PM</button>
                        </div>
                        <div className="d-flex justify-content-around">
                            <button className="btn btn-outline-danger">15:00 PM</button>
                            <button className="btn btn-outline-danger">18:00 PM</button>
                            <button className="btn btn-outline-danger">19:00 PM</button>
                            <button className="btn btn-outline-danger">20:00 PM</button>
                        </div>
                        <p className="reserve-notification">You will reserve this table only for one hour, for more time please
                            contact the restaurant.</p>
                        <button className="btn btn-danger btn-lg btn-block">Complete the Reservation</button>
                    </div>
                </section>
            </div>
        </>);
}

export default Restaurant;