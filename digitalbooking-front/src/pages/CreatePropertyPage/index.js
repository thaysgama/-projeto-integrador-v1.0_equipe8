import React, { useCallback, useEffect, useState, useContext } from "react";
import "./style.scss";
import { Link, useNavigate } from "react-router-dom";
import * as Yup from "yup";
import { Button, Form, Row, Col, Container } from "react-bootstrap";
import { Formik, Field, FieldArray } from "formik";
import Swal from "sweetalert2";
import api from "../../services/api";
import { AuthContext } from "../../contexts/AuthProvider";

const CreatePropertyPage = () => {
  const navigate = useNavigate();
  const { authState } = useContext(AuthContext);

  const [categories, setCategories] = useState([]);
  const [cities, setCities] = useState([]);
  const [suggestionsCity ,setSuggestionsCity] = useState([]);
  const [characteristics, setCharacteristics] = useState([]);

  const loadData = async () =>{
    try{
      await api.get("/category").then((response) => {
        setCategories(response.data.content);
      });
      await api.get("/characteristic").then((response) => {
        setCharacteristics(response.data.content);
      });
      await api.get("/city").then((response) => {
        setCities(response.data.content);
      });

    } catch (error) {
      Swal.fire({
          title: 'Página não encontrada',
          icon: 'error',
          buttonsStyling: false
      })
      }
  }

  useEffect(() =>  {
    loadData()
  }, []);


    // useEffect(() => {
    //    const loadCities = async()=>{
    //        const response = await api.get('/city')
    //        //console.log(response.data)
    //        setCity(response.data.content)
    //    }
    //    loadCities();
    // }, [])
    // const onSuggestHandler = (text)=>{
    //     setText(text);
    //     setSuggestions([])
    // }
    // const onChangeHandler =(text)=>{
    //     let matches=[]
    //     if(text.length>0) {
    //         matches = cities.filter(city=>{
    //             const regex = new RegExp(`${text}`,"gi");
    //             return city.name.match(regex)
    //         })
    //     }
    //     //console.log('matches', matches)
    //     setSuggestions(matches)
    //     setText(text)
    // }

  const initialValues = {
    name: "",
    description: "",
    address: "",
    generalRules: "",
    safetyRules: "",
    cancellationRules: "",
    characteristicIds: [],
    imageList: [
      {
        title: "",
        url: "",
      },
    ],
    categoryId: "",
    cityId: "",
    proprietorId: authState.id,
  };

  const validationSchema = Yup.object().shape({
    //name: Yup.string().required('Informe o nome da propriedade'),
    //category: Yup.string().required('Informe a categoria'),
    //address: Yup.string().required('Informe o endereço'),
    //city: Yup.string().required('Informe a cidade onde a propriedade está'),
    //description: Yup.string().required('descreva a propriedade'),
    //imageProperty: Yup.string().required('adicione fotos da sua propriedade')
  });

  const handleSubmit = async (values, { setSubmitting }) => {
    try {
      await api.post(`/product`, { ...values }).then((response) => {
        if (response) {
          Swal.fire({
            title: "Registro feito com sucesso.",
            icon: "success",
            // text: 'Ative sua conta confirmando seu email.'
          });
        }
      });
    } catch (error) {
      Swal.fire({
        title: error.response.status,
        icon: "error",
        text: error.response.data.message,
      });
    }

    setTimeout(() => {
      setSubmitting(false);
      navigate("/");
    }, 2000);
  };


  return (
    <div id="createProduct">
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
          setFieldValue,
          getFieldProps,
        }) => (
          <Container className="productPage">
            <h3 className="firstTittle">Criar Propriedade</h3>
            <Form className="formProduct" onSubmit={handleSubmit}>
              <Row className="propertyRow">
                <Form.Group
                  as={Col}
                  md="6"
                  className="mb-4"
                  controlId="validationFormik14"
                >
                  <Form.Label>Nome da Propriedade:</Form.Label>
                  <Form.Control
                    type="text"
                    placeholder="Insira o nome da propriedade"
                    name="name"
                    required
                    value={values.name}
                    onChange={handleChange}
                    isInvalid={!touched.name && !!errors.name}
                  />
                  <Form.Control.Feedback type="invalid">
                    {errors.name}
                  </Form.Control.Feedback>
                </Form.Group>

                <Form.Group
                  as={Col}
                  md="6"
                  className="mb-4"
                  controlId="validationFormik15"
                >
                  <Form.Label>Categoria:</Form.Label>
                  <Form.Select
                    aria-label="Default select example"
                    name="categoryId"
                    required
                    value={values.categoryId}
                    onChange={handleChange}
                    isInvalid={!touched.categoryId && !!errors.categoryId}
                    placeholder="Escolha uma categoria"
                  >
                    <option>Escolha uma categoria</option>
                    {categories.map((item) => {
                      return (
                        <option key={item.id} value={item.id} >
                          {item.title}
                        </option>
                      );
                    })}
                  </Form.Select>
                  <Form.Control.Feedback type="invalid">
                    {errors.categoryId}
                  </Form.Control.Feedback>
                </Form.Group>

                <Form.Group
                  as={Col}
                  md="6"
                  className="mb-4"
                  controlId="validationFormik16"
                >
                  <Form.Label>Endereço:</Form.Label>
                  <Form.Control
                    type="text"
                    placeholder="Insira o endereço da propriedade"
                    name="address"
                    required
                    value={values.address}
                    onChange={handleChange}
                    isInvalid={!touched.adress && !!errors.adress}
                  />
                  <Form.Control.Feedback type="invalid">
                    {errors.adress}
                  </Form.Control.Feedback>
                </Form.Group>

                <Form.Group
                  as={Col}
                  md="6"
                  className="mb-4"
                  controlId="validationFormik17"
                >
                  <Form.Label>Cidade:</Form.Label>
                  <Form.Select
                    aria-label="Default select example"
                    name="cityId"
                    required
                    value={values.cityId}
                    onChange={handleChange}
                    isInvalid={!touched.cityId && !!errors.cityId}
                  >
                    <option>Escolha uma cidade</option>
                    {cities.map((item) => {
                      return (
                        <option key={item.id} value={item.id}>
                          {item.name}
                        </option>
                      );
                    })}
                  </Form.Select>
                  <Form.Control.Feedback type="invalid">
                    {errors.cityId}
                  </Form.Control.Feedback>
                </Form.Group>

                <Form.Group
                  as={Col}
                  md="12"
                  className="mb-4"
                  controlId="validationFormik18"
                >
                  <Form.Label>Descrição</Form.Label>
                  <Form.Control
                    as="textarea"
                    rows={3}
                    type="text"
                    required
                    placeholder="Descreva sua propriedade"
                    name="description"
                    value={values.description}
                    onChange={handleChange}
                    isInvalid={!touched.description && !!errors.description}
                  />
                  <Form.Control.Feedback type="invalid">
                    {errors.description}
                  </Form.Control.Feedback>
                </Form.Group>
              </Row>

              <Row className="propertyRow1">
                <h3 className="secondTittle">Adicionar Atributos</h3>

                <div key={`inline-checkbox`} className="mb-3">
                  {characteristics.map((item) => {
                    return (
                      <Col
                        className="product-card"
                        key={item.id}
                        xs={12}
                        lg={6}
                      >
                        <Form.Check
                          inline
                          label={item.name}
                          name="characteristicIds"
                          type="checkbox"
                          id={item.id}
                          value={item.id}
                          onChange={handleChange}
                        />
                        <span className="material-icons">{item.icon}</span>
                      </Col>
                    );
                  })}
                </div>
              </Row>

              <Row className="propertyRow">
                <h3 className="secondTittle">Políticas do Produto</h3>
                <Form.Group
                  as={Col}
                  md="4"
                  className="mb-4"
                  controlId="validationFormik21"
                >
                  <Form.Label>Regras da Casa</Form.Label>
                  <Form.Control
                    as="textarea"
                    rows={3}
                    type="text"
                    required
                    placeholder="Descrição"
                    name="generalRules"
                    value={values.generalRules}
                    onChange={handleChange}
                    isInvalid={!touched.generalRules && !!errors.generalRules}
                  />
                  <Form.Control.Feedback type="invalid">
                    {errors.generalRules}
                  </Form.Control.Feedback>
                </Form.Group>

                <Form.Group
                  as={Col}
                  md="4"
                  className="mb-4"
                  controlId="validationFormik22"
                >
                  <Form.Label>Saúde e Segurança</Form.Label>
                  <Form.Control
                    as="textarea"
                    rows={3}
                    type="text"
                    required
                    placeholder="Descrição"
                    name="safetyRules"
                    value={values.safetyRules}
                    onChange={handleChange}
                    isInvalid={!touched.safetyRules && !!errors.safetyRules}
                  />
                  <Form.Control.Feedback type="invalid">
                    {errors.safetyRules}
                  </Form.Control.Feedback>
                </Form.Group>

                <Form.Group
                  as={Col}
                  md="4"
                  className="mb-4"
                  controlId="validationFormik23"
                >
                  <Form.Label>Política de Cancelamento</Form.Label>
                  <Form.Control
                    as="textarea"
                    rows={3}
                    type="text"
                    required
                    placeholder="Descrição"
                    name="cancellationRules"
                    value={values.cancellationRules}
                    onChange={handleChange}
                    isInvalid={
                      !touched.cancellationRules && !!errors.cancellationRules
                    }
                  />
                  <Form.Control.Feedback type="invalid">
                    {errors.cancellationRules}
                  </Form.Control.Feedback>
                </Form.Group>
              </Row>

              <Row className="propertyRow">
                <h3 className="secondTittle">Carregar imagens</h3>

                <FieldArray
                  name="imageList"
                  render={(arrayHelpers) => (
                    <div>
                      {values.imageList.map((img, index) => (
                        <Row key={index} className="propertyRow">
                          <Form.Group as={Col} md="3" className="mb-4">
                            <Form.Control
                              as={Field}
                              name={`imageList[${index}].title`}
                              type="text"
                              required
                              value={values.imageList[index].title}
                              onChange={handleChange}
                              isInvalid={!!errors.image}
                              placeholder="Insira um título"
                            />
                            <Form.Control.Feedback type="invalid">
                              {errors.imageList}
                            </Form.Control.Feedback>
                          </Form.Group>

                          <Form.Group as={Col} md="8" className="mb-4">
                            <Form.Control
                              as={Field}
                              name={`imageList.${index}.url`}
                              type="text"
                              required
                              value={values.imageList[index].url}
                              onChange={handleChange}
                              isInvalid={!!errors.image}
                              placeholder="Insira uma url"
                            />
                            <Form.Control.Feedback type="invalid">
                              {errors.imageList}
                            </Form.Control.Feedback>
                          </Form.Group>
                          <Col md="1">
                            {index === values.imageList.length-1  ? 
                            <Button
                            className="imgButtonPlus"
                            type="button"
                            onClick={() => arrayHelpers.push({ title: "", url: "" })}
                          >
                          <span className="material-icons">
                            add
                          </span>
                          </Button> :
                          <Button
                          className="imgButtonMinus"
                          type="button"
                          onClick={() => arrayHelpers.remove(index)}
                        >
                          <span className="material-icons">
                          close
                          </span>
                        </Button>
                          }
                          </Col>
                        </Row>
                      ))}
                      
                    </div>
                  )}
                />

                <div className="d-flex register">
                  <Button
                    className="registerButton"
                    type="submit"
                    disabled={isSubmitting}
                  >
                    Criar
                  </Button>
                </div>
              </Row>
            </Form>
          </Container>
        )}
      </Formik>
    </div>
  );
};

export default CreatePropertyPage;
