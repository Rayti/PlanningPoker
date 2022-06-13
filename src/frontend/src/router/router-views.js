import {createRouter, createWebHistory} from 'vue-router';

const routes = [
    {
        path: "/",
        name: "home",
        component: () => import(/* webpackChunkName: "about" */ '../views/HomeView.vue')
    },
    {
        path: "/game/:roomName",
        name: "game",
        component: () => import(/* webpackChunkName: "about" */ '../views/GameView.vue')
    },
    {
        path: "/policy",
        name: "policy",
        component: () => import('../views/PersonalDataPolicy')
    },
    {
        path: "/history",
        name: "history",
        component: () => import('../views/HistoryView')
    },
];


const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
})

export default router