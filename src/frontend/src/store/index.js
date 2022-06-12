import {createStore} from 'vuex'

const store = createStore({
    state: {
        roomName: '',
        stories: [],
		userName: '',
        isHost: false,
        // stories: {},
        otherPlayersCards: [],
        sessionId: ''
    },
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
            if(story.id !== undefined && typeof story.name == 'string' ) {
                state.stories.push({
                    id: story.id,
                    name: story.name,
                    tasks: story.tasks,
                })
            }
        },
        addTaskToStory(state, arg){
            let story = this.getters.getStory(arg.storyID);
            story.tasks.push(arg.task);
        },
        addTaskToStoryInside(state, arg){
            let story = this.getters.getStory(arg.storyID);
            story.tasks.push(arg.task);

        },
        deleteTaskStory(state, arg){
            let tasks = this.getters.getStoryTasks(arg.storyID);
            const index = tasks.indexOf(arg.task);
            tasks.splice(index, 1);
        },
        deleteStory(state, storyId){
            let story = this.getters.getStory(storyId);
            const index = state.stories.indexOf(story);
            state.stories.splice(index,1);

        },
        updateStory(state, arg) {
            let story = this.getters.getStory(arg.storyId);
            story.name = arg.newName;

        },
		changeBasicInformation (state, payload) {
        state.roomName = payload.roomName
        state.userName = payload.userName
        state.isHost = payload.isHost
      },
      setCards(state, payload) {
        const filteredCards = payload.filter(card => card.userName !== state.userName)
        console.log(filteredCards)
        state.otherPlayersCards = filteredCards
      },
      cleanStore(state) {
            state.roomName = null;
            state.userName = null;
            state.isHost = null;
            state.stories = [];
            state.otherPlayersCards = [];
            state.sessionId = '';
      },
      removeCards(state) {
        state.otherPlayersCards = []
      },
    },
    actions: {
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
        commit("changeBasicInformation", payload)
      },
      addResults({ commit }, payload) {
        commit("setCards", payload)
      },
      removeResults({ commit }) {
        commit("removeCards")
      },
        cleanStore({commit}) {
            commit("cleanStore");
        }
    },
    modules: {
    }
})

export default store