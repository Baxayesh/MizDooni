import axios from 'axios';
import { useState, useEffect } from 'react';
import { useParams, useLocation, useNavigate  } from "react-router-dom";

const User = (props) => {

    const location = useLocation();
    const navigate = useNavigate();
    const [user, setUser] = useState({});
    let { id } = useParams();

    useEffect(() => {
        axios.get(`https://reqres.in/api/users/${id}`)
            .then((res) => {
                setUser(res?.data?.data);
                console.log(res?.data?.data);
            })
            .catch((error) => console.log(error.response.data.error))

    }, [id]);

    useEffect(() => {
        const params = new URLSearchParams(location.search);
        const param1 = params.get('param1');
        const param2 = params.get('param2');
        console.log('param1:', param1); 
        console.log('param2:', param2);

    }, [location.search]);

    return (<>
        <img src={user.avatar} style={{ borderRadius: '50%', width: '100px' }} alt='' />
        <h4>{user.first_name} {user.last_name}</h4>
        <h5>{user.email}</h5>
        <button onClick={() =>{navigate('/users')}} className="btn mt-3 btn-danger">Back</button>
    </>);
}

export default User;