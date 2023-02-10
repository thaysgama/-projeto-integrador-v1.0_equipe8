import { createContext, useState, useEffect } from "react";

export const ReservationContext = createContext();

export const NO_RESERVATION_STATE = {
    checkInDate: "",
    checkOutDate: "",
    clientId: "",
    productId: ""
}


const ReservationProvider = ({ children }) =>{
    const [reservationState, setReservationState] = useState(
        JSON.parse(localStorage.getItem("reservationState")) ||
        NO_RESERVATION_STATE
    );

    useEffect(() => {
        localStorage.setItem("reservationState", JSON.stringify(reservationState));
      }, [reservationState]);

    return (
        <ReservationContext.Provider
            value={{reservationState, setReservationState}}>
            {children}
        </ReservationContext.Provider>
    )
}

export default ReservationProvider;