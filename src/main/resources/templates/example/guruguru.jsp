<%@ page import="java.util.Enumeration"%>

<!-- Kendo ui core & combobx ------------------------------------------------------------------------------------------>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugins/kendoui.core/styles/kendo.common.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugins/kendoui.core/styles/kendo.default.min.css" />
<script src="${pageContext.request.contextPath}/resources/plugins/kendoui.core/js/kendo.ui.core.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/kendoui.core/js/kendo.combobox.min.js"></script>

<style>
  table td:nth-child(2) {
    word-break: break-all;
  }

  .dropdown-menu.combobox {
    overflow: auto;
    max-height: 250px;
    min-width: 100%;
    padding: 0;
  }

  .dropdown-menu.combobox li.autocomplete {
    padding: .2rem .4rem;
    min-height: 1.5rem;
  }

  .dropdown-menu.combobox li.autocomplete:hover {
    background-color: lightgray;
  }
</style>
<section class="content">
  <div class="container">
    <t:messagesPanel panelClassName="callout" panelTypeClassPrefix="callout-" disableHtmlEscape="true" />
    <!-- ここより下にメインコンテンツを記入 -->

    <form:form action="guruguru" method="POST" modelAttribute="guruguruForm" enctype="multipart/form-data"
      autocomplete="off">

      <!-- EnterキーによるPOSTを無効化 -->
      <input type="submit" disabled style="display:none" />

      <div class="row py-4">
        <a href="" class="btn btn-button mr-2">再描画</a>
        <button type="submit" class="btn btn-button">送信</button>
      </div>

      <table class="table table-inverse">
        <thead class="thead-inverse">
          <tr>
            <th>UI部品名</th>
            <th width="50%">フォーム</th>
            <th width="30%">表示</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>テキストフィールド</td>
            <td>
              <form:label path="textfield001">テキストフィールド001</form:label>
              <form:input path="textfield001" cssClass="form-control" cssErrorClass="form-control is-invalid" />
              <form:errors path="textfield001" cssClass="invalid-feedback" />
            </td>
            <td>
              ${f:h(guruguruForm.textfield001)}
            </td>
          </tr>
          <tr>
            <td>テキストフィールド(数値・整数)</td>
            <td>
              <form:label path="textfield002">テキストフィールド002(数値・整数)</form:label>
              <form:input path="textfield002" cssClass="form-control" cssErrorClass="form-control is-invalid" />
              <form:errors path="textfield002" cssClass="invalid-feedback" />
            </td>
            <td>
              ${f:h(guruguruForm.textfield002)}
            </td>
          </tr>
          <tr>
            <td>テキストフィールド(数値・小数あり)</td>
            <td>
              <form:label path="textfield003">テキストフィールド003(数値・小数あり)</form:label>
              <form:input path="textfield003" cssClass="form-control" cssErrorClass="form-control is-invalid" />
              <form:errors path="textfield003" cssClass="invalid-feedback" />
            </td>
            <td>
              ${f:h(guruguruForm.textfield003)}
            </td>
          </tr>
          <tr>
            <td>テキストフィールド(真偽値)</td>
            <td>
              <form:label path="textfield004">テキストフィールド004(真偽値)</form:label>
              <form:input path="textfield004" cssClass="form-control" cssErrorClass="form-control is-invalid" />
              <form:errors path="textfield004" cssClass="invalid-feedback" />
            </td>
            <td>
              ${f:h(guruguruForm.textfield004)}
            </td>
          </tr>
          <tr>
            <td>テキストフィールド(複数の値)</td>
            <td>
              <form:label path="textfield005">テキストフィールド005(複数の値)</form:label>
              <form:input path="textfield005" cssClass="form-control" cssErrorClass="form-control is-invalid" />
              <form:errors path="textfield005" cssClass="invalid-feedback" />
            </td>
            <td>
              ${f:h(guruguruForm.textfield005)}
            </td>
          </tr>
          <tr>
            <td>ラジオボタン</td>
            <td>
              <div>
                <form:label path="radio002">ラジオボタン1</form:label>
              </div>
              <div class="form-check-inline">
                <form:radiobutton path="radio001" cssClass="" cssErrorClass="is-invalid" value="true" />
                <form:label path="radio001" for="radio0011">はい</form:label>
                <form:radiobutton path="radio001" cssClass="" cssErrorClass="is-invalid" value="false" />
                <form:label path="radio001" for="radio0012">いいえ</form:label>
              </div>
              <div>
                <form:errors path="radio001" cssClass="invalid-feedback" />
              </div>
            </td>
            <td>
              ${f:h(guruguruForm.radio001)}
            </td>
          </tr>
          <tr>
            <td>ラジオボタンズ</td>
            <td>
              <div>
                <form:label path="radio002">ラジオボタン2</form:label>
              </div>
              <div class="form-check-inline">
                <form:radiobuttons path="radio002" cssClass="" cssErrorClass="is-invalid" items="${radio002Options}"
                  itemLabel="label" itemValue="value" />
              </div>
              <form:errors path="radio002" cssClass="invalid-feedback" />
            </td>
            <td>
              ${f:h(guruguruForm.radio002)}
            </td>
          </tr>
          <tr>
            <td>チェックボックス</td>
            <td>
              <div>
                <form:label path="checkbox001">チェックボックス1</form:label>
              </div>
              <div class="form-check-inline">
                <form:checkbox path="checkbox001" cssClass="" cssErrorClass="is-invalid" value="yes" />
                <form:label path="checkbox001" for="checkbox0011">利用規約に合意する</form:label>
              </div>
              <form:errors path="checkbox001" cssClass="invalid-feedback" />
            </td>
            <td>
              ${f:h(guruguruForm.checkbox001)}
            </td>
          </tr>
          <tr>
            <td>チェックボックス</td>
            <td>
              <div>
                <form:label path="checkbox002">チェックボックス1</form:label>
              </div>
              <div class="form-check-inline">
                <form:checkboxes path="checkbox002" cssClass="" cssErrorClass="is-invalid" items="${radio002Options}"
                  itemLabel="label" itemValue="value" />
              </div>
              <form:errors path="checkbox002" cssClass="invalid-feedback" />
            </td>
            <td>
              ${f:h(guruguruForm.checkbox002)}
            </td>
          </tr>
          <tr>
            <td>テキストエリア</td>
            <td>
              <div>
                <form:label path="textarea001">テキストエリア</form:label>
                <form:textarea path="textarea001" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  rows="5" />
                <form:errors path="textarea001" cssClass="invalid-feedback" />
              </div>
            </td>
            <td>
              ${f:br(f:h(guruguruForm.textarea001))}
            </td>
          </tr>

          <tr>
            <td>
              日付
            </td>
            <td>
              <form:label path="localdate001">日付</form:label>
              <div class="input-group" id="localdate001-input-group" data-target-input="nearest">
                <form:input path="localdate001" cssClass="form-control datetimepicker-input"
                  cssErrorClass="form-control datetimepicker-input is-invalid"
                  data-target="#localdate001-input-group" />
                <div class="input-group-append" data-target="#localdate001-input-group" data-toggle="datetimepicker">
                  <div class="input-group-text"><i class="fa fa-calendar fa-fw"></i></div>
                </div>
              </div>
              <form:errors path="localdate001" cssClass="invalid-feedback" />
            </td>
            <td>
              ${guruguruForm.localdate001.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))}
            </td>
          </tr>

          <tr>
            <td>
              日付と時刻
            </td>
            <td>
              <form:label path="localdatetime001">日付と時刻</form:label>
              <!-- DatePickerで時刻を扱うためにページ内でJavaScriptが必要-->
              <div class="input-group datetime" id="localdatetime001-input-group" data-target-input="nearest">
                <form:input path="localdatetime001" cssClass="form-control datetimepicker-input"
                  cssErrorClass="form-control datetimepicker-input is-invalid"
                  data-target="#localdatetime001-input-group" />
                <div class="input-group-append" data-target="#localdatetime001-input-group"
                  data-toggle="datetimepicker">
                  <div class="input-group-text"><i class="far fa-clock fa-fw"></i></div>
                </div>
              </div>
              <form:errors path="localdatetime001" cssClass="invalid-feedback" />
            </td>
            <td>
              ${guruguruForm.localdatetime001.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"))}
            </td>
          </tr>

          <tr>
            <td>セレクト</td>
            <td>
              <form:label path="select001">セレクト(単一の値)</form:label>
              <form:select path="select001" cssClass="form-control" cssErrorClass="form-control is-invalid"
                items="${selectoptionsblank}" itemLabel="label" itemValue="value" />
              <form:errors path="select001" cssClass="invalid-feedback" />
            </td>
            <td>
              ${f:h(guruguruForm.select001)}
            </td>
          </tr>

          <tr>
            <td>セレクト</td>
            <td>
              <form:label path="select005">セレクト(単一の値)</form:label>
              <form:select path="select005" cssClass="form-control" cssErrorClass="form-control is-invalid">
                <form:option value="" label=" - Select -" />
                <form:options items="${selectoptions}" itemLabel="label" itemValue="value" />
              </form:select>
              <form:errors path="select005" cssClass="invalid-feedback" />
            </td>
            <td>
              ${f:h(guruguruForm.select005)}
            </td>
          </tr>

          <tr>
            <td>セレクト</td>
            <td>
              <form:label path="orderStatus">セレクト(単一の値・CodeList)</form:label>
              <form:select path="orderStatus" cssClass="form-control" cssErrorClass="form-control is-invalid">
                <form:option value="" label="--Select--" />
                <form:options items="${CL_ORDERSTATUS}" />
              </form:select>
              <form:errors path="orderStatus" cssClass="invalid-feedback" />
            </td>
            <td>
              ${f:h(guruguruForm.orderStatus)}
            </td>
          </tr>

          <tr>
            <td>セレクト</td>
            <td>
              <form:label path="select002">セレクト(複数の値)</form:label>
              <form:select path="select002" multiple="true" cssClass="form-control"
                cssErrorClass="form-control is-invalid" items="${selectoptions}" itemLabel="label" itemValue="value" />
              <form:errors path="select002" cssClass="invalid-feedback" />
            </td>
            <td>
              ${f:h(guruguruForm.select002)}
            </td>
          </tr>

          <tr>
            <td>セレクト(Select2)</td>
            <td>
              <form:label path="select003">セレクト(Select2 単一の値)</form:label>
              <form:select path="select003" cssClass="form-control select2"
                cssErrorClass="form-control is-invalid select2" items="${selectoptionsblank}" itemLabel="label"
                itemValue="value" />
              <form:errors path="select003" cssClass="invalid-feedback" />
            </td>
            <td>
              ${f:h(guruguruForm.select003)}
            </td>
          </tr>

          <tr>
            <td>セレクト(Select2)</td>
            <td>
              <form:label path="select004">セレクト(Select2 複数の値)</form:label>
              <form:select path="select004" multiple="true" cssClass="form-control select2"
                cssErrorClass="form-control is-invalid select2" items="${selectoptions}" itemLabel="label"
                itemValue="value" />
              <form:errors path="select004" cssClass="invalid-feedback" />
            </td>
            <td>
              ${f:h(guruguruForm.select004)}
            </td>
          </tr>

          <tr>
            <td>セレクト(Select2-tags)</td>
            <td>
              <form:label path="combobox001">セレクト(Select2-tags)</form:label>
              <div>
                <form:select path="combobox001" cssClass="select2-tags form-control"
                  cssErrorClass="select2-tags form-control is-invalid" items="${selectoptionsblank}" itemLabel="label"
                  itemValue="value" />
              </div>
              <form:errors path="combobox001" cssClass="invalid-feedback" />
            </td>
            <td>
              ${f:h(guruguruForm.combobox001)}
            </td>
          </tr>

          <tr>
            <td>セレクト(Select2-tags)</td>
            <td>
              <form:label path="combobox002">セレクト(Select2-tags 複数の値)</form:label>
              <div>
                <form:select path="combobox002" multiple="true" cssClass="select2-tags form-control"
                  cssErrorClass="select2-tags form-control is-invalid" items="${selectoptions}" itemLabel="label"
                  itemValue="value" />
              </div>
              <form:errors path="combobox002" cssClass="invalid-feedback" />
            </td>
            <td>
              ${f:h(guruguruForm.combobox002)}
            </td>

          <tr>
            <td>
              コンボボックス
            </td>
            <td>
              <form:label path="combobox003">コンボボックス(Kendo UI)</form:label>
              <div>
                <form:select path="combobox003" cssClass="kendoComboBox" cssErrorClass="kendoComboBox is-invalid"
                  items="${selectoptionsblank}" itemLabel="label" itemValue="value" />
              </div>
              <form:errors path="combobox003" cssClass="invalid-feedback" />
            </td>
            <td>
              ${f:h(guruguruForm.combobox003)}
            </td>
          </tr>

          <tr>
            <td>
              コンボボックス
            </td>
            <td>
              <form:label path="combobox004">コンボボックス(datalist)</form:label>
              <div>
                <form:input path="combobox004" cssClass="form-control" cssErrorClass="form-control is-invalid"
                  list="keywords" />
                <datalist id="keywords">
                  <option value="ウィキペディア">
                  <option value="ウィルス対策">
                  <option value="ウィンドウズ">
                </datalist>
              </div>
              <form:errors path="combobox004" cssClass="invalid-feedback" />
            </td>
            <td>
              ${f:h(guruguruForm.combobox004)}
            </td>
          </tr>

          <tr>
            <td>
              コンボボックス
            </td>
            <td>
              <form:label path="combobox005">コンボボックス(Bootstrap-toggle & Filter)</form:label>
              <!-- @See https://www.w3schools.com/bootstrap/bootstrap_filters.asp -->
              <div class="dropdown input-group">
                <form:input path="combobox005" cssClass="form-control" cssErrorClass="form-control is-invalid" />
                <div class="input-group-append" data-toggle="dropdown">
                  <div class="input-group-text"><i class="fas fa-angle-down"></i></div>
                </div>
                <ul class="dropdown-menu dropdown-menu-right combobox" aria-labelledby="dropdownMenuButton">
                  <input class="form-control" id="myInput" type="text" placeholder="Filter..">
                  <c:forEach var="item" items="${selectoptionsblank}" varStatus="status">
                    <li class="autocomplete" data-autocomplete="${item.label}" data-target="combobox005">${item.label}
                    </li>
                  </c:forEach>
                </ul>
              </div>
              <form:errors path="combobox005" cssClass="invalid-feedback" />
            </td>
            <td>
              ${f:h(guruguruForm.combobox005)}
            </td>
          </tr>



          <tr>
            <td>ファイル
            </td>
            <td>
              <form:label path="file001">ファイル</form:label>
              <div>
                <form:input type="file" path="file001" cssClass="form-control-file"
                  cssErrorClass="form-control-file is-invalid" />
              </div>
              <form:errors path="file001" cssClass="invalid-feedback" />
            </td>
            <td>
              ${f:h(guruguruForm.file001.originalFilename)}
            </td>
          </tr>

          <tr>
            <td>
            </td>
            <td>
            </td>
            <td>
            </td>
          </tr>

        </tbody>
      </table>






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
    <details>
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

<footer class="main-footer">
  <div class="container">
    <div class="float-right">
      <a href="" class="btn btn-button mr-2">再描画</a>
      <button type="submit" class="btn btn-button" form="guruguruForm">送信</button>
    </div>
    <div>
      <a href="#" class="btn-button">BackToTop</a>
    </div>
  </div>
</footer>

<script>
  $(function () {

    $('.select2').select2();

    $('.select2[multiple]').select2({
      closeOnSelect: false
    });

    $('.select2-tags').select2({
      tags: true,
    });

    $('.datetime').datetimepicker({
      format: '',
    });

    $(".kendoComboBox").kendoComboBox();

    $("#myInput").on("keyup", function () {
      var value = $(this).val().toLowerCase();
      $(".dropdown-menu li").filter(function () {
        $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
      });
    });

  });


  $(document).on('ontouched click', '.autocomplete', function () {
    var text = $(this).data('autocomplete');
    var target = $(this).data('target');
    $('input[name="' + target + '"]').val(text);
  });
</script>
