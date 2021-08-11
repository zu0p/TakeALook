<template>
  <div class="chat-window" style="z-index:2">
    <Header/>
    <button @click="$emit('close')">뒤로</button>
    <button @click=connect>connect</button>
    <button @click=disconnect>disconnect</button>
      <!-- <template>
        <span>헤더리스트</span>
      </template>
    </Header> -->
    <MessageList>
      <template>
        <span>메세지리스트</span>
      </template>
    </MessageList>
    <!-- <UserInput/> -->
    <form @submit.prevent="send" action="#">
      <input v-model="info.message"><button type="submmit">전송</button>
    </form>
  </div>
</template>

<script>
import Header from './chat-header.vue'
import MessageList from './chat-message-list.vue'
import UserInput from './chat-user-input.vue'
import ChatList from './chat-list.vue'
import { reactive } from '@vue/reactivity'
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'


export default {
  components: {
    Header,
    MessageList,
    UserInput,
    ChatList,
  },
  setup(){
    const stomp = Stomp
    const SocketJS = SockJS
    const info = reactive({
      message:'',
      logs: []
    })
    // const socket = new SocketJS ("ws://localhost:8080/stomp/chat")
    const connect = function () {
      const serverURL = "http://localhost:8080/stomp/chat"
      let socket = new SocketJS(serverURL)
      const stompClient = stomp.over(socket)
      console.log(`연결 시도, ${serverURL}`)
      stompClient.connect(
        {},
        frame => {
          console.log("연결 성공", frame)
        },
        err => {
          console.log("연결 실패", err)
        }
      )

      // socket.onopen = function () {
      //   info.logs.push({ event: "connect", data:"ws://localhost:8080/stomp/chat"})
      //   socket.onmessage = function (data) {
      //     info.logs.push({ event: "수신", data})
      //     console.log(data)
      //   }
      // }
      // console.log(socket)
      // console.log(info.logs)
    }

    const disconnect = function () {
      socket.close()
      console.log(socket)
    }

    const send = function () {
      socket.send(info.message)
      info.logs.push({ event: "전송", data: info.message})
      info.message = ''
      console.log(socket)
    }
    return {info, connect, disconnect, send}
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

.chat-window.closed {
  opacity: 0;
  display: none;
  bottom: 90px;
}

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
