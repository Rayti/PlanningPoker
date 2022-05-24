import { createStore } from 'vuex'

const store = createStore({
    state: {
      roomName: '',
      stories: {},
    },
    getters: {
    },
    mutations: {
      setRoomName (state, roomName) {
        state.roomName = roomName
      },
    },
    actions: {
      setRoomName (context, roomName) {
        context.commit('setRoomName', roomName)
      },
    },
    modules: {
    }
  })
  
  export default store