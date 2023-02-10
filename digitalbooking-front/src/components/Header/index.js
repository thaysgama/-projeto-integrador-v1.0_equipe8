import './style.scss';
import { Navbar, Container } from 'react-bootstrap';
import logo1 from '../../assets/logo1.svg';
import logo2 from '../../assets/logo2.svg';
import NotLogged from '../NotLogged';
import Logged from '../Logged';
import { AuthContext } from '../../contexts/AuthProvider';
import { useContext } from 'react';



export function Header() {
    const {authState} = useContext(AuthContext);

    return (
        
        <section id="header_container">
            <Navbar bg="light" expand="sm"  >
                <Container >
                    <Navbar.Brand href="/" >
                        <img src={logo1} alt="logo" className='logo-mobile'/>
                        <img src={logo2} alt="logo2" className='logo-desktop'/>

                    </Navbar.Brand>
                    {authState.token !== "" ? <Logged/> : <NotLogged/>}
                

                </Container>
            </Navbar>

    </section>
    )
}

export default Header;