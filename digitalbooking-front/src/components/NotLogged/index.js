/* eslint-disable react/style-prop-object */
import './style.scss';
import { Navbar, Container, Nav, Offcanvas, Row, Col, CloseButton } from 'react-bootstrap';
import { useState } from 'react';
//import { Picture } from 'react-responsive-picture';
import {Link, useLocation} from "react-router-dom";
import logo1 from '../../assets/logo1.svg';
import logo2 from '../../assets/logo2.svg';
import facebook from '../../assets/icon-facebook.svg';
import linkedin from '../../assets/icon-linkedin.svg';
import twitter from '../../assets/icon-twitter.svg';
import instagram from '../../assets/icon-instagram.svg';


export function NotLogged() {
    const location = useLocation();

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);



    return (
        
        <section id="not-logged">
            <Navbar bg="light" expand="sm"  >
                <Container >
                    <Navbar.Toggle aria-controls="navbarScroll" onClick={handleShow}/>
                    <Navbar.Offcanvas
                        id="offcanvasNavbar"
                        aria-labelledby="offcanvasNavbarLabel"
                        placement="end"
                        show={show} onHide={handleClose}
                    >
                        <div className= " sandwich display:inline">
                            
                                <div className= "menu col-4">
                                    <Offcanvas.Header closeButton></Offcanvas.Header>
                                </div>
                                <div className= "col-4 float:right">
                                    <Offcanvas.Title id="offcanvasNavbarLabel">MENU</Offcanvas.Title>
                                </div>
                           

                        </div>

                        <Offcanvas.Body >
                            <Nav>
                                <Nav.Link as={Link} to={"/create"} onClick={handleClose}>Criar conta</Nav.Link>
                                <hr />
                                <Nav.Link as={Link} to={"/start"} onClick={handleClose}>Fazer login</Nav.Link>
                            </Nav>
                        </Offcanvas.Body>



                        <div className='icons'>
                            
                            <Navbar.Brand href="#"><img src={facebook}></img></Navbar.Brand>
                            <Navbar.Brand href="#"><img src={linkedin}></img></Navbar.Brand>
                            <Navbar.Brand href="#"><img src={twitter}></img></Navbar.Brand>
                            <Navbar.Brand href="#"><img src={instagram}></img></Navbar.Brand>
                        </div>
                    </Navbar.Offcanvas>

                    {/* HEADER DESKTOP */}

                    <Navbar expand="lg" className='header-desktop' >
                        <Container fluid>
                            <Navbar.Collapse id="navbarScroll">
                                <Nav
                                    className="me-auto my-2 my-lg-0"
                                    style={{ maxHeight: '100px' }}
                                    navbarScroll
                                >
                                    <div className='link-desktop'>
                                        {location.pathname === "/create" ? null : <Nav.Link as={Link} to={"/create"} >Criar Conta</Nav.Link>}
                                        {location.pathname === "/start" ? null : <Nav.Link as={Link} to={"/start"}>Iniciar Sessão</Nav.Link>}
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
export default NotLogged;