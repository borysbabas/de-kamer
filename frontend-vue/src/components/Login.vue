<script setup lang="ts">

</script>

<template>
  <form>
    <div class="wrapper">
    <label>Enter your credentials.</label>
    <input placeholder="E-mail or username">
    <input placeholder="Password" type="password">
      <button>Log in</button>
      <a href="">Create account.</a>
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
color: white; /* Tekstkleur van de knop */
border: none;
border-radius: 4px;
cursor: pointer; /* Cursor verandert naar pointer */
font-size: 16px;
}
a{
  margin-top:8px;
  color: blue;

}

</style>