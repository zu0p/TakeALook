<template>
  <div class="chat-window">
    <Header>
    </Header>
    <MessageList>
      <template>
        <span>메세지리스트</span>
      </template>
    </MessageList>
    <UserInput/>
  </div>
</template>

<script>
import Header from './chat-header.vue'
import MessageList from './chat-message-list.vue'
import UserInput from './chat-user-input.vue'
import ChatList from './chat-list.vue'

export default {
  components: {
    Header,
    MessageList,
    UserInput,
    ChatList,
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
