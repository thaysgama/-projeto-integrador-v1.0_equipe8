import { Col, Row, Container } from "react-bootstrap"
import React, { useEffect, useState} from 'react';
import api from "../../services/api";
import ProductCard from "../ProductCard"
import "./style.scss"

const ProductSection = ({url}) =>{
    const [products, setProducts] = useState([]);
    
    useEffect(() => {
        api.get(`${url}`)
        .then((response) => {
            setProducts(response.data.content);
        })
    }, [url])

    return(
        <div className="products-section">
        <Container className="section-title">Recomendações</Container>
        <Container>
            <Row>
                {
                    products.map(item =>{
                        return(
                            <ProductCard {...item} key={item.id} />
                        )
                        
                    })
                }
            </Row>
        </Container>
        </div>
    )
}

export default ProductSection;