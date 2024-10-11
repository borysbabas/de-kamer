import './assets/reset.css'
import { createApp } from 'vue'
import App from "./App.vue"
import type {Router} from "vue-router";
import {createRouter, createWebHistory} from "vue-router";
import HomePage from "@/components/pages/HomePage.vue";
import LoginPage from "@/components/pages/LoginPage.vue";
import RegisterPage from "@/components/pages/RegisterPage.vue";

const router: Router = createRouter({
    history: createWebHistory(),
    routes: [
        {path: "/", component: HomePage, name: "Home"},
        {path: "/login", component: LoginPage, name: "Login"},
        {path: "/register", component: RegisterPage, name: "Register"},
    ]
})

createApp(App).use(router).mount('#app');