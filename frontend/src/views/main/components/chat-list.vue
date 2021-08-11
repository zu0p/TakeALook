<template>
  <!-- <div class="chat-list" :style="{background: userListColor.userList.bg}"> -->
  <div class="chat-list" style="z-index:1;">
    <div class="chat-list-header">
      <div class="chat-list-header-title">
        <span>대화 리스트</span>
      </div>
      <div class="chat-list-header-close-button">
        <el-button type="primary" icon="el-icon-close" @click="$emit('close')"></el-button>
      </div>
    </div>
    <div class="chat-list-body">
    <!-- <tr v-for="가장 최근 메세지 in 메세지 방 리스트" :key="메세지 방.id"> -->
      <!-- 유저 이름 클릭 시 chat-window창으로 이동 -->
      <hr>
      <el-button @click="chatWindow">zu0p</el-button>
      <hr>
      <el-button @click="chatWindow">nahyeon</el-button>
      <hr>
      <el-button @click="chatWindow">masterLee</el-button>
      <hr>
      <el-button @click="chatWindow">jihyun</el-button>
      <hr>
      <el-button @click="chatWindow">junsung</el-button>
    </div>
  </div>
  <chat-window v-if="info.chatWindow" @back="chatWindow()" @close="$emit('close')"/>
</template>

<script>
import { reactive } from '@vue/reactivity'
import ChatWindow from './chat-window.vue'

export default {
  components: {
    ChatWindow,
  },

  props: ['back', 'close'],

  setup () {
    console.log(541)
    const info = reactive({
      chatWindow: false,
    })


    const chatWindow = function () {
      if (info.chatWindow) {
        info.chatWindow = false
      } else {
        info.chatWindow = true
      }
    }

    return {info, chatWindow}
  }
}
</script>

<style scoped>
.chat-list {
  width: 370px;
  height: calc(100% - 120px);
  max-height: 590px;
  position: fixed;
  right: 30px;
  bottom: 100px;
  box-sizing: border-box;
  box-shadow: 0px 7px 40px 2px rgba(148, 149, 150, 0.1);
  background: lightgrey;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  border-radius: 10px;
  font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
  animation: fadeIn;
  animation-duration: 0.3s;
  animation-timing-function: ease-in-out;
}

.chat-list-header {
  background-color: cornflowerblue;
  min-height: 75px;
  border-top-left-radius: 9px;
  border-top-right-radius: 9px;
  padding: 10px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.2);
  position: relative;
  box-sizing: border-box;
  display: flex;
}

.chat-list-header-title {
  align-self: center;
  padding: 10px;
  flex: 1;
  user-select: none;
  font-size: 20px;
}

.chat-list-header-close-button {
  width: 40px;
  align-self: center;
  height: 40px;
  margin-right: 10px;
  box-sizing: border-box;
  cursor: pointer;
  border-radius: 5px;
  margin-left: auto;
}
/* chat-window에서 chat-list로 넘어갈 때 fadeIn */
@keyframes fadeIn {
  0% {
    display: none;
    opacity: 0;
  }

  100% {
    display: flex;
    opacity: 1;
  }
}

.chat-list-body {
  text-align: center;
}

/* 프로필 사진 오른쪽에 나타나는 메세지 CSS 속성 */
.chat-element {
  font-size: 20px;
  vertical-align: middle;
}
</style>
