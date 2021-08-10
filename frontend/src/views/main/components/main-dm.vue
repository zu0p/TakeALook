<template>

    <!-- <div
      v-if="showLauncher"
      class="sc-launcher"
      :class="{opened: isOpen}"
      :style="{backgroundColor: colors.launcher.bg}"
      @click.prevent="isOpen ? close() : openAndFocus()"
    > -->
    <div
      class="chat-launcher"
      :class="{opened: isOpen}"
      :style="{backgroundColor: colors.launcher.bg}"
      @click.prevent="isOpen"
    >
      <img v-if="info.isOpen" class="chat-closed-icon" :src="icons.close.img" :alt="icons.close.name" />
      <img v-else class="chat-open-icon" :src="icons.open.img" :alt="icons.open.name" style="size:10px"/>
    </div>
    <!-- info상태가 isOpen일 때 chat-list를 나타낸다. -->
    <chat-list v-if="info.isOpen"/>
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
        'header' in c &&
        'bg' in c.header &&
        'text' in c.header &&
        'launcher' in c &&
        'bg' in c.launcher &&
        'messageList' in c &&
        'bg' in c.messageList &&
        'sentMessage' in c &&
        'bg' in c.sentMessage &&
        'text' in c.sentMessage &&
        'receivedMessage' in c &&
        'bg' in c.receivedMessage &&
        'text' in c.receivedMessage &&
        'userInput' in c &&
        'bg' in c.userInput &&
        'text' in c.userInput,
      default: function () {
        return {
          header: {
            bg: '#4e8cff',
            text: '#ffffff'
          },
          launcher: {
            bg: '#4e8cff'
          },
          messageList: {
            bg: '#ffffff'
          },
          sentMessage: {
            bg: '#4e8cff',
            text: '#ffffff'
          },
          receivedMessage: {
            bg: '#f4f7f9',
            text: '#ffffff'
          },
          userInput: {
            bg: '#f4f7f9',
            text: '#565867'
          }
        }
      }
    },
    alwaysScrollToBottom: {
      type: Boolean,
      default: () => false
    },
    messageStyling: {
      type: Boolean,
      default: () => false
    },
    disableUserListToggle: {
      type: Boolean,
      default: false
    }
  },
  // computed: {
  //   chatWindowTitle() {
  //     if (this.title !== '') return this.title

  //     if (this.participants.length === 0) return 'You'
  //     if (this.participants.length > 1) return 'You, ' + this.participants[0].name + ' & others'

  //     return 'You & ' + this.participants[0].name
  //   }
  // },


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
