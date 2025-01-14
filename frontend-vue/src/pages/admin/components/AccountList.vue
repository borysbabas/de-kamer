<script setup lang="ts">
import {getAllAccounts} from "@/composables/useAdmin";
import type {AdminViewAccount} from "@/types/Account";
import {useAccount} from "@/composables/useAccount";

const {accounts, banAccount, unbanAccount} = getAllAccounts();
const {adminAccount} = useAccount();

function banAccountHandler(account: AdminViewAccount) {
  if (!confirm(`Weet je zeker dat je ${account.userName} wilt ${account.banned ? "deblokkeren" : "blokkeren"}?`)) {
    return;
  }
  if (account.banned) {
    unbanAccount(account.id);
    return;
  }
  banAccount(account.id);
}

</script>

<template>
  <div>
    <h2>Account lijst</h2>
    <div class="account-list">
      <div class="account-row heading">
        <div>Id</div>
        <div>Gebruikersnaam</div>
        <div>E-mail</div>
        <div>Account type</div>
        <div>Status</div>
        <div>Beheren</div>
      </div>
      <div v-for="account in accounts" :key="account.id" class="account-row">
        <div>{{ account.id }}</div>
        <div>{{ account.userName }}</div>
        <div>{{ account.email }}</div>
        <div>{{ account.role }}</div>
        <div>{{ account.banned ? "Geblokkeerd" : "Actief" }}</div>
        <div class="manage-buttons">
          <button @click="banAccountHandler(account)" :class="{banned: account.banned}"
          v-if="adminAccount && account.id !== adminAccount.id">
            {{ account.banned ? "Deblokkeren" : "Blokkeren" }}
          </button>
<!--          <button>-->
<!--            Wijzigen-->
<!--          </button>-->
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>

.account-list {
  display: flex;
  flex-direction: column;
  width: 100%;

}

.account-row {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  gap: 1rem;
  padding: 10px;
  align-items: center;
  border-bottom: 1px solid var(--light-primary);
}

.account-row.heading {
  font-weight: bold;
  font-size: 1.2rem;
  background-color: var(--button);
}

.manage-buttons button {
  margin: 0;
  width: 100%;
}

.manage-buttons button.banned {
  background-color: var(--button);
}

.manage-buttons button.banned:hover {
  background-color: var(--button-hover);
}


</style>