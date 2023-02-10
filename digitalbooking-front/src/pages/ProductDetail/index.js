import React, {useEffect, useState,  useContext} from 'react'
import MapDetail from "../../components/MapDetail";
import ProductRules from "../../components/ProductRules";
import HeaderProduct from "../../components/HeaderProduct";
import ProductPhotos from "../../components/ProductPhotos";
import ProductDescription from "../../components/ProductDescription";
import ProductCalendar from "../../components/ProductCalendar";
import ProductFeature from "../../components/ProductFeature";
import { ReservationContext } from "../../contexts/ReservationProvider";
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { setDefaultLocale } from 'react-datepicker';
import { useFetch } from '../../hooks/useFetch';



function ProductDetail() {
  const {id} = useParams()
  const { setReservationState } = useContext(ReservationContext);
  useEffect(() => {
    setReservationState((reservationState) => ({
      ...reservationState,
      productId: id
    }))
  }, [id, setReservationState])
  
  const {
    data:product,
    loading,
    error
  } = useFetch({url:`/product/${id}`})



    if(loading){
      return <h1>Loading...</h1>
    }
    if(!!error){
      return <h1>{error}</h1>
    }


    return (
  
      <>
      {product && (
        <div> 
          <HeaderProduct product={product}/>
          <ProductPhotos product={product}/>
          <ProductDescription product={product}/>
          <ProductFeature/>
          <ProductCalendar product={product}/>
          <MapDetail product={product}/>
          <ProductRules product={product}/>
        </div>)
      }
      </>
  
  
    )
  }
  export default ProductDetail;