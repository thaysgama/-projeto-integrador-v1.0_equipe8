import { Row, Container } from "react-bootstrap"
import React, { useEffect, useState, useContext} from 'react';
import api from "../../services/api";
import ReservationCard from "../ReservationCard"
import "./style.scss"
import { AuthContext } from "../../contexts/AuthProvider";

const ReservationSection = ({url}) =>{
    const [reservations, setReservations] = useState([]);
    const { authState} = useContext(AuthContext);
    
    useEffect(() => {
        api.get(`${url}`, {
            headers: {
              'Authorization': `Bearer ${authState.token}`
            }
        })
        .then((response) => {
            setReservations(response.data.content);
        })
    }, [url, authState.token])

    return(
        <div className="products-section">
        <Container className="section-title">Minhas reservas</Container>
        <Container>
            <Row>
                {
                    reservations.map(item =>{
                        return(
                            <ReservationCard {...item} key={item.id} />
                        )
                        
                    })
                }
            </Row>
        </Container>
        </div>
    )
}

export default ReservationSection;