import './style.scss';



function ProductDescription({product}) {

    
    return (
        <>
            <section id="description">
                <h2>{product.name}</h2>
                <p>{product.description}</p>
            </section>
        </>
    )

}

export default ProductDescription;