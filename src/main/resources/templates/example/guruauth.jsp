<%@ page import="java.util.Enumeration"%>


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

    <form:form action="guruauth" method="POST" modelAttribute="guruguruForm" enctype="multipart/form-data"
      autocomplete="off">

      <!-- EnterキーによるPOSTを無効化 -->
      <input type="submit" disabled style="display:none" />

      <div class="row">
        <div class="12">
        </div>
      </div>
      <div class="row">
        <div class="12">
          <form:label path="textfield001">テキストフィールド001</form:label>
          <form:input path="textfield001" cssClass="form-control" cssErrorClass="form-control is-invalid"
          disabled="${authMap.textfield001__disabled}" readonly="${authMap.textfield001__readonly}"/>
          <form:errors path="textfield001" cssClass="invalid-feedback" />
        </div>
      </div>







    </form:form>


    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>

<hr>
<%@ include file="/WEB-INF/views/common/includes/include-debug.jsp" %>

<footer class="main-footer">
  <div class="container">
    <div class="float-right">
      <a href="" class="btn btn-button mr-2">再描画</a>
      <button type="submit" class="btn btn-button" form="guruguruForm" name="1">送信</button>
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
