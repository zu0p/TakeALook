<template>
  <div class="chat-list" :style="{background: userListColor.userList.bg}">
    <table style="padding-top: 5px;">
      <tbody>
        <!-- <tr v-for="가장 최근 메세지 in 메세지 방 리스트" :key="메세지 방.id"> -->
          <!-- 사진을 가로로 중간에 위치 -->
          <td style="text-align: center;">
            <img :src="user.imageUrl" class="img-msg" />
          </td>
          <td class="user-element" :style="{color: userListColor.userList.text}">
            {{ user.name }}
          </td>
        <!-- </tr> -->
      </tbody>
    </table>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
.chat-list {
  height: 100%;
  overflow: auto;
  padding-left: 5px;
  padding-top: 8px;
}
 /* 채팅중인 상대 유저 프로필 사진  */
.img-msg {
  border-radius: 50%;
  width: 50px;
  margin-right: 5px;
}
/* 프로필 사진 오른쪽에 나타나는 메세지 CSS 속성 */
.chat-element {
  font-size: 20px;
  vertical-align: middle;
}
</style>
