<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h3>Page Title</h3>
      </div>
      <div class="col-18">
        <!-- ページタイトル右の余白 -->
      </div>
    </div>
  </div>
</section>
<section class="content">
  <div class="container">
    <t:messagesPanel panelClassName="callout" panelTypeClassPrefix="callout-" disableHtmlEscape="true" />
    <!-- ここより下にメインコンテンツを記入 -->




    <form:form action="test5" modelAttribute="authenticationTestForm" enctype="multipart/form-data"
      autocomplete="off">


      <c:if test="${!authMap.test001__hidden}">
          <form:input path="test001" cssClass="form-control" cssErrorClass="form-control is-invalid"
          disabled="${authMap.test001__disabled}" readonly="${authMap.test001__readonly}" />
      </c:if>
      <c:if test="${authMap.test001__view}">
        ${f:h(authenticationTestForm.test001)}
      </c:if>





      </form:form>

    ${authMap}


    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>

<%@ include file="/WEB-INF/views/common/includes/include-debug.jsp" %>