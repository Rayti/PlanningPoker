import axios from 'axios';
const API_URL = 'http://localhost:8080/api/poker';
const config ={headers: {"Content-type": "application/json",
        'Access-Control-Allow-Credentials':true}}


export class WebService{

    constructor(){
    }

    addUser(userName) {
        const url = `${API_URL}/newUser/`+userName;
        return axios.get(url,config).then(response => response.data);
    }
}