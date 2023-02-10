import './style.scss' ;
import { Navbar, Container, Nav, Offcanvas, Row, Col } from 'react-bootstrap';
import facebook from '../../assets/icon-facebook-desktop.svg';
import linkedin from '../../assets/icon-linkedin-desktop.svg';
import twitter from '../../assets/icon-twitter-desktop.svg';
import instagram from '../../assets/icon-instagram-desktop.svg';




export function Footer() {
    const generateDateString = () => {
        const currentYear = `${new Date().getFullYear()}`;
        return currentYear;
    }
    <p>&copy; {generateDateString()} Digital Booking</p>


    return (

        <section id="footer_container">
         
                <div className='copyright'>
                    <p>&copy;{generateDateString()} Digital Booking</p>
                </div>
                <div className='icons'>
                            <img src={facebook}/>
                            <img src={linkedin}/>
                            <img src={twitter}/>
                            <img src={instagram}/>
                </div>
            
        </section>
     
    )
}
export default Footer;