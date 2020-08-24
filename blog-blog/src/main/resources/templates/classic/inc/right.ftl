<div class="panel panel-default widget rightIn delay-1">
	<div class="panel-heading">
		<h3 class="panel-title"><i class="fa fa-area-chart"></i> 热门文章</h3>
	</div>
	<div class="panel-body">
		<@sidebar method="hottest_posts">
		<ul class="elasticstack" id="elasticstack">
			<#list results as row>
				<li>
					<img src="<@resource src=row.thumbnail/>" alt="image"/>
					<h5>
						<a href="${base}/post/${row.id}">${row.title}</a>
					</h5>
				</li>
			</#list>
		</ul>
		</@sidebar>
	</div>
</div>

<div class="panel panel-default widget rightIn delay-3">
	<div class="panel-heading">
		<h3 class="panel-title"><i class="fa fa-bars"></i> 最新发布</h3>
	</div>
	<div class="panel-body">
		<@sidebar method="latest_posts">
			<ul class="list">
				<#list results as row>
					<li>${row_index + 1}. <a href="${base}/post/${row.id}">${row.title}</a></li>
				</#list>
			</ul>
		</@sidebar>
	</div>
</div>

<div class="panel panel-default widget rightIn delay-5">
	<div class="panel-heading">
		<h3 class="panel-title"><i class="fa fa-tags"></i> 标签</h3>
	</div>
	<div class="panel-body">
		<@sidebar method="hottest_tags">
			<ul class="list">
				<#list results as row>
					<li style="display:inline">
						<a href="${base}/tag/${row.name}/"><i class="fa fa-tag"></i> ${row.name}</a>
						<span class="label label-default">${row.posts}</span>
					</li>
				</#list>
			</ul>
		</@sidebar>
	</div>
	<div class="text-center"><a href="${base}/tags" nav="tags"><i class="fa fa-hand-o-right">所有标签</i></a></div>
</div>
<script src="${options['site_version']}/dist/js/draggabilly.pkgd.min.js"></script>
<script src="${options['site_version']}/dist/js/elastiStack.js"></script>
<script>
	new ElastiStack( document.getElementById( 'elasticstack' ) );
</script>