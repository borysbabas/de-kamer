<script setup lang="ts">
import{ref, defineEmits} from  "vue"
import Login from "./Login.vue"
import Register from "@/components/Register.vue";

const state = ref(null)
const emit = defineEmits(["toggle-state"])
let loginStatus=ref(false)
const receiveLoginEmit=(status)=>{
  console.log(status)
  loginStatus.value=receiveLoginEmit(status)


}



const login=()=>{
  state.value=Login
  if(state.value){
    if(state.value!==null && state.value !== undefined){
      emit("toggle-state", true)
    }
  }else{
    console.log("error")
  }
}
const register=()=>{
  state.value=Register
  if(state.value){
  if(state.value!==null && state.value !== undefined){
    emit("toggle-state", true)

  }
  }else{
    console.log("error")
  }
}


</script>
<template>
  <div class="navigatie">
    <ul>
      <li id="Home"><a>Home</a></li>
      <li id="Home"><input id="search-bar" type="search" placeholder="Search..." v-model="searchQuery"></li>
      <li v-on:click="login"><a>Login</a></li>
      <li v-on:click="register"><a>Register</a></li>
      </ul>
  </div>
 <component :is="state"></component>

</template>

<style>

.navigatie {
  background-color: #333;
  padding: 10px 20px;
}
#Home{
  margin-right:auto;
}
 #search-bar{
  width: 800px;
  height: 0px;
  padding: 10px;
  font-size: 16px;
  border: 2px solid #ccc;
  border-radius: 20px;
  outline: none;
  transition: all 0.3s ease;
}
ul {
  list-style: none;
  margin: 0;
  padding: 0;
  display: flex;
  justify-content: flex-end;
  align-items: center;
}

li {
  margin: 0 20px;
  padding: 10px 15px;
}

a {
  cursor: pointer;
  text-decoration: none;
  color: white;
  font-size: 1.1rem;
  font-weight: bold;
  transition: color 0.3s ease;
}

a:hover {
  color: darkgray;
}

.navigatie ul li:hover {
  background-color: #444;
  border-radius: 8px;
}


@media (max-width: 768px) {
  ul {
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-start;
  }

  li {
    margin: 10px 0;
  }
}


</style>