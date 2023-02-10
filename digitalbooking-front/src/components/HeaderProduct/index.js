import './style.scss';


function HeaderProduct({product}) {


    return (
        <>
            <section id="main-container">
                <div id="title-arrow">
                    <div id="title-cat">
                        <p>{product?.category?.title}</p>
                        <h1>{product?.name}</h1>
                    </div>
                    <div id="arrow">
                    <a href="./category"><i className="fa-solid fa-chevron-left"></i></a>
                    </div>
                </div>
                <div id="adress-rate">
                    <div id="adress">
                        <div id="adress-location">
                        <i className="fa-solid fa-location-dot"></i>
                        <p id="p-first">{product?.city?.name}</p>
                        </div>                        
                        <p id="p-second">{product?.city?.latitude}</p>
                    </div>
                    {/* <div id="rate"></div> */}
                </div>
            </section>   
        </>



    )
}


export default HeaderProduct;