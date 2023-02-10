import { Modal, Button } from "react-bootstrap";
import { useState } from "react";
import "./style.scss";

const ModalTeste = ({ product }) => {
  let productImages = product.images;
  if (productImages.length < 6) {
    for (let i = productImages.length; i < 6; i++) {
      productImages.push(product.images[0]);
    }
  } else if (product.images.length > 6) {
    productImages = product.images.slice(0, 6);
  }

  const [lgShow, setLgShow] = useState(false);
  const handleClose = () => setLgShow(false);

  return (
    <>
      <Button
        variant="link"
        className="buttonModal"
        onClick={() => setLgShow(true)}
      >
        Ver mais
      </Button>
      
      <Modal
        className="modal"
        size="lg"
        show={lgShow}
        onHide={handleClose}
        aria-labelledby="contained-modal-title-vcenter"
        centered
      >
        <Modal.Header className="d-flex" closeButton>
          {/* <!-- Carousel wrapper --> */}
          <div
            id="carouselMDExample"
            className="carousel slide carousel-fade"
            data-mdb-ride="carousel"
          >
            {/* <!-- Slides --> */}
            <div className="carousel-inner mb-8 rounded-3">
              <div className="carousel-item active">
                <img
                  src={productImages[0].url}
                  className="d-block w-100"
                  alt={productImages[0].title}
                />
              </div>
              <div className="carousel-item">
                <img
                  src={productImages[1].url}
                  className="d-block w-100"
                  alt={productImages[1].title}
                />
              </div>
              <div className="carousel-item">
                <img
                  src={productImages[2].url}
                  className="d-block w-100"
                  alt={productImages[2].title}
                />
              </div>
              <div className="carousel-item">
                <img
                  src={productImages[3].url}
                  className="d-block w-100"
                  alt={productImages[3].title}
                />
              </div>
              <div className="carousel-item">
                <img
                  src={productImages[4].url}
                  className="d-block w-100"
                  alt={productImages[4].title}
                />
              </div>
              <div className="carousel-item">
                <img
                  src={productImages[5].url}
                  className="d-block w-100"
                  alt={productImages[5].title}
                />
              </div>
            </div>

            {/* <!-- Slides --> */}

            {/* <!-- Controls --> */}
            <button
              className="carousel-control-prev h-50"
              type="button"
              data-mdb-target="#carouselMDExample"
              data-mdb-slide="prev"
            >
              <span
                className="carousel-control-prev-icon"
                aria-hidden="true"
              ></span>
              <span className="visually-hidden">Previous</span>
            </button>
            <button
              className="carousel-control-next h-50"
              type="button"
              data-mdb-target="#carouselMDExample"
              data-mdb-slide="next"
            >
              <span
                className="carousel-control-next-icon"
                aria-hidden="true"
              ></span>
              <span className="visually-hidden">Next</span>
            </button>
            {/* <!-- Controls --> */}

            {/* <!-- Thumbnails --> */}
            <div className="carousel-indicators">
              <button
                type="button"
                data-mdb-target="#carouselMDExample"
                data-mdb-slide-to="0"
                className="active"
                aria-current="true"
                aria-label="Slide 1"
              >
                <img
                  src={productImages[0].url}
                  className="d-block w-100 img-fluid"
                  alt={productImages[0].title}
                />
              </button>
              <button
                type="button"
                data-mdb-target="#carouselMDExample"
                data-mdb-slide-to="1"
                aria-label="Slide 2"
              >
                <img
                  src={productImages[1].url}
                  className="d-block w-100 img-fluid"
                  alt={productImages[1].title}
                />
              </button>
              <button
                type="button"
                data-mdb-target="#carouselMDExample"
                data-mdb-slide-to="2"
                aria-label="Slide 3"
              >
                <img
                  src={productImages[2].url}
                  className="d-block w-100 img-fluid"
                  alt={productImages[2].title}
                />
              </button>
              <button
                type="button"
                data-mdb-target="#carouselMDExample"
                data-mdb-slide-to="3"
                aria-label="Slide 4"
              >
                <img
                  src={productImages[3].url}
                  className="d-block w-100 img-fluid"
                  alt={productImages[3].title}
                />
              </button>
              <button
                type="button"
                data-mdb-target="#carouselMDExample"
                data-mdb-slide-to="4"
                aria-label="Slide 5"
              >
                <img
                  src={productImages[4].url}
                  className="d-block w-100 img-fluid"
                  alt={productImages[4].title}
                />
              </button>
              <button
                type="button"
                data-mdb-target="#carouselMDExample"
                data-mdb-slide-to="5"
                aria-label="Slide 0"
              >
                <img
                  src={productImages[5].url}
                  className="d-block w-100 img-fluid"
                  alt={productImages[5].title}
                />
              </button>
            </div>
            {/* <!-- Thumbnails --> */}
          </div>
        </Modal.Header>
      </Modal>
    </>
  );
};

export default ModalTeste;
