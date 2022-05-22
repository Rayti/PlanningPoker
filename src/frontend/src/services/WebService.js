import axios from 'axios';
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
const API_URL = 'http://localhost:8080/api/poker';
/*const config ={headers: {"Content-type": "application/json",
        'Access-Control-Allow-Credentials':true}}*/
const config = {headers: {}}


export class WebService{

    receivedMessages = [];
    sendMessage = null;
    connected = false;

    constructor(){
    }

    addUser(userName) {
        const url = `${API_URL}/newUser/`+userName;
        axios.get(url, config).then(response => response.data);
    }

    send(msg){
        console.log("Send message: " + this.sendMessage);
        if(this.stompClient && this.stompClient.connected){
            const msgToParse = { message: msg};
            this.stompClient.send("/backend-request/chat", JSON.stringify(msgToParse), {});

        }
    }

    connect(){
        this.socket = new SockJS("http://localhost:8080//register-websocket");
        this.stompClient = Stomp.over(this.socket);
        this.stompClient.connect(
            {},
            frame => {
                this.connected = true;
                console.log(frame);
                this.stompClient.subscribe("/backend-response/messages", tick => {
                    //here is response processed from backend
                    console.log("RESPONSE FROM BACKEND:");
                    console.log(tick);
                    this.receivedMessages.push(JSON.parse(tick.body).message);
                });
            },
            error => {
                console.log(error);
                this.connected = false;
            })
    }

    disconnect(){
        if(this.stompClient){
            this.stompClient.disconnect();
        }
        this.connected = false;
    }

    tickleConnection(){
        this.connected ? this.disconnect() : this.connect();
    }
}