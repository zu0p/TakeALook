<template>

  <el-row>
    <el-scrollbar class="chat-scrollbar" height="600px" ref="scrollbar">
      <el-card shadow="never" style="width: 90%" v-for="i in state.chats" :key="i">
        <div class="messages">
          <el-tag type="info" style="margin-right:10px"><span>{{i.userId}}</span></el-tag>
          <span>{{i.message}}</span>
        </div>
      </el-card>
    </el-scrollbar>
  </el-row>
  <el-row>
    <el-input v-model="state.inputMessage" style="width: 70%; margin: 0 3% 0 6px"></el-input>
    <el-button @click="clickSend">전송</el-button>
  </el-row>

</template>

<script>
import { reactive, ref } from '@vue/reactivity'
import { onUpdated } from '@vue/runtime-core'
export default {
  name: 'chat-form',
  setup(props, {emit}){
    const scrollbar = ref(null)
    let state = reactive({
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
      const newMessage ={
        userId: 'zu0p',
        message: state.inputMessage
      }

      state.chats.push(newMessage)
      state.inputMessage =''

    }

    onUpdated(()=>{
      console.log(scrollbar)
      scrollbar.setScrollTop(600)
    })

    return {state, clickSend}
  }
}
</script>

<style>
.messages{
  text-align: left;
}
.chat-scrollbar{
  width: 100%!important;
}
</style>
