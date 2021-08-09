<template>

    <!-- <div
      v-if="showLauncher"
      class="sc-launcher"
      :class="{opened: isOpen}"
      :style="{backgroundColor: colors.launcher.bg}"
      @click.prevent="isOpen ? close() : openAndFocus()"
    > -->
    <div
      v-if="showLauncher"
      class="sc-launcher"
      :class="{opened: isOpen}"
      :style="{backgroundColor: colors.launcher.bg}"
      @click.prevent="isOpen"
    >
      <div v-if="newMessagesCount > 0 && !isOpen" class="sc-new-messsages-count">
        {{ newMessagesCount }}
      </div>
      <img v-if="info.isOpen" class="sc-closed-icon" :src="icons.close.img" :alt="icons.close.name" />
      <img v-else class="sc-open-icon" :src="icons.open.img" :alt="icons.open.name" style="size:10px"/>
    </div>
    <chat-window v-if="info.isOpen" style="z-index:1"/>
</template>

<script>

import CloseIcon from '@/assets/images/close-icon.png'
import OpenIcon from '@/assets/images/logo-no-bg.svg'
import ChatWindow from './chat-window.vue'
import { reactive } from '@vue/reactivity'

export default {
  components: {
    ChatWindow
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
      }
    },
    showEmoji: {
      type: Boolean,
      default: false
    },
    showEdition: {
      type: Boolean,
      default: false
    },
    showDeletion: {
      type: Boolean,
      default: false
    },
    isOpen: {
      type: Boolean,
      required: true
    },
    open: {
      type: Function,
      required: true
    },
    close: {
      type: Function,
      required: true
    },
    showFile: {
      type: Boolean,
      default: false
    },
    showLauncher: {
      type: Boolean,
      default: true
    },
    showCloseButton: {
      type: Boolean,
      default: true
    },
    showHeader: {
      type: Boolean,
      default: true
    },
    participants: {
      type: Array,
      required: true
    },
    title: {
      type: String,
      default: () => ''
    },
    titleImageUrl: {
      type: String,
      default: () => ''
    },
    onMessageWasSent: {
      type: Function,
      required: true
    },
    messageList: {
      type: Array,
      default: () => []
    },
    newMessagesCount: {
      type: Number,
      default: () => 0
    },
    placeholder: {
      type: String,
      default: 'Write a message...'
    },
    showTypingIndicator: {
      type: String,
      default: () => ''
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
  watch: {
    $props: {
      deep: true,
      immediate: true,
      handler(props) {
        for (const prop in props) {
          // store.setState(prop, props[prop])
        }
      }
    }
  },
  methods: {
    openAndFocus() {
      // this.open()
      this.$root.$emit('focusUserInput')
    },
  },

  setup(){
    const info = reactive({
      isOpen: false,
      close: false
    })

    const isOpen = function () {
      if (info.isOpen == false) {
        info.isOpen = true
        check()
      } else {
        info.isOpen = false
        close()
      } console.log(info.isOpen)
    }

    const close = function () {
      console.log("close")
      if (info.close == false) {
        info.close = true
      }
      info.close = false
    }

    const check = function () {
      console.log("check")
    }
    return {info, isOpen, close, check}
  }

}
</script>

<style>
.sc-open-icon{
  margin-top: 25%;
  width: 50%;
  height: 50%;
}
</style>
