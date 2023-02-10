import './style.scss';

import { useState, useEffect, useContext } from "react";
import Calendar from "react-calendar";
import { ReservationContext } from "../../contexts/ReservationProvider";
import { useParams } from 'react-router-dom';

function CustomCalendar() {
  const { reservationState, setReservationState } = useContext(ReservationContext);
  const {id} = useParams();
  const intitalValueCheckIn = reservationState.checkInDate && reservationState.productId ===id  ? reservationState.checkInDate :  new Date();
  const intitalValueCheckOut = reservationState.checkOutDate && reservationState.productId ===id  ? reservationState.checkOutDate :  new Date();

    const [date, setDate] = useState([intitalValueCheckIn, intitalValueCheckOut]);
    

    useEffect(() => {
      setReservationState((reservationState) => ({
        ...reservationState,
        checkInDate: date[0],
        checkOutDate: date[1]
      }))
    }, [date, setReservationState])
    
    
    return (
        <>
          <div className="app">
           
            <div className="calendar-container" id="double-view">
                <Calendar 
                    onChange={setDate} 
                    value={date}
                    selectRange={true}
                    showDoubleView={true}
                    minDate={new Date()}
                    defaultValue={date}
                    formatShortWeekday = {(locale, date) => ['D', 'S', 'T', 'Q', 'Q', 'S', 'S'][date.getDay()]}
                    
 
                    
                />
            </div>

            <div className="calendar-container" id="one-view">
                <Calendar 
                    onChange={setDate} 
                    value={date}
                    selectRange={true}                    
                    minDate={new Date()}
                    defaultValue={date}
                    formatShortWeekday = {(locale, date) => ['D', 'S', 'T', 'Q', 'Q', 'S', 'S'][date.getDay()]}                    
                  />
                    
 
                    
                
            </div>



            {/* {date.length > 0 ? (
            <p className='text-center'>
              <span className='bold'>Start:</span>{' '}
              {date[0].toDateString()}
              &nbsp;|&nbsp;
              <span className='bold'>End:</span> {date[1].toDateString()}
            </p>
          ) : (
            <p className='text-center'>
              <span className='bold'>Default selected date:</span>{' '}
              {date.toDateString()}
            </p>
          )} */}
    </div>
    </>       
    )
}

export default CustomCalendar;