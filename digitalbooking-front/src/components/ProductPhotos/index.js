import ModalTeste from "../ModalTeste";
import "./style.scss";

import { Carousel, Container, Row, Col } from "react-bootstrap";

function ProductPhotos({ product }) {
  
  let productImages = product.images.slice(1, product.length);
  let firstImage = product.images[0];
  if (productImages.length < 4) {
    for (let i = product.images.length; i <= 4; i++) {
      productImages.push(firstImage);
      product.images.push(firstImage);
    }
  } else if (product.images.length > 4) {
    productImages = productImages.slice(0, 4);
  }

  return (
    <>
      <section >
        <Container  id="photo-container">
          <Row id="modal-container">
            <Col className="photo-container-1">
              {product && (
                <div id="photo-1">
                  <img src={firstImage.url} alt="" />
                </div>
              )}
              
            </Col>
            <Col className="photo-container-2">
              {productImages.map((image) => {
                return (
                  <Row key={image.id}>
                    <div id={`photo-${image.id}`} className="div-photos">
                      <img src={image.url} alt=""/>
                    </div>
                  </Row>
                );
              })}
              <ModalTeste id="modal" product={product}></ModalTeste>
            </Col>
          </Row>
        </Container>
        

        <Carousel id="carousel">
          <Carousel.Item interval={1000}>
            {/* <img src={foto1} className="d-block w-100" /> */}
            <img
              className=" w-100"
              src={product.images[0].url}
              alt="First slide"
            />
          </Carousel.Item>
          <Carousel.Item interval={500}>
            {/* <img src={foto1} className="d-block w-100"/> */}
            <img
              className=" w-100"
              src={product.images[1].url}
              alt="Second slide"
            />
          </Carousel.Item>
          <Carousel.Item>
            {/* <img src={foto1} className="d-block w-100"/> */}
            <img
              className="d-block w-100"
              src={product.images[2].url}
              alt="Third slide"
            />
          </Carousel.Item>
          <Carousel.Item>
            {/* <img src={foto1} className="d-block w-100"/> */}
            <img
              className="d-block w-100"
              src={product.images[3].url}
              alt="Third slide"
            />
          </Carousel.Item>
          <Carousel.Item>
            {/* <img src={foto1} className="d-block w-100"/> */}
            <img
              className="d-block w-100"
              src={product.images[4].url}
              alt="Third slide"
            />
          </Carousel.Item>
        </Carousel>
      </section>
    </>
  );
}

export default ProductPhotos;
