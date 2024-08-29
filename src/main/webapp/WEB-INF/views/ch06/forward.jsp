<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%--
include 지시자의 역할 : 외부의 파일의 내용을 가져와서 삽입시켜줌
 --%>
<jsp:include page="/WEB-INF/views/common/top.jsp"/>

<div class="card">
	<div class="card-header">
		request에 저장된 데이터 이용
	</div>

	<div class="card-body">
		<div class="m-3">
			<p>mid: ${member1.mid}</p>
			<p>mname: ${member1.mname}</p>
			<p>memail: ${member1.memail}</p>
		</div>
		
		<hr>
		
		<div class="m-3">
			<p>mid: ${member1.mid}</p>
			<p>mname: ${member1.mname}</p>
			<p>memail: ${member1.memail}</p>
		</div>
	</div>		
</div>
<%--include 액션의 역할 : 외부의 JSP를 실행하고 그 결과를 삽입시켜줌--%>			
<jsp:include page="/WEB-INF/views/common/bottom.jsp"/>