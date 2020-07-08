
<h1>최종 - Test1</h1>
<h1>ASSEMBLE - 업무 협업 웹</h1>

<h2>개요</h2>
<p>회사 내에서 사용하는 업무 협업 웹</p>
<h2>담당 업무</h2>
<p> 실시간 , 그룹 채팅 구현 및 검색</p>

<h2>설명</h2>
<p>각각의 회사가 개설 할 수 있는 자신들의 협업 공간은 ASSEMBLE 이라고 한다</p>
<p>각 ASSEMBLE은 최초 개설자를 제외 이메일로 초대받을시에만 가입이 가능하다</p>
<p>ASSEMBLE마다 아이디는 중복이 가능하며 다른 ASSEMBLE을 가입 할 때마다 해당 ASSEMBLE에 대해 가입해야한다 </p>
<p>아이디는 중복이 가능하지만 이메일은 중복이 불가능하다.</p>
<p>ASSEMBLE 내에는 그룹이 존재하며 자신이 속할 수 있는 그룹이 존재 공개그룹 비공개그룹이 존재한다.</p>
<p>비공개그룹은 그 그룹에 속해야만 글을 읽을 수 있다</p>
<p>업무 상황에 대해 멤버에 대한 요청,진행,완료 상태를 지정 할 수 있다.</p>
<p>북마크 기능 , 내가 쓴 글 기능</p>
<p>1:1채팅, 그룹채팅이 가능 하며 그룹 채팅은 2명이상의 회원을 선택해야 한다. 또한 메시지가 도착할시 채팅목록의 아이디 옆 파란불이 들어온다</p>
<p>검색이 가능하며 아이디,이름,내용에 대한 통합검색이다</p>


<h2>채팅 구현 방법</h2>
<p>Spring WebSocket  SockJS, STOMP 프로토콜을 이용</p>
<p>STOMP의 주소 구독방법을 통한 소켓통신 방법을 이용</p>
<br/>
<p>두개의 소켓을 지정</p>
<p>메시지가 도착할시 알림을 위한 소켓</p>
<p>-모든 회원에게 오는 메시지를 구독 하고 있는 소켓을 하나 따로 설정</p>
<p>-받는 회원이 자신이면 보낸 회원의 이름에 파란불이 들어오도록 알림 구성 </p>

<p>메시지 통신을 위한 소켓</p>
<p>-상대방과 자신의 회원번호를 정렬하여 </p>
<p>-고정된 주소를 구독하도록 구현</p>
<p>-채팅 할 상대방 이름을 클릭하면 채팅방이 생성되며 Ajax를 이용 이전에 대화한 내용을 db에서 불러옴</p>
<p>메시지를 전송시에 보낸 회원정보와 받을 회원 번호 고정된 주소를 담아서 db에 저장</p>




