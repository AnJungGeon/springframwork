<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%--
include 지시자의 역할 : 외부의 파일의 내용을 가져와서 삽입시켜줌
 --%>
<jsp:include page="/WEB-INF/views/common/top.jsp"/>

<div class="card">
	<div class="card-header">
		요청 헤더값 얻기
	</div>

	<div class="card-body">
		<p>브라우저 종류: ${browser}</p>
		<p>클라이언트 IP: ${clientIp}</p>
	</div>		
</div>
<%--include 액션의 역할 : 외부의 JSP를 실행하고 그 결과를 삽입시켜줌--%>			
<jsp:include page="/WEB-INF/views/common/bottom.jsp"/>