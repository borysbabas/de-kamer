<script setup lang="ts">
import { defineEmits } from "vue";

const emit = defineEmits(["updateDescription"]);

const applyMarkdown = (markdown: string) => {
  const textarea = document.querySelector("textarea");
  if (textarea) {
    const start = textarea.selectionStart;
    const end = textarea.selectionEnd;
    const text = textarea.value;
    const before = text.substring(0, start);
    const after = text.substring(end, text.length);
    textarea.value = `${before}${markdown}${after}`;
    textarea.focus();
    textarea.setSelectionRange(
      start + markdown.length,
      start + markdown.length
    );
    emit("updateDescription", textarea.value);
  }
};
</script>

<template>
  <div class="markdown-menu">
    <button @click="applyMarkdown('# heading')">
      <svg viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path
          id="Vector"
          d="M16 10L19 9L19 19M3 5V12M3 12V19M3 12H11M11 5V12M11 12V19"
          stroke="#000000"
          stroke-width="2"
          stroke-linecap="round"
          stroke-linejoin="round"
        ></path>
      </svg>
    </button>
    <button @click="applyMarkdown('## subheading')">
      <svg viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path
          id="Vector"
          d="M15 12.5V12C15 10.3431 16.3431 9 18 9H18.1716C19.7337 9 20.9996 10.2665 20.9996 11.8286C20.9996 12.5788 20.702 13.2982 20.1716 13.8286L15 19.0002L21 19M3 5V12M3 12V19M3 12H11M11 5V12M11 12V19"
          stroke="#000000"
          stroke-width="2"
          stroke-linecap="round"
          stroke-linejoin="round"
        ></path>
      </svg>
    </button>
    <button @click="applyMarkdown('**bold**')">
      <svg viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg" fill="none">
        <path
          fill="#000000"
          fill-rule="evenodd"
          d="M4 1a1 1 0 00-1 1v16a1 1 0 001 1v-1 1h8a5 5 0 001.745-9.687A5 5 0 0010 1H4zm6 8a3 3 0 100-6H5v6h5zm-5 2v6h7a3 3 0 100-6H5z"
        ></path>
      </svg>
    </button>
    <button @click="applyMarkdown('_italic_')">
      <svg viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg" fill="none">
        <path
          stroke="#000000"
          stroke-linecap="round"
          stroke-linejoin="round"
          stroke-width="2"
          d="M12 3L8 17m4-14H8m4 0h4M8 17H4m4 0h4"
        ></path>
      </svg>
    </button>
    <button @click="applyMarkdown('`code`')">
      <svg viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path
          d="M7 8L3 11.6923L7 16M17 8L21 11.6923L17 16M14 4L10 20"
          stroke="#000000"
          stroke-width="2"
          stroke-linecap="round"
          stroke-linejoin="round"
        ></path>
      </svg>
    </button>
    <button @click="applyMarkdown('> blockquote')">
      <svg
        fill="#000000"
        xmlns="http://www.w3.org/2000/svg"
        viewBox="0 0 16 16"
        class="icon-quote"
        stroke="#000000"
        stroke-width="0.00016"
        transform="matrix(-1, 0, 0, 1, 0, 0)"
      >
        <path
          d="M2.5 3a.5.5 0 0 0 0 1h11a.5.5 0 0 0 0-1h-11zm0 3a.5.5 0 0 0 0 1h6a.5.5 0 0 0 0-1h-6zm0 3a.5.5 0 0 0 0 1h6a.5.5 0 0 0 0-1h-6zm0 3a.5.5 0 0 0 0 1h11a.5.5 0 0 0 0-1h-11zm10.113-5.373a6.59 6.59 0 0 0-.445-.275l.21-.352c.122.074.272.17.452.287.18.117.35.26.51.428.156.164.289.351.398.562.11.207.164.438.164.692 0 .36-.072.65-.216.873-.145.219-.385.328-.721.328-.215 0-.383-.07-.504-.211a.697.697 0 0 1-.188-.463c0-.23.07-.404.211-.521.137-.121.326-.182.569-.182h.281a1.686 1.686 0 0 0-.123-.498 1.379 1.379 0 0 0-.252-.37 1.94 1.94 0 0 0-.346-.298zm-2.168 0A6.59 6.59 0 0 0 10 6.352L10.21 6c.122.074.272.17.452.287.18.117.35.26.51.428.156.164.289.351.398.562.11.207.164.438.164.692 0 .36-.072.65-.216.873-.145.219-.385.328-.721.328-.215 0-.383-.07-.504-.211a.697.697 0 0 1-.188-.463c0-.23.07-.404.211-.521.137-.121.327-.182.569-.182h.281a1.749 1.749 0 0 0-.117-.492 1.402 1.402 0 0 0-.258-.375 1.94 1.94 0 0 0-.346-.3z"
        />
      </svg>
    </button>
    <button @click="applyMarkdown('- list item')">
      <svg viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path
          d="M8 6.00067L21 6.00139M8 12.0007L21 12.0015M8 18.0007L21 18.0015M3.5 6H3.51M3.5 12H3.51M3.5 18H3.51M4 6C4 6.27614 3.77614 6.5 3.5 6.5C3.22386 6.5 3 6.27614 3 6C3 5.72386 3.22386 5.5 3.5 5.5C3.77614 5.5 4 5.72386 4 6ZM4 12C4 12.2761 3.77614 12.5 3.5 12.5C3.22386 12.5 3 12.2761 3 12C3 11.7239 3.22386 11.5 3.5 11.5C3.77614 11.5 4 11.7239 4 12ZM4 18C4 18.2761 3.77614 18.5 3.5 18.5C3.22386 18.5 3 18.2761 3 18C3 17.7239 3.22386 17.5 3.5 17.5C3.77614 17.5 4 17.7239 4 18Z"
          stroke="#000000"
          stroke-width="2"
          stroke-linecap="round"
          stroke-linejoin="round"
        ></path>
      </svg>
    </button>
    <button @click="applyMarkdown('[link](url)')">
      <svg viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path
          d="M14 7H16C18.7614 7 21 9.23858 21 12C21 14.7614 18.7614 17 16 17H14M10 7H8C5.23858 7 3 9.23858 3 12C3 14.7614 5.23858 17 8 17H10M8 12H16"
          stroke="#000000"
          stroke-width="2"
          stroke-linecap="round"
          stroke-linejoin="round"
        ></path>
      </svg>
    </button>
  </div>
</template>

<style scoped>
.markdown-menu {
  display: flex;
  flex-direction: column;
  gap: 10px;
  position: fixed;
  left: 10px;
  top: 150px;
}

button {
  background: var(--button);
  color: var(--light-primary);
  border: none;
  padding: 10px;
  cursor: pointer;
  border-radius: 5px;
  display: flex;
  align-items: center;
  justify-content: center;
}

button svg {
  width: 28px;
  height: 28px;
  font-size: 24px;
}
</style>
