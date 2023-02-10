import React, { useContext, useState, useCallback, useEffect } from "react";
import ProductRules from "../../components/ProductRules";
import { useNavigate, useParams } from "react-router-dom";
import * as Yup from "yup";
import { Modal, Button, Form, Row, Col, Container } from "react-bootstrap";
import { Formik } from "formik";
import { AuthContext } from "../../contexts/AuthProvider";
import { ReservationContext } from "../../contexts/ReservationProvider";
import CustomCalendar from "../../components/CustomCalendar";
import ModalReservation from "../../components/ModalReservation";
import "./style.scss";
import Swal from "sweetalert2";
import api from "../../services/api";

function ReservationDetail() {
  const { id } = useParams();

  const { authState } = useContext(AuthContext);
  const { reservationState} =
    useContext(ReservationContext);
  const [product, setProduct] = useState();

  const navigate = useNavigate();

  const loadData = useCallback(async ({ id }) => {
    try {
      await api.get(`/product/${id}`).then((response) => {
        setProduct(response.data);
      });
    } catch (error) {
      Swal.fire({
        title: "Página não encontrada",
        icon: "error",
        buttonsStyling: false,
      });
    }
  }, []);

  useEffect(() => {
    loadData({ id: id });
  }, [id, loadData]);

  const stringDateFormatAPI = (date) => {
    return date && date instanceof Date ? date.toISOString().split("T")[0] : "";
  };

  const stringDateFormatView = (date) => {
    return date && date instanceof Date
      ? date.getDate() + "/" + (date.getMonth() + 1) + "/" + date.getFullYear()
      : "";
  };

  const [lgShow, setLgShow] = useState(false);
  const handleClose = () => setLgShow(false);
  const [dateCheckIn, setDateCheckIn] = useState(
    stringDateFormatView(new Date())
  );
  const [dateCheckOut, setDateCheckOut] = useState("");

  useEffect(() => {
    setDateCheckIn(stringDateFormatView(reservationState.checkInDate));
    setDateCheckOut(stringDateFormatView(reservationState.checkOutDate));
  }, [reservationState.checkInDate, reservationState.checkOutDate]);

  const initialValues = {
    firstNameReservation: authState.firstName,
    lastNameReservation: authState.lastName,
    emailReservation: authState.email,
    cityReservation: "",
  };

  const validationSchema = Yup.object().shape({
    // firstNameReservation: Yup.string().required('Informe seu nome'),
    // lastNameReservation: Yup.string().required('Informe seu sobrenome'),
    // emailReservation: Yup.string().email('Formato de e-mail inválido').required('Você precisa informar um e-mail'),
    // cityReservation: Yup.string().required('Preencha campo cidade'),
  });

  const handleSubmit = async (values, { setSubmitting }) => {
    if (authState.getToken === "" || authState.role.name !== "ROLE_CLIENT") {
      navigate("/start");
      Swal.fire({
        title: "Erro de Autenticação",
        icon: "error",
        text: "Você precisa estar logado como cliente para finalizar uma reserva, por favor faça o login.",
      });
    } else {
      try {
        const dateCheckIn = stringDateFormatAPI(reservationState.checkInDate);
        const dateCheckOut = stringDateFormatAPI(reservationState.checkOutDate);
        const reservationData = {
          checkInDate: dateCheckIn,
          checkOutDate: dateCheckOut,
          clientId: authState.id,
          productId: product.id,
        };
        await api
          .post(
            `/booking`,
            { ...reservationData },
            {
              headers: {
                Authorization: `Bearer ${authState.token}`,
              },
            }
          )
          .then((response) => {
            setLgShow(true);
          });
      } catch (error) {
        console.log(error.response);
        Swal.fire({
          title: error.response.status,
          icon: "error",
          text: error.response.data,
        });
      }
      setTimeout(() => {
        setSubmitting(false);
        navigate("/");
      }, 3000);
    }
  };

  return (
    <>
      {product && (
        <Container className="reservationDetail">
          <h3 className="h3Dados"> Complete seus dados</h3>
          <Formik
            initialValues={initialValues}
            validationSchema={validationSchema}
            onSubmit={handleSubmit}
          >
            {({
              touched,
              errors,
              isSubmitting,
              values,
              handleChange,
              handleSubmit,
            }) => (
              <div id="reservationForm">
                <Form
                  className="formReservation"
                  noValidate
                  onSubmit={handleSubmit}
                >
                  <Row
                    lg={2}
                    md={1}
                    sm={1}
                    className="reservation-informations"
                  >
                    <Col lg={7} md={12} sm={12} className="coluna1">
                      <Row lg={2} md={1} sm={1} className="reservationForm">
                        <Form.Group
                          as={Col}
                          md="12"
                          className="mb-2 mt-2"
                          controlId="validationFormik10"
                        >
                          <Form.Label>Nome:</Form.Label>
                          <Form.Control
                            type="text"
                            placeholder="Insira seu nome"
                            name="firstNameReservation"
                            value={values.firstNameReservation}
                            onChange={handleChange}
                            isInvalid={
                              !touched.firstNameReservation &&
                              !!errors.firstNameReservation
                            }
                          />
                          <Form.Control.Feedback
                            type="invalid"
                            className="reservationFormError"
                          >
                            {errors.firstNameReservation}
                          </Form.Control.Feedback>
                        </Form.Group>

                        <Form.Group
                          as={Col}
                          md="12"
                          className="mb-2 mt-2"
                          controlId="validationFormik11"
                        >
                          <Form.Label>Sobrenome</Form.Label>
                          <Form.Control
                            type="text"
                            placeholder="Insira seu sobrenome"
                            name="lastNameReservation"
                            value={values.lastNameReservation}
                            onChange={handleChange}
                            isInvalid={
                              !touched.lastNameReservation &&
                              !!errors.lastNameReservation
                            }
                          />
                          <Form.Control.Feedback
                            type="invalid"
                            className="reservationFormError"
                          >
                            {errors.lastNameReservation}
                          </Form.Control.Feedback>
                        </Form.Group>

                        <Form.Group
                          as={Col}
                          md="12"
                          className="mb-2"
                          controlId="validationFormik12"
                        >
                          <Form.Label>E-mail</Form.Label>
                          <Form.Control
                            type="emailReservation"
                            placeholder="Insira seu e-mail"
                            aria-describedby="inputGroupPrepend"
                            name="emailReservation"
                            value={values.emailReservation}
                            onChange={handleChange}
                            isInvalid={
                              !touched.emailReservation &&
                              !!errors.emailReservation
                            }
                          />
                          <Form.Control.Feedback
                            type="invalid"
                            className="reservationFormError"
                          >
                            {errors.emailReservation}
                          </Form.Control.Feedback>
                        </Form.Group>

                        <Form.Group
                          as={Col}
                          md="12"
                          className="mb-2"
                          controlId="validationFormik13"
                        >
                          <Form.Label>Cidade</Form.Label>

                          <Form.Control
                            type="text"
                            className="cityReservation"
                            placeholder="Digite sua cidade"
                            aria-describedby="inputGroupPrepend"
                            name="cityReservation"
                            value={values.cityReservation}
                            onChange={handleChange}
                            isInvalid={
                              !touched.cityReservation &&
                              !!errors.cityReservation
                            }
                          />

                          <Form.Control.Feedback
                            type="invalid"
                            className="reservationFormError"
                          >
                            {errors.cityReservation}
                          </Form.Control.Feedback>
                        </Form.Group>
                      </Row>

                      <Row>
                        <Col>
                          <div id="calendar-area">
                            <h3 div className="h3ReservationForm">
                              Selecione sua data de reserva
                            </h3>
                            <CustomCalendar />
                          </div>
                        </Col>
                      </Row>

                      <Row>
                        <Col>
                          <h3 div className="h3ReservationForm">
                            {" "}
                            Seu horário de chegada
                          </h3>
                          <div className="time-informations">
                            <h6 div className="checkInOut1">
                              Seu quarto estará pronto para check-in entre 10h00
                              e 23h00
                            </h6>
                            <label className="labelChegada" htmlFor="appt">
                              Indique a sua hora prevista de chegada
                            </label>
                            <Form.Select aria-label="Escolha seu horário">
                              <option className="default-option">
                                Selecione sua hora de chegada
                              </option>
                              <option value="1-AM">01:00 AM</option>
                              <option value="2-AM">02:00 AM</option>
                              <option value="3-AM">03:00 AM</option>
                              <option value="4-AM">04:00 AM</option>
                              <option value="5-AM">05:00 AM</option>
                              <option value="6-AM">06:00 AM</option>
                              <option value="7-AM">07:00 AM</option>
                              <option value="8-AM">08:00 AM</option>
                              <option value="9-AM">09:00 AM</option>
                              <option value="10-AM">10:00 AM</option>
                              <option value="11-AM">11:00 AM</option>
                              <option value="12-AM">12:00 AM</option>
                              <option value="1-PM">01:00 PM</option>
                              <option value="2-PM">02:00 PM</option>
                              <option value="3-PM">03:00 PM</option>
                              <option value="4-PM">04:00 PM</option>
                              <option value="5-PM">05:00 PM</option>
                              <option value="6-PM">06:00 PM</option>
                              <option value="7-PM">07:00 PM</option>
                              <option value="8-PM">08:00 PM</option>
                              <option value="9-PM">09:00 PM</option>
                              <option value="10-PM">10:00 PM</option>
                              <option value="11-PM">11:00 PM</option>
                              <option value="12-PM">12:00 PM</option>
                            </Form.Select>
                          </div>
                        </Col>
                      </Row>
                    </Col>

                    <Col lg={5} md={12} sm={12} className="coluna2">
                      <Row>
                        <Col className="photoReservation">
                          <div className="reservationPhoto">
                            <h3 className="title">Detalhe da reserva</h3>
                            <img
                              src={product.images[0].url}
                              className="photo-reservation"
                              alt={product.images[0].title}
                            ></img>
                            <div className="informations">
                              <div className="informations-product">
                                <p className="category">CASA</p>
                                <h3 div className="hermitage">{product.name}</h3>
                                <div className="classification">
                                  <i className="fa-solid fa-location-dot"></i>
                                  <p>{product.city.name}</p>
                                </div>
                              </div>
                              <hr />
                              <div className="checkin">
                                <h6 className="checkInOut">Check in</h6>
                                <h6 className="checkInOut">{dateCheckIn}</h6>
                              </div>
                              <hr />
                              <div className="checkout">
                                <h6 className="checkInOut">Check out</h6>
                                <h6 className="checkInOut">{dateCheckOut}</h6>
                              </div>
                              <hr />
                              <Button
                                variant="link"
                                type="submit"
                                className="buttonModalReservation"
                              >
                                Confirmar reserva
                              </Button>
                              <Modal
                                className="modalReservation"
                                // size="lg"
                                show={lgShow}
                                onHide={handleClose}
                                aria-labelledby="contained-modal-title-vcenter"
                                centered
                              >
                                <Modal.Body className="modalReservation">
                                  <svg
                                    className="svgModalReservation"
                                    width="78"
                                    height="74"
                                    viewBox="0 0 78 74"
                                    fill="none"
                                    xmlns="http://www.w3.org/2000/svg"
                                  >
                                    <path
                                      d="M78 36.9823L69.3491 27.1534L70.5545 14.1424L57.7555 11.2432L51.0545 0L39 5.16197L26.9455 0L20.2445 11.2432L7.44545 14.107L8.65091 27.118L0 36.9823L8.65091 46.8113L7.44545 59.8576L20.2445 62.7568L26.9455 74L39 68.8027L51.0545 73.9646L57.7555 62.7215L70.5545 59.8223L69.3491 46.8113L78 36.9823ZM31.9091 54.6603L17.7273 40.5179L22.7264 35.5327L31.9091 44.6546L55.2736 21.355L60.2727 26.3755L31.9091 54.6603Z"
                                      fill="#1DBEB4"
                                    />
                                  </svg>

                                  <h4 className="modalReservation">
                                    Muito obrigado(a)!
                                  </h4>
                                  <p className="modalReservation">
                                    Sua reserva foi feita com sucesso
                                  </p>

                                  <Button
                                    variant="link"
                                    type="button"
                                    className="buttonModalReservation2"
                                    onClick={handleClose}
                                  >
                                    Ok
                                  </Button>
                                </Modal.Body>
                              </Modal>
                            </div>
                          </div>
                        </Col>
                      </Row>
                    </Col>
                  </Row>
                </Form>
              </div>
            )}
          </Formik>
          <ProductRules />
        </Container>
      )}
    </>
  );
}
export default ReservationDetail;
