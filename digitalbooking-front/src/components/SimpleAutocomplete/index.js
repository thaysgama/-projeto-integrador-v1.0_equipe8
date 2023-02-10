import "./style.scss";
import api from '../../services/api';
import { useState, useEffect} from "react";



export default function SimpleAutocomplete() {
    const [city, setCity] = useState([]);
    const [text, setText] = useState("");
    const [suggestions ,setSuggestions] = useState([]);
    const placeholder="Onde vamos?"

    useEffect(() => {
       const loadCities = async()=>{
           const response = await api.get('/city')
           setCity(response.data.content)
       }
       loadCities();
    }, [])
    const onSuggestHandler = (text)=>{
        setText(text);
        setSuggestions([])
    }
    const onChangeHandler =(text)=>{
        let matches=[]
        if(text.length>0) {
            matches = city.filter(city=>{
                const regex = new RegExp(`${text}`,"gi");
                return city.name.match(regex)
            })
        }
        setSuggestions(matches)
        setText(text)
    }


    return (
        <>
            <input placeholder={placeholder} id="city" className="field" type="text" onChange={e=>onChangeHandler(e.target.value)}
                value={text} 
                onBlur={()=>{setTimeout(()=>{setSuggestions([])}, 100);}}
            />
            <div id="suggestionsBox">
                {suggestions && suggestions.map((suggestion, i)=>

                    <div key={i}  className="suggestion" onClick={()=>onSuggestHandler(suggestion.name)}>
                        {suggestion.name}</div>
                )}
            </div>
        </>



    )
}