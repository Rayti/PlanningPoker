import HomeView from "../views/HomeView.vue";
import { createRouter, createWebHistory } from 'vue-router'
import GameView from "@/views/GameView";

const routes = [
    {
        path: "/",
        name: "home",
        component: HomeView
    },
    {
        path: "/game",
        name: "GameView",
        component: GameView
    },

];


const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
})

export default router