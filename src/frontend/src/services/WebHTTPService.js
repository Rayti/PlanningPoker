import axios from "axios";


const API_URL = "http://localhost:8080/api/poker";
/*const config ={headers: {"Content-type": "application/json",
        'Access-Control-Allow-Credentials':true}}*/
const config = { headers: { 'Access-Control-Allow-Credentials': true } }

export class WebHTTPService {
    store = null;
    connected = false;
    // receivedMessages = [];
    // sendMessage = null;
    // connected = false;
    // roomName = "";
    // userName = "";
    // msgv2 = [];
    // responseMessage = {message: "", success: false};
    // webService = null;

    constructor(store) {
        this.store = store;
    }


    async createRoomRequest(roomName, userName) {
        const link = `${API_URL}/${roomName}/${userName}/create-room`;
        try {
            const response = await axios.get(link, config);
            return response.data;
        } catch (error) {
            console.log(error.message); 
            return null;
        }
    }

    async canJoinRoomRequest(roomName, userName) {
        console.log(this.store.state.userName);
        const link = `${API_URL}/${roomName}/${userName}/can-join-room`;
        try {
            const response = await axios.get(link, config);
            return response.data;
        } catch (error) {
            console.log(error.message); 
            return null;
        }
    }

    async joinExistingRoomRequest(roomName, userName) {
        const link = `${API_URL}/${roomName}/${userName}/join-room`;
        try {
            const response = await axios.get(link, config);
            return response.data;
        } catch (error) {
            console.log(error.message); 
            return null;
        }
    }

    async leaveRoomRequest(roomName, userName) {
        const link = `${API_URL}/${roomName}/${userName}/leave-room`;
        try {
            const response = await axios.get(link, config);
            return response.data;
        } catch (error) {
            console.log(error.message); 
            return null;
        }
    }

    async deleteRoomRequest(roomName, userName) {
        const link = `${API_URL}/${roomName}/${userName}/delete-room`;
        try {
            const response = await axios.get(link, config);
            return response.data;
        } catch (error) {
            console.log(error.message); 
            return null;
        }
    }

    // selectCard(roomName, userName, selectedCard) {
    //     if (this.stompClient && this.stompClient.connected) {
    //         console.log("I've selected a card");
    //         this.stompClient.send(`/backend-socket-request/api/poker/${roomName}/${userName}/select-card/0/${selectedCard}`);
    //     }
    // }
    //
    // finishGame(roomName, userName) {
    //     if (this.stompClient && this.stompClient.connected) {
    //         console.log("I've ended the game");
    //         this.stompClient.send(`/backend-socket-request/api/poker/${roomName}/${userName}/finish-game`);
    //     }
    // }
    //
    // goToNextGame(roomName, userName) {
    //     if (this.stompClient && this.stompClient.connected) {
    //         console.log("Moved to the next game");
    //         this.stompClient.send(`/backend-socket-request/api/poker/${roomName}/${userName}/next-game`);
    //     }
    // }

    async getIntoCurrentGame(roomName, userName) {
        const link = `${API_URL}/${roomName}/${userName}/game-data`;
        try {
            const response = await axios.get(link, config);
            const data = await response.data;
            console.log(data);
            for(let i=0;i< data.stories.length;i++){
                this.store.dispatch("addStory", data.stories[i]);
            }
            this.store.dispatch("changeCurrentStory", data.currentStory)
            console.log("getintocurrent");
            console.log(data.users);
            this.store.dispatch("addPlayers", data.users);
            return data;
        } catch (error) {
            console.log(error.message); 
            return null;
        }
    }

    //################# START OF USER METHODS #################
    async registerUserAccount(userName, password) {
        const link = `${API_URL}/user/register/${userName}/${password}`;
        try {
            return await axios.get(link, config);
        } catch (error) {
            console.log(error.message);
            return null;
        }
    }

    async getUserGameHistory(userName){
        const link = `${API_URL}/user/game-history/${userName}`;
        try {
            return await axios.get(link, config);
        }catch (error) {
            console.log(error.message);
            return null;
        }
    }


    async userAccountExist(userName){
        //TODO

    }

    async deleteUserAccount(userName){
        //TODO
    }

    async logIn(userName, password) {
        const link = `${API_URL}/user/log-in/${userName}/${password}`;
        try {
            return await axios.get(link, config);
        } catch (error) {
            console.log(error.message);
            return null;
        }
    }

    async logOut() {
        const userName = this.store.state.userName;
        const sessionId = this.store.state.sessionId;
        const link = `${API_URL}/user/log-out/${userName}/${sessionId}`;
        try {
            return await axios.get(link, config);
        } catch (error){
            console.log(error.message)
            return null;
        }
    }


    //################# END OF USER METHODS #################

    // test(msg){
    //     const link = `${API_URL}/test`
    //     axios.get(link, config).then(response => this.msgv2.push(response.data.message))

    // }

    // addUser(userName) {
    //     const url = `${API_URL}/test`;
    //     axios.get(url, config).then(response => response.data.message);
    // }

    // send(msg){
    //     console.log("Send message: " + this.sendMessage);
    //     if(this.stompClient && this.stompClient.connected){
    //         const msgToParse = { message: msg};
    //         this.stompClient.send("/backend-request/chat", JSON.stringify(msgToParse), {});

    //     }
    // }

    // tickleConnection(){
    //     this.connected ? this.disconnect() : this.connect();
    // }   
}