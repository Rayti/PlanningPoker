import { createStore } from 'vuex'

const store = createStore({
    state: {
      roomName: '',
      userName: '',
      isHost: false,
      stories: {},
      otherPlayersCards: [],
    },
    getters: {
    },
    mutations: {
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
      }
    },
    actions: {
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