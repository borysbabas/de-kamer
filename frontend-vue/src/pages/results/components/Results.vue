<script setup lang="ts">
</script>

<template>
  <section class="first main">
    <h1>Uitslagen</h1>
    <div class="searchbar-container">
      <div class="searchbar-wrapper">
        <svg xmlns="http://www.w3.org/2000/svg" class="search-icon" viewBox="0 0 512 512">
          <path d="M416 208c0 45.9-14.9 88.3-40 122.7L502.6 457.4c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L330.7 376c-34.4 25.2-76.8 40-122.7 40C93.1 416 0 322.9 0 208S93.1 0 208 0S416 93.1 416 208zM208 352a144 144 0 1 0 0-288 144 144 0 1 0 0 288z"/>
        </svg>
        <input class="searchbar" type="text" placeholder="Zoek je gemeente">
      </div>
    </div>
  </section>
</template>

<style scoped>
.main {
  min-height: calc(100vh - 150px);
  object-fit: cover;
  background-color: var(--dark-primary);
  color: var(--light-primary);
  padding: 0 var(--page-padding);
  position: relative;
  z-index: 0;
}

.first {
  padding-top: 150px;
  margin: 0 auto;
}

.searchbar-container {
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

.searchbar-wrapper {
  position: relative;
  width: 100%;
}

.search-icon {
  position: absolute;
  left: 10px;
  top: 50%;
  transform: translateY(-50%);
  width: 26px;
  fill: var(--dark-primary);
  cursor: pointer;
}

.searchbar {
  width: calc(100% - 40px);
  height: 40px;
  border-radius: 10px;
  border: none;
  padding-left: 40px;
  font-size: 1rem;
}
</style>