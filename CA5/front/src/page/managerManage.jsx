import Navbar from "../components/navbar";
import { Table } from "../components/table";
import { ReserveTable } from "../components/reserveTable";
import { useEffect, useContext, useState } from "react";
import NavContext from "../context/nav";
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';


const ManagerManage = () => {

    const ctx = useContext(NavContext);
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    return (
        <>
            <Navbar />
            <div className="h-100" style={{ marginTop: "90px" }}>
                <div className="container-fluid text-bg-danger bg-danger d-flex justify-content-between text-light">
                    <div className="col-auto ms-5">{ctx.managerRest.Name}</div>
                    <div className="col-10 me-5 text-end px-5">
                        Address:
                        {ctx.managerRest.restaurantAddress?.street}, {ctx.managerRest.restaurantAddress?.city},
                        {ctx.managerRest.restaurantAddress?.counrty}
                    </div>
                </div>

                <div className="container-fluid d-flex h-100 justify-content">
                    <div className="col-4">
                        <div className="container h-100 border-end border-5 border-danger">
                            <div className="row align-items-center">
                                <div className="col py-3 rounded-top">
                                    <span className="font-weight-bold">Reservation List</span>
                                </div>
                                <div className="col text-end">
                                    <span className="col me-1" style={{ fontSize: "10px", color: "#7e7e7e" }}>
                                        Select a table to see its reservations
                                    </span>
                                </div>
                            </div>
                            <table className="table">
                                <tbody>
                                    {
                                        ctx.resTable.map((reservation) => (
                                            <ReserveTable
                                                date={reservation.datetime}
                                                name={reservation.username}
                                                tableNumber={reservation.tableNumber}
                                                key={reservation.number}
                                            />
                                        ))
                                    }
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div className="col lightPink">
                        <div className="container bg-light">
                            <div className="row d-flex justify-content-start align-items-start">
                                <div className="text-decoration-none text-danger" style={{ cursor: "pointer" }} onClick={handleShow}>
                                    +Add Table
                                </div>
                                <Modal show={show} onHide={handleClose}>
                                    <Modal.Header closeButton>
                                        <Modal.Title>Add Table</Modal.Title>
                                    </Modal.Header>
                                    <Modal.Body>
                                        <Form className="d-flex">
                                            <Form.Label>Number of Seats</Form.Label>
                                            <Form.Control
                                                onChange={(e)=>ctx.handleSeatChange(e)}
                                                type="text"
                                                placeholder=""
                                                autoFocus
                                                style={{ backgroundColor: "#F5F5F5", width: "150px", marginLeft: "130px" }}
                                            />
                                        </Form>
                                    </Modal.Body>
                                    <Modal.Footer>
                                        <Button className="col" variant="danger" onClick={(e)=>{
                                            setShow(false);
                                            ctx.handleAddTable(e);
                                            
                                        }}>
                                            Add
                                        </Button>

                                    </Modal.Footer>
                                </Modal>
                            </div>
                            <div className="container align-self-center">
                                <div className="row justify-content-center">
                                    {
                                        (ctx.showTable == []) || (ctx.showTable == undefined) ?
                                            (<p className="d-flex align-content-center justify-content-center">
                                                No Tables have been added.</p>) :
                                            (ctx.showTable.map((table, i) => (
                                                <Table
                                                    table={table.tableNumber}
                                                    seat={table.numberOfSeats}
                                                    key={i}
                                                />
                                            )))
                                    }
                                    <Table
                                        table={"test"}
                                        seat={"test"}
                                    />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>);
}

export default ManagerManage;