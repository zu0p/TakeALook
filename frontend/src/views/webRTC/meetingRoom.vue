<template>
  <div id="room">
    <h2 id="room-header"></h2>

    <div id="participants">
      <el-container>
        <!-- <el-header><h1>거래 세션</h1></el-header>
        <el-divider></el-divider>
        <el-container> -->
          <el-main>
            <el-row :gutter="40">
              <el-col id="seller" :span="16"></el-col>
              <el-col id="propse" :span="8">
                <propse-form />
              </el-col>
            </el-row>
            <el-row id="buyer" :gutter="20">
            </el-row>
          </el-main>
          <el-aside id="chat" width="30%">
            <chat-form :room="state.room" :receiveMessage="receiveMsg" :endReceiveMsg="onReceiveMessageEnded"/>
          </el-aside>
        <!-- </el-container> -->
      </el-container>
    </div>
    <!-- <input type="button" id="button-leave" @click="leaveRoom"
      value="Leave room"> -->
    <el-button @click="leaveRoom" >Leave Room</el-button>
	</div>
</template>

<script>
import { reactive } from '@vue/reactivity'
import { useStore } from 'vuex'
import { onBeforeMount, onBeforeUnmount, onMounted } from '@vue/runtime-core'
import Participant from './js/participant'
import ws from './js/webSocket.js'
import PropseForm from './components/proposeForm.vue'
import ChatForm from './components/chatForm.vue'
import { useRouter } from 'vue-router'

export default {
  name: 'meeting-detail-page',
  components: {
    PropseForm,
    ChatForm
  },
  setup(props, {emit}){
    const store = useStore()
    const router = useRouter()
    const state = reactive({
      room:'',
      name:'',
      participants:{},
    })
    const receiveMsg = reactive({
      flag: false,
      message:{}
    })
    const kurentoUtils = require('kurento-utils')
    // const ws = new WebSocket(`wss://i5d101.p.ssafy.io:8443/groupcall`)
    ws.onmessage = function(message) {
      var parsedMessage = JSON.parse(message.data)
      console.log(parsedMessage.name)
      console.log(state.participants[parsedMessage.name])
      console.info('Received message: ' + message.data)

      switch (parsedMessage.id) {
      case 'existingParticipants':
        onExistingParticipants(parsedMessage)
        break
      case 'newParticipantArrived':
        onNewParticipant(parsedMessage)
        break
      case 'participantLeft':
        onParticipantLeft(parsedMessage)
        break
      case 'receiveVideoAnswer':
        receiveVideoResponse(parsedMessage)
        break
      case 'iceCandidate':
        state.participants[parsedMessage.name].rtcPeer.addIceCandidate(parsedMessage.candidate, function (error) {
              if (error) {
              console.error("Error adding candidate: " + error)
              return
              }
          })
          break
      case 'broadCastNewMessage':
        onReceiveMessage(message.data)
        break
      default:
        console.error('Unrecognized message', parsedMessage)
      }
    }
    // sessionStorage.setItem('ws', ws)

    onBeforeMount(()=> {
      let curUrl = document.location.href.split('/').reverse()
      state.room = curUrl[1]
      state.name = curUrl[0]
      const message = {
        id : 'joinRoom',
        name : state.name,
        room : state.room,
      }
      sendMessage(message)
    })

    onBeforeUnmount(()=>{
      ws.close()
    })

    const onNewParticipant = function(request){
      // console.log("누가 들어옴!!")
      receiveVideo(request.name)
    }

    const receiveVideo = function(sender) {
      console.log("sender: "+sender)
      var participant = new Participant(sender)
      // state.participants[sender] = participant
      var video = participant.getVideoElement()

      var options = {
          remoteVideo: video,
          onicecandidate: participant.onIceCandidate.bind(participant)
        }

      participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerRecvonly(options,
          function (error) {
            if(error) {
              return console.error(error)
            }
            this.generateOffer (participant.offerToReceiveVideo.bind(participant))
      })

      state.participants[sender] = participant
    }

    const receiveVideoResponse = function(result){
      state.participants[result.name].rtcPeer.processAnswer (result.sdpAnswer, function (error) {
          if (error) return console.error (error)
        })
    }

    const callResponse = function(message){
      if (message.response != 'accepted') {
        console.info('Call not accepted by peer. Closing call');
        stop()
      } else {
        webRtcPeer.processAnswer(message.sdpAnswer, function (error) {
          if (error) return console.error (error)
        })
      }
    }

    const onExistingParticipants = function(msg) {
      var constraints = {
        audio : true,
        video : {
          mandatory : {
            maxWidth : 320,
            maxFrameRate : 30,
            minFrameRate : 24
          }
        }
      }
      if(state.name==seller)
        constraints.video.mandatory.maxWidth = 720
      console.log(state.name + " registered in room " + state.room)
      var participant = new Participant(state.name)
      // participants[state.name] = participant
      var video = participant.getVideoElement()

      var options = {
            localVideo: video,
            mediaConstraints: constraints,
            onicecandidate: participant.onIceCandidate.bind(participant)
          }
      participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerSendonly(options,
        function (error) {
          if(error) {
            return console.error(error)
          }
          this.generateOffer (participant.offerToReceiveVideo.bind(participant))
      })
      state.participants[state.name] = participant
      console.log("1. "+state.participants[state.name])

      msg.data.forEach(receiveVideo)
    }

    const leaveRoom = function() {
      //alert("leave")
      sendMessage({
        id : 'leaveRoom'
      });

      for ( var key in state.participants) {
        state.participants[key].dispose()
      }
      ws.close()

      router.push({name:'home'})
    }

    const onParticipantLeft = function(request) {
      console.log('Participant ' + request.name + ' left')
      var participant = state.participants[request.name]
      participant.dispose()
      delete state.participants[request.name]
    }

    const sendMessage = function(message) {
      var jsonMessage = JSON.stringify(message)
      console.log('Sending message: ' + jsonMessage)
      waitForConnection(function(){
        ws.send(jsonMessage)
        if (typeof callback !== 'undefined') {
          callback();
        }
      }, 2000)
    }

    const waitForConnection = function (callback, interval) {
      if (ws.readyState === 1) {
          callback();
      } else {
          var that = this;
          // optional: implement backoff for interval here
          setTimeout(function () {
              console.log("websocket connecting...")
              waitForConnection(callback, interval);
          }, interval);
      }
    }

    const onReceiveMessage = function(msg){
      console.log("meetinRoom: "+msg)
      receiveMsg.flag = true
      receiveMsg.message = msg
    }

    const onReceiveMessageEnded = function(){
      console.log("flag: true -> false")
      receiveMsg.flag = false
    }
    return {ws, state, receiveMsg, onReceiveMessageEnded, onReceiveMessage, waitForConnection, onNewParticipant, receiveVideo, receiveVideoResponse, callResponse, onExistingParticipants, leaveRoom, onParticipantLeft, sendMessage}
  }

}
</script>

<style>
.bg-blue {
  background: #F0F8FF;
}
.grid-content {
  border-radius: 4px;
  min-height: 500px;
}
.participant video {
  border-radius: 4px;
}
#seller.participant video{
  height: 500px;
}
.el-aside{
  overflow: hidden;
}
</style>
