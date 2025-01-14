<script setup lang="ts">
import {getAllAccounts} from "@/composables/useAdmin";

const {accounts} = getAllAccounts();
</script>

<template>
  <div>
    <h2>Account lijst</h2>
    <table>
      <thead>
      <tr>
        <th>Id</th>
        <th>Gebruikersnaam</th>
        <th>E-mail</th>
        <th>Account type</th>
        <th>Status</th>
        <th>Beheren</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="account in accounts" :key="account.id">
        <td>{{ account.id }}</td>
        <td>{{ account.userName }}</td>
        <td>{{ account.email }}</td>
        <td>{{ account.role }}</td>
        <td>{{ account.banned
            ? "Verbannen"
            : "Actief" }}
        </td>
        <td class="manage-buttons">
          <button>Verbannen</button>
<!--          <button>Verwijderen</button>-->
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>

table {
  width: 100%;
  border-collapse: collapse;
}

table, th, td {
  border: 1px solid var(--light-primary);

}

th, td {
  padding: 15px;
  text-align: left;
}

th {
  background-color: var(--button);
  color: var(--light-primary);
  font-weight: 700;
  font-size: 1.1rem;
}

.manage-buttons {
  display: flex;
  gap: 10px;
  align-items: center;
}

.manage-buttons button {
  margin: 0;
}

table {
  margin-top: 20px;
}
</style>