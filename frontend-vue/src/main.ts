import './assets/reset.css'
import {createApp} from 'vue'
import App from "./App.vue"
import type {Router} from "vue-router";
import {createRouter, createWebHistory} from "vue-router";
import HomePage from "@/pages/home/HomePage.vue";
import ResultsPage from "@/pages/results/ResultsPage.vue";
import LoginPage from "@/pages/login/LoginPage.vue";
import RegisterPage from "@/pages/register/RegisterPage.vue";
import CandidateDetailsPage from "@/pages/candidate-details/CandidateDetailsPage.vue";
import ForumPage from "@/pages/forum/ForumHome.vue";
import PartyDetailsPage from "@/pages/party-details/PartyDetailsPage.vue";
import TermsOfServicePage from "@/pages/terms-of-service/TermsOfServicePage.vue";
import PrivacyPolicyPage from "@/pages/privacy-policy/PrivacyPolicyPage.vue";
import SitemapPage from "@/pages/sitemap/SitemapPage.vue";

const router: Router = createRouter({
    history: createWebHistory(),
    routes: [
        {path: "/", component: HomePage, name: "Home"},
        {path: "/uitslagen", component: ResultsPage, name: "Results"},
        {path: "/forum", component: ForumPage, name: "Forum"},
        {path: "/forum/login", component: LoginPage, name: "Login"},
        {path: "/forum/register", component: RegisterPage, name: "Register"},
        {path: "/candidate/:candidateId", name: "CandidateDetails", component: CandidateDetailsPage, props: true},
        {path: '/party/:partyId', name: 'PartyDetails', component: PartyDetailsPage},
        {path: "/algemene-voorwaarden", name: "TermsOfService", component: TermsOfServicePage},
        {path: "/privacyverklaring", name:"PrivacyPolicy", component: PrivacyPolicyPage},
        {path: "/sitemap", name: "Sitemap", component: SitemapPage},
    ]
})

createApp(App).use(router).mount('#app');