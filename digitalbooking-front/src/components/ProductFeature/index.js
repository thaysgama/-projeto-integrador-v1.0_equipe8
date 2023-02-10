import './style.scss' ;
import {GiKitchenTap} from 'react-icons/gi'
import {AiFillCar} from 'react-icons/ai'
import {FaTv} from 'react-icons/fa'
import {MdPool, MdPets} from 'react-icons/md'
import {BsSnow, BsWifi2} from 'react-icons/bs'




export function ProductFeature() {
    
    return (

        <section id="product_feature">
         <h2> O que esse lugar oferece?</h2>
            <hr/>
        <div className='icons-features'>
            <div>
                <ul>
                    <li><GiKitchenTap/> Cozinha </li>
                    <li><AiFillCar/> Estacionamento gratuito</li>
                    <li><FaTv/> Televisor</li>
                    <li><MdPool/> Piscina</li>
                    <li><BsSnow/> Ar condicionado</li>
                    <li><BsWifi2/> Wifi</li>
                    <li><MdPets/> Aceita Pets</li>

                </ul>
            </div>
        </div>         
        </section>
     
    )
}
export default ProductFeature;
