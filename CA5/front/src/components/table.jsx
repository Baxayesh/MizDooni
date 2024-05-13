import React from "react";
import SharpSign from "../image/SharpSign.png";
import Sofa from "../image/Sofa.png";
import "bootstrap/dist/css/bootstrap.min.css";

export const Table = (props) => {
    return (
        <div className="col-md-auto border m-3" style={{ backgroundColor: "#861a24", borderRadius: "12px" }}>
            <div className="container-fluid p-2">
                <div className="row ms-1 mb-1 align-items-center">
                    <div className="col-auto">
                        <img src={SharpSign} alt="" />
                    </div>
                    <div className="col-auto text-light">{props.table}</div>
                </div>
                <div className="row ms-1 mb-1 align-items-center">
                    <div className="col-auto">
                        <img src={Sofa} alt="" />
                    </div>
                    <div className="col-auto text-light">{props.seat}</div>
                </div>
            </div>
        </div>
    );
};