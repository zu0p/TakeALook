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


</style>
