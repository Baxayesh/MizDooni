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
import ManagerManage from './page/managerManage';
import NavContext from './context/nav';
import axios from 'axios';
import * as yup from 'yup';
import Customer from './page/Customer';
import Manager from './page/manager';


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
    const [reviews, setReviews] = useState([]);
    const [userReserves, setUserReserves] = useState([]);
    const [managerRest, setManagerRest] = useState([]);
    const [showTable, setShowTable] = useState([]);
    const [resTable, setResTable] = useState([]);
    const [seatNum, setSeatNum] = useState([]);
    const [addComment, setAddComment] = useState([]);

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

    useEffect(() => {
      axios.get(baseUrl + "restaurants")
          .then((res) => {

              setManagerRest(res.data)
          })
          .catch((err) => { console.log(err) });
  }, [])

  useEffect(() => {
    axios.get(baseUrl + `tables?restaurant=${managerRest.Name}`)
        .then((res) => {
            setShowTable(res.data)
        })
        .catch((err) => { console.log(err) });
}, [])

useEffect(() => {
  axios.get(baseUrl + `reserves?restaurant=${managerRest.Name}`)
      .then((res) => {
        setResTable(res.data)
      })
      .catch((err) => { console.log(err) });
}, [])
    

    
  const [values, setValues] = useState({});

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

    const [valuesReg, setValuesReg] = useState({
      username: "",
      password: "",
      email: "",
      country: "",
      city: "",
      role: "",
    });


    async function handleSubmitReg (event) {
        event.preventDefault();
        const reqBody = {
          username: valuesReg.username,
          password: valuesReg.password,
          city: valuesReg.city,
          country: valuesReg.country,
          role: valuesReg.role,
          email: valuesReg.email
        };
        const response = await axios.post('http://localhost:8080/users', reqBody  );
        // navigate("/");
        console.log(response);
    };

    const handleChangeReg = (e) => {
        const { value, name } = e.target;
        setValuesReg({ ...valuesReg, [name]: value });
      }

      async function handleLogout () {
        await axios.delete(baseUrl+"sessions")
        .then(response => {
          console.log('Resource deleted successfully:', response.data);
        })
        .catch(error => {
          console.error('Error deleting resource:', error);
        });
      }

      const handleSeatChange = (e) => {
        const { value, name } = e.target;
        setSeatNum({ ...valuesReg, [name]: value });
      }

      async function handleAddTable (event) {
        event.preventDefault();
        const reqBody = {
          restaurant: managerRest.Name,
          seats: seatNum
        }
        await axios.post(baseUrl + "tables", reqBody)
        .then(response => {
          console.log('Resource deleted successfully:', response.data);
        })
        .catch(error => {
          console.error('Error deleting resource:', error);
        });
      }

      useEffect(() => {
        axios.get("http://localhost:8080/reserves")
            .then((res) => {

                setUserReserves(res.data)
            })
            .catch((err) => { console.log(err) });
    }, []);

    async function handleAddComment () {
      const reqBody = {
        foodRate: "1",
        ambientRate: "1",
        serviceRate:"1",
        overallRate:"1",
        comment:addComment.toString
      }
      await axios.post(baseUrl + "reviews", reqBody)
      .then(response => {
        console.log('Resource deleted successfully:', response.data);
      })
      .catch(error => {
        console.error('Error deleting resource:', error);
      });
    }

    const [addRest, setAddRest] = useState({
          name: "",
          type: "",
          openTime: "",
          closeTime: "",
          description: "",
          country: "",
          city: "",
          street: "",
          image:""
    });


    async function handleSubmitAddRest (event) {
        event.preventDefault();
        const reqBody = {
          name: addRest.name,
          type: addRest.type,
          openTime: addRest.openTime,
          closeTime: addRest.closeTime,
          description: addRest.description,
          country: addRest.country,
          city: addRest.city,
          street: addRest.street,
          image: "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.self.com%2Fstory%2Feating-at-restaurant-coronavirus&psig=AOvVaw1m6jc1B6itDisx82WP9GOx&ust=1714935016836000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCJC9zPrU9IUDFQAAAAAdAAAAABAE"
        };
        const response = await axios.post('http://localhost:8080/restaurants', reqBody  );
        console.log(response);
    };

    const handleChangeAddRest = (e) => {
        const { value, name } = e.target;
        setAddRest({ ...addRest, [name]: value });
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
                        reviews:reviews,
                        setReviews:setReviews,
                        userReserves:userReserves,
                        setUserReserves:setUserReserves,
                        valuesReg:valuesReg,
                        handleSubmitReg:handleSubmitReg,
                        handleChangeReg:handleChangeReg,
                        managerRest:managerRest,
                        showTable:showTable,
                        resTable:resTable,
                        handleLogout:handleLogout,
                        handleSeatChange:handleSeatChange,
                        handleAddTable:handleAddTable,
                        addComment:addComment,
                        setAddComment:setAddComment,
                        handleAddComment:handleAddComment,
                        addRest:addRest,
                        handleChangeAddRest:handleChangeAddRest,
                        handleSubmitAddRest:handleSubmitAddRest,

                    }}
                >
                
                <div className="app mt-3">
                    <BrowserRouter>
                        <Routes>
                            <Route path="/users/:id" element={<User />} />
                            <Route path="/users" element={<Users />} />
                            <Route path="/managerManage" element={<ManagerManage />} />
                            <Route path="/manager" element={<Manager />} />
                            <Route path="/customer" element={<Customer />} />
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