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
        <p>{{info.productName}}</p>
      </div>
      <div class="chat-window-header-close-button" @click="$emit('close')">
        <img :src="info.btn.CloseIcon" :alt="info.btn.name"/>
      </div>
    </div>
    <div class="chat-window-body" id="chat-window-scroll">
      <div class="chat-window-message" v-for="(chat, idx) in info.saveChatList" :key="idx">
        <div v-if="info.saveChatList" id="chatContent">
          <div v-if="userId===chat.writer">
            <p class="chat-writer1"></p>
            <div class="chat-messages1">
              <p class="chat-message1">{{chat.message}}</p>
            </div>
          </div>
          <div v-else>
            <p class="chat-writer">{{chat.writer}}</p>
            <div class="chat-messages">
              <p class="chat-message">{{chat.message}}</p>
            </div>
          </div>
        </div>
        <div v-else>
          <p>아직 메세지가 없습니다...</p>
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
import { onBeforeMount, onUnmounted, onUpdated } from '@vue/runtime-core'


export default {
  components: {
    MessageList,
    UserInput,
    ChatList,
    CloseIcon,
    BackIcon,
  },

  props:["roomId","userId","productName"],



  setup(props){

    console.log(props.userId)
    console.log(props.productName)
    const store = useStore()
    const info = reactive({
      saveChatList: [],
      msg:'',
      productName: props.productName,
      roomId: props.roomId,
      connected: false,
      nickname: props.userId,
      btn: {
        CloseIcon,
        BackIcon,
        name: 'default',
       },
    })


    onBeforeMount(()=>{
      console.log(info.roomId)
      store.dispatch('root/requestChatData', info.roomId)
        .then(res=>{
          if(res.data.statusCode != 404) {
            info.saveChatList = res.data
          }
          else{
            info.saveChatList = false
          }
        })
        .catch(res=>{
          console.log('채팅 내용을 불러오는데 실패했습니다.')
        })
    })

    const serverURL = "https://i5d101.p.ssafy.io:8080/stomp/chat"
    var socket = new SockJS(serverURL)
    const stompClient = Stomp.over(socket)
    stompClient.connect(
      {},
      frame => {
        info.connected = true

        stompClient.subscribe("/sub/chat/room/"+info.roomId, res=>{
          if(info.saveChatList) {
            info.saveChatList.push(JSON.parse(res.body))
          }
          else{
            info.saveChatList = []
            info.saveChatList.push(JSON.parse(res.body))
          }
        })
        window.scrollTo({ left: 0, top: document.body.scrollHeight, behavior: "smooth" });

      },
      err => {
        console.log("연결 실패", err)
        info.connected = false
      }
    )



    // 전송 시 chatList에 추가
    const send = function () {
      if (stompClient && stompClient.connected) {
        var message = {
          roomId: info.roomId,
          writer: info.nickname,
          message: info.msg,
          sendTime: Date.now()
        }
        stompClient.send('/pub/chat/message', JSON.stringify(message), {})
      }
      info.msg = ""
    }

    // 어떤 식으로든 채팅 화면을 벗어나면 채팅내용이 저장
    onUnmounted(()=>{
      stompClient.disconnect()
    })

    onUpdated(()=>{
      let scroll = document.getElementById("chat-window-scroll")
      scroll.scrollTop = scroll.scrollHeight
    })

    return {info, send, }
  },

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
  /* 너비330px, 패딩 좌우 20xp씩 총 370px */
  width: 330px;
  max-height: 429px;
  padding: 5px 20px;
  border-radius: 6px;
  font-weight: 300;
  vertical-align: top;
  position: relative;
  /* 채팅 내용이 화면에 채울 수 있는 내용보다 더 많아지면 자동으로 스크롤 바 생성 */
  overflow: auto;
  height: 70vh;
}

.chat-window-body .chat-window-message {
  width: 100%;
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

.chat-messages {
  display: flex;
  white-space: pre-wrap;
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


.chat-window-body .chat-window-message .chat-writer1 {
  text-align: right;
  max-height: 20px;
  margin-bottom: 5px;
  font-weight: 300;
  font-size: 12px;
  line-height: 1.4;
  -webkit-font-smoothing: subpixel-antialiased;
}

.chat-messages1 {
  /* border: 3px solid red; */
  /* 채팅 친 내용만큼만 감싸서 보여주기 */
  display: flex;
  white-space: pre-wrap;
  /* 오른쪽에 채팅 나타내기 */
  justify-content: flex-end;
}

.chat-window-body .chat-window-message .chat-message1 {
  /* border: 3px solid orange; */
  /* message text color */
  color: #f4f7f9;
  /* message background color */
  background-color: #4e8cff;
  margin-top: 0px;
  margin-right: 5px;
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


.chat-user-input {
  width: 100%;
  min-height: 55px;
  max-height: 76px;
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
