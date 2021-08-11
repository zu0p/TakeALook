<template>
  <div class="chat-window" style="z-index:2">
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
      <WebSocket/>
    </div>
    <!-- 메세지 리스트 -->
    <UserInput/>
  </div>
</template>

<script>
import MessageList from './chat-message-list.vue'
import UserInput from './chat-user-input.vue'
import ChatList from './chat-list.vue'
import WebSocket from './web-socket.vue'


export default {
  components: {
    MessageList,
    UserInput,
    ChatList,
    WebSocket,
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
