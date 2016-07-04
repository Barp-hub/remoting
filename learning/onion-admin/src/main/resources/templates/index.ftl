
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>表格</title>

  <link href="${context}/lib/bui/css/bs3/dpl.css" rel="stylesheet">
  <link href="${context}/lib/bui/css/bs3/bui.css" rel="stylesheet">
  
<script type="text/javascript">
	var context = '${context}';
</script>

</head>
<body>
<div class="row-fluid">
	<div class="span24">
		<div class="grid"></div>
	</div>
</div>
<div id="user-add-content" class="hide">
	<form class="user-add-form form-horizontal">
		<div class="row-fluid">
			<div class="control-group span24">
				<label class="control-label"><s>*</s>用户名：</label>
				<div class="controls">
					<input type="text" class="input-normal control-text"
						name="username"
						data-rules="{required : true,maxlength:20,minlength:5}">
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="control-group span24">
				<label class="control-label"><s>*</s>密码：</label>
				<div class="controls">
					<input type="password"
						class="input-normal control-text user-password" name="password"
						data-rules="{required : true,maxlength:20,minlength:5}">
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="control-group span24">
				<label class="control-label"><s>*</s>确认密码：</label>
				<div class="controls">
					<input type="password" class="input-normal control-text"
						name="confirm-password"
						data-rules="{required : true,maxlength:20,minlength:5,equalTo:'.user-password'}">
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="control-group span24">
				<label class="control-label">描述：</label>
				<div class="controls control-row4">
					<textarea class="input-large" type="text" name="description"></textarea>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="control-group span24">
				<label class="control-label"><s>*</s>是否启用</label>
				<div class="controls bui-form-field-radiolist"
					data-items="{'0':'启用','1':'禁用'}">
					<input name="enable" type="hidden" value="0">
				</div>
			</div>
		</div>
	</form>
</div>

  <script src="${context}/lib/bui/jquery-1.8.1.min.js"></script>
  <script src="${context}/lib/bui/bui-min.js"></script>
  <script src="${context}/script/index.js"></script>
</body>
</html>
