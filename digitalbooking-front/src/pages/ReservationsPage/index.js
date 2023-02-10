import {useState, useContext} from "react";
import ReservationSection from "../../components/ReservationSection";
import { AuthContext } from "../../contexts/AuthProvider";

function ReservationsPage() {


  const { authState } = useContext(AuthContext);
  const url = `booking/user/${authState.id}`;

    return (
      <>
        {/* <Helmet>
        </Helmet> */}
          <ReservationSection url={url}/>
      </>
    )
  }
  export default ReservationsPage;