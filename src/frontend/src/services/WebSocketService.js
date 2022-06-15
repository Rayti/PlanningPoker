import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

const SOCKET_API_URL = "/backend-socket-request/api/poker";

export class WebSocketService {


    constructor(store) {
        this.store = store;
    }

    createWebSocketConnection(roomName) {
        this.socket = new SockJS("http://localhost:8080/register-websocket");
        this.stompClient = Stomp.over(this.socket);
        this.stompClient.connect(
            {},
            frame => {
                console.log("frame", frame);
                this.connected = true;
                this.stompClient.subscribe(`/backend-socket-response/${roomName}`, tick => {
                    //here is response processed from backend
                    console.log("RESPONSE FROM BACKEND:");
                    console.log(tick);

                    let receivedMsg = JSON.parse(tick.body);
                    switch (receivedMsg.type) {
                        case "GameResultMessage":
                            this.store.dispatch("addResults", receivedMsg);
                            break;
                        case "CurrentGameStoryMessage":
                            this.store.dispatch("changeCurrentStory", receivedMsg.storyMessage);
                            break;
                        case "CreateStoryMessage":
                            this.store.dispatch("addStory", receivedMsg);
                            break;
                        case "DeleteStoryMessage":
                            this.store.dispatch("deleteStory", receivedMsg.storyId);
                            break;
                        case "StoryDescriptionUpdateMessage":
                            this.store.commit('updateStory', receivedMsg);
                            break;
                        case "CreateTaskMessage":
                            this.store.commit('addTask', receivedMsg);
                            break;
                        case "DeleteTaskMessage":
                            this.store.commit('deleteTask', receivedMsg)
                            break;
                        case "NextGameMessage":
                            this.store.dispatch("removeResults");
                            break; 
                        case "JoinedRoomMessage":
                            if (receivedMsg.userName === this.store.state.userName) {
                                break;
                            }
                            this.store.dispatch("addPlayer", receivedMsg.userName);
                            break;
                    }
                    console.log(receivedMsg);
                    // this.receivedMessages.push(JSON.parse(tick.body).message);
                });
            },
            error => {
                console.log(error);
                this.connected = false;
            }
        );
    }

    disconnect() {
        if (this.stompClient) {
            this.stompClient.disconnect();
        }
        this.connected = false;
    }

    joinExistingRoom(roomName, userName) {
        console.log("jestem tu");
        if (this.stompClient && this.stompClient.connected) {
            console.log("I've joined an exisitng room");
            this.stompClient.send(`${SOCKET_API_URL}/${roomName}/${userName}/join-room-ws`);
        }
    }
    selectCard(roomName, userName, selectedCard) {
        if (this.stompClient && this.stompClient.connected) {
            console.log("I've selected a card");
            this.stompClient.send(`${SOCKET_API_URL}/${roomName}/${userName}/select-card/0/${selectedCard}`);
        }
    }

    finishGame(roomName, userName) {
        if (this.stompClient && this.stompClient.connected) {
            console.log("I've ended the game");
            this.stompClient.send(`${SOCKET_API_URL}/${roomName}/${userName}/finish-game`);
        }
    }

    goToNextGame(roomName, userName) {
        if (this.stompClient && this.stompClient.connected) {
            console.log("Moved to the next game");
            this.stompClient.send(`${SOCKET_API_URL}/${roomName}/${userName}/next-game`);
        }
    }

    addNewUserStory(roomName, userName, storyId, storyName,tasks){
        if (this.stompClient && this.stompClient.connected) {
            let taskMessageList=[];
            for(let i=0;i<tasks.length;i++){
                taskMessageList.push({
                    type:"TaskMessage",
                    id: tasks[i].id,
                    description:tasks[i].description
                })
            }
            console.log("I've added a story");
            let arg = {
                type: "CreateStoryMessage",
                id: storyId,
                description: storyName,
                tasks: taskMessageList
            }
            this.stompClient.send(`${SOCKET_API_URL}/${roomName}/${userName}/story-create`, JSON.stringify(arg));
        }
    }

    chooseUserStory(roomName, userName, storyId){
        if (this.stompClient && this.stompClient.connected) {
            console.log("I've chosen story");
            this.stompClient.send(`${SOCKET_API_URL}/${roomName}/${userName}/${storyId}choose-story`);
        }
    }

    updateUserStory(roomName, userName, storyId, storyNewDescription){
        if (this.stompClient && this.stompClient.connected) {
            console.log("I've chosen story");
            let arg = {
                type: "StoryDescriptionMessage",
                id: storyId,
                description: storyNewDescription
            }
            this.stompClient.send(`${SOCKET_API_URL}/${roomName}/${userName}/${storyId}/story-update/description`,
                JSON.stringify(arg));
        }
    }

    deleteUserStory(roomName, userName, storyId){
        if (this.stompClient && this.stompClient.connected) {
            console.log("I've chosen story");
            this.stompClient.send(`${SOCKET_API_URL}/${roomName}/${userName}/${storyId}/story-delete`);
        }
    }

    addNewTask(roomName, userName, storyId,taskId, taskName){
        if (this.stompClient && this.stompClient.connected) {

            console.log("I've added a story");
            let arg = {
                type: "CreateTaskMessage",
                id: taskId,
                description: taskName
            }
            this.stompClient.send(`${SOCKET_API_URL}/${roomName}/${userName}/${storyId}/task-create`, JSON.stringify(arg));
        }
    }

    deleteTask(roomName, userName, storyId, taskId){
        if (this.stompClient && this.stompClient.connected) {
            console.log("I've chosen story");
            this.stompClient.send(`${SOCKET_API_URL}/${roomName}/${userName}/${storyId}/${taskId}/task-delete`);
        }
    }
}