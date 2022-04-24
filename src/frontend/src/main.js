
import App from './App.vue'
import "./plugins/bootstrap-vue";
import Vue from 'vue'
import router from "@/router/router-views";




Vue.config.productionTip = false

/* eslint-disable no-new */
// const app = new Vue({
//     el: '#app',
//     components: { App },
//     template: '<App/>'
// })
// app.use(router)
// app.mount('#app')
// // Make BootstrapVue available throughout your project
Vue.config.productionTip = false;

new Vue({
    router,
    render: h => h(App)
}).$mount("#app");