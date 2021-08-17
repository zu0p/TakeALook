<template>
  <!-- <div class="chat-list" :style="{background: userListColor.userList.bg}"> -->
  <div class="chat-list" style="z-index:1;">
    <div class="chat-list-header">
      <div class="chat-list-header-title">
        <h1 style="margin-top:0; margin-bottom:0;">채팅</h1>
      </div>
      <div class="chat-list-header-close-button">
        <i class="el-icon-close" @click="$emit('close')" style="font-weight:bold;"></i>
      </div>
    </div>
    <div v-if="info.chatList" class="chat-list-body scrollable" style="margin-top:5px;">
    <!-- <tr v-for="가장 최근 메세지 in 메세지 방 리스트" :key="메세지 방.id"> -->
      <div v-for="chat in info.chatList[0]" :key="chat.roomId" @click="chatWindow({roomId:chat.roomId, productName: chat.productName})">
        <chat-lists :chat="chat"/>
      </div>
    </div>
    <div v-else class="chat-list-body" style="margin-top:50px;">
      <h1>생성된 대화방이 없습니다</h1>
    </div>
  </div>
  <chat-window v-if="info.chatWindow" @close="$emit('close')" @back="chatWindow()" :roomId="info.roomId" :userId="info.userId" :productName="info.productName" />
</template>

<script>
import { reactive } from '@vue/reactivity'
import { useStore } from 'vuex'
import ChatWindow from './chat-window.vue'
import ChatLists from './chat-lists.vue'

export default {
  components: {
    ChatWindow,
    ChatLists
  },

  props: ['back', 'close'],

  setup () {
    const store = useStore()

    const info = reactive({
      chatWindow: false,
      chatList: [],
      roomId: '',
      userId: '',
      productName: '',
    })

    store.dispatch('root/requestUserInfo')
      .then(res=> {
        // console.log(res.data)
        info.userId = res.data.userId
      })

    store.dispatch('root/requestChatList')
      .then(res=> {
        if (res.data.statusCode != 404 ){
          info.chatList.push(res.data)
        } else {
          info.chatList = false
        }
      })

    const chatWindow = function (e) {
      console.log(e)
      if (!e) {
        info.chatWindow = false
      } else {
        info.roomId = e.roomId
        info.productName = e.productName
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
  background: white;
  display: flex;
  flex-direction: column;
  /* justify-content: space-between; */
  border-radius: 10px;
  font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
  animation: fadeIn;
  animation-duration: 0.3s;
  animation-timing-function: ease-in-out;
}

.chat-list-header {
  background-color: white;
  min-height: 50px;
  border-top-left-radius: 9px;
  border-top-right-radius: 9px;
  padding: 10px;
  /* box-shadow: 0 1px 4px rgba(0, 0, 0, 0.2); */
  position: relative;
  box-sizing: border-box;
  display: flex;
}

.chat-list-header-title {
  text-align: left;
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
  /* margin-top: 10px; */
  /* margin-right: 10px; */
  box-sizing: border-box;
  cursor: pointer;
  border-radius: 5px;
  /* margin-left: auto; */
  font-size:30px;
}

.chat-list-header-close-button > i:hover{
  color: rgb(88, 172, 250);
}

.chat-list-body {
  margin-left: 20px;
}

.scrollable {
  overflow: hidden;
  overflow-y: scroll;
}

.scrollable::-webkit-scrollbar {
  width: 20px;
}

.scrollable::-webkit-scrollbar-thumb {
  background-color: white;
  border-radius: 10px;
}

.scrollable::-webkit-scrollbar-track {
  background-color: white;
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
</style>
