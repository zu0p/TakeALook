<template>
  <div id="app">
 <input type="text" v-model="nickname" placeholder="닉네임을 입력해주세요" required autofocus>
<button id = "name" @click="connect">확인</button>
<div style = "width:300px; height: 300px; border:1px solid;">

  <div
      v-for="(item, idx) in chatList"
      :key="idx"
    >
      <h3>{{item.message}}</h3>
    </div>
</div>
<input
      v-model="msg"
      type="text"
    >
  <button @click="send">확인</button>
 </div>
</template>


<script>
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

export default {
  name: 'App',
  data() {
    return {
      msg:"",
      nickname: "",
      roomId: 1,
      chatList: []
    }
  },
  methods: {
     connect() {
      const serverURL = "http://localhost:8080/stomp/chat"
      var socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)

    this.stompClient.connect(
        {},
        frame => {
          this.connected = true;
          console.log('소켓 연결 성공', frame);

          this.stompClient.subscribe("/sub/chat/room/"+this.roomId, res => {
            console.log('sub 성공', res.body);
             this.chatList.push(JSON.parse(res.body))
          });
          var message = {
          roomId: this.roomId,
          writer: this.nickname,
        };

          this.stompClient.send("/pub/chat/enter", JSON.stringify(message), {});
        },
        error => {
          console.log('소켓 연결 실패', error);
          this.connected = false;
        }
      );
    },
     send() {
      this.msg = `${this.nickname} : ${this.msg}`
      console.log("Send message:" + this.message);
      if (this.stompClient && this.stompClient.connected) {
        var message = {
         roomId: this.roomId,
          message: this.msg,
           writer: this.nickname,
        };
        this.stompClient.send("/pub/chat/message", JSON.stringify(message), {});
      }
      this.msg=""
    }

  }
}
</script>
