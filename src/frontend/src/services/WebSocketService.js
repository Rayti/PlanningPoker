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

                    let receivedMsg = JSON.parse(tick.body); // TUTAJ można zrobić switcha i w zależności od odpowiedzi coś tam robić
                    switch (receivedMsg.type) {
                        case "GameResultMessage":
                            this.store.dispatch("addResults", receivedMsg.selectedCards);
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

    addNewUserStory(roomName, userName, storyId, storyName){
        if (this.stompClient && this.stompClient.connected) {
            console.log("I've added a story");
            let arg = {
                type: "StoryDescriptionMessage",
                id: storyId,
                description: storyName
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
}