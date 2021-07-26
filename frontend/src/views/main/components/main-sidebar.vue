<template>
  <el-row
    class="main-sidebar"
    :gutter="10"
    :style="{ 'width': width }">
    <div class="hide-on-small">
      <el-menu
        :default-active="String(state.activeIndex)"
        active-text-color="#ffd04b"
        class="el-menu-vertical-demo"
        @select="menuSelect">
        <el-menu-item v-for="(item, index) in state.menuItems" :key="index" :index="index.toString()">
          <i v-if="item.icon" :class="['ic', item.icon]"/>
          <span>{{ item.title }}</span>
        </el-menu-item>
      </el-menu>
    </div>
  </el-row>
</template>
<script>
import { reactive, computed, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'main-header',

  props: {
    width: {
      type: String,
      default: '240px'
    }
  },
  setup() {
    const store = useStore()
    const router = useRouter()

    const state = reactive({
      searchValue: null,
      menuItems: computed(() => {
        const MenuItems = store.getters['root/getMenus']
        let keys = Object.keys(MenuItems)
        let menuArray = []
        for (let i = 0; i < keys.length; ++i) {
          let menuObject = {}
          menuObject.key = keys[i]
          menuObject.icon = MenuItems[keys[i]].icon
          menuObject.title = MenuItems[keys[i]].name
          menuObject.path = MenuItems[keys[i]].path
          menuObject.children = MenuItems[keys[i]].children
          menuObject.hidden = MenuItems[keys[i]].hidden

          const token = localStorage.getItem('accessToken')
          if(MenuItems[keys[i]].hidden==true){ //로그인 유저에게만 보여지는 메뉴이면
            if(token!=null){ //로그인되어있다면
              menuArray.push(menuObject)
              MenuItems[keys[i]].hidden = false
              menuObject.hidden = false
            }
          }
          else{
            menuArray.push(menuObject)
          }
        }

        //console.log(menuArray)
        return menuArray
      }),
      activeIndex: computed(() => store.getters['root/getActiveMenuIndex'])
    })

    if (state.activeIndex === -1) {
      state.activeIndex = 0
      store.commit('root/setMenuActive', 0)
    }

    const menuSelect = function (param) {
      store.commit('root/setMenuActive', param)
      // const MenuItems = store.getters['root/getMenus']
      // let keys = Object.keys(MenuItems)
      // router.push({
      //   name: keys[param]
      // })
      router.push({
        name: state.menuItems[param].key
      })
    }

    return { state, menuSelect }
  }
}
</script>

<style>
.main-sidebar .el-menu {
  margin-top: 0;
  padding-left: 0;
}
.main-sidebar .hide-on-small {
  height: 100%;
}
.main-sidebar .hide-on-small .el-menu {
  height: 100%;
}
.main-sidebar .el-menu .el-menu-item {
  cursor: pointer;
  border-right: none;
}
.main-sidebar .el-menu .el-menu-item .ic {
  margin-right: 5px;
}
</style>
