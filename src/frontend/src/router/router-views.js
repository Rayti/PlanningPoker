import HomeView from "../views/home-view.vue";
import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    {
        path: "/",
        name: "home",
        component: HomeView
    }

];


const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
})

export default router