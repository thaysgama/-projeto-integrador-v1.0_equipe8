/* eslint-disable react/style-prop-object */
import './style.scss';
import { Navbar, Container, Nav, Offcanvas, Row, Col } from 'react-bootstrap';
import {Link, useNavigate} from "react-router-dom";
import facebook from '../../assets/icon-facebook.svg';
import linkedin from '../../assets/icon-linkedin.svg';
import twitter from '../../assets/icon-twitter.svg';
import instagram from '../../assets/icon-instagram.svg';
import { AuthContext, NO_AUTH_STATE } from '../../contexts/AuthProvider';
import { useContext } from 'react';


export function Logged() {
    const {authState, setAuthState} = useContext(AuthContext);
    const userInitials = authState.firstName[0].concat(authState.lastName[0]).toUpperCase();
    const navigate = useNavigate();

    const handleLogout = () =>{
        setAuthState(authState=>({
            ...authState,
            ...NO_AUTH_STATE
        }))
        
    }
    
    return (
        
        <section id="logged">
            <Navbar bg="light" expand="sm"  >
                <Container >
                    <Navbar.Toggle aria-controls="navbarScroll" />
                    {/* HEADER DO SANDWICH */}
                    <Navbar.Offcanvas
                        id="offcanvasNavbar"
                        aria-labelledby="offcanvasNavbarLabel"
                        placement="end"
                    >
                        <div className= " sandwich display:inline">
                            
                            <div className= "menu col-4">
                                <Offcanvas.Header closeButton></Offcanvas.Header>
                            </div>
                                <div className= "menu col-4 user-login">
                                    <div className='circulo'><p>{userInitials}</p></div>
                                    <div className='name-user'>Olá, {authState.firstName} {authState.lastName}</div>
                                </div>
                            

                        </div>

                        <Offcanvas.Body className='body-logged'>
                            <Nav>
                                <Nav.Link onClick={handleLogout} as={Link} to={"/"}>Deseja <span className='orange'>encerrar a sessão</span>?</Nav.Link>
                            </Nav>
                           
                        </Offcanvas.Body>
                        <hr/>
                        <div className='icons'>
                            <Navbar.Brand href="#"><img src={facebook}></img></Navbar.Brand>
                            <Navbar.Brand href="#"><img src={linkedin}></img></Navbar.Brand>
                            <Navbar.Brand href="#"><img src={twitter}></img></Navbar.Brand>
                            <Navbar.Brand href="#"><img src={instagram}></img></Navbar.Brand>
                        </div>
                    </Navbar.Offcanvas>

                            {/* HEADER DO DESKTOP */}
                    <Navbar expand="lg" className='header-desktop' >
                        <Container fluid>
                            <Navbar.Collapse id="navbarScroll">
                                <Nav
                                    className="me-auto my-2 my-lg-0"
                                    style={{ maxHeight: '100px' }}
                                    navbarScroll
                                >
                                    <div className='link-desktop'>
                                        {authState.role.name === 'ROLE_PROPRIETOR' ? <div className="admin" onClick={()=>navigate('/property')}>Administrar</div> : ""}
                                        {authState.role.name === 'ROLE_CLIENT' ? <div className="admin" onClick={()=>navigate('/reservations')}>Minhas Reservas</div> : ""}
                                        <div className='circulo-desktop'><p>{userInitials}</p></div>
                                        <div className='name-user-desktop'>Olá, <p>{authState.firstName} {authState.lastName}</p></div>
                                        <div className='logout' onClick={handleLogout}>X</div>
                                    </div>
                                    
                                   
                                </Nav>
                            </Navbar.Collapse>
                        </Container>
                    </Navbar>

                </Container>
            </Navbar>

    </section>

    )
}
export default Logged;