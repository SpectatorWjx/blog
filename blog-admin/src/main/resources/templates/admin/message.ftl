<#if message??>
<div class="alert alert-danger" id="message">
    <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
    ${message}
</div>
</#if>

<#if data??>
	<#if (data.code >= 0)>
    <div class="alert alert-success" id="success">
        <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
	${data.message}
    </div>
	<#else>
    <div class="alert alert-danger" id="danger">
        <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
	${data.message}
    </div>
	</#if>
</#if>
<script>
setTimeout("document.getElementById('message').style.display='none'", 1500);
setTimeout("document.getElementById('success').style.display='none'", 1500);
setTimeout("document.getElementById('danger').style.display='none'", 1500);
</script>