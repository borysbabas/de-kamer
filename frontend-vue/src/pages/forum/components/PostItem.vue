<script setup lang="ts">
defineProps({
  post: {
    type: Object,
    required: true
  }
});
</script>

<template>
  <li class="post">
    <div class="post-title-container">
      <h3 class="post-title">{{ post.title }}</h3>
    </div>
    <div class="post-container">
      <p class="post-description">{{ post.description }}</p>
      <div class="post-info">
        <p class="post-user">Posted by: {{ post.userName }}</p>
        <p class="post-date">{{ post.dateCreated }}</p>
      </div>
    </div>
  </li>
</template>

<style scoped>
.post {
  border: 1px solid var(--light-primary);
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 0;
  height: 200px;
  transition: transform 0.2s, box-shadow 0.2s;
  overflow: hidden;
}

.post:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.5);
}

.post-title-container {
  background-color: var(--dark-secondary);
  padding: 13px 20px;
  border-bottom: 1px solid var(--light-primary);
  min-height: 36px;
  border-radius: 10px 10px 0 0;
}


.post-title {
  font-size: 1.5rem;
  margin: 0;
}

.post-container {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  gap: 20px;
  padding: 20px;
  flex-grow: 1;
}

.post-description {
  font-size: 1rem;
  margin: 0;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  line-clamp: 0;
  -webkit-box-orient: vertical;
}

.post-user {
  font-size: 0.8rem;
  margin: 0;
}

.post-date {
  font-size: 0.8rem;
  margin: 0;
}
</style>