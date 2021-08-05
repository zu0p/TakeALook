<template>
    <div class="sc-message-list">

      <template >
        <slot name="user-avatar"> </slot>
      </template>
      <template >
        <slot name="text-message-body">
        </slot>
      </template>
      <template>
        <slot name="system-message-body">
        </slot>
      </template>
      <template>
        <slot name="text-message-toolbox">
        </slot>
      </template>

  </div>
</template>

<script>

export default {

}
</script>

<style scoped>
.sc-message-list {
  height: 80%;
  overflow-y: auto;
  background-size: 100%;
  padding: 40px 20px;
  background: lightgrey;
}
</style>
