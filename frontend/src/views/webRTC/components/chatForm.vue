<template>

  <!-- <el-row> -->
    <!-- <el-scrollbar class="chat-scrollbar" height="600px" ref="scrollbar"> -->
  <div id="container">
    <div id="chat-container">
      <ul id="chat">
        <li class="chats" v-for="i in state.chats" :key="i">
          <el-card shadow="never" style="width: 90%">
            <div class="messages">
              <el-tag v-if="!i.me" type="info"><span>{{i.name}}</span></el-tag>
              <el-tag v-if="i.me" type="success"><span>{{i.name}}</span></el-tag>
              <span>{{i.message}}</span>
            </div>
          </el-card>
        </li>
      </ul>
    </div>
      <!-- </el-scrollbar> -->
    <!-- </el-row> -->
    <div id="send-container">
      <el-input v-model="state.inputMessage" style="width: 70%; margin: 0 3% 0 6px"  @keyup.enter="clickSend"></el-input>
      <el-button @click="clickSend">전송</el-button>
    </div>
  </div>

</template>

<script>
import { reactive, ref } from '@vue/reactivity'
import { onBeforeUpdate, onMounted, onUpdated, watch, watchEffect } from '@vue/runtime-core'
import { useStore } from 'vuex'
import ws from '../js/webSocket'

export default {
  name: 'chat-form',

  props: ['name', 'room', 'receiveMessage'],

  setup(props, {emit}){
    const store = useStore()
    let state = reactive({
      curUserId: props.name,
      roomId: props.room,
      chats: [],
      inputMessage : ''
    })

    const propsMsg = watchEffect(()=>{
      //console.log(props.receiveMessage)
      if(props.receiveMessage.flag){
        const jsoned = JSON.parse(props.receiveMessage.message)
        const received = {
          name: jsoned.name,
          message: jsoned.message,
          me: state.curUserId==jsoned.name?true:false
        }
        state.chats.push(received)
        // console.log(state.chats)

        emit('endReceiveMsg')
      }
    })

    const clickSend = function(){
      if(state.inputMessage=='') return
      const newMessage ={
        id: 'sendChatMessage',
        name: state.curUserId,
        room: state.roomId,
        message: state.inputMessage
      }

      ws.send(JSON.stringify(newMessage))
      state.inputMessage =''

    }

    onMounted(()=>{
      store.dispatch('root/requestUserInfo')
        .then(res=>{
          state.curUserId= res.data.userId
        })
    })

    onUpdated(()=>{
      let scroll = document.getElementById("chat-container")
      scroll.scrollTop = scroll.scrollHeight
    })

    return {state, clickSend}
  }
}
</script>

<style>
.messages{
  text-align: left;
  margin-right:5px;
}
.chat-scrollbar{
  width: 100%!important;
}
#chat{
  /* display: flex; */
  /* overflow: scroll; */
  /* flex-direction: column-reverse; */
  /* overflow-y: auto; */
  /* height: 500px; */
  list-style-type: none;
}
#container{
  border: thin solid gainsboro;
  border-radius: 4px;
}
#chat-container{
  overflow-y: scroll;
  height: 70vh;
}
#send-container{
  /* height: 30vh; */
  margin: 0 0 15px 0;
}
</style>
