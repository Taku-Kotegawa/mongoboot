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

    <form:form action="transactiontoken" modelAttribute="transactionTokenForm" autocomplete="off">

      ページ番号は「${transactionTokenForm.screenId}」

      <c:if test="${transactionTokenForm.screenId != 1}" var="text001__disabled" />
      <c:if test="${transactionTokenForm.screenId != 2}" var="text002__disabled" />
      <c:if test="${transactionTokenForm.screenId != 3}" var="text003__disabled" />
      <c:if test="${transactionTokenForm.screenId != 4}" var="text004__disabled" />

      <div class="form-group row">
        <form:label path="text001" cssClass="col-4 col-form-label">
          テキスト001
        </form:label>
        <form:input path="text001" cssClass="form-control col-10" disabled="${text001__disabled}" />
      </div>

      <div class="form-group row">
        <form:label path="text002" cssClass="col-4 col-form-label">
          テキスト002
        </form:label>
        <form:input path="text002" cssClass="form-control col-10" disabled="${text002__disabled}" />
      </div>

      <div class="form-group row">
        <form:label path="text003" cssClass="col-4 col-form-label">
          テキスト003
        </form:label>
        <form:input path="text003" cssClass="form-control col-10" disabled="${text003__disabled}" />
      </div>

      <div class="form-group row">
        <form:label path="text004" cssClass="col-4 col-form-label">
          テキスト004
        </form:label>
        <form:input path="text004" cssClass="form-control col-10" disabled="${text004__disabled}" />
      </div>

      <button type="submit" name="1" class="btn btn-button">1へ進む</button>
      <button type="submit" name="2" class="btn btn-button">2へ進む</button>
      <button type="submit" name="3" class="btn btn-button">3へ進む</button>
      <button type="submit" name="4" class="btn btn-button">4へ進む</button>
      <button type="submit" name="5" class="btn btn-button">リダイレクトして1に戻る</button>

    </form:form>

    <!------------------------------------------------------------------------->
    <hr>

    <!-- 暗黙オブジェクト(request)に格納されている値の項目一覧 -->
    <details>
      <summary>Request</summary>
      <table>
        <%
    Enumeration enum_request = request.getAttributeNames();
    while(enum_request.hasMoreElements()) {
      String key = (String)enum_request.nextElement();

      out.println("<tr>");
          out.println("<td>");
          out.println(key);
          out.println("</td>");
          out.println("<td>");
          out.println(request.getAttribute(key));
          out.println("</td>");
      out.println("</tr>");
    }
    %>
      </table>
    </details>

    <!-- 暗黙オブジェクト(session)に格納されている値の項目一覧 -->
    <details open>
      <summary>Session</summary>
      <table>
        <%
    Enumeration enum_session = session.getAttributeNames();
    while(enum_session.hasMoreElements()) {
      String key = (String)enum_session.nextElement();

      out.println("<tr>");
          out.println("<td>");
          out.println(key);
          out.println("</td>");
          out.println("<td>");
          out.println(session.getAttribute(key));
          out.println("</td>");
      out.println("</tr>");
    }
    %>
      </table>
    </details>

    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>
