<template>
    <div
      class="chat-launcher"
      :class="{opened: isOpen}"
      :style="{backgroundColor: colors.launcher.bg}"
      @click.prevent="isOpen()"
    >
      <img v-if="info.isOpen" class="chat-closed-icon" :src="icons.close.img" :alt="icons.close.name" />
      <img v-else class="chat-open-icon" :src="icons.open.img" :alt="icons.open.name" style="size:10px"/>
    </div>
    <!-- info상태가 isOpen일 때 chat-list를 나타낸다. -->
    <chat-list v-if="info.isOpen" @close="isOpen()" />
</template>

<script>

import CloseIcon from '@/assets/images/close-icon.png'
import OpenIcon from '@/assets/images/logo-no-bg.svg'
import ChatList from './chat-list.vue'
import ChatWindow from './chat-window.vue'
import { reactive } from '@vue/reactivity'

export default {
  components: {
    ChatList,
    ChatWindow,
  },
  props: {
    icons: {
      type: Object,
      default: function () {
        return {
          open: {
            img: OpenIcon,
            name: 'default'
          },
          close: {
            img: CloseIcon,
            name: 'default'
          }
        }
      },

    },

    colors: {
      type: Object,
      validator: (c) =>
        'launcher' in c &&
        'bg' in c.launcher,
      default: function () {
        return {
          launcher: {
            bg: '#4e8cff'
          },
        }
      }
    },
  },
  setup(props){
    console.log(props)
    const info = reactive({
      isOpen: false,
      close: false,
    })

    const isOpen = function () {
      if (info.isOpen == false) {
        info.isOpen = true
      } else {
        info.isOpen = false
      }
    }

    return { info, isOpen }
  }

}
</script>

<style>
.chat-open-icon{
  margin-top: 25%;
  width: 50%;
  height: 50%;
}
</style>
