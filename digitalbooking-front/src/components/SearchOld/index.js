import "./style.scss";

import Calendar from "../Calendar";
import React, { useEffect, useState } from 'react';
import api from "../../services/api";
import { useNavigate } from "react-router-dom";
import SimpleAutocomplete from "../SimpleAutocomplete";
import { Button } from 'react-bootstrap';
import { Formik, Form, Field } from 'formik'




function SearchOld() {
  const title = "Buscar casas, apartamentos e muito mais...";
  
  const navigate = useNavigate();
  const handleCalendar = () => { return <Calendar ></Calendar>;}
  const [destiny, setDestiny] = useState([]);


  //const [checkIn, setcheckIn] = useState()
  //const [checkOut, setcheckOut] = useState()

  // const handleDates = (startDate, endDate)=>{
  //   if (startDate != null){
  //     setcheckIn(startDate)
  //   }
  //   if(endDate !=null){
  //     setcheckOut(endDate)
  //   }
  //   console.log(checkIn)
  // }

  const handleSubmit = (values) => {
    navigate(`/city/${values.city}`)
  }

  useEffect(() => {
    api.get('/city')
      .then((response) => {

        setDestiny(response.data.content);

      })
  }, [])


  return (
    <>
      <section id="search_container" className="col-md-3 col-sm-8">

        <h1>{title}</h1>       
       
        <Formik 
          initialValues={{city: ''}}
          className="" 
          id="field_container"
          onSubmit={handleSubmit}>        
        {({ 
            values,
            handleChange, 
            handleSubmit, 
          }) => (
          <Form id="form-group" onSubmit={handleSubmit}>

            <div className="field-box search">
            <i className="fa-solid fa-location-dot"></i>            
             <Field type="text" className="field"  onChange={handleChange}  name="city" value={values.city} component={SimpleAutocomplete}/>

        </div>

            <div className="field-box calendar">
            <i className="fa-solid fa-calendar-day"></i>          
            <Field type="text" className="field" component={handleCalendar}/>       
            </div>


            <Button  id="button" className="btn btn-primary" type="submit">Buscar</Button>
          </Form>)}
        </Formik> 
      </section>
    </>
  );
}

export default SearchOld;
