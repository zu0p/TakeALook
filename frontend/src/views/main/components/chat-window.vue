<template>
  <div class="chat-window" style="z-index:2">

    <!-- <MessageList>
      <template>
        <span>메세지리스트</span>
      </template>
    </MessageList> -->
    <div class="chat-window-header">
      <div class="chat-window-header-back-button" @click="$emit('back')">
        <img :src="info.btn.BackIcon" :alt="info.btn.name"/>
      </div>
      <div class="chat-window-header-title">
        <p>판매자 닉네임</p>
      </div>
      <div class="chat-window-header-close-button" @click="$emit('close'), save()">
        <img :src="info.btn.CloseIcon" :alt="info.btn.name"/>
      </div>
    </div>
    <div class="chat-window-body">
      <div class="chat-window-message" v-for="(chat, idx) in info.data.chatList" :key="idx">
          <p class="chat-writer">{{chat.writer}}</p>
        <div class="chat-messages">
          <p class="chat-message">{{chat.message}}</p>
        </div>
      </div>
      <div>
      <!-- <MessageList
        v-for="(chat, idx) in info.chatList"
        :key="idx"
        :chat="chat"
        :user="(chat.writer)"
      > -->
      <!-- 슬록 속성(slot props) 을 이용해서 자식 컴포넌트에 정의 되어있는 데이터를 부모 컴포넌트에서 접근할 수 있다. -->
      <!-- <template v-slot:user-avatar="scopedProps">
        <slot name="user-avatar" :user="scopedProps.user" :message="scopedProps.message"> </slot>
      </template>
      <template v-slot:text-message-body="scopedProps">
        <slot
          name="text-message-body"
          :message="scopedProps.message"
          :messageText="scopedProps.messageText"
          :messageColors="scopedProps.messageColors"
          :me="scopedProps.me"
        >
        </slot>
      </template>
      <template v-slot:system-message-body="scopedProps">
        <slot name="system-message-body" :message="scopedProps.message"> </slot>
      </template>
      <template v-slot:text-message-toolbox="scopedProps">
        <slot name="text-message-toolbox" :message="scopedProps.message" :me="scopedProps.me">
        </slot>
      </template>
      </MessageList> -->
      </div>
    </div>
    <!-- 메세지 리스트 -->
    <div class="chat-user-input">
      <div class="chat-user-input-text">
        <el-input placeholder="Please input" v-model="info.msg" @keydown.enter.prevent="send"></el-input>
      </div>
      <div class="chat-user-input-button">
        <el-button type="primary" icon="el-icon-s-promotion" circle="" @click="send"></el-button>
      </div>
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
import CloseIcon from '@/assets/images/close-icon-big.png'
import BackIcon from '@/assets/images/back-icon-big.png'


