export function setPlatform (state, isDesktop) {
  state.isDesktopPlatform = isDesktop
}

export function setMenuActive (state, index) {
	console.log('setMenuActive', state,index)
	const keys = Object.keys(state.menus)
	state.activeMenu = keys[index]
}

export function setMenuActiveMenuName (state, menuName) {
	state.activeMenu = menuName
}

export function SET_USER_INFO(state, userId){
  state.userId = userId
}
// actions에서 받은 SET_CONFERENCE_INFO commit 호출을 여기서 처리
export function SET_CONFERENCE_INFO(state, conferenceId){
  // 여기에 있는 것들이 기존에 작성한 내용들
  state.conferenceId = conferenceId
  state.form.title = title
  state.form.category = category
  state.form.price = price
  state.form.reservation_dateTime = reservation_dateTime
  state.form.desc = desc
  state.src.imageUrl = imageUrl
}

export function SET_ACCESSTOKEN(state, token){
  state.accessToken = token
}
