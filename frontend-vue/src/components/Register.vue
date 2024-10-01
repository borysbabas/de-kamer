<script setup lang="ts">
import {onMounted, ref} from "vue"
const postMethod=async ()=>{
  try{
    const response= await fetch("http://localhost:3001/register",{
      method: "POST",
      headers:{
        "Content-Type" : "application/json"
      },
      body: JSON.stringify(formData.value)
    });
    if (response.ok){
      const result= await response.json();
      console.log("data succesvol verstuurd", result)

    }else{
      console.error("data niet succesvol verstuurd.",reponse.statusText)
    }
  }catch(error){
    console.log(error)

  }

}
const formData=ref({
  email:"",
  username:"",
  dateOfBirth:"",
  password:"",

})
</script>

<template>
  <form @submit.prevent>
    <div class="wrapper">
      <label>Enter your personal information.</label>
      <input v-model="formData.email" placeholder="E-mail">
      <input v-model="formData.username" placeholder="Username">
      <input v-model="formData.dateOfBirth" type="date" placeholder="date of birth.">
      <input v-model="formData.password" type="password" placeholder="Password">
      <button type="submit" @click= "postMethod">Submit</button>
    </div>
  </form>

</template>

<style scoped>
form{
  display:flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
.wrapper{
  margin-top: 50px;
  display: flex;
  flex-direction: column;
  padding: 32px 32px;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  width: 30%;
}
input{
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  margin-bottom: 20px;
}
label{
  display: block;
  margin-bottom: 5px;
  color: #555;
}
button{
  width: 100%;
  padding: 10px;
  background-color: #444;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
}
a{
  margin-top:8px;
  color: blue;

}

</style>