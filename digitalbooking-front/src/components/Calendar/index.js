import React, { useEffect, useState} from "react";
import "./styles.scss";

import { RangeDatePicker } from "react-google-flight-datepicker";
//import "react-google-flight-datepicker/dist/main.css";
import ptBR from "date-fns/locale/pt-BR";
// import { registerLocale } from "react-datepicker";
// registerLocale("pt-BR",ptBR);


export default function Calendar() {

  const [checkIn, setcheckIn] = useState()
  const [checkOut, setcheckOut] = useState()

  const onDateChange =(startDate, endDate)=>{
    if (startDate != null){
      setcheckIn(startDate)
    }
    if(endDate !=null){
      setcheckOut(endDate)
    }
  }

  

  // useEffect(() => {
  //   handleDates(checkIn, checkOut)
  // }, [checkIn, checkOut, handleDates])
  

  return (
    
      <RangeDatePicker
        startDate={null}
        endDate={null}
        startDatePlaceholder="Check-in"
        endDatePlaceholder="Check-out"
        dateFormat="DD/MM/YY"
        monthFormat="MMM YYYY"      
        highlightToday
        icon={false}
        
      />

  );
}
