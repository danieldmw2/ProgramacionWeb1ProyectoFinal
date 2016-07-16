<#import "master.ftl" as layout/>
<@layout.master title="Share your images with us!">

<div class="container">
    <div class="row">
        <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1">

        <form method="GET" action="/upload">
            <button class="btn btn-primary">Agregar Imagen</button>
        </form>

        <br />
        <#list images as image>

                <div class="row">
                    <div class="col-lg-4">
                        <div class="well">
                            <#if image.usuario?has_content>
                                <strong>${image.titulo}</strong> publicado por <strong>${image.usuario.username}</strong>
                            <#else>
                                <strong>${image.titulo}</strong> publicado por <strong>Anon</strong>
                            </#if>
                            <a href="image/${image.id}"><img class="thumbnail img-responsive" src="data:image/png;base64,${image.base}"></a>
                        </div>
                    </div>

                    <div class="col-lg-3">
                        <div class="well">
                            <button class="btn btn-success" onclick="copyToClipboard('http://localhost:4567/image/${image.id}')">Get Link</button>
                        </div>
                    </div>
                </div>

                <button class="btn btn-primary" onclick="document.location = '/edit?id=${image.id}'">Editar</button>
                <button class="btn btn-danger" onclick="javascript:redirectWithData('/deleteImage', 'id', '${image.id}')">Borrar</button>
                <br><br>

        </#list>
            <!-- Pager -->
            <ul class="pager">
                <li class="next">
                    <a href="/home?p=${page}">Publicaciones más antiguas →</a>
                </li>
            </ul>

        </div>
    </div>
</div>



</@layout.master>