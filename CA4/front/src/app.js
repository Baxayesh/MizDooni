import React, { useState, useEffect } from 'react';
import Users from './components/users';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Login from './page/login';
import Home from './page/home';
import Register from './page/register';
import User from './components/user';
import NotFound from './page/notFound';
import SearchResult from './page/searchResult';
import Restaurant from './page/restaurant';
import Footer from './components/footer';
import Navbar from './components/navbar';
import NavContext from './context/nav';
import axios from 'axios';
import * as yup from 'yup';

const App = () => {
    const [data, setData] = useState([]);
    const [ratingData, setRatingData] = useState([]);
    const [date, setDate] = useState(new Date());
    const [location, setLocation] = useState([]);
    const [restType, setRestType] = useState([]);
    const [searchInfo, setSearchInfo] = useState([]);
    const [searchType, setSearchType] = useState([]);
    const [searchLoc, setSearchLoc] = useState([]);
    const [resultName, setResultName] = useState([]);
    const [rest, setRest] = useState([]);
    const [resDate, setResDate] = useState(new Date());
    const [people, setPeople] = useState([]);
    const [times, setTimes] = useState([]);
    const baseUrl = "http://localhost:8080/";
   
    
    useEffect(() => {
        axios.get(baseUrl + "restaurants?recommendBy=userLocation")
            .then((res) => {

                setData(res.data)
            })
            .catch((err) => { console.log(err) });
    }, [])

    useEffect(() => {
        axios.get(baseUrl + "restaurants?recommendBy=rating")
            .then((res) => {

                setRatingData(res.data)
            })
            .catch((err) => { console.log(err) });
    }, [])

    useEffect(() => {
        setDate(new Date());
    }, [])

    useEffect(() => {
        axios.get(baseUrl + "locations")
            .then((res) => {
                setLocation(res.data)
            })
            .catch((err) => { console.log(err) });
    }, [])

    useEffect(() => {
        axios.get(baseUrl + "foodTypes")
            .then((res) => {
                setRestType(res.data)
            })
            .catch((err) => { console.log(err) });
    }, [])
    useEffect(() => {
        axios.get(`http://localhost:8080/restaurants?name=${searchInfo}`)
            .then((res) => {
                setResultName(res.data.items)
            })
            .catch((err) => { console.log(err) });
    }, [searchInfo])

    useEffect(() => {
        axios.get(`http://localhost:8080/restaurants?name=${searchInfo}/availableTimeSlots?onDate=${resDate}&requestedSeats=${people}`)
            .then((res) => {
                setTimes(res.data)
            })
            .catch((err) => { console.log(err) });
    }, [searchInfo, resDate, people])
    

    
  const [values, setValues] = useState({});
//   const navigate = useNavigate();

  async function validate() {
    const schema = yup.object().shape({
      username: yup.string().required('فیلد نام کاربری الزامی می باشد'),
      password: yup.string().min(4, 'پسورد باید حداقل چهار کاراکتر باشد')
    })
    try {
      const result = await schema.validate(values, { abortEarly: false });
      return result;
    } catch (error) {
      console.log(error);
    }
  }

  async function handleSubmit(e) {
    e.preventDefault();
    const result = await validate();
    console.log(result);
    if (result) {
      try {
        const response = await axios.post('http://localhost:8080/sessions', result);
        // navigate("/");
        console.log(response)
      } catch (error) {
        console.log(error);
      }
    }
  }

  const handleChange = (e) => {
    const { value, name } = e.target;
    setValues({ ...values, [name]: value });
  }



    return ( 
        <>
                <NavContext.Provider
                    value={{
                        data:data,
                        ratingData:ratingData,
                        date:date,
                        location:location,
                        restType:restType,
                        searchInfo:searchInfo,
                        handleSearchBar: setSearchInfo,
                        searchType: searchType,
                        setSearchType:setSearchType,
                        searchLoc:searchLoc,
                        setSearchLoc:setSearchLoc,
                        resultName:resultName,
                        values:values,
                        handleSubmit:handleSubmit,
                        handleChange:handleChange,
                        rest:rest,
                        setRest:setRest,
                        resDate: resDate,
                        setResDate:setResDate,
                        people: people,
                        setPeople:setPeople,
                        times:times,
                        
                    }}
                >
                
                <div className="app mt-3">
                    <Navbar />
                    <BrowserRouter>
                        <Routes>
                            <Route path="/users/:id" element={<User />} />
                            <Route path="/users" element={<Users />} />
                            <Route path="/restaurant" element={<Restaurant />} />
                            <Route path="/SearchResult" element={<SearchResult />} />
                            <Route path="/register" element={<Register />} />
                            <Route path="/login" element={<Login />} />
                            <Route path="/" exact element={<Home />} />
                            <Route path="*" element={<NotFound />} />
                        </Routes>
                    </BrowserRouter>
                </div>
                <Footer />
            </NavContext.Provider >
            </>
     );
}
 
export default App;

// class App extends Component {
//     state = {}
//     render() {
//         return (
            // <>
            //     <NavContext.Provider
            //         value={{
                        
            //         }}
            //     >
            //     {/* <Navbar /> */}
            //     <div className="app mt-3">
            //         <BrowserRouter>
            //             <Routes>
            //                 <Route path="/users/:id" element={<User />} />
            //                 <Route path="/users" element={<Users />} />
            //                 <Route path="/restaurant" element={<Restaurant />} />
            //                 <Route path="/SearchResult" element={<SearchResult />} />
            //                 <Route path="/register" element={<Register />} />
            //                 <Route path="/login" element={<Login />} />
            //                 <Route path="/" exact element={<Home />} />
            //                 <Route path="*" element={<NotFound />} />
            //             </Routes>
            //         </BrowserRouter>
            //     </div>
            //     <Footer />
            // </NavContext.Provider >
            // </>
//         );
//     }
// }

// export default App;