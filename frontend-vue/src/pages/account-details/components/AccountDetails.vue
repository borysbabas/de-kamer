<script setup lang="ts">

import {computed, inject, onMounted, ref} from "vue";

/**
 * api is a constant that is dynamic with the deployment
 */
const api = import.meta.env.VITE_API_URL
const sessionService: any = inject("sessionService")


/**
 * decode the jwt token for its userId
 */
const userID = JSON.parse(atob(sessionService.getTokenFromBrowserStorage().split(".")[1])).jti;

/**
 * private variables
 */
//variables from the API call
const fetchedEmail = ref("");
const fetchedUsername = ref("")
const fetchedDateOfBirth = ref("")
// userInputPopUp checks whether to show to variable or not
const userInputPopUp = ref("");
const requiredfetchedData = ref<string>("")
//checks whether popup is enabled
let isPopUpVisible = ref(false)
const selectedSubject = ref("")
const selectedSubjectTranslated = ref("")
const changeEmailString = computed(() => {
  return `verander uw ${selectedSubject.value}`
})

/**
 * Handles all logic related to updating the required account details.
 * Checks the required value,sends an HTTP request and checks if it was successful or not.
 */
async function updateAccountCredentials() {
  if (selectedSubject.value === "email") {
    selectedSubjectTranslated.value = "email"
  }
  if (selectedSubject.value === "gebruikersnaam") {
    selectedSubjectTranslated.value = "userName"
  } else if (selectedSubject.value === "geboortedatum") {
    selectedSubjectTranslated.value = "dateOfBirth"
  } else if (selectedSubject.value === "wachtwoord") {
    selectedSubjectTranslated.value = "password"
  }
  try {
    const response = await fetch(api + `/account/update/${userID}`, {
          method: "PUT",
          headers: {
            "Content-type": "application/json"
          },
          body: JSON.stringify({
            field: selectedSubjectTranslated.value,
            value: userInputPopUp.value
          })
        }
    )
    if (response.ok) {
      alert(`${selectedSubject.value} is succesvol bijgewerkt`)
      window.location.reload()
    } else {
      console.error("Fout bij het updaten van gegevens", response.status)
    }
  } catch (error) {
    console.error(error)
  }
}


/**
 * function to delete a user once the prompt is selected.
 * note: the fetch is carefully put together altering the fetch contents might result in error.must
 * be prefixed with intended Bearer<space> prefix.
 *
 */
async function deleteAccount() {
  const confirmed = window.confirm("Weet je dit zeker? je staat op het punt je account te verwijderen.")
  if (confirmed) {
    try {
      const response = await fetch(api + `/account/delete`, {
        method: "DELETE",
      })
      if (response.ok) {
        sessionService.clearUserName()
        sessionService.clearToken()
        window.location.reload()
      }
    } catch (e) {
      console.log("error", e)

    }
  }
}

/**
 * handles the button click
 * @param selectedSubjectParam defines which part of the account is being changed
 */
function handleBtnClick(selectedSubjectParam: any) {
  isPopUpVisible.value = true
  switch (selectedSubjectParam) {
    case "email":
      selectedSubject.value = "email";
      requiredfetchedData.value = fetchedEmail.value;
      break;
    case "userName":
      selectedSubject.value = "gebruikersnaam";
      requiredfetchedData.value = fetchedUsername.value;
      break;
    case "password":
      selectedSubject.value = "wachtwoord";
      break;
    case "dateOfBirth":
      selectedSubject.value = "geboortedatum";
      requiredfetchedData.value = fetchedDateOfBirth.value;
      break;
  }

}

/**
 * When loading the page shows the required credentials for the specific user by its unique identifier.
 */
onMounted(async () => {
  try {
    const response = await fetch(api + `/account/${userID}`)
    if (response.ok) {
      const userData = await response.json();
      fetchedEmail.value = userData.email;
      fetchedUsername.value = userData.userName;
      fetchedDateOfBirth.value = userData.dateOfBirth;
    }
  } catch (error) {
    console.error("Error fetching user details", error)
  }
})
</script>

<template>
  <section v-if="isPopUpVisible" class="popup">
    <div class="popup-wrapper">
      <div class="popup-container">
        <button @click="isPopUpVisible=false" class="close-btn">X</button>
        <h3 class="popup-p">{{ selectedSubject != "wachtwoord" ? "Huidige" : "" }} {{ selectedSubject }} :
          {{ selectedSubject != "wachtwoord" ? requiredfetchedData : "" }} </h3>
        <input required v-model="userInputPopUp" class="popup-input" :placeholder="changeEmailString">
        <button @click="updateAccountCredentials">bevestigen</button>
      </div>
    </div>
  </section>
  <section class="account-details">
    <div class="wrapper">
      <h1>Account details</h1>
      <div class="container">
        <div class="box">
          email: {{ fetchedEmail }}
          <div class="box-button">
            <button @click="handleBtnClick('email')">wijzig</button>
          </div>
        </div>
        <div class="box">
          gebruikersnaam: {{ fetchedUsername }}
          <div class="box-button">
            <button @click="handleBtnClick('userName')">wijzig</button>
          </div>
        </div>
        <div class="box">
          wachtwoord: *********
          <div class="box-button">
            <button @click="handleBtnClick('password')">wijzig</button>
          </div>
        </div>
        <div class="box">
          geboortedatum: {{ fetchedDateOfBirth }}
          <div class="box-button">
            <button @click="handleBtnClick('dateOfBirth')">wijzig</button>
          </div>
        </div>
        <div class="box">
          <div class="delete-container">
            <button @click="deleteAccount" class="delete-btn">verwijder huidig account</button>
          </div>
        </div>
      </div>
    </div>
  </section>


</template>

<style scoped>
.popup-p {
  color: var(--dark-secondary);
  margin-bottom: 15px;
}

.popup-input {
  width: 80%;
  padding: 10px;
  margin-bottom: 15px;
  border: 1px solid var(--dark-secondary);
  border-radius: 4px;
  background-color: #fff;
  color: var(--dark-secondary);
}

.popup-container {
  background-color: var(--light-primary);

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100%;
}

.close-btn {
  position: absolute;
  top: 5px;
  right: 5px;
}

.popup {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  background-color: transparent;
  padding: 0;
  top: 0;
  z-index: 1;
  width: 100%;
  height: 100%;
}

.popup-wrapper {
  position: relative;
  width: 500px;
  height: 250px;
  background-color: white;
  color: black;
}

.box-button {
  margin-left: auto;
}

.delete-btn {
  background-color: #E74C3C;
}

.delete-container {
  display: flex;
  justify-content: center;
  align-items: center;
  margin: auto;
}

.account-details {
  display: flex;
  justify-content: center;
  align-items: center;
  height: calc(100vh - 56px);
  background-color: var(--dark-primary);
  color: var(--light-primary);
}

.wrapper {
  width: 90%;
  max-width: 600px;
  padding: 20px;
  background-color: var(--dark-secondary);
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
}

h1 {
  text-align: center;
  margin-bottom: 20px;
  font-size: 24px;
  color: var(--light-primary);
  border-bottom: 1px solid var(--light-primary);
  padding-bottom: 10px;
}

.container {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.box {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 15px;
  background-color: var(--dark-primary);
  border-radius: 5px;
  transition: background-color 0.3s ease, box-shadow 0.3s ease;
}


button {
  padding: 5px 10px;
  font-size: 14px;
  background-color: var(--light-primary);
  color: var(--dark-secondary);
  border-radius: 3px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  border: 1px solid;

}

button:hover {
  background-color: var(--dark-primary);
  color: var(--light-primary);
  border: 1px solid var(--light-primary);
}

.delete-btn {
  text-align: center;
}

.delete-btn {
  width: 100%;
  background-color: #ff4d4d;
  color: white;
  border: none;
}

.delete-btn:hover {
  background-color: #ff1a1a;
  color: white;
  border: none;
}

</style>