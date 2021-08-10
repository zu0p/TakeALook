<template>
  <div class="chat-header" >
    <slot>
      <el-button type="primary" icon="el-icon-back"></el-button>
      <div class="chat-header--title enabled">
        Fuxxing CSS
      </div>
    </slot>
    <div class="chat-header--close-button">
      <el-button type="primary" icon="el-icon-close"></el-button>
    </div>
  </div>
</template>

<script>
import CloseIcon from '@/assets/images/close-icon-big.png'
export default {
  props: {
    icons: {
      type: Object,
      default: function () {
        return {
          close: {
            img: CloseIcon,
            name: 'default'
          }
        }
      }
    },
  },
}
</script>

<style>
.chat-header {
  background-color: cornflowerblue;
  min-height: 75px;
  border-top-left-radius: 9px;
  border-top-right-radius: 9px;
  padding: 10px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.2);
  position: relative;
  box-sizing: border-box;
  display: flex;
}

.chat-header .back {

}

.chat-header--img {
  border-radius: 50%;
  align-self: center;
  padding: 10px;
}

.chat-header--title {
  align-self: center;
  padding: 10px;
  flex: 1;
  user-select: none;
  font-size: 20px;
}

.chat-header--title.enabled {
  cursor: pointer;
  border-radius: 5px;
}

.chat-header--title.enabled:hover {
  box-shadow: 0px 2px 5px rgba(0.2, 0.2, 0.5, 0.1);
}

.chat-header--close-button {
  width: 40px;
  align-self: center;
  height: 40px;
  margin-right: 10px;
  box-sizing: border-box;
  cursor: pointer;
  border-radius: 5px;
  margin-left: auto;
}

.chat-header--close-button:hover {
  box-shadow: 0px 2px 5px rgba(0.2, 0.2, 0.5, 0.1);
}

.chat-header--close-button img {
  width: 100%;
  height: 100%;
  padding: 13px;
  box-sizing: border-box;
}

@media (max-width: 450px) {
  .chat-header {
    border-radius: 0px;
  }
}
</style>
