import { useContext, useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import NavContext from "../context/nav";
import axios from "axios";
import Time from "../image/Time.png";
import REVIEW from "../image/carbon_review.png";
import TYP from "../image/Vector_Dizy.png";
import MM from "../image/mapMarker.svg";
import DOT from "../image/DOT.png";
import "../css/styles.css";
import DatePicker from 'react-date-picker';
import 'react-date-picker/dist/DatePicker.css';
import 'react-calendar/dist/Calendar.css';
import Dropdown from 'react-bootstrap/Dropdown';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Fstar from "./../image/starFull.svg";
import Estar from "./../image/starEmpty.svg";
import Pagination from "../image/pagination.png";
import Navbar from "../components/navbar";
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';

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

    useEffect(() => {
        axios.get(`http://localhost:8080/reviews?restaurant=${state.name}&limit=5`)
            .then((res) => {

                ctx.setReviews(res.data.items)
            })
            .catch((err) => { console.log(err) });
    }, []);

    const handleChange = (e) => {
        const { value, name } = e.target;
        ctx.setAddComment({ ...ctx.addComment, [name]: value });
      }

    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const [showReserve, setShowReserve] = useState(false);
    const handleCloseReserve = () => setShowReserve(false);
    const handleShowReserve = () => setShowReserve(true);

    console.log(ctx.reviews)
    return (
        <>
            <Navbar />
            <div id="sec1" className="container" style={{ marginTop: "100px" }}>
                <div className="row">
                    <div className="col-6">
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
                                <img src={REVIEW} alt="review" /> &nbsp; {ctx.rest.rating?.reviewCount} Reviews &nbsp;&nbsp;
                                <img src={TYP} alt="type" />&nbsp; {ctx.rest.Type}
                            </p>
                            <p className="rest-loc">
                                <img src={MM} alt="mapMarker" /> &nbsp;&nbsp;
                                {ctx.rest.restaurantAddress?.country}, {ctx.rest.restaurantAddress?.city}, {ctx.rest.restaurantAddress?.street}
                            </p>
                            <p>
                                {ctx.rest.Description}
                            </p>
                        </div>

                    </div>
                    <div className="col-6" style={{ right: -"50px" }}>
                        <h5 style={{ marginTop: "20px" }}>Reserve Table</h5>
                        <div className="d-flex justify-content-around">
                            For
                            <Dropdown onChange={(e) => { ctx.setPeople(e.target.value) }}>
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


                                        (ctx.times.items != undefined) ? (ctx.times.items != []) ?

                                            (ctx.times?.items.map((p, i) => (
                                                <button className="btn btn-outline-danger" key={i}>{p}</button>
                                            ))) :
                                            (
                                                <p className="">No Table is Available on this date.</p>
                                            ) : (
                                            <>
                                                <div className="d-flex justify-content-around">
                                                    <button className="btn btn-outline" disabled="true">11:00 PM</button>
                                                    <button className="btn btn-outline" disabled="true">12:00 PM</button>
                                                    <button className="btn btn-outline" disabled="true">13:00 PM</button>
                                                    <button className="btn btn-outline" disabled="true">14:00 PM</button>
                                                </div>
                                                <div className="d-flex justify-content-around">
                                                    <button className="btn btn-outline" disabled="true">15:00 PM</button>
                                                    <button className="btn btn-outline" disabled="true">18:00 PM</button>
                                                    <button className="btn btn-outline" disabled="true">19:00 PM</button>
                                                    <button className="btn btn-outline" disabled="true">20:00 PM</button>
                                                </div>
                                            </>
                                        )

                                    }
                                </Col>
                            </Row>
                        </Container>

                        <p className="reserve-notification">You will reserve this table only for one hour, for more time please
                            contact the restaurant.</p>
                        <button className="btn btn-danger btn-lg btn-block col" onClick={handleShowReserve}>Complete the Reservation</button>
                        <Modal show={showReserve} onHide={handleCloseReserve}>
                            <Modal.Header closeButton>
                                <Modal.Title>Reservation Detail</Modal.Title>
                            </Modal.Header>
                            <Modal.Body>
                                <p style={{ color: "#7E7E7E" }}>Note:  Please Arrive at Least 15 Minutes Early.</p>
                                <div className="row">
                                    <div className="col-8">Table Number</div>
                                    <div className="col-4 text-center">12</div>
                                </div>
                                <div className="row">
                                    <div className="col-8">Time</div>
                                    <div className="col-4 text-center">12:00 P.M.</div>
                                </div>
                                <div className="row">
                                    <div className="col">Address</div>
                                    
                                </div>
                                <div className="row">
                                    <div className="col" style={{ color: "#7E7E7E" }}>Iran,  Boshehr, Vali-e-Asr Square</div>
                                    
                                </div>
                            </Modal.Body>
                            <Modal.Footer>
                                <Button className="col" variant="outline-danger" onClick={handleCloseReserve}>
                                    Close
                                </Button>
                                
                            </Modal.Footer>
                        </Modal>

                    </div>
                </div>
            </div>
            <div id="sec2" className="container">
                <div className="row"
                    style={{ backgroundColor: "#FFF6F7", borderRadius: "12px 12px 12px 12px", marginBottom: "50px" }}>
                    <div className="col-md-6 col-xs-12">
                        <p className="review-title">What {ctx.rest.rating?.reviewCount} people are saying</p>
                        <p style={{ color: " #7E7E7E", fontSize: "12px" }}>
                            {
                                (ctx.rest.rating?.overallScore == 5) ? (
                                    <>
                                        <img src={Fstar} alt="star" />
                                        <img src={Fstar} alt="star" />
                                        <img src={Fstar} alt="star" />
                                        <img src={Fstar} alt="star" />
                                        <img src={Fstar} alt="star" />
                                    </>
                                ) : (4 < ctx.rest.rating?.overallScore < 5) ? (
                                    <>
                                        <img src={Fstar} alt="star" />
                                        <img src={Fstar} alt="star" />
                                        <img src={Fstar} alt="star" />
                                        <img src={Fstar} alt="star" />
                                        <img src={Estar} alt="star" />
                                    </>
                                ) : (3 < ctx.rest.rating?.overallScore <= 4) ? (
                                    <>
                                        <img src={Fstar} alt="star" />
                                        <img src={Fstar} alt="star" />
                                        <img src={Fstar} alt="star" />
                                        <img src={Estar} alt="star" />
                                        <img src={Estar} alt="star" />
                                    </>
                                ) : (2 < ctx.rest.rating?.overallScore <= 3) ? (
                                    <>
                                        <img src={Fstar} alt="star" />
                                        <img src={Fstar} alt="star" />
                                        <img src={Estar} alt="star" />
                                        <img src={Estar} alt="star" />
                                        <img src={Estar} alt="star" />
                                    </>
                                ) : (1 < ctx.rest.rating?.overallScore <= 2) ? (
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
                            &nbsp;&nbsp; 4 based on recent ratings
                        </p>
                    </div>
                    <div className="col-md-6 col-xs-12">
                        <div className="d-flex justify-content-around mt-2">
                            <p className="rating-name">Food</p>
                            <p className="rating-name">Service</p>
                            <p className="rating-name">Ambience</p>
                            <p className="rating-name">Overall</p>
                        </div>
                        <div className="d-flex justify-content-around">
                            <p className="ratings">{ctx.rest.rating?.foodScore}</p>
                            <p className="ratings">{ctx.rest.rating?.serviceScore}</p>
                            <p className="ratings">{ctx.rest.rating?.ambianceScore}</p>
                            <p className="ratings">{ctx.rest.rating?.overallScore}</p>
                        </div>
                    </div>
                </div>
            </div>
            <div className="container-fluid row">
                <div className="col-md-6 col-xs-6">
                    <p className="rest-info-heading">{ctx.rest.rating?.reviewCount} Reviews</p>
                </div>
                <div className="col-md-6 col-xs-6 d-flex justify-content-end">
                    <button className="btn btn-danger" onClick={handleShow}>Add Review</button>
                    <Modal show={show} onHide={handleClose}>
                        <Modal.Header closeButton>
                            <Modal.Title>Add Review for</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            <p style={{ color: "#7E7E7E" }}>Note: Reviews can only be made by diners who have eaten at this restaurant </p>
                            <Form>
                                <Form.Group className="mb-3 d-flex" controlId="exampleForm.ControlInput1">
                                    <Form.Label className="me-2">Food Quality</Form.Label>
                                    <div className="align-content-center ms-5">
                                        <img src={Estar} alt="star" />
                                        <img src={Estar} alt="star" />
                                        <img src={Estar} alt="star" />
                                        <img src={Estar} alt="star" />
                                        <img src={Estar} alt="star" />
                                    </div>
                                </Form.Group>
                                <Form.Group className="mb-3 d-flex" controlId="exampleForm.ControlInput1">
                                    <Form.Label className="me-5">Service</Form.Label>
                                    <div className="ms-5">
                                        <img src={Estar} alt="star" />
                                        <img src={Estar} alt="star" />
                                        <img src={Estar} alt="star" />
                                        <img src={Estar} alt="star" />
                                        <img src={Estar} alt="star" />
                                    </div>
                                </Form.Group>
                                <Form.Group className="mb-3 d-flex" controlId="exampleForm.ControlInput1">
                                    <Form.Label className="me-5">Ambience</Form.Label>
                                    <div className="ms-4">
                                        <img src={Estar} alt="star" />
                                        <img src={Estar} alt="star" />
                                        <img src={Estar} alt="star" />
                                        <img src={Estar} alt="star" />
                                        <img src={Estar} alt="star" />
                                    </div>
                                </Form.Group>
                                <Form.Group className="mb-3 d-flex" controlId="exampleForm.ControlInput1">
                                    <Form.Label className="me-5">Overall</Form.Label>
                                    <div className="ms-5">
                                        <img src={Estar} alt="star" />
                                        <img src={Estar} alt="star" />
                                        <img src={Estar} alt="star" />
                                        <img src={Estar} alt="star" />
                                        <img src={Estar} alt="star" />
                                    </div>
                                </Form.Group>
                                <Form.Group
                                    className="mb-3"
                                    controlId="exampleForm.ControlTextarea1"
                                >
                                    <Form.Label>Comment</Form.Label>
                                    <Form.Control
                                        as="textarea"
                                        placeholder="Type your review..."
                                        rows={5}
                                        style={{ backgroundColor: "#F5F5F5" }}
                                        onChange={(e)=>{handleChange(e)}}
                                    />
                                </Form.Group>

                            </Form>
                        </Modal.Body>
                        <Modal.Footer>
                            <Button variant="danger" onClick={ctx.handleAddComment} className="col">
                                Submit Review
                            </Button>
                            <Button variant="outline-danger" onClick={handleClose} className="col">
                                Cancel
                            </Button>
                        </Modal.Footer>
                    </Modal>
                </div>
            </div>


            <div className="container mb-5">
                {
                    ctx.reviews.map((p, i) => (
                        <div className="row">
                            <div className="col-md-8 col-xs-12">
                                <div className="row">
                                    <div className="avatar">AD</div>
                                    <div className="col-11">
                                        <p className="review-writer">{p.issuer}</p>
                                        <p style={{ fontSize: "12px", fontFamily: "Spline Sans" }}>
                                            Overall <span style={{ color: "#ED3545" }}>{p.overallScore}</span>
                                            <img src={DOT} />
                                            Food <span style={{ color: "#ED3545" }}>{p.foodScore}</span>
                                            <img src={DOT} />
                                            Service <span style={{ color: "#ED3545" }}>{p.serviceScore}</span>
                                            <img src={DOT} />
                                            Ambience <span style={{ color: "#ED3545" }}>{p.ambianceScore}</span>
                                        </p>
                                        <p style={{ fontFamily: "Spline Sans", fontSize: "14px" }}>
                                            {p.comment}
                                        </p>
                                    </div>
                                </div>
                            </div>
                            <div className="col-md-4 col-xs-12">
                                <p style={{ color: "#7E7E7E", fontSize: "12px", marginTop: "10px" }}>
                                    {
                                        (p.overallScore == 5) ? (
                                            <>
                                                <img src={Fstar} alt="star" />
                                                <img src={Fstar} alt="star" />
                                                <img src={Fstar} alt="star" />
                                                <img src={Fstar} alt="star" />
                                                <img src={Fstar} alt="star" />
                                            </>
                                        ) : (4 < p.overallScore < 5) ? (
                                            <>
                                                <img src={Fstar} alt="star" />
                                                <img src={Fstar} alt="star" />
                                                <img src={Fstar} alt="star" />
                                                <img src={Fstar} alt="star" />
                                                <img src={Estar} alt="star" />
                                            </>
                                        ) : (3 < p.overallScore <= 4) ? (
                                            <>
                                                <img src={Fstar} alt="star" />
                                                <img src={Fstar} alt="star" />
                                                <img src={Fstar} alt="star" />
                                                <img src={Estar} alt="star" />
                                                <img src={Estar} alt="star" />
                                            </>
                                        ) : (2 < p.overallScore <= 3) ? (
                                            <>
                                                <img src={Fstar} alt="star" />
                                                <img src={Fstar} alt="star" />
                                                <img src={Estar} alt="star" />
                                                <img src={Estar} alt="star" />
                                                <img src={Estar} alt="star" />
                                            </>
                                        ) : (1 < p.overallScore <= 2) ? (
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
                                    &nbsp;&nbsp;   Dined on {p.issueTime}
                                </p>
                            </div>
                            <hr />
                        </div>
                    ))
                }
                <div className="row justify-content-center">
                    <div className="col-auto">
                        <div className="d-flex align-items-center justify-content-center">
                            <img src={Pagination} alt="" />
                        </div>
                    </div>
                </div>
            </div>

        </>);
}

export default Restaurant;