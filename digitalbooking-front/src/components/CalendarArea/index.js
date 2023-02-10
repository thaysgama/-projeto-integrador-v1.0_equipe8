// import ProductCalendar from '../ProductCalendar';
import './style.scss';



function CalendarArea() {
    return(

        <>
        
            <section id="calendar-container">
                <div id="calendar-area">
                    <p>Datas disponíveis</p>
                    {/* <ProductCalendar /> */}
                </div>
                <div id="reservation">
                    <p>Aicione as datas da sua viagem para obter preçs exatos</p>
                    <input type="submit" className="btn btn-primary" />
                </div>
            </section>  
        
        
        </>



    )




}


export default CalendarArea;