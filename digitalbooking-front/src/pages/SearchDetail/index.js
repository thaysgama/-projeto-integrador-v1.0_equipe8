import React from "react";
import CategorySection from "../../components/CategorySection";
import Search from "../../components/Search";
import { useFetch } from '../../hooks/useFetch';
import { useParams } from 'react-router-dom';
import ProductSection from "../../components/ProductSection";


function SearchDetail() {
    const {search} = useParams()

    const url = "product/"+search.replace(/=|&/g, '/');


    return (
      <>
        <Search/>
        <CategorySection/>
        <ProductSection url={url}/>
      </>
     )
  }
  export default SearchDetail;