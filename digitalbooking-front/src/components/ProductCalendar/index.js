import './style.scss';
// import '@natscale/react-calendar/dist/main.cs

import CustomCalendar from '../CustomCalendar';
import { AuthContext, NO_AUTH_STATE } from "../../contexts/AuthProvider";
import React, { useContext,useState, useCallback } from 'react';
import {useNavigate} from 'react-router-dom';
import Swal from "sweetalert2";
// import { Calendar } from '@natscale/react-calendar';

function ProductCalendar({product}) {
  const { authState, setAuthState } = useContext(AuthContext);
  const [value, setValue] = useState(new Date());
  const navigate = useNavigate();
  const onChange = useCallback(
    (value) => {
      setValue(value);
    },
    [setValue],
  );

  const handleSubmit = () =>{
    if (authState.token === ""|| authState.role.name !== "ROLE_CLIENT") {
      if(authState.role.name !== "ROLE_CLIENT"){
        setAuthState(authState=>({
          ...authState,
          ...NO_AUTH_STATE
      }))
      }
      Swal.fire({
        title: "Erro de Autenticação",
        icon: "error",
        text: "Você precisa estar logado como cliente para finalizar uma reserva, por favor faça o login.",
      })
      navigate("/start");
    } else {
      navigate(`/product/${product.id}/reservation`)
    }
  }

//   const isDisabled = useCallback((date) => {
//     // disable wednesdays and any date that is divisible by 5
//     if (date.getDay() === 3 || date.getDate() % 5 === 0) {
//       return true;
//     }
//   }, []);

  return (
    <>
    <section id="calendar-container">

      <div id="calendar-area">
          <p>Datas disponíveis</p>
          <CustomCalendar />
      </div>
      
      <div id="reservation-container">
        <div id="reservation-area">
        <p>Adicione as datas da sua viagem para obter preços exatos</p>
        <input type="submit" className="btn btn-primary botaoReserva" value='Iniciar a reserva' onClick={handleSubmit}/>
        </div>
      </div>
    </section>  
</>

  );
}


export default ProductCalendar;