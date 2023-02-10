import {useState} from "react";
import CategorySection from "../../components/CategorySection";
import ProductSection from "../../components/ProductSection";
import Search from "../../components/Search";
import CategoryFilter from "../../contexts/CategoryFilter";


function Home() {
  // const data = useAxios('/products')
  // useEffect(() => {
  //   if (data.length > 0){
  //     setShow(true)
  //   }
  // }, [show, data.length])
  const [categoryFilter, setCategoryFilter]= useState("");
  const url = "product";

    return (
  
      // <>
      // {!show ? 
      // <Loader/> : 
      <>
        {/* <Helmet>
        </Helmet> */}
        <Search/>
        <CategorySection/>
        <CategoryFilter.Provider value={{categoryFilter, setCategoryFilter}}>
          <ProductSection url={url}/>
        </CategoryFilter.Provider>
      </>
  //   }
  // </> 
    )
  }
  export default Home;