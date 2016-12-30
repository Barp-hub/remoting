seajs.use([ 'bui/grid', 'bui/data', 'bui/overlay', 'bui/toolbar', 'bui/form' ], function(Grid, Data, Overlay, Toolbar, Form) {

	var Store = Data.Store;
	var columns = [ {
		title : '用户名',
		dataIndex : 'username',
		width : 150
	}, {
		title : '是否启用',
		dataIndex : 'enable',
		width : 100,
		renderer : function(value, row) {
			if (value == 0) {
				return "已启用";
			}
			return "未启用";
		}
	}, {
		title : '描述',
		dataIndex : 'description',
		width : 250
	}, {
		title : '操作',
		width : 180,
		renderer : function(value, row) {
			if (row.enable == 0) {
				return '<span class="grid-command btn-edit">编辑</span>&nbsp;&nbsp;<span class="grid-command btn-user-disable">禁用</span>&nbsp;&nbsp;<span class="grid-command btn-role">角色</span>';
			}
			return '<span class="grid-command btn-edit">编辑</span>&nbsp;&nbsp;<span class="grid-command btn-user-enable">启用</span>&nbsp;&nbsp;<span class="grid-command btn-role">角色</span>';
		}
	} ];
	var store = new Store({
		url : context + '/list',
		autoLoad : true,
		pageSize : 20
	});

	var grid = new Grid.Grid({
		render : '.grid',
		columns : columns,
		loadMask : true,
		store : store,
		width : '99.9%',
		height : 400,
		plugins : [ Grid.Plugins.CheckSelection ],
		tbar : {
			items : [ {
				btnCls : 'button button-small',
				text : '<i class="icon-plus"></i>添加',
				listeners : {
					'click' : function() {
						userAddForm.setFieldValue('username', '');
						userAddForm.setFieldValue('password', '');
						userAddForm.setFieldValue('confirm-password', '');
						userAddForm.setFieldValue('enable', '0');
						userAddForm.clearErrors();
						userAddDialog.show();
					}
				}
			} ]
		},
		bbar : {
			pagingBar : true
		}
	});

	grid.render();

	
	grid.on('itemclick', function(e) {
		if ($(e.domTarget).hasClass('btn-user-disable')) {
			BUI.Message.Alert('确认禁用？', 'warning');
			return false;
		}
		if ($(e.domTarget).hasClass('btn-user-enable')) {
			BUI.Message.Alert('确认启用？', 'warning');
			return false;
		}
		if ($(e.domTarget).hasClass('btn-edit')) {
			BUI.Message.Alert('编辑？', 'warning');
			return false;
		}
		if ($(e.domTarget).hasClass('btn-role')) {
			BUI.Message.Alert('角色？', 'warning');
			return false;
		}
	});

	var userAddForm = new Form.HForm({
		srcNode : '.user-add-form'
	}).render();

	var userAddDialog = new Overlay.Dialog({
		title : '添加用户',
		width : 540,
		height : 320,
		contentId : 'user-add-content',
		success : function() {
			userAddForm.valid();
			if (userAddForm.isValid()) {
				var data = userAddForm.serializeToObject();
				//添加用户...
			}
		}
	});

	var searchBar = new Toolbar.Bar({
		elCls : 'pull-right',
		items : [ {
			content : '<input name="username" class="username-search" placeholder="用户名"/>'
		}, {
			xclass : 'bar-item-button',
			btnCls : 'button button-small button-primary user-search-button',
			text : '搜索'
		} ]
	});
	grid.get('tbar').addChild(searchBar);

});