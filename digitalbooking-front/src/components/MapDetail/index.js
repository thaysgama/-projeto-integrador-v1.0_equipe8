import './style.scss';
import { GoogleMap, useJsApiLoader, Marker } from '@react-google-maps/api';





export function MapDetail() {
    const { isLoaded } = useJsApiLoader({
        id: 'google-map-script',
        googleMapsApiKey: "AIzaSyAJ57YK0OPk9UCw5UK6IvBfGw1-6psoXMs"
      });

    const position = {
        lat: -27.590824,
        lng: -48.551262
    } ;


    return (
        
      

        <section id="map_detail">
            <h2> Onde você vai estar?</h2>
            <hr/>
            <div className='details-destination'>
            <p>Buenos Aires, Argentina</p>

            {
                isLoaded ? (
                    <GoogleMap className="map-container"
                      mapContainerStyle={{width: '100%', height:'70%', borderRadius: '5px'}}
                      center={position}
                      zoom={15}
                
                    >
                      <Marker position={position} options={{
                          label: {
                              text: "Posição Teste",
                              className: "map-marker"
                          }
                      }}/>
                    </GoogleMap>
                ) : <></>
              }

              
  </div>
            
        </section>



    )
}


export default MapDetail;