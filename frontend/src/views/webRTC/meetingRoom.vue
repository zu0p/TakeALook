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
                <propse-form :state="state" :updatePrice="updatePrice" :successTrade="successTrade" @onSellerOrBuyer="matchingTrade" @onUser="failTrade"/>
              </el-col>
            </el-row>
            <el-row id="buyer" :gutter="20">
            </el-row>
          </el-main>
          <el-aside id="chat" width="30%">
            <chat-form :name="state.name" :room="state.room" :receiveMessage="receiveMsg" @endReceiveMsg="onReceiveMessageEnded"/>
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
import { reactive, ref } from '@vue/reactivity'
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
  // props:['isSeller', 'basePrice'],
  setup(props, {emit}){
    const store = useStore()
    const router = useRouter()
    const state = reactive({
      room:'',
      name:'',
      role: '',
      participants:{},
      isStart: false,
      productId: '',
      priceGap: 0,
      sellerName : ''
    })
    const receiveMsg = reactive({
      flag: false,
      message:{}
    })
    const updatePrice = reactive({
      curPrice: 0
    })
    const successTrade = reactive({
      flag: false,
      sellerId: '',
      buyerId: ''
    })
    const kurentoUtils = require('kurento-utils')
    // const ws = new WebSocket(`wss://i5d101.p.ssafy.io:8443/groupcall`)
    ws.onmessage = function(message) {
      var parsedMessage = JSON.parse(message.data)
      //console.log(parsedMessage.name)
      //console.log(state.participants[parsedMessage.name])
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
      case 'updatePrice':
        //console.log(message.data)
        onUpdatePrice(message.data)
        break
      case 'success':
        onSuccess(message.data)
        break
      case 'startCount':
        onStartCount(message.data)
        break
      default:
        console.error('Unrecognized message', parsedMessage)
      }
    }
    // sessionStorage.setItem('ws', ws)

    onBeforeMount(()=> {
      //console.log(props.basePrice)
      // state.role = props.isSeller==1?'seller':'buyer'
      let curUrl = document.location.href.split('/').reverse()
      state.room = curUrl[1]
      state.name = curUrl[0]
      let req = {
        room: state.room
      }
      //console.log(req)
      store.dispatch('root/requestTradeSectionInfo', req)
        .then(res=>{
          state.sellerName = res.data.sellerId
          if(res.data.sellerId == state.name)state.role = 'seller'
          else state.role = 'buyer'
          // console.log(state.role)

          state.productId = res.data.productId
          state.priceGap = res.data.priceGap
          updatePrice.curPrice = res.data.basePrice

          const message = {
            id : 'joinRoom',
            name : state.name,
            room : state.room,
            role : state.role,
            basePrice: updatePrice.curPrice
            // basePrice: props.basePrice
          }

          // updatePrice.curPrice = props.basePrice
          // console.log(message)
          sendMessage(message)
      })
    })

    onMounted(()=>{

    })

    onBeforeUnmount(()=>{
      // console.log("close!!")
      ws.close()
    })

    const onNewParticipant = function(request){
      // console.log("누가 들어옴!!")
      receiveVideo(request.name)
    }

    const receiveVideo = function(sender) {
      // console.log("sender: "+sender)
      let tmpRole = 'buyer'
      if(sender == state.sellerName)
        tmpRole = 'seller'
      var participant = new Participant(sender, tmpRole)
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
      if(state.role=='seller')
        constraints.video.mandatory.maxWidth = 720
      //console.log(state.name + " registered in room " + state.room)
      var participant = new Participant(state.name, state.role)

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
      //console.log("1. "+state.participants[state.name])

      msg.data.forEach(receiveVideo)
    }

    const leaveRoom = function() {
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
      //console.log("meetinRoom receive message: "+msg)
      receiveMsg.flag = true
      receiveMsg.message = msg
    }

    const onReceiveMessageEnded = function(){
      console.log("flag: true -> false")
      receiveMsg.flag = false
    }

    const onUpdatePrice = function(msg){
      //console.log(msg.currentPrice)
      const jsoned = JSON.parse(msg)
      //console.log(jsoned.currentPrice)
      updatePrice.curPrice = jsoned.currentPrice
      // console.log(updatePrice.curPrice)
    }

    const onSuccess = function(msg){
      console.log(msg)
      const jsoned = JSON.parse(msg)
      successTrade.flag = true
      successTrade.sellerId = jsoned.sellerId
      successTrade.buyerId = jsoned.buyerId
    }

    const matchingTrade = function(price){
      //DM으로 보내주기
      console.log('매칭 성공')
      const req = {
        seller: successTrade.sellerId,
        buyer: successTrade.buyerId,
        price: price,
        productId: state.productId,
        tradeDate: new Date().getTime()
      }
      // console.log(req)
      store.dispatch('root/requestProductSold', state.productId)
        .then(res=>{
          console.log(res)
        })
      store.dispatch('root/requestUpdateMaxPrice',{curPrice: updatePrice.curPrice, room: state.room})
        .then(res =>{
          console.log(res)
        })
      store.dispatch('root/requestMatching', req)
        .then(res=>{
          console.log(res)
          alert('거래 성공! DM에서 거래를 이어나가세요!')
          router.push({name:'home'})
        })

    }

    const failTrade = function(){
      //낙찰 실패한 유저들
      console.log('매칭 실패')
      alert('거래가 종료되었습니다.')
      router.push({name:'home'})
    }

    const onStartCount = function(){
      // seller가 count 시작 == 가격제안 시작
      state.isStart = true
    }

    return {ws, state, receiveMsg, updatePrice, successTrade, onStartCount, matchingTrade, failTrade, onSuccess, onUpdatePrice, onReceiveMessageEnded, onReceiveMessage, waitForConnection, onNewParticipant, receiveVideo, receiveVideoResponse, callResponse, onExistingParticipants, leaveRoom, onParticipantLeft, sendMessage}
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
.participant {
	border-radius: 4px;
	/* border: 2px groove; */
	margin-left: 5;
	margin-right: 5;
	/* width: 150; */
	text-align: center;
	overflow: hide;
	/* float: left; */
	padding: 5px;
	border-radius: 10px;
	-webkit-box-shadow: 0 0 200px rgba(255, 255, 255, 0.5), 0 1px 2px
		rgba(0, 0, 0, 0.3);
	box-shadow: 0 0 200px rgba(255, 255, 255, 0.5), 0 1px 2px
		rgba(0, 0, 0, 0.3);
	/*Transition*/
	-webkit-transition: all 0.3s linear;
	-moz-transition: all 0.3s linear;
	-o-transition: all 0.3s linear;
	transition: all 0.3s linear;
}
.participant video {
  border-radius: 4px;
}
#seller>video{
  width: 53vh;
  /* height: 400px; */
}
.el-aside{
  overflow: hidden;
}
#col-buyer{
  margin: 5px 5px 10px 5px;
}
#seller{
  margin: 0 0 15px 0;
}
#buyer{
  overflow-x: scroll;
}
</style>
