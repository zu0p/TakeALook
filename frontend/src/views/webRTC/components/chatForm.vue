<template>

  <!-- <el-row> -->
    <!-- <el-scrollbar class="chat-scrollbar" height="600px" ref="scrollbar"> -->
  <div id="container">
    <div id="chat-container">
      <ul id="chat">
        <li class="chats" v-for="i in state.chats" :key="i">
          <el-card shadow="never" style="width: 90%">
            <div class="messages">
              <el-tag type="info"><span>{{i.userId}}</span></el-tag>
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
import { onMounted, onUpdated } from '@vue/runtime-core'
import { useStore } from 'vuex'
import ws from '../js/webSocket'

export default {
  name: 'chat-form',
  setup(props, {emit}){
    const store = useStore()
    const scrollbar = ref(null)
    let state = reactive({
      curUserId:'',
      chats:[
        {
          userId: 'zu0p',
          message: '생활기스 없나요??'
        },
        {
          userId: 'zu0p',
          message: '뒷면도 보여주세요!'
        },
        {
          userId: 'test2',
          message: '거래 시작 언제하나요?'
        },
        {
          userId: 'test1',
          message: '잘안보여요..'
        },
        {
          userId: 'test3',
          message: '좀 더 가까이서 보여주실 수 있나요?'
        },
      ],
      inputMessage : ''
    })

    const clickSend = function(){
      if(state.inputMessage=='') return
      const newMessage ={
        id: 'sendChatMessage',
        userId: state.curUserId,
        message: state.inputMessage
      }

      state.chats.push(newMessage)
      ws.send(JSON.stringify(newMessage))
      state.inputMessage =''
    }

    onMounted(()=>{
      store.dispatch('root/requestUserInfo')
        .then(res=>{
          console.log(res)
          state.curUserId= res.data.userId
          console.log(state.curUserId)
        })
    })

    onUpdated(()=>{
      let scroll = document.getElementById("chat-container")
      console.log("updated"+scroll.scrollTop)
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
#chat-container{
  overflow-y: scroll;
  height: 70vh;
}
#send-container{
  height: 30vh;
}
</style>
