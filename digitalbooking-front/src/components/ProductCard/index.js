import { Card, Col, Button } from "react-bootstrap";
import {Link} from "react-router-dom";
import './style.scss';
import {useState} from "react";
import {Rating, StyledRating, styled} from "@mui/material"

const ProductCard = (props) =>{
    const { id, name, description, characteristics, images, city, category } = props;

    const [value, setValue] = useState(3);

    const [favorite, setFavorite] = useState(false);

    const handleClick = (event) =>{
        if(!favorite){
            event.target.style.color = 'red'
            setFavorite(true);
        } else{
            event.target.style.color = 'white'
            setFavorite(false);
        }
    }

    const StyledRating = styled(Rating)({
        '& .MuiRating-iconFilled': {
          color: '#F0572D',
        },
        '& .MuiRating-iconHover': {
          color: '#F0572D',
        },
      });
    
    return(
        <Col className="product-card" xs={12} xl={6}>
            <Card >
                <div id="container-image" >
                    <span className="material-icons heart" onClick={handleClick}>
                        favorite
                    </span>
                    <Card.Img variant="top" src={images[0].url} alt={name} />
                </div>
                <Card.Body>
                    <Card.Title>
                        <div className="sec-title">
                            <div>
                                <div className="type-rating">
                                    <h6>CASA</h6>
                                    <StyledRating
                                    name="simple-controlled"
                                    value={value}
                                    size="small"
                                    onChange={(event, newValue) => {
                                        setValue(newValue);
                                    }}
                                    />   
                                </div>
                                
                                <h5>{name}</h5>
                            </div>
                            <div className="rating-element">
                                <div className="rating">
                                    4
                                </div>
                                <div className="rating-text">
                                    Muito bom
                                </div>
                            </div>
                        </div>


                        <p className="location">
                            <span className="material-icons icharac">location_on</span>
                            {city.name} - {city.uf} - 
                            <span className="color-highlight"> MOSTRAR NO MAPA</span>
                        </p>
                        <div>
                            {characteristics.map( item =>{
                                return(
                                    <span className="material-icons icharac" key={item.id}>
                                      {item.icon}
                                    </span>
                                )
                                
                            })}
                        </div>
                        
                    </Card.Title>
                    <Card.Text>
                        
                        
                            <span className="card-description">{description}</span>
                            <span className="color-highlight">mais...</span> 
                     
                                
                        <Button type="submit" as={Link} to={`/product/${id}`}>Ver mais</Button>         
                    </Card.Text>
                </Card.Body>
            </Card>
        </Col>
    )
}

export default ProductCard;