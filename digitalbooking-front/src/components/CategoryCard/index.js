import { Card, Col } from "react-bootstrap";
import './style.scss';
import {Link, useNavigate, } from "react-router-dom";

const CategoryCard = (props) =>{
    const { id, title, description, urlImage } = props;
    
    const navigate = useNavigate();

    const handleClick = () =>{
        navigate(`/category=${id}`)
    }
    
    return(
        <Col className="category-card" xs={12} md={6} lg={3}>
            <Card onClick={handleClick}>
                <Card.Img variant="top" src={urlImage} alt={title}/>
                <Card.Body>
                    <Card.Title>{title}</Card.Title>
                    <Card.Text>
                        158 unidades
                    </Card.Text>
                </Card.Body>
            </Card>
        </Col>
    )
}

export default CategoryCard;