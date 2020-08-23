<section class="content-header">
  <div class="container">
    <div class="row mb-2">
      <div class="col-18">
        <!-- ページタイトルを記入 -->
        <h4>入力チェックサンプル</h4>
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

    <form:form action="validation" modelAttribute="validationForm" enctype="multipart/form-data" autocomplete="off">

      <div class="row mt-4">
        <div class="col-18">
          <h4>必須チェック</h4>
        </div>
        <div class="col-18">
          <h4>真偽値</h4>
        </div>
      </div>
      <div class="row">
        <div class="col-5">
          <form:label path="textfield001">@NotNull</form:label>
          <form:input path="textfield001" cssClass="form-control" cssErrorClass="form-control is-invalid" />
          <form:errors path="textfield001" cssClass="invalid-feedback" />
        </div>

        <div class="col-5 offset-1">
          <form:label path="textfield002">@NotBlank</form:label>
          <form:input path="textfield002" cssClass="form-control" cssErrorClass="form-control is-invalid" />
          <form:errors path="textfield002" cssClass="invalid-feedback" />
        </div>

        <div class="col-5 offset-1">
          <form:label path="textfield003">@NotEmpty</form:label>
          <form:input path="textfield003" cssClass="form-control" cssErrorClass="form-control is-invalid" />
          <form:errors path="textfield003" cssClass="invalid-feedback" />
        </div>

        <div class="col-5 offset-1">
          <form:label path="textfield004">@AssertTrue</form:label>
          <form:input path="textfield004" cssClass="form-control" cssErrorClass="form-control is-invalid" />
          <form:errors path="textfield004" cssClass="invalid-feedback" />
        </div>

        <div class="col-5 offset-1">
          <form:label path="textfield005">@AssertFalse</form:label>
          <form:input path="textfield005" cssClass="form-control" cssErrorClass="form-control is-invalid" />
          <form:errors path="textfield005" cssClass="invalid-feedback" />
        </div>

        <div class="col-5 offset-1">
          <form:label path="textfield006">@Null</form:label>
          <form:input path="textfield006" cssClass="form-control" cssErrorClass="form-control is-invalid" />
          <form:errors path="textfield006" cssClass="invalid-feedback" />
        </div>
      </div>
      <div class="row mt-4">
        <div class="col-12">
          <h4>数値(整数)</h4>
        </div>
        <div class="col-12">
          <h4>数値(小数点)</h4>
        </div>
      </div>
      <div class="row">
        <div class="col-5">
          <form:label path="textfield007">@Min(2)</form:label>
          <form:input path="textfield007" cssClass="form-control text-right"
            cssErrorClass="form-control is-invalid text-right" />
          <form:errors path="textfield007" cssClass="invalid-feedback" />
        </div>

        <div class="col-5 offset-1">
          <form:label path="textfield008">@Max(10)</form:label>
          <form:input path="textfield008" cssClass="form-control text-right"
            cssErrorClass="form-control is-invalid text-right" />
          <form:errors path="textfield008" cssClass="invalid-feedback" />
        </div>

        <div class="col-5 offset-1">
          <form:label path="textfield011">@DecimalMin("1.0")</form:label>
          <form:input path="textfield011" cssClass="form-control text-right"
            cssErrorClass="form-control is-invalid text-right" />
          <form:errors path="textfield011" cssClass="invalid-feedback" />
        </div>

        <div class="col-5 offset-1">
          <form:label path="textfield012">@DecimalMax("99.9")</form:label>
          <form:input path="textfield012" cssClass="form-control text-right"
            cssErrorClass="form-control is-invalid text-right" />
          <form:errors path="textfield012" cssClass="invalid-feedback" />
        </div>

        <div class="col-11 offset-1">
          <form:label path="textfield017">@Digits(integer = 3,fraction = 2)</form:label>
          <form:input path="textfield017" cssClass="form-control text-right"
            cssErrorClass="form-control is-invalid text-right" />
          <form:errors path="textfield017" cssClass="invalid-feedback" />
        </div>

      </div>


      <div class="row mt-4">
        <div class="col-12">
          <h4>正の数、負の数</h4>
        </div>
        <div class="col-12">
        </div>
      </div>

      <div class="row">
        <div class="col-5">
          <form:label path="textfield013">@Positive</form:label>
          <form:input path="textfield013" cssClass="form-control text-right"
            cssErrorClass="form-control is-invalid text-right" />
          <form:errors path="textfield013" cssClass="invalid-feedback" />
        </div>

        <div class="col-5 offset-1">
          <form:label path="textfield014">@PositiveOrZero</form:label>
          <form:input path="textfield014" cssClass="form-control text-right"
            cssErrorClass="form-control is-invalid text-right" />
          <form:errors path="textfield014" cssClass="invalid-feedback" />
        </div>
        <div class="col-5 offset-1">
          <form:label path="textfield015">@Negative</form:label>
          <form:input path="textfield015" cssClass="form-control text-right"
            cssErrorClass="form-control is-invalid text-right" />
          <form:errors path="textfield015" cssClass="invalid-feedback" />
        </div>
        <div class="col-5 offset-1">
          <form:label path="textfield016">@NegativeOrZero</form:label>
          <form:input path="textfield016" cssClass="form-control text-right"
            cssErrorClass="form-control is-invalid text-right" />
          <form:errors path="textfield016" cssClass="invalid-feedback" />
        </div>
      </div>

      <div class="row mt-4">
        <div class="col-12">
          <h4>日付</h4>
        </div>
        <div class="col-12">
        </div>
      </div>
      <div class="row">
        <div class="col-5">
          <form:label path="localdate001">@Future</form:label>
          <div class="input-group" id="localdate001-input-group" data-target-input="nearest">
            <form:input path="localdate001" cssClass="form-control datetimepicker-input"
              cssErrorClass="form-control datetimepicker-input is-invalid" data-target="#localdate001-input-group" />
            <div class="input-group-append" data-target="#localdate001-input-group" data-toggle="datetimepicker">
              <div class="input-group-text"><i class="fa fa-calendar fa-fw"></i></div>
            </div>
          </div>
          <form:errors path="localdate001" cssClass="invalid-feedback" />
        </div>

        <div class="col-5 offset-1">
          <form:label path="localdate002">@FutureOrPresent</form:label>
          <div class="input-group" id="localdate002-input-group" data-target-input="nearest">
            <form:input path="localdate002" cssClass="form-control datetimepicker-input"
              cssErrorClass="form-control datetimepicker-input is-invalid" data-target="#localdate002-input-group" />
            <div class="input-group-append" data-target="#localdate002-input-group" data-toggle="datetimepicker">
              <div class="input-group-text"><i class="fa fa-calendar fa-fw"></i></div>
            </div>
          </div>
          <form:errors path="localdate002" cssClass="invalid-feedback" />
        </div>

        <div class="col-5 offset-1">
          <form:label path="localdate003">@Past</form:label>
          <div class="input-group" id="localdate003-input-group" data-target-input="nearest">
            <form:input path="localdate003" cssClass="form-control datetimepicker-input"
              cssErrorClass="form-control datetimepicker-input is-invalid" data-target="#localdate003-input-group" />
            <div class="input-group-append" data-target="#localdate003-input-group" data-toggle="datetimepicker">
              <div class="input-group-text"><i class="fa fa-calendar fa-fw"></i></div>
            </div>
          </div>
          <form:errors path="localdate003" cssClass="invalid-feedback" />
        </div>

        <div class="col-5 offset-1">
          <form:label path="localdate004">@PastOrPresent</form:label>
          <div class="input-group" id="localdate004-input-group" data-target-input="nearest">
            <form:input path="localdate004" cssClass="form-control datetimepicker-input"
              cssErrorClass="form-control datetimepicker-input is-invalid" data-target="#localdate004-input-group" />
            <div class="input-group-append" data-target="#localdate004-input-group" data-toggle="datetimepicker">
              <div class="input-group-text"><i class="fa fa-calendar fa-fw"></i></div>
            </div>
          </div>
          <form:errors path="localdate004" cssClass="invalid-feedback" />
        </div>

      </div>

      <div class="row mt-4">
        <div class="col-36">
          <h4>文字数(コードポイント考慮なし)、正規表現、メールアドレス</h4>
        </div>
      </div>
      <div class="row">
        <div class="col-5">
          <form:label path="textfield019">@Size(min=2, max=4)</form:label>
          <form:input path="textfield019" cssClass=" form-control " cssErrorClass="form-control is-invalid" />
          <form:errors path="textfield019" cssClass="invalid-feedback" />
        </div>
        <div class="col-5 offset-1">
          <form:label path="textfield020">@Size(min=2)</form:label>
          <form:input path="textfield020" cssClass=" form-control " cssErrorClass="form-control is-invalid" />
          <form:errors path="textfield020" cssClass="invalid-feedback" />
        </div>
        <div class="col-5 offset-1">
          <form:label path="textfield021">@Size(max=4)</form:label>
          <form:input path="textfield021" cssClass=" form-control " cssErrorClass="form-control is-invalid" />
          <form:errors path="textfield021" cssClass="invalid-feedback" />
        </div>
        <div class="col-5 offset-1">
          <form:label path="textfield018">@Email</form:label>
          <form:input path="textfield018" cssClass=" form-control " cssErrorClass="form-control is-invalid" />
          <form:errors path="textfield018" cssClass="invalid-feedback" />
        </div>
        <div class="col-5 offset-1">
          <form:label path="textfield009">@Pattern</form:label>
          <form:input path="textfield009" cssClass=" form-control " cssErrorClass="form-control is-invalid" />
          <form:errors path="textfield009" cssClass="invalid-feedback" />
        </div>
      </div>

      <div class="row mt-4">
        <div class="col-12">
          <h4>Hibernate固有</h4>
        </div>
        <div class="col-12">
        </div>
      </div>
      <div class="row">
        <p>
          @CreditCardNumber, @Currency, @DurationMax, @DurationMin, @EAN, @ISBN, @Length, @CodePointLength, @LuhnCheck,
          @Mod10Check, @Mod11Check, @Range, @SafeHtml, @ScriptAssert, @UniqueElements, @URL <br>
          (参照)https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/?v=6.1#validator-defineconstraints-hv-constraints
        </p>
      </div>
      <div class="row">
        <div class="col-5">
          <form:label path="textfield010">@SafeHtml</form:label>
          <form:input path="textfield010" cssClass=" form-control " cssErrorClass="form-control is-invalid" />
          <form:errors path="textfield010" cssClass="invalid-feedback" />
        </div>
        <div class="col-13 offset-1">
          <form:label path="textfield022">@CodePointLength(min = 1, max = 2)[サロゲートペア文字->𠀋]</form:label>
          <form:input path="textfield022" cssClass=" form-control " cssErrorClass="form-control is-invalid" />
          <form:errors path="textfield022" cssClass="invalid-feedback" />
        </div>
        <div class="col-5 offset-1">
          <form:label path="textfield023">@UniqueElements</form:label>
          <form:input path="textfield023" cssClass=" form-control " cssErrorClass="form-control is-invalid" />
          <form:errors path="textfield023" cssClass="invalid-feedback" />
        </div>

      </div>

      <div class="row mt-4">
        <div class="col-12">
          <h4>Terasoluna固有</h4>
        </div>
        <div class="col-12">
        </div>
      </div>
      <div class="row">
        <p>@ExistInCodeList, @ConsistOf, @ByteMin, @ByteMax, @ByteSize, @Compare,
        </p>
      </div>
      <div class="row">
        <div class="col-11">
          <form:label path="textfield999">@ExistInCodeList(codeListId = "CL_ORDERSTATUS")</form:label>
          <form:input path="textfield999" cssClass=" form-control " cssErrorClass="form-control is-invalid" />
          <form:errors path="textfield999" cssClass="invalid-feedback" />
        </div>
        <div class="col-11 offset-1">
          <form:label path="textfield998">Validate List Element</form:label>
          <form:input path="textfield998" cssClass=" form-control " cssErrorClass="form-control is-invalid" />
          <form:errors path="textfield998" cssClass="invalid-feedback" />
        </div>
      </div>
      <div class="row">
        <div class="col-8">
          <form:label path="textfield024">@ConsistOf({JIS_X_0208_Kanji.class})</form:label>
          <form:input path="textfield024" cssClass=" form-control " cssErrorClass="form-control is-invalid" />
          <form:errors path="textfield024" cssClass="invalid-feedback" />
        </div>
        <div class="col-8 offset-1">
          <form:label path="textfield025">@ConsistOf({FullHalfJIS2.class})</form:label>
          <form:input path="textfield025" cssClass=" form-control " cssErrorClass="form-control is-invalid" />
          <form:errors path="textfield025" cssClass="invalid-feedback" />
        </div>
      </div>
    </form:form>

    <!-- ここより上にメインコンテンツを記入 -->
  </div>
</section>


<footer class="main-footer p-3">
  <div class="container">
    <div class="float-right">
      <a href="" class="btn btn-button mr-2">再描画</a>
      <button type="submit" class="btn btn-button" form="validationForm">送信</button>
    </div>
    <div>
      <a href="#" class="btn-button">BackToTop</a>
    </div>
  </div>
</footer>
