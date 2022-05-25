import { createStore } from 'vuex'

const store = createStore({
    state: {
        roomName: '',
        stories: [],
		userName: '',
      isHost: false,
      stories: {},
      otherPlayersCards: []
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
        setRoomName (state, roomName) {
            state.roomName = roomName
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
        updateStory(state, story) {
            let id = story.id;
            let completed = story.completed;
            let name = story.name;
            let findEl = this.getters.getStory(story.id);
            if(findEl.id != null) {
                if(completed !== undefined) {
                    findEl.completed = completed;
                }
                if(name !== undefined) {
                    findEl.name = name;
                }
            }
            else {
                console.log(`To Do List Item ${id} couldn't be found`);
            }
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
      removeCards(state) {
        state.otherPlayersCards = []
      },
    },
    actions: {
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
      }
    },
    modules: {
    }
})

export default store