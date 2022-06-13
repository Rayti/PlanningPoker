import {createStore} from 'vuex'
const getDefaultState = () => {
    return {
        roomName: '',
        stories: [],
        userName: '',
        currentStory:{},
        isHost: false,
        // stories: {},
        otherPlayersCards: []
    }
}
const store = createStore({

    state: getDefaultState(),
    //     {
    //     roomName: '',
    //     stories: [],
	// 	userName: '',
    //     currentStory:{},
    //   isHost: false,
    //   // stories: {},
    //   otherPlayersCards: []
    // },
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
            // Merge rather than replace so we don't lose observers
            // https://github.com/vuejs/vuex/issues/1118
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
       setCurrentStory(state,payload){
           if(payload) {
               state.currentStory = this.getters.getStory(payload.id);
           }

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
        commit("changeBasicInformation", payload)
      },
      addResults({ commit }, payload) {
        commit("setCards", payload)
      },
      removeResults({ commit }) {
        commit("removeCards")
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
      },
        cleanStore({commit}) {
            commit("cleanStore");
        }
    },
    modules: {
    }
})

export default store