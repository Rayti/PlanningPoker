import axios from 'axios';
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
const API_URL = 'http://localhost:8080/api/poker';
/*const config ={headers: {"Content-type": "application/json",
        'Access-Control-Allow-Credentials':true}}*/
const config = {headers: {'Access-Control-Allow-Credentials':true}}


export class WebService{

    receivedMessages = [];
    sendMessage = null;
    connected = false;
    roomName = "";
    userName = "";
    msgv2 = [];
    responseMessage = {message: "", success: false};
    webService = null;

    constructor(){
    }

    test(msg){
        const link = `${API_URL}/test`
        axios.get(link, config).then(response => this.msgv2.push(response.data.message))

    }

    addUser(userName) {
        const url = `${API_URL}/test`;
        axios.get(url, config).then(response => response.data.message);
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
                this.stompClient.subscribe("/backend-response/messages/param", tick => {
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

    async createRoomRequest(roomName, userName) {
        const link = `${API_URL}/${roomName}/${userName}/create-room`;
        await axios.get(link, config).then(response => this.responseMessage = response.data);
    }

    async joinRoomRequest(roomName, userName) {
        const link = `${API_URL}/${roomName}/${userName}/join-room`;
        await axios.get(link, config).then(response => this.responseMessage = response.data);
    }

    connectRoom(roomName) {
        this.socket = new SockJS("http://localhost:8080//register-websocket");
        this.stompClient = Stomp.over(this.socket);
        this.stompClient.connect(
            {},
            frame => {
                this.connected = true;
                console.log(frame);
                this.stompClient.subscribe(`/backend-response/messages/${roomName}`, tick => {
                    //here is response processed from backend
/*                    console.log("RESPONSE FROM BACKEND:");
                    console.log(tick);*/

                    let receivedMsg = JSON.parse(tick.body); // TUTAJ można zrobić switcha i w zależności od odpowiedzi coś tam robić

                    this.receivedMessages.push(JSON.parse(tick.body).message);
                });
            },
            error => {
                console.log(error);
                this.connected = false;
            })
    }

    sendSelectedCard(){
        //selectedCard schema = {id: "1", value: "5"}
        console.log("Send message: " + this.sendMessage);
        if(this.stompClient && this.stompClient.connected){
/*            const msgToParse = { message: msg};
            this.stompClient.send(`/backend-request/${this.roomName}/{${this.userName}/select-card`, JSON.stringify(msgToParse), {});*/

        }
    }

    sendCreatedTask(){}

    sendCreatedUserStory(){}

    sendNextGame(){}
}