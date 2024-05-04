import { useContext } from "react";
import NavContext from "../context/nav";
import { Link, useNavigate } from "react-router-dom";
import Navbar from "../components/navbar";
import { useState } from 'react';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';
import Dropdown from 'react-bootstrap/Dropdown';

const Manager = () => {

    const ctx = useContext(NavContext);

    const navigate = useNavigate();
    const [show, setShow] = useState(false);
    const handleClose = () => {

        
        setShow(false);
       
    }
    const handleShow = () => setShow(true);
    const hours = [
        "01:00:00", "02:00:00", "03:00:00", "04:00:00", "05:00:00", "06:00:00", "07:00:00", "08:00:00", "09:00:00", "10:00:00", "11:00:00", "12:00:00",
        "13:00:00", "14:00:00", "15:00:00", "16:00:00", "17:00:00", "18:00:00", "19:00:00", "20:00:00", "21:00:00", "22:00:00", "23:00:00", "00:00:00"
    ]
    return (
        <>
            <Navbar />
            <div class="col-6 mx-auto py-5">
                <div className="d-flex row mt-5 rounded-3" style={{ backgroundColor: "#FFF6F7" }}>
                    <div className="col" >
                        <span>Your reservations are also emailed to </span>
                        <Link to="/" className="text-decoration-none">
                            <span className="text-danger">Tom_holland@ut.ac.ir</span>
                        </Link>
                    </div>
                    <button className="btn btn-danger btn-sm col-2 rounded-5"
                        onClick={() => {
                            ctx.handleLogout();
                            navigate("/login");
                        }}
                    > logout </button>
                </div>
                <div class="row mt-3">
                    <div class="container mb-4">
                        <div class="row align-items-center">
                            <div class="col py-3">My Restaurant</div>
                            <div class="col text-end">
                                <button type="button" class="btn btn-danger rounded-5" onClick={handleShow}>
                                    Add
                                </button>
                                <Modal show={show} onHide={handleClose}>
                                    <Modal.Header closeButton>
                                        <Modal.Title>Add Restaurant</Modal.Title>
                                    </Modal.Header>
                                    <Modal.Body>
                                        <Form >
                                            <Form.Group className="mb-3 d-flex" controlId="exampleForm.ControlInput1">
                                                <Form.Label className="me-5">Name</Form.Label>
                                                <Form.Control
                                                    value={ctx.addRest?.name}
                                                    onChange={(e) => ctx.handleChangeAddRest(e)}
                                                    style={{ backgroundColor: "#F5F5F5", width: "150px", marginLeft: "130px" }}
                                                    type="text"

                                                />
                                            </Form.Group>
                                            <Form.Group className="mb-3 d-flex" controlId="exampleForm.ControlInput1">
                                                <Form.Label className="me-5">Type</Form.Label>
                                                <Form.Control
                                                value={ctx.addRest?.type}
                                                    onChange={(e) => ctx.handleChangeAddRest(e)}
                                                    style={{ backgroundColor: "#F5F5F5", width: "150px", marginLeft: "140px" }}
                                                    type="text"

                                                />
                                            </Form.Group>
                                            <Form.Group
                                                className="mb-3"
                                                controlId="exampleForm.ControlTextarea1"
                                            >
                                                <Form.Label>Description</Form.Label>
                                                <Form.Control
                                                value={ctx.addRest?.description}
                                                    onChange={(e) => ctx.handleChangeAddRest(e)}
                                                    as="textarea"
                                                    placeholder="Type about your restaurant..."
                                                    rows={5}
                                                    style={{ backgroundColor: "#F5F5F5" }}
                                                />
                                            </Form.Group>
                                            <Form.Group className="mb-3 d-flex" controlId="exampleForm.ControlInput1">
                                                <Form.Label className="me-5">Counrty</Form.Label>
                                                <Form.Control
                                                value={ctx.addRest?.country}
                                                    onChange={(e) => ctx.handleChangeAddRest(e)}
                                                    style={{ backgroundColor: "#F5F5F5", width: "150px", marginLeft: "130px" }}
                                                    type="text"

                                                />
                                            </Form.Group>
                                            <Form.Group className="mb-3 d-flex" controlId="exampleForm.ControlInput1">
                                                <Form.Label className="me-5">City</Form.Label>
                                                <Form.Control
                                                value={ctx.addRest?.city}
                                                    onChange={(e) => ctx.handleChangeAddRest(e)}
                                                    style={{ backgroundColor: "#F5F5F5", width: "150px", marginLeft: "159px" }}
                                                    type="text"

                                                />
                                            </Form.Group>
                                            <Form.Group className="mb-3 d-flex" controlId="exampleForm.ControlInput1">
                                                <Form.Label className="me-5">Street</Form.Label>
                                                <Form.Control
                                                value={ctx.addRest?.street}
                                                    onChange={(e) => ctx.handleChangeAddRest(e)}
                                                    style={{ backgroundColor: "#F5F5F5", width: "150px", marginLeft: "146px" }}
                                                    type="text"

                                                />
                                            </Form.Group>
                                            <Form.Group className="mb-3 d-flex" >
                                                <Form.Label className="me-5">Start Hour</Form.Label>
                                                <Dropdown onChange={(e) => ctx.handleChangeAddRest(e)}
                                                    style={{ backgroundColor: "#F5F5F5", width: "150px", marginLeft: "130px", borderRadius: "12px", borderStyle: "groove" }}>

                                                    <Dropdown.Menu>
                                                        {
                                                            hours.map((st, i) => (
                                                                <Dropdown.Item value={ctx.addRest?.openTime}>{st}</Dropdown.Item>
                                                            ))
                                                        }

                                                    </Dropdown.Menu>
                                                    <Dropdown.Toggle variant="light" id="number"></Dropdown.Toggle>
                                                </Dropdown>
                                            </Form.Group>
                                            <Form.Group className="mb-3 d-flex" >
                                                <Form.Label className="me-5">End Hour</Form.Label>
                                                <Dropdown onChange={(e) => ctx.handleChangeAddRest(e)}
                                                    style={{ backgroundColor: "#F5F5F5", width: "150px", marginLeft: "135px", borderRadius: "12px", borderStyle: "groove" }}>
                                                    <Dropdown.Toggle variant="light" id="number"></Dropdown.Toggle>
                                                    <Dropdown.Menu>
                                                        {
                                                            hours.map((et, i) => (
                                                                <Dropdown.Item value={ctx.addRest?.closeTime}>{et}</Dropdown.Item>
                                                            ))
                                                        }

                                                    </Dropdown.Menu>
                                                </Dropdown>
                                            </Form.Group>
                                        </Form>
                                    </Modal.Body>
                                    <Modal.Footer className="d-flex justify-content-center">
                                        <Button variant="danger" onClick={(e) => ctx.handleSubmitAddRest(e)} className="col">
                                            Add
                                        </Button>

                                    </Modal.Footer>
                                </Modal>
                            </div>
                        </div>
                    </div>
                    <hr />
                    <table class="table">
                        <tbody>
                            {ctx.managerRest.map((restaurant) => (
                                <tr key={restaurant.Name} class="align-items-center">
                                    {" "}
                                    <th scope="row" class="align-middle">
                                        {restaurant.Name}
                                    </th>
                                    <td class="align-middle">
                                        {restaurant.restaurantAddress?.city}, {restaurant.restaurantAddress?.country}
                                    </td>
                                    <td class="text-end">
                                        <div class="text-decoration-none text-danger">
                                            <button
                                                onClick={() => { navigate("/managerManage") }}
                                                type="button"
                                                class="btn btn-danger rounded-5"
                                            >
                                                Manage
                                            </button>
                                        </div>
                                    </td>{" "}
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </>);
}

export default Manager;