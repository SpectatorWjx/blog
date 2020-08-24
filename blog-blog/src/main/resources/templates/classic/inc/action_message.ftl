<#if message??>
	<script>
		layer.msg("${message}", {icon: 2});
		windows.reload();
	</script>
</#if>
<#if data??>
	<#if (data.code == 200)>
		<script>
			layer.msg("${data.message}", {icon: 1});
			windows.reload();
		</script>
	<#else>
		<script>
			layer.msg("${data.message}", {icon: 2});
			windows.reload();
		</script>
	</#if>
</#if>