import "./style.scss";
import { Link, useNavigate } from "react-router-dom";
import { Button, Form } from "react-bootstrap";
import { Formik } from "formik";
import { useContext } from "react";
import { AuthContext } from "../../contexts/AuthProvider";
import visibility from '../../assets/visibility.svg';
import visibilityOff from '../../assets/visibilityOff.svg';
import Swal from 'sweetalert2';
import api from '../../services/api';

const Login = () => {
  const { setAuthState} = useContext(AuthContext);

  const navigate = useNavigate();

  const initialValues = {
    email: "",
    password: "",
  };

  const handleSubmit = async (values, { setErrors, setSubmitting }) => {
    try {
      let responseTokens; 
      let responseInfo;
      await api.post(`/user/login`, {...values})
          .then((response)=>{
            responseTokens = response.data;
            api.get(`/user/info`, {
              headers: {
                'Authorization': `Bearer ${responseTokens.access_token}`
              }
            }).then((response) =>{
              responseInfo = response.data

              setAuthState((authState) => ({
                ...authState,
                token: responseTokens.access_token,
                refreshToken: responseTokens.refresh_token,
                id:responseInfo.id,
                firstName: responseInfo.firstName,
                lastName:  responseInfo.lastName,
                email: responseInfo.email,
                role: responseInfo.role,
                favoriteProducts: responseInfo.favoriteProducts
              }));
              navigate("/");
            })

      });
     
    } catch (error) {
      console.log(error.response)
      Swal.fire({
        title: error.response.status,
        icon: 'error',
        text: error.response.data.message
      });
    }
    setTimeout(() => {
      setSubmitting(false);
    }, 2000);
  };

  function handleClickPasswordStart() {
        
    let input = document.querySelector('.password-start');
    let img = document.querySelector('.imageVisibilityStart')
    if (input.getAttribute('type') === 'password') {
        input.setAttribute('type', 'text');
        img.setAttribute('src', visibility);
    } else {
        input.setAttribute('type', 'password');
        img.setAttribute('src', visibilityOff);
    }
}

  return (
    <div id="login1">
      <Formik initialValues={initialValues} onSubmit={handleSubmit}>
        {({ errors, isSubmitting, values, handleChange, handleSubmit }) => (
          <Form
            className="formLogin col-lg-4 col-md-10 col-10 md-5"
            onSubmit={handleSubmit}
          >
            <h3 className="h3Login"> Iniciar sessão</h3>
            <Form.Group className="mb-4" controlId="formBasicEmail">
              <Form.Label>E-mail</Form.Label>
              <Form.Control
                type="email"
                placeholder="Insira seu e-mail"
                value={values.email}
                name="email"
                onChange={handleChange}
                className="is-invalid"
              />
              <Form.Control.Feedback type="invalid">
                {errors.email}
              </Form.Control.Feedback>
            </Form.Group>

            <Form.Group className="mb-4" controlId="formBasicPassword">
              <Form.Label>Senha</Form.Label>
              <div className="input-password-start">
                <Form.Control
                  type="password"
                  placeholder="Digite sua senha"
                  value={values.password}
                  name="password"
                  onChange={handleChange}
                  className="password-start is-invalid"
                />
                
                  <img src={visibilityOff} onClick={handleClickPasswordStart} className="imageVisibilityStart login"></img>
                
              </div>
              <Form.Control.Feedback type="invalid">
                {errors.password}
              </Form.Control.Feedback>
            </Form.Group>
            <div className="d-flex login">
              <Button
                className="mb-3 col-lg-4 col-md-4 col-12 login"
                type="submit"
                disabled={isSubmitting}
              >
                Entrar
              </Button>
            </div>
            <p className="pLogin mb-3">
              Ainda não tem uma conta?
              <Link to="/create"> Registre-se</Link>{" "}
            </p>
          </Form>
        )}
      </Formik>
    </div>
  );
};

export default Login;
