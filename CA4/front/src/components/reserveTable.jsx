import React from "react";

export const ReserveTable = (props) => {
  const extractHour = (dateTimeString) => {
    const date = new Date(dateTimeString);
    const hour = date.getHours();
    const minute = date.getMinutes();
    return `${hour}:${minute}`;
  };
  return (
    <tr className="">
      <td>At: {extractHour(props.date)}</td>
      <td>By {props.name}</td>
      <td>Table-{props.tableNumber}</td>
    </tr>
  );
};