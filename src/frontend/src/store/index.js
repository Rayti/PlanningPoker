import {createStore} from 'vuex'
const getDefaultState = () => {
    return {
        roomName: "",
        userName: "",
        isHost: false,
        currentGameResult: "",
        stories: [],
        currentStory:{},
        sessionId: "",
        mySelectedCard: "",
        isSelectedCardConfirmed: false,
        otherPlayers: []
    }
}
const store = createStore({

    state: getDefaultState(),
    getters: {
        getStoriesAll (state) {
            return state.stories;
        },
        getStory: (state) => (id) =>{
            let findEl = state.stories.find((x) => x.id == id);
            return findEl
        },
        getStoryTasks: (state) => (id) =>{
            let findEl = state.stories.find((x) => x.id == id);
            return (findEl.tasks.length != 0) ? findEl.tasks : []
        }
    },
    mutations: {
        resetState (state) {
            Object.assign(state, getDefaultState())
        },
        setUserName(state, userName){
          state.userName = userName;
        },
        setSessionId(state, sessionId){
            state.sessionId = sessionId;
        },
        setRoomName (state, roomName) {
            state.roomName = roomName;
        },

        addStory (state, story) {
            if(story.id !== undefined && typeof story.description == 'string' ) {
                state.stories.push({
                    id: story.id,
                    description: story.description,
                    tasks: story.tasks,
                    lastEstimation: ""
                })
            }
        },
        addTask(state, arg){
            const newTask = {
                id: arg.id,
                description: arg.description
            }
            let story = this.getters.getStory(arg.storyId);
            story.tasks.push(newTask);
        },
        addTaskToStoryInside(state, arg){
            let story = this.getters.getStory(arg.storyID);
            story.tasks.push(arg.task);

        },
        deleteTask(state, arg){
            let tasks = this.getters.getStoryTasks(arg.storyId);
            const taskToDelete = tasks.find((x) => x.id == arg.id);
            const index = tasks.indexOf(taskToDelete);
            tasks.splice(index, 1);
        },
        deleteStory(state, storyId){
            let story = this.getters.getStory(storyId);
            const index = state.stories.indexOf(story);
            state.stories.splice(index,1);
            if(storyId==state.currentStory.id){
                state.currentStory={};
            }

        },
        updateStory(state, arg) {
            let story = this.getters.getStory(arg.storyId);
            story.description = arg.description;

        },
        setCurrentStory(state,payload){
            if(payload) {
                state.currentStory = this.getters.getStory(payload.id);
            }
 
        },
        resetStory(state) {
            const index = state.stories.findIndex(story => story.id === state.currentStory.id);
            if (index !== -1) {
                state.stories[index].lastEstimation = state.currentGameResult;
            }
            state.currentStory = {};
        },

		setBasicInformation (state, payload) {
            state.roomName = payload.roomName
            state.isHost = payload.isHost
        },
        setCardSelection(state, payload) {
            state.mySelectedCard = payload.selectedCard;
            state.isSelectedCardConfirmed = payload.confirmation;
        },
        setCards(state, payload) {
            console.log("here",payload)
            const filteredCards = payload.selectedCards.filter(card => card.userName !== state.userName)
            for (let i = 0; i < filteredCards.length; i++) {
                const index = state.otherPlayers.findIndex(player => player.userName === filteredCards[i].userName);
                if (index !== -1) {
                    state.otherPlayers[index] = filteredCards[i];
                }
            }
            // console.log(filteredCards)
            // state.otherPlayersCards = filteredCards
        },
        setCurrentGameResult(state, payload) {
            state.currentGameResult = payload.gameAvgValue
        },
        addPlayer(state, payload) {
            state.otherPlayers = [ ...state.otherPlayers, { userName: payload }]
        },
        addPlayers(state, payload) {
            const players = payload.filter(player => player.userName !== state.userName).map(player => { return { userName: player.userName }})
            state.otherPlayers = [ ...state.otherPlayers, ...players ]
        },
        deletePlayer(state, payload) {
            const players = state.otherPlayers.filter(player => player.userName !== payload);
            state.otherPlayers = players
        },
        cleanStore(state) {
                state.roomName = "";
                state.isHost = false;
                state.stories = [];
                state.otherPlayers = [];
                state.currentGameResult = "";
                state.mySelectedCard = "";
                state.isSelectedCardConfirmed = false;
        },
        removeCards(state) {
            const players = state.otherPlayers.map(player => { return { userName: player.userName }});
            state.otherPlayers = players;
        },
        removeGameResult(state) {
            state.currentGameResult = ""
        },
        removeSelection(state) {
            state.mySelectedCard = "";
            state.isSelectedCardConfirmed = false;
        }
    },
    actions: {
        resetState ({ commit }) {
            commit('resetState')
        },
        setUserName({commit}, payload){
            commit('setUserName', payload);
        },
        setSessionId({commit}, payload){
            commit("setSessionId", payload);
        },
        setRoomName (context, roomName) {
            context.commit('setRoomName', roomName)
        },
		setBasicInformation ({ commit }, payload) {
            commit("setBasicInformation", payload)
        },
        addResults({ commit }, payload) {
            commit("setCards", payload)
            commit("setCurrentGameResult", payload)
        },
        addPlayer({ commit }, payload) {
            commit("addPlayer", payload)
        },
        addPlayers({ commit }, payload) {
            commit("addPlayers", payload)
        },
        deletePlayer({ commit }, payload) {
            commit("deletePlayer", payload);
        },
        removeResults({ commit }) {
            commit("removeCards");
            commit("resetStory");
            commit("removeGameResult");
            commit("removeSelection");
        },
        changeCurrentStory({ commit }, payload) {
            commit("setCurrentStory", payload)
        },
        addStory({ commit }, payload){
            commit("addStory", payload)
        },
        deleteStory({ commit }, payload){
                commit("deleteStory", payload)
        },
        updateStory({ commit }, payload){
            commit("updateStory", payload)
            },
        addTask({ commit }, payload){
            commit("addTask", payload)
        },
        deleteTask({ commit }, payload){
            commit("addTask", payload)
        },
        cleanStore({commit}) {
            commit("cleanStore");
        },
        setCardSelection({ commit }, payload) {
            commit("setCardSelection", payload);
        }
    },
    modules: {
    }
})

export default store