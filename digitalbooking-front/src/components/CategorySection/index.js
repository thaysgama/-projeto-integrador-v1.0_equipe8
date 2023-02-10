import {  Row, Container } from "react-bootstrap"
import React, { useEffect, useState} from 'react';
import CategoryCard from "../CategoryCard"
import { useNavigate } from 'react-router-dom';

import CategoryFilter from "../../contexts/CategoryFilter";

import "./style.scss"
import api from "../../services/api";

const CategorySection = () =>{

    const [categories, setCategories] = useState([]);
    
    useEffect(() => {
        api.get('/category')
        .then((response) => {
            setCategories(response.data.content);
            
        })
    }, [])

    return(
        <>
        <Container className="section-title">
            Buscar por destinos
        </Container>
            
        <Container>
            <Row>
            {
                    categories.map(item =>{
                        return(
                            <CategoryCard {...item} key={item.id}/>
                        )
                        
                    })
                }

            </Row>
        </Container>
        </>
    )
}

export default CategorySection;