import { Card, Col, Button } from "react-bootstrap";
import {Link} from "react-router-dom";
import './style.scss';
import {useEffect,useState} from "react";
import api from "../../services/api";

const ReservationCard = (props) =>{
    const { id, bookingTime, checkInDate, checkOutDate , clientId, productId } = props;

    function reformatDate(dateStr)
    {
      let dArr = dateStr.split("-");
      return dArr[2]+ "/" +dArr[1]+ "/" +dArr[0].substring(2); 
    }

    let checkInFormated = reformatDate(checkInDate)
    let checkOutFormated = reformatDate(checkOutDate)

    const [product, setProduct] = useState({});
    
    useEffect(() => {
        api.get(`/product/${productId}`)
        .then((response) => {
            setProduct(response.data);
        })
        
    }, [productId])
    
    return(
        <>
        {product.images && (<Col className="product-card" xs={12} xl={6}>
            <Card >
                <div id="container-image" >
                    <Card.Img variant="top" src={product.images[0].url} alt={product.name} />
                </div>
                <Card.Body>
                    <Card.Title>
                        <div className="sec-title">
                            <div>                                
                                <h5>{product.name}</h5>
                            </div>
                        </div>

                        <p className="location">
                            <span className="material-icons icharayarnc">location_on</span>
                            {product.city.name} - {product.city.uf} - 
                        </p>
                        <div>
                            <div className="text-reservation py-2">
                                Check In: {checkInFormated}
                            </div>
                            <div className="text-reservation py-2">
                                Check Out: {checkOutFormated}
                            </div>
                        </div>
                        
                    </Card.Title>
                    <Card.Text>
                                
                        <Button type="submit" as={Link} to={`/product/${id}`}>Ver mais</Button>         
                    </Card.Text>
                </Card.Body>
            </Card>
        </Col>) }
        </>
    )
}

export default ReservationCard;