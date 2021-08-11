<template>
  <div class="chat-user-input">
    <div class="file-container">
      <span class="icon-file-message">
        <!-- <img :src="icons.file.img" :alt="icons.file.name" height="15"/> -->
      </span>
      <span class="delete-file-message" @click="cancelFile()">
        <!-- <img height="10" title="Remove the file"/> -->
      </span>
    </div>
    <form class="chat-user-input">
      <div class="chat-user-input-text">
        <el-input placeholder="Please input" v-model="state.form.message"></el-input>
      </div>
      <div class="chat-user-input-buttons">
        <div class="chat-user-input-button">
          <el-button type="primary" icon="el-icon-s-promotion" circle=""></el-button>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import { ref, reactive, } from 'vue'

export default {
  name: 'UserInputForm',

  setup () {
    // 독립적인 반응형 값 생성 ref()
    const userInputForm = ref(null)
    const state = reactive({
      form: {
        message: '',
      },
    })
    return { userInputForm, state }
  },

}
</script>

<style>
.chat-user-input {
  min-height: 55px;
  margin: 0px;
  position: relative;
  bottom: 0;
  display: flex;
  background-color: #f4f7f9;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
  transition: background-color 0.2s ease, box-shadow 0.2s ease;
}

.chat-user-input-text {
  flex-grow: 1;
  outline: none;
  border-bottom-left-radius: 10px;
  box-sizing: border-box;
  padding: 18px;
  font-size: 15px;
  line-height: 1.33;
  white-space: pre-wrap;
  word-wrap: break-word;
  color: #565867;
  -webkit-font-smoothing: antialiased;
  max-height: 200px;
  overflow-x: hidden;
  overflow-y: auto;
}

.chat-user-input--text:empty:before {
  content: attr(placeholder);
  display: block; /* For Firefox */
  filter: contrast(15%);
  outline: none;
  cursor: text;
}

.chat-user-input-buttons {
  display: flex;
  align-items: center;
  padding: 0 4px;
}

.chat-user-input-button {
  margin: 0 4px;
}

.chat-user-input.active {
  box-shadow: none;
  background-color: white;
  box-shadow: 0px -5px 20px 0px rgba(150, 165, 190, 0.2);
}

.file-container {
  background-color: #f4f7f9;
  border-top-left-radius: 10px;
  padding: 5px 20px;
  color: #565867;
}

.delete-file-message {
  font-style: normal;
  float: right;
  cursor: pointer;
  color: #c8cad0;
}

.delete-file-message:hover {
  color: #5d5e6d;
}

.icon-file-message {
  margin-right: 5px;
}
</style>
