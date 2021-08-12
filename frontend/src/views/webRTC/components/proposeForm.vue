<template>
  <el-card>
    <template #header>
      <span> 가격 제안하기 </span>
    </template>
    <el-form label-position="leftPosition">
      <el-form-item label="현재가">
        <span>{{state.curPrice}}</span>
      </el-form-item>
      <el-form-item label="낙찰 카운트다운">
        <span>{{state.count}}</span>
      </el-form-item>
      <el-form-item  v-if="!state.isSeller">
        <el-input-number v-model="state.proposePrice" @change="handleChange" :step="state.gap"></el-input-number>
      </el-form-item>
      <el-form-item>
        <el-button v-if="state.isSeller" @click="requestCountStart">카운트 시작</el-button>
        <el-button v-if="!state.isSeller" @click="propose" :disabled="!state.isStart">가격 제안</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
import { reactive } from '@vue/reactivity'
import ws from '../js/webSocket'
import {onMounted, watchEffect} from '@vue/runtime-core'
import { useStore } from 'vuex'

export default {
  name: 'propose-form',
  props: ['state', 'updatePrice', 'successTrade'],
  setup(props, {emit}){
    const store = useStore()
    const state = reactive({
      curPrice: 0,
      proposePrice: 0,
      gap: props.state.priceGap,
      count: 30,
      isSeller: props.state.role=='seller'?true:false,
      isStart: props.state.isStart,
      firstStart: true
    });
    // onMounted(()=>{
    //   console.log(props.state)
    // })

    const updated = watchEffect(()=>{
      state.isSeller = props.state.role=='seller'?true:false
      state.gap = props.state.priceGap
      // state.isStart = props.state.isStart

      // 거래 시작 == count start
      if(props.state.isStart && state.firstStart){
        state.firstStart = false
        state.isStart = props.state.isStart
        countDown()
        //emit('started')
      }


      // 가격제안
      if(!props.successTrade.flag){
        //console.log(props.updatePrice)
        let prevPrive = state.curPrice
        state.curPrice = props.updatePrice.curPrice

        if(prevPrive != state.curPrice){
          state.count = 30
          state.proposePrice = state.curPrice
        }
      }
      else{
        // 낙찰성공
        if(props.state.name == props.successTrade.sellerId || props.state.name == props.successTrade.buyerId){
          console.log('seller: '+props.successTrade.sellerId)
          console.log('buyer: '+props.successTrade.buyerId)
          emit('onSellerOrBuyer')
          ws.close()
        }
        else{
          console.log('seller: '+props.successTrade.sellerId)
          console.log('buyer: '+props.successTrade.buyerId)
          emit('onUser')
          ws.close()
        }
      }
    })

    const countDown = function(){
      if(state.count == 0){
        if(props.state.role =='seller'){ //seller인 경우
        //console.log(props.name)
          const req = {
            id: 'tradeClosed',
            room: props.state.room
          }
          console.log(req)
          ws.send(JSON.stringify(req))
        }
        // alert("낙찰!!")
        console.log("낙찰")
      }
      if(state.count>0){
        setTimeout(()=>{
          state.count-=1
          // console.log(state.count)
          countDown()
        }, 1000)
      }
    }

    const requestCountStart = function(){
      const reqWS = {
        id: 'startRequestCount',
        room: props.state.room
      }
      //console.log(reqWS)
      ws.send(JSON.stringify(reqWS))

      const reqBE = props.state.room
      store.dispatch('root/requestSetStarted', reqBE)
    }

    const handleChange = function(value) {
      //console.log(value);
    }

    const propose = function(){
      state.curPrice = state.proposePrice
      const req = {
        id: 'proposePrice',
        room: props.state.room,
        name: props.state.name,
        price: state.proposePrice
      }
      // console.log(req)
      ws.send(JSON.stringify(req))
    }

    return {state, handleChange, countDown, requestCountStart, propose}
  }
}
</script>

<style>

</style>
