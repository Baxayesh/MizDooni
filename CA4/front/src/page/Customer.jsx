import { Link } from "react-router-dom";
import { useContext, useState } from "react";
import NavContext from "../context/nav";
import Navbar from "../components/navbar";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';

const Customer = () => {
    const ctx = useContext(NavContext);
    const navigate = useNavigate();

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);


    console.log(ctx.userReserves)

    return (
        <>
            <Navbar />
            <div className="container py-5 mt-5">
                <div className="d-flex row mt-2 rounded-3" style={{ backgroundColor: "#FFF6F7" }}>
                    <div className="col">
                        <span>Your reservations are also emailed to </span>
                        <Link to="/" className="text-decoration-none">
                            <span className="text-danger">Tom_holland@ut.ac.ir</span>
                        </Link>

                    </div>
                    <div className="col text-end">Address: Tehran, Iran</div>
                    <button className="btn btn-danger btn-sm col-1 rounded-5"
                        onClick={() => {
                            ctx.handleLogout();
                            navigate("/login");
                        }}
                    > logout </button>
                </div>

                <div className="row mt-3">
                    <table className="table" style={{ backgroundColor: "#D9D9D9", fontFamily: "Spline Sans" }}>
                        <div className="py-3 rounded-3 ms-3">My Reservations</div>
                        <tbody>
                            {/* <tr>
                                <th scope="row" className="text-success">
                                    2024-06-22 16:00
                                </th>
                                <td className="text-danger">ali daei dizi</td>
                                <td className="text-success">Table-12</td>
                                <td className="text-success">4-seats</td>
                                <td className="text-center">
                                    <button className="text-decoration-none text-danger rounded-3" onClick={handleShow}>
                                        Cancel
                                    </button>
                                    <Modal show={show} onHide={handleClose}>
                                        <Modal.Header closeButton>
                                            <Modal.Title>Cancel Reservation at </Modal.Title>
                                        </Modal.Header>
                                        <Modal.Body>
                                            <p style={{ color: "#7E7E7E" }}>Note: Once you hit the Cancel button, your reserve will be canceled</p>
                                            <form>
                                                <div class="form-check">
                                                    <input class="form-check-input" type="checkbox" id="gridCheck" />
                                                    <label class="form-check-label" for="gridCheck">
                                                        I agree
                                                    </label>
                                                </div>

                                            </form>
                                        </Modal.Body>
                                        <Modal.Footer>
                                            <Button variant="danger" className="col" onClick={handleClose}>
                                                Cancel
                                            </Button>

                                        </Modal.Footer>
                                    </Modal>
                                </td>
                            </tr> */}
                            {
                                (ctx.userReserves != []) ?
                                    (<p className="text-center" >No Reservasion yet!</p>) :
                                    (ctx.userReserves?.reserveTime > ctx.date) ?
                                        (
                                            <tr>
                                                <th scope="row" className="text-success">
                                                    2024-06-22 16:00
                                                </th>
                                                <td className="text-danger">ali daei dizi</td>
                                                <td className="text-success">Table-12</td>
                                                <td className="text-success">4-seats</td>
                                                <td className="text-center">
                                                    <button className="text-decoration-none text-danger rounded-3" onClick={handleShow}>
                                                        Cancel
                                                    </button>
                                                    <Modal show={show} onHide={handleClose}>
                                                        <Modal.Header closeButton>
                                                            <Modal.Title>Cancel Reservation at </Modal.Title>
                                                        </Modal.Header>
                                                        <Modal.Body>
                                                            <p style={{ color: "#7E7E7E" }}>Note: Once you hit the Cancel button, your reserve will be canceled</p>
                                                            <form>
                                                                <div class="form-check">
                                                                    <input class="form-check-input" type="checkbox" id="gridCheck" />
                                                                    <label class="form-check-label" for="gridCheck">
                                                                        I agree
                                                                    </label>
                                                                </div>

                                                            </form>
                                                        </Modal.Body>
                                                        <Modal.Footer>
                                                            <Button variant="danger" className="col" onClick={handleClose}>
                                                                Cancel
                                                            </Button>

                                                        </Modal.Footer>
                                                    </Modal>
                                                </td>
                                            </tr>
                                        ) :
                                        (
                                            <tr>
                                                <th scope="row" className="text-muted">
                                                    2024-06-22 16:00
                                                </th>
                                                <td className="text-danger">ali daei dizi</td>
                                                <td className="text-muted">Table-12</td>
                                                <td className="text-muted">4-seats</td>
                                                <td className="text-center">
                                                    <button className="text-decoration-none text-danger rounded-3">
                                                        Add Comment
                                                    </button>
                                                </td>
                                            </tr>
                                        )
                            }

                        </tbody>
                    </table>
                </div>
            </div >
        </>
    );
}

export default Customer;