<template>
  <div class="chat-message-list" @scroll="handleScroll">
    <span>메세지리스트</span>
    <font-awesome-icon icon="phone"/>
  </div>
</template>

<script>

export default {

}
</script>

<style>
.chat-message-list {
  height: 80%;
  overflow-y: auto;
  background-size: 100%;
  padding: 40px 20px;
}
</style>