export default {
  components: {
    MessageList,
    UserInput,
    ChatList,
    CloseIcon,
    BackIcon,
  },

  props:["roomId","userId"],

  setup(props){
    console.log(props.userId)
    const store = useStore()
    const info = reactive({
      data: {
        chatList: [],
      },
      msg:'',
      roomId: props.roomId,
      connected: false,
      nickname: props.userId,
      btn: {
        CloseIcon,
        BackIcon,
        name: 'default',
       },
    })

      const serverURL = "https://i5d101.p.ssafy.io:8080/stomp/chat"
      var socket = new SockJS(serverURL)
      const stompClient = Stomp.over(socket)
      console.log(`연결 시도, ${serverURL}`)
      stompClient.connect(
        {},
        frame => {
          info.connected = true
          console.log("연결 성공", frame)
          console.log(store)


          stompClient.subscribe("/sub/chat/room/"+info.roomId, res=>{
            console.log("sub 성공", res.body)
            info.data.chatList.push(JSON.parse(res.body))
          })
          console.log(0)
          console.log(info)
          var Datenow = Date.now()
          var message = {
            roomId: info.roomId,
            writer: info.nickname,
            sendTime: Datenow.toString(),
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
          var Datenow = Date.now()
          console.log('현재시간은' + Datenow)
          var message = {
            roomId: info.roomId,
            writer: info.nickname,
            message: info.msg,
            sendTime: Datenow.toString(),
          }
          console.log(message.sendTime)
          stompClient.send('/pub/chat/message', JSON.stringify(message), {})
          console.log(1)
          console.log(JSON.stringify(message))
          console.log(2)
        }
        info.msg = ""
      }

      const save = function () {

      store.dispatch('root/requestSaveChatList', (info.data) )
        .then(res=> {
          console.log(res)
          console.log('채팅이 성공적으로 저장되었습니다')
        })
        .catch(res=> {
          console.log('채팅내용저장에 실패했습니다.')
        })
      }

    return {info, send, save}
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
  border-radius: 10px;
  font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
  animation: fadeIn;
  animation-duration: 0.3s;
  animation-timing-function: ease-in-out;
}

.chat-window-header {
  background-color: #4e8cff;
  min-height: 75px;
  border-top-left-radius: 9px;
  border-top-right-radius: 9px;
  padding: 10px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.2);
  position: relative;
  box-sizing: border-box;
  display: flex;
}

.chat-window-header-title {
  /* border: 3px solid purple; */
  /* cursor: pointer; */
  color: white;
  font-weight: 900;
  text-align: center;
  width: 100%;
  border-radius: 5px;
}

.chat-window-header-back-button {
  /* border: 3px solid blue; */
  min-width: 40px;
  align-self: center;
  min-height: 40px;
  margin-right: 10px;
  box-sizing: border-box;
  cursor: pointer;
  border-radius: 5px;
  margin-left: auto;
}

.chat-window-header-close-button {
  /* border: 3px solid red; */
  min-width: 40px;
  align-self: center;
  min-height: 40px;
  margin-right: 10px;
  box-sizing: border-box;
  cursor: pointer;
  border-radius: 5px;
  margin-left: auto;
}

.chat-window-header-close-button:hover {
  /* border: 3px solid red; */
  box-shadow: 0px 2px 5px rgba(0.2, 0.2, 0.5, 0.1);
}

.chat-window-header-back-button:hover {
  /* border: 3px solid red; */
  box-shadow: 0px 2px 5px rgba(0.2, 0.2, 0.5, 0.1);
}

.chat-window-header-close-button img {
  /* border: 3px solid red; */
  width: 100%;
  height: 100%;
  padding: 10px;
  box-sizing: border-box;
}

.chat-window-header-back-button img {
  /* border: 3px solid red; */
  width: 100%;
  height: 100%;
  padding: 10px;
  box-sizing: border-box;
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
.chat-window-body {
  width: 100%;
  padding: 5px 20px;
  border-radius: 6px;
  font-weight: 300;
  vertical-align: top;
  position: relative;
}

.chat-window-body .chat-window-message {
  width: 100%;
}

.chat-messages {
  display: flex;
  white-space: pre-wrap;
}

.chat-window-body .chat-window-message .chat-writer {
  text-align: left;
  max-height: 20px;
  margin-bottom: 5px;
  font-weight: 300;
  font-size: 12px;
  line-height: 1.4;
  -webkit-font-smoothing: subpixel-antialiased;
}

.chat-window-body .chat-window-message .chat-message {
  /* message text color */
  color: #263238;
  /* message background color */
  background-color: #f4f7f9;
  margin-top: 0px;
  margin-right: 40px;
  margin-left: 5px;
  margin-bottom: 5px;
  padding: 5px 10px;
  border-radius: 6px;
  font-weight: 400;
  font-size: 14px;
  line-height: 1.4;
  position: relative;
  -webkit-font-smoothing: subpixel-antialiased;


}


.chat-message--me {
  text-align: right;
}

.chat-message--them {
  text-align: left;
}

.chat-user-input {
  width: 100%;
  min-height: 55px;
  margin: 0px;
  position: absolute;
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
  /*padding: top, right, bottom, left */
  padding: 18px 5px 18px 18px;
  font-size: 15px;
  line-height: 1.33;
  word-wrap: break-word;
  color: #565867;
  -webkit-font-smoothing: antialiased;
  max-height: 100px;
  overflow-x: hidden;
  overflow-y: auto;
}

.chat-user-input-button {
  display: flex;
  align-items: center;
    /*padding: top, right, bottom, left */
  padding: 18px 10px 18px 5px;
}


</style>
