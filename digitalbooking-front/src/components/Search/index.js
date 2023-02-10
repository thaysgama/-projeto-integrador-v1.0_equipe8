import './style.scss';

import { Container, Row, Col, Form, Button } from 'react-bootstrap';
import { useEffect, useState } from 'react';
import { useNavigate } from "react-router-dom";
import api from '../../services/api';
import DatePicker from "react-multi-date-picker";
import Toolbar from "react-multi-date-picker/plugins/toolbar";

export default function Search() {
    const title = "Buscar casas, apartamentos e muito mais...";
    const [cities, setCities] = useState([]);
    const [date, setDate] = useState([]);
    const [text, setText] = useState('');
    const [suggestions, setSuggestions] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const loadCities = async()=>{
            const response = await api.get('/city')
            setCities(response.data.content)
        }
        loadCities();
     }, [])

     const onSuggestHandler = (text)=>{
         setText(text);
         setSuggestions([])
     }
     const onChangeHandler =(text)=>{
         let matches=[]
         if(text.length>0) {
             matches = cities.filter(city=>{
                 const regex = new RegExp(`${text}`,"gi");
                 return city.name.match(regex)
             })
         }
         setSuggestions(matches)
         setText(text)
     }


  const weekDays = ["D", "S", "T", "Q", "Q", "S", "S"]
  const months = ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"]

  const handleSubmit = (event) => {
      let textId = getTextId();
      let checkIn;
      let checkOut;
      if(date.length===2){
        checkIn = new Date(date[0].year, date[0].monthIndex, date[0].day) 
        checkIn = checkIn.toISOString().split("T")[0]
        checkOut = new Date(date[1].year, date[1].monthIndex, date[1].day) 
        checkOut = checkOut.toISOString().split("T")[0]
      }
      event.preventDefault();
      if(textId !== "" && date.length===2){
        navigate(`/city=${textId}&date=${checkIn}&${checkOut}`)
      } else if (textId !== ""){
        navigate(`/city=${textId}`)
      } else if (date.length===2){
        navigate(`/date=${checkIn}&${checkOut}`)
      } else{
        event.preventDefault();
      }
    
  }
  
  let getTextId = () =>{
    let city = cities.filter(item => item.name === text)
    return city.length>0 ? city[0].id : "";
  }
  
  

//   let textId

//     if (text === "Fortaleza") {
//         textId = "1"
//     }
//     if (text === "Maceió") {
//         textId = "2"
//     }
//     if (text === "Florianópolis") {
//         textId = "3"
//     }
//     if (text === "Natal") {
//         textId = "4"
//     }
//     if (text === "São Paulo") {
//         textId = "5"
//     }
//     if (text === "Rio de Janeiro") {
//         textId = "6"
//     }
//     if (text === "Belo Horizonte") {
//         textId = "7"
//     }
//     if (text === "Curitiba") {
//         textId = "8"
//     }
//     if (text === "Salavador") {
//         textId = "9"
//     }
//     if (text === "Olinda") {
//         textId = "10"
//     }
//     if (text === "Petrópolis") {
//         textId = "11"
//     }
//     if (text === "Ouro Preto") {
//         textId = "12"
//     }
//     if (text === "Alto Paraíso de Goiás") {
//         textId = "13"
//     }
//     if (text === "Lençois") {
//         textId = "14"
//     }
//     if (text === "Bonito") {
//         textId = "15"
//     }
//     if (text === "Mateiros") {
//         textId = "16"
//     }
  

    return (
      <>
        <Container
          fluid
          id="search_container"
          className="d-flex justify-content-center align-items-center"
        >
          <Row>
            <h1 className="text-center">{title}</h1>
            <Col>
              <Form
                className="form d-flex align-items-center justify-content-center"
                onSubmit={handleSubmit}
              >
                 <div className="container-input1">
                            <Form.Control className="field me-2" type="text" placeholder="&#xf3c5; Onde vamos?" onChange={e=>onChangeHandler(e.target.value)} value={text}></Form.Control> 
                            
                                <div id="suggestionsBox">
                                    {suggestions && suggestions.map((suggestion, i)=>

                                        <div key={i}  className="suggestion" onClick={()=>onSuggestHandler(suggestion.name)}>
                                            {suggestion.name}
                                        </div>
                                    )}
                                </div>   
                            </div>

                      <DatePicker
                        id="input-calendar"
                        value={date} 
                        onChange={setDate}
                        portal
                        range
                        placeholder="&#xf133; Check-in - Check-out"
                        numberOfMonths={2}
                        weekDays={weekDays}
                        months={months}
                        format="DD/MM/YYYY"
                        plugins={[
                          <Toolbar
                            position="bottom"
                            sort={["deselect"]}
                            names={{ deselect: "Limpar" }}
                          />,
                        ]}
                        mapDays={({ date }) => {
                          let isBefore = new Date();

                          if (isBefore > date)
                            return {
                              disabled: true,
                              style: { color: "#ccc" },
                              onClick: () =>
                                alert("Escolha uma data no futuro."),
                            };
                        }}
                      />

                <Button className="button" variant="primary" type="submit">
                  Buscar
                </Button>
              </Form>
            </Col>
          </Row>
        </Container>
      </>
    );

}