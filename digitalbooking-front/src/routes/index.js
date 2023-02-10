import {BrowserRouter, Routes, Route } from 'react-router-dom';
import {HelmetProvider} from 'react-helmet-async';
import Header from '../components/Header';
import Home from '../pages/Home';
import CreatePropertyPage from '../pages/CreatePropertyPage';
import StartSession from '../pages/StartSession';
import CreateAccount from '../pages/CreateAccount';
import Footer from '../components/Footer';
import AuthProvider from '../contexts/AuthProvider';
import ReservationProvider from '../contexts/ReservationProvider';
import ProductDetail from '../pages/ProductDetail';
import SearchDetail from '../pages/SearchDetail';
import ReservationDetail from '../pages/ReservationDetail';
import ReservationsPage from '../pages/ReservationsPage';

const RouteList = () => {
  return(
    <BrowserRouter>
      <HelmetProvider>
        <AuthProvider>
        <ReservationProvider>
          <Header/>
          <Routes>
            <Route path="/" element={<Home/>} />
            <Route path="/:search" element={<SearchDetail />} />
            <Route path="/start" element={<StartSession/>}/>
            <Route path="/create" element={<CreateAccount/>}/>
            <Route path="/product/:id" element={<ProductDetail/>}/>
            <Route path="/product/:id/reservation" element={<ReservationDetail/>}/>
            <Route path="/property" element={<CreatePropertyPage/>}/>
            <Route path="/reservations" element={<ReservationsPage/>}/>
          </Routes>
          <Footer/>
         </ReservationProvider>
        </AuthProvider>
      </HelmetProvider>
    </BrowserRouter>
  )
};
  export default RouteList;