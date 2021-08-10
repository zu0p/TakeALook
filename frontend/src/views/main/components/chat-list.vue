<template>
  <!-- <div class="chat-list" :style="{background: userListColor.userList.bg}"> -->
  <div class="chat-list" style="background-color:lightblue; z-index:1;">
    <div class="chat-header" >
      <slot>
        <div class="chat-header--title enabled">
          Fuxxing CSS
        </div>
      </slot>
      <div class="chat-header--close-button">
        <el-button type="primary" icon="el-icon-close" @click="clickClose()"></el-button>
      </div>
    </div>
    <table style="padding-top: 5px;">
      <tbody>
        <!-- <tr v-for="가장 최근 메세지 in 메세지 방 리스트" :key="메세지 방.id"> -->
        <tr>
          <!-- 사진을 가로로 중간에 위치 -->
          <td style="text-align: center;">
            <h1>대화 리스트</h1>
            <p>최준성</p>
            <p>신지현</p>
          </td>
          <td class="user-element">
            <!-- 유저 이름 클릭 시 chat-window창으로 이동 -->
            <el-button @click="chatWindow">유저 이름</el-button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
  <chat-window v-if="info.chatWindow" @back="chatWindow()"/>
</template>

<script>
import { reactive } from '@vue/reactivity'
import ChatWindow from './chat-window.vue'
import Header from './chat-header.vue'

export default {
  components: {
    ChatWindow,
    Header
  },

  props: ['back', 'close'],

  setup (props, {emit}) {
    console.log(541)
    console.log(props)
    if(props.back == "back"){
      info.chatWindow = false
    }
    console.log(emit)

    const info = reactive({
      chatWindow: false
    })

    const clickClose = function () {
      if (props.close == "close") {
        info.isOpen = false
      }
    }
    console.log(emit)

    const chatWindow = function () {
      if (info.chatWindow) {
        info.chatWindow = false
      } else {
        info.chatWindow = true
      }
    }

    return {info, chatWindow, clickClose}
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
  background: white;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  border-radius: 10px;
  font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
  animation: fadeIn;
  animation-duration: 0.3s;
  animation-timing-function: ease-in-out;
}
 /* 채팅중인 상대 유저 프로필 사진  */
.img-msg {
  border-radius: 50%;
  width: 50px;
  margin-right: 5px;
}
/* 프로필 사진 오른쪽에 나타나는 메세지 CSS 속성 */
.chat-element {
  font-size: 20px;
  vertical-align: middle;
}
</style>
