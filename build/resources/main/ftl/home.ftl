<#import "master.ftl" as layout/>
<@layout.master title="Share your images with us!">

<div class="container">
    <form method="GET" action="/upload">
        <button class="btn btn-primary">Agregar Imagen</button>
    </form>

    <br />
    <#list images as image>
        <div class="col-lg-4" >
            <div class="well">
                <#if image.usuario?has_content>
                    <strong>${image.titulo}</strong> publicado por <strong>${image.usuario.username}</strong>
                <#else>
                    <strong>${image.titulo}</strong> publicado por <strong>Anon</strong>
                </#if>
                <a href="/image/${image.id}"><img class="thumbnail" width="300px" height="300px" src="data:image/png;base64,${image.base}"></a>

                <div class="well">
                    <button class="btn btn-success" onclick="copyToClipboard('https://serene-grace.herokuapp.com/image/${image.id}', 'https://serene-grace.herokuapp.com/plainImage/${image.id}')">Get Link</button>
                    <button class="btn btn-primary" onclick="document.location = '/edit?id=${image.id}'">Editar</button>
                    <button class="btn btn-danger" onclick="javascript:redirectWithData('/deleteImage', 'id', '${image.id}')">Borrar</button>
                </div>
            </div>
        </div>
    </#list>
        <!-- Pager -->
        <ul class="pager">
            <li class="next">
                <a href="/home?p=${page}">Publicaciones más antiguas →</a>
            </li>
        </ul>
</div>



</@layout.master>