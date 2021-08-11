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
      <el-form-item>
        <el-input-number v-model="state.proposePrice" @change="handleChange" :step="state.gap"></el-input-number>
      </el-form-item>
      <el-form-item>
        <el-button v-if="state.isSeller" @click="countDown">카운트 시작</el-button>
        <el-button v-if="!state.isSeller" @click="propose">가격 제안</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
import { reactive } from '@vue/reactivity'
import ws from '../js/webSocket'
import {watchEffect} from '@vue/runtime-core'

export default {
  name: 'propose-form',
  props: ['state', 'updatePrice', 'successTrade'],
  setup(props, {emit}){
    const state = reactive({
      curPrice: 0,
      proposePrice: 0,
      gap: 10,
      count: 3,
      isSeller: props.state.role=='seller'?true:false
    });

    const updated = watchEffect(()=>{
      if(!props.successTrade.flag){
        console.log(props.updatePrice)
        state.curPrice = props.updatePrice.curPrice
        state.proposePrice = state.curPrice

        // count reset
        state.count = 3
      }
      else{
        // 낙찰성공
        if(props.state.name == props.successTrade.sellerId || props.state.name == props.successTrade.buyerId){
          emit('onSellerOrBuyer')
        }
        else{
          emit('onUser')
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
        alert("낙찰!!")
      }
      if(state.count>0){
        setTimeout(()=>{
          state.count-=1
          countDown()
        }, 1000)
      }
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
      console.log(req)
      ws.send(JSON.stringify(req))
    }

    return {state, handleChange, countDown, propose}
  }
}
</script>

<style>

</style>
