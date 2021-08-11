<template>
  <div style="text-align:left;" >
    <h1>{{ chat.productName }} <span style="font-size:13px"> {{ chat.price }}</span></h1>
    <span>{{ chat.receiver }} </span>
    <hr>
    <!-- <span style="font-size:13px"> {{ chat.tradeDate.slice(5,7) }}월 {{ chat.tradeDate.slice(8,10) }}일
      {{ chat.tradeDate.slice(11,13) }}시 {{ chat.tradeDate.slice(14,16) }}분
    </span> -->
  </div>
</template>

<script>
export default {
  props: ["chat"],
}
</script>

<style>

</style>
