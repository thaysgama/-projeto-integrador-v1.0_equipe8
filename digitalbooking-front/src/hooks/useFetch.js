import {useEffect, useState} from 'react'
import api from '../services/api'

export const useFetch = ({
    method="get",
    url,
    body=null,
    config=null
}) => {
    const [data, setData] = useState(null)
   const [loading, setLoading] = useState(false);
   const [error, setError] = useState(null);

   useEffect(() => {
    async function fetchData() {
      try{
        setLoading(true)
        const response = await api[method](url)
        setData(response.data)
        setLoading(false)
      }catch(error){
        setError(error.message)
      }finally{
          setLoading(false)
      }
     
    }
    fetchData()
  }, [])

  
  return {
      data,
      loading,
      error
  }

}