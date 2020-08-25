// DataTables標準の要素にClassを追加
$.extend($.fn.dataTableExt.oStdClasses, {
    "sFilterInput": "form-control ml-0 mr-4",
    "sLengthSelect": "form-control",
});

// DataTablesの共通設定
$.extend($.fn.dataTable.defaults, {

    // サーバサイド処理を有効
    'serverSide': true,

    // 遅延読込
    "deferRender": true,

    // データ取得先のURLの初期値設定
    // flattenはSpringMVCでデータを受け取るためのクエリパラメータの変換処理
    'ajax': {
        'url': 'list/json',
        'data': flatten
    },

    // 検索条件・並び順などの状態をLocalStorageに保存し、再表示時に復元
    'stateSave': true,

    // グローバルフィルタ、ページ数切替、ページネーションボタン等の部品のレイアウトを調整
    'dom': "<'row'<'col-18 d-inline-flex'fB><'col-18 text-right'l>>"
        + "<'row'<'col-36'tr>>"
        + "<'row'<'col-15'i><'col-21'p>>",

    // 日本語化
    'language': {
        'sEmptyTable': 'テーブルにデータがありません',
        'sInfo': ' _TOTAL_ 件中 _START_ から _END_ まで表示',
        'sInfoEmpty': ' 0 件中 0 から 0 まで表示',
        'sInfoFiltered': '（全 _MAX_ 件より抽出）',
        'sInfoPostFix': '',
        'sInfoThousands': ',',
        'sLengthMenu': '_MENU_',
        'sLoadingRecords': '読み込み中...',
        'sProcessing': '処理中...',
        'sSearch': '',
        'sZeroRecords': '一致するレコードがありません',
        'oPaginate': {
            'sFirst': '<i class="fa fa-angle-double-left"></i>',
            'sLast': '<i class="fa fa-angle-double-right"></i>',
            'sNext': '<i class="fa fa-angle-right"></i>',
            'sPrevious': '<i class="fa fa-angle-left"></i>'
        },
        'oAria': {
            'sSortAscending': ': 列を昇順に並べ替えるにはアクティブにする',
            'sSortDescending': ': 列を降順に並べ替えるにはアクティブにする'
        },
        // ボタンに名前(フォントアイコン)を割り当てる
        'buttons': {
            'colvis': '<i class="fas fa-filter"></i>',
            'copy': '<i class="fa fa-copy fa-fw"></i>',
            'excel': '<i class="fa fa-file-excel-o fa-fw"></i>'
        },
    },
    // 列順変更許可
    'colReorder': true,

});


/**
 * 項目単位フィルタを追加する共通処理
 * @param {*} table
 */
function addFieldFilter(table) {

    // 項目単位フィルタのためのイベント処理を設定する。
    $('input.dataTables_column_filter').on('keyup', function (e, s) {
        if (e.which == 13 || this.value.length == 0) {
            var idx = $(this).attr('data-column');
            table.column(idx).search(this.value).draw();
//            fnRecoverFieldSearch(table);
        }
    });

    // 画面リロード時の復元
    fnRecoverFieldSearch(table);
}

/**
 * StateSave利用時に項目単位フィルタの検索値を検索フィールドに復元、StateSave=falseの場合は不要
 * @param {*} table
 */
function fnRecoverFieldSearch(table) {
    table.columns().every(function () {
        var idx = this.index();
        var str = this.search();
        if (str != undefined) {
            $('#col_filter_' + idx).val(str);
        }
    });
}

function addFieldFilter2(table) {

    // 項目単位フィルタのためのイベント処理を設定する。
    $('input.dataTables_column_filter').on('keyup', function (e, s) {
        if (e.which == 13 || this.value.length == 0) {
            var th = $(this).parents('th')[0];
            var visIndex = th.cellIndex;
            table.column(visIndex + ':visIdx').search(this.value).draw();
        }
    });

    fnRecoverFieldSearch(table);
//    restoreColumnFilterByColReOrder(table);

}


//列指定検索テキストボックスの値復元（ColReorder対応）
// function restoreColumnFilterByColReOrder(table) {

//     // 列の並び順を控えていく
//     var colOrder = table.colReorder.order();

//     // DataTablesで定義されている列数分だけループする
//     table.columns().every(function () {
//         var data = this.data();
//         var idx = this.index();
//         var str = table.columns(idx).search()[0];
//         $('#col' + colOrder[idx] + '_filter').val(str);
//     });
// }



// DataTables で列検索を行い再描画する（ColReorder対応）
// function filterColumnByColReOrder(table, col) {
//     var th = $(col).parents('th')[0];
//     var visIndex = th.cellIndex;
//     var str = $(col).val();
//     table.column(visIndex + ':visIdx').search(str).draw();
// }


/**
 * グローバルフィルターでEnterキーで送信
 * @param {*} table
 */
function fnGlobalFilterOnReturn(table) {
    $('.dataTables_filter input').unbind();
    $('.dataTables_filter input').bind('keyup', function (e) {
        if (e.which == 13 || this.value.length == 0) {
            table.search(this.value).draw();
        }
    });
}





/**
 * DataTables Ajaxクエリの修正
 * @param {*} params
 */
function flatten(params, settings) {

    params.columns.forEach(function (column, index) {
        params['columns[' + index + '].data'] = column.data;
        params['columns[' + index + '].name'] = column.name;
        params['columns[' + index + '].searchable'] = column.searchable;
        params['columns[' + index + '].orderable'] = column.orderable;
        params['columns[' + index + '].search.regex'] = column.search.regex;
        params['columns[' + index + '].search.value'] = column.search.value;
    });
    delete params.columns;

    params.order.forEach(function (order, index) {
        params['order[' + index + '].column'] = order.column;
        params['order[' + index + '].dir'] = order.dir;
    });
    delete params.order;

    params['search.regex'] = params.search.regex;
    params['search.value'] = params.search.value;
    delete params.search;

    return params;
}


/**
 * 状態クリアボタン
 */
$.fn.dataTable.ext.buttons.stateClear = {
    text: '<i class="fas fa-redo"></i>',
    titleAttr: '状態クリア',
    action: function (e, dt, node, config) {
        dt.state.clear();
        window.location.reload();
    }
};

/**
 * 再検索ボタン
 */
$.fn.dataTable.ext.buttons.refresh = {
    text: '<i class="fa fa-search fa-fw" aria-hidden="true"></i>',
    titleAttr: '再検索',
    action: function (e, dt, node, config) {
        dt.ajax.reload();
    }
};

/**
 * 新規作成ボタン
 */
$.fn.dataTable.ext.buttons.createnew = {
    text: '<i class="far fa-file"></i>',
    titleAttr: '新規作成',
    action: function (e, dt, node, config) {
        window.location.href = "create?form&distination=list";
    }
};
