<template>
  <div class="chat-window" style="z-index:2">

    <!-- <MessageList>
      <template>
        <span>메세지리스트</span>
      </template>
    </MessageList> -->
    <div class="chat-window-header">
      <el-button type="primary" icon="el-icon-back" @click="$emit('back')"></el-button>
      <div class="chat-window-header-title">
        hellow
      </div>
      <div class="chat-window-header-close-button">
        <el-button type="primary" icon="el-icon-close" @click="$emit('close')"></el-button>
      </div>
    </div>
    <div class="chat-window-body">
      <div v-for="(chat, idx) in info.chatList" :key="idx">
        <span>{{chat.writer}} : {{chat.message}}</span>
      </div>
    </div>
    <!-- 메세지 리스트 -->
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
          <el-input placeholder="Please input" v-model="info.msg" @keydown.enter.prevent="send"></el-input>
        </div>
        <div class="chat-user-input-buttons">
          <div class="chat-user-input-button">
            <el-button type="primary" icon="el-icon-s-promotion" circle="" @click="send"></el-button>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>


<script>
import MessageList from './chat-message-list.vue'
import UserInput from './chat-user-input.vue'
import ChatList from './chat-list.vue'
import { reactive } from '@vue/reactivity'
import { useStore } from 'vuex'
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'


export default {
  components: {
    MessageList,
    UserInput,
    ChatList,
  },
  //! 변경
  props:["roomId"],

  setup(props){
    const store = useStore()
    const info = reactive({
      msg:'',
      roomId:props.roomId,
      chatList: [],
      connected: false,
      nickname: "dd"
    })

      const serverURL = "http://localhost:8080/stomp/chat"
      var socket = new SockJS(serverURL)
      const stompClient = Stomp.over(socket)
      console.log(`연결 시도, ${serverURL}`)
      stompClient.connect(
        {},
        frame => {
          info.connected = true
          console.log("연결 성공", frame)

          stompClient.subscribe("/sub/chat/room/"+info.roomId, res=>{
            console.log("sub 성공", res.body)
            info.chatList.push(JSON.parse(res.body))
          })
          var message = {
            roomId: info.roomId,
            writer: info.nickname
          }
          stompClient.send("/pub/chat/enter", JSON.stringify(message), {})
        },
        err => {
          console.log("연결 실패", err)
          info.connected = false
        }
      )

      const send = function () {
        if (stompClient && stompClient.connected) {
          var message = {
            roomId: info.roomId,
            message: info.msg,
            writer: info.nickname
          }
          stompClient.send('/pub/chat/message', JSON.stringify(message), {})
        }
        info.msg = ""
      }
    return {info, send}
  }
}
</script>


<style scoped>
.chat-window {
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

.chat-window-header {
  background-color: cornflowerblue;
  min-height: 75px;
  text-align: center;
  border-top-left-radius: 9px;
  border-top-right-radius: 9px;
  padding: 10px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.2);
  position: relative;
  box-sizing: border-box;
  display: flex;
}

.chat-window-header-title {
  cursor: pointer;
  border-radius: 5px;
}

.chat-window-header-close-button {
  width: 40px;
  align-self: center;
  height: 40px;
  margin-right: 10px;
  box-sizing: border-box;
  cursor: pointer;
  border-radius: 5px;
  margin-left: auto;
}

.chat-window.closed {
  opacity: 0;
  display: none;
  bottom: 90px;
}

/* chat-list에서 chat-window로 넘어갈 때 fadeIn */
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

.chat-message--me {
  text-align: right;
}
.chat-message--them {
  text-align: left;
}

@media (max-width: 450px) {
  .chat-window {
    width: 100%;
    height: 100%;
    max-height: 100%;
    right: 0px;
    bottom: 0px;
    border-radius: 0px;
  }
  .chat-window {
    transition: 0.1s ease-in-out;
  }
  .chat-window.closed {
    bottom: 0px;
  }
}
</style>
