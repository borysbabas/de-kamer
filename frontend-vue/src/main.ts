import './assets/reset.css'
import { createApp } from 'vue'
import App from "./App.vue"
import type {Router} from "vue-router";
import {createRouter, createWebHistory} from "vue-router";
import HomePage from "@/components/pages/HomePage.vue";
import ResultsPage from "@/components/pages/ResultsPage.vue";
import LoginPage from "@/components/pages/LoginPage.vue";
import RegisterPage from "@/components/pages/RegisterPage.vue";
import AboutPage from "@/components/pages/CandidateDetailsPage.vue";
import CandidateDetailsPage from "@/components/pages/CandidateDetailsPage.vue";

const router: Router = createRouter({
    history: createWebHistory(),
    routes: [
        {path: "/", component: HomePage, name: "Home"},
        {path: "/uitslagen", component: ResultsPage, name: "Results"},
        {path: "/log-in", component: LoginPage, name: "Login"},
        {path: "/login", component: LoginPage, name: "Login"},
        {path: "/register", component: RegisterPage, name: "Register"},
        {path:"/candidate/:candidateID", name:"CandidateDetails", component:CandidateDetailsPage, props:true}
    ]
})

createApp(App).use(router).mount('#app');