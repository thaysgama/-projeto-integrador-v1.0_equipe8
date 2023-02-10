import './style.scss' ;




export function ProductRules() {
    
    return (

        <section id="product_rules">
         <h2> O que você precisa saber:</h2>
            <hr/>
        <div className='informations-rules'>
            <div>
                <h4>Regras da casa</h4>
                <ul>
                    <li>Check-out: 10:00</li>
                    <li>Não é permitido festas</li>
                    <li>Não fumar</li>
                </ul>
            </div>
            <div>
                <h4>Saúde e Segurança</h4>
               <ul>
                   <li>Diretrizes de distanciamento social e outras regulamentações relacionadas ao coronavírus se aplicam</li>
                   <li>Detector de fumaça</li>
                   <li>Depósito de segurança</li>
               </ul>
            </div>
            <div>
                <h4>Política de cancelamento</h4>
                <p>Adicione as datas da viagem para obter detalhes de cancelamento para esta estadia</p>
            </div>
        </div>
               
            
        </section>
     
    )
}
export default ProductRules;