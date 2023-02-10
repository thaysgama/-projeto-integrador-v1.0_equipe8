import React from "react";
import "./style.scss";
import {Link, useNavigate  } from "react-router-dom";
import * as Yup from "yup";
import {Button,Form, Row, Col} from "react-bootstrap";
import { Field, Formik} from 'formik';
import visibility from '../../assets/visibility.svg';
import visibilityOff from '../../assets/visibilityOff.svg';
import Swal from 'sweetalert2';
import api from '../../services/api';

const Register = () =>{
  
  const navigate = useNavigate();

  const initialValues = {
    firstName:'',
    lastName:'',
    email:'',
    password:'',
    confirmPassword:'',
    roleId:''
  }

  const validationSchema = Yup.object().shape({
    firstName: Yup.string().required('Informe seu nome'),
    lastName: Yup.string().required('Informe seu sobrenome'),
    email: Yup.string().email('Formato de e-mail inválido').required('Você precisa informar um e-mail'),
    password: Yup.string().min(6, 'A senha precisa ter no mínimo 6 caracteres').required('Preencha campo senha'),
    confirmPassword: Yup.string().oneOf([Yup.ref('password'), null], 'As senhas precisam ser iguais'),
    roleId: Yup.string().required("Escolha uma das opções.")
  });
  
  const handleSubmit = async (values, { setSubmitting }) => {
    try{
      await api.post(`/user`, {...values})
      .then((response)=>{
          if(response){
            Swal.fire({
              title: 'Registro feito com sucesso.',
              icon: 'success',
              text: 'Ative sua conta confirmando seu email.'
            });
          }
        });

    } catch(error){
      Swal.fire({
        title: error.response.status,
        icon: 'error',
        text: error.response.data.message
      });
    }

    setTimeout(() => {
      setSubmitting(false);
      navigate("/");
    }, 2000);
  };

  function handleClickPassword() {
    
    let input = document.querySelector('.password');
    let img = document.querySelector('.imageVisibility');
    if (input.getAttribute('type') === 'password') {
      input.setAttribute('type', 'text');
      img.setAttribute('src', visibility);
    } else {
      input.setAttribute('type', 'password');
    img.setAttribute('src', visibilityOff);
    }
     
   }
   function handleClickPasswordConfirm() {
       let input = document.querySelector('.password-confirm');
       let img = document.querySelector('.imageVisibilityConfirm')
       if (input.getAttribute('type') === 'password') {
         input.setAttribute('type', 'text');
         img.setAttribute('src', visibility);
       } else {
         input.setAttribute('type', 'password');
         img.setAttribute('src', visibilityOff);
       }
   }
  
  return (
    <div id="register">
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
          <Form className="formRegister col-lg-4 col-md-10 col-10 md-5" noValidate onSubmit={handleSubmit}>
            <Row>
              <h3 className="h3Register"> Criar conta</h3>
              <Form.Group as={Col} md="6" className="mb-4" controlId="validationFormik01">
                <Form.Label>Nome:</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Insira seu nome"
                  name="firstName"
                  value={values.firstName}
                  onChange={handleChange}
                  isInvalid={!touched.firstName && !!errors.firstName}
                />
                <Form.Control.Feedback type="invalid">
                  {errors.firstName}
                </Form.Control.Feedback>
              </Form.Group>

              <Form.Group as={Col} md="6" className="mb-4" controlId="validationFormik02">
                <Form.Label>Sobrenome</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Insira seu sobrenome"
                  name="lastName"
                  value={values.lastName}
                  onChange={handleChange}
                  isInvalid={!touched.lastName && !!errors.lastName}
                />
                <Form.Control.Feedback type="invalid">
                  {errors.lastName}
                </Form.Control.Feedback>
              </Form.Group>

              <Form.Group md="4" className="mb-4" controlId="validationFormikUsername">
                <Form.Label>E-mail</Form.Label>
                <Form.Control
                  type="email"
                  placeholder="Insira seu e-mail"
                  aria-describedby="inputGroupPrepend"
                  name="email"
                  value={values.email}
                  onChange={handleChange}
                  isInvalid={!touched.email && !!errors.email}
                />
                <Form.Control.Feedback type="invalid">
                  {errors.email}
                </Form.Control.Feedback>
                
              </Form.Group>

              <Form.Group className="mb-3" controlId="validationFormikPassword">
                <Form.Label>Senha</Form.Label>

                <div className="input-password">

                  <Form.Control
                    type="password"
                    className="password"
                    placeholder="Digite sua senha"
                    aria-describedby="inputGroupPrepend"
                    name="password"
                    value={values.password}
                    onChange={handleChange}
                    isInvalid={!touched.password && !!errors.password}
                  />

                  
                    <img src={visibilityOff} onClick={handleClickPassword} className="imageVisibility register"></img>


                  
                </div>

                <Form.Control.Feedback type="invalid">
                  {errors.password}
                </Form.Control.Feedback>
              </Form.Group>

              <Form.Group className="mb-3" controlId="validationFormik05">
                <Form.Label>Confirme sua senha</Form.Label>

                <div className="input-password-confirm">
                  <Form.Control
                    type="password"
                    className="password-confirm"
                    placeholder="Digite novamente sua senha"
                    aria-describedby="inputGroupPrepend"
                    name="confirmPassword"
                    value={values.confirmPassword}
                    onChange={handleChange}
                    isInvalid={!touched.confirmPassword && !!errors.confirmPassword}
                  />
                  
                    <img src={visibilityOff} onClick={handleClickPasswordConfirm} className="imageVisibility register"></img>
                  
                </div>
                  <Form.Control.Feedback type="invalid">
                    {errors.confirmPassword}
                  </Form.Control.Feedback>
                  
                </Form.Group>
                <Form.Group className="d-flex">
                  
                    <Form.Check 
                      type='radio'
                      label='Cliente'
                      name="roleId"
                      id='client-radio'
                      className="mx-auto"
                      value='1'
                      onChange={handleChange}
                      isInvalid={errors.roleId}
                      
                    />
                    <Form.Check 
                      type='radio'
                      label='Proprietário'
                      name="roleId"
                      id='proprietor-radio'
                      className="mx-auto"
                      value='3'
                      onChange={handleChange}
                      isInvalid={errors.roleId}
                    />
                    <Form.Control.Feedback type="invalid">
                            {errors.roleId}
                    </Form.Control.Feedback>
                </Form.Group>

                <div className="d-flex register">
                  <Button className="mb-3 col-lg-4 col-md-4 col-12 register" type="submit" disabled={isSubmitting}>Criar conta</Button>
                </div>
                <p className='pRegister'>Já tem uma conta?
                  <Link to="/start">  Iniciar sessão</Link> </p>
              </Row>
            </Form>
            )}
            </Formik>
      </div> 
    );

}


export default Register;
