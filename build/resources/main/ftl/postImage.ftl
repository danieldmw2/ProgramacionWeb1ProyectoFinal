<#import "master.ftl" as layout/>
<@layout.master title="Comment on your friend's pictures!">

<!-- Page Content -->
<div class="container">

    <div class="row">

        <!-- Blog Post Content Column -->
        <div class="col-lg-8">

            <!-- Blog Post -->

            <!-- Title -->
            <h1>${titulo}</h1>

            <!-- Author -->
            <p class="lead">
                <#if image.usuario?has_content>
                    Publicado por <a>${image.usuario.username}</a>
                <#else>
                    Publicado por <a>Anon</a>
                </#if>
            </p>
            <p class="lead">
                Visto ${image.views} veces
            </p>
            <p class="lead">
                Bandwidth Actual: ${image.bandwidth}Kb
                Bandwidth Total: ${image.bandwidthTotal}Mb
            </p>

            <hr>

            <a href="/image/${image.id}"><img src="data:image/png;base64,${image.getBase()}"></a>

            <!-- Post Content -->
            <p class="lead">${descripcion}</p>


            <button class="btn btn-primary" onclick="javascript:redirectWithData('/likeImage', 'id', '${image.id}')"><span class="glyphicon glyphicon-thumbs-up"></span> ${image.likes}</button>
            <button class="btn btn-primary" onclick="javascript:redirectWithData('/dislikeImage', 'id', '${image.id}')"><span class="glyphicon glyphicon-thumbs-down"></span> ${image.dislikes}</button>
            <hr>

            <!-- Blog Comments -->

            <!-- Comments Form -->
            <div class="well">
                <h4>Comenta:</h4>
                <form role="form" method="POST" action="/insertComment">
                    <div class="form-group">
                        <textarea class="form-control" name="comentario" rows="3" required></textarea>
                        <input type="hidden" name="idImage" value="${image.id}">
                    </div>
                    <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-comment"></span> Publicar comentario</button>
                </form>
            </div>
            <hr>

            <!-- Posted Comments -->

            <#list comentarios as c>
                <!-- Comment -->
                <div class="media">
                    <a class="pull-left" href="#">
                    </a>
                    <div class="media-body">
                        <h4 class="media-heading">${c.autor.username}
                        </h4>
                    ${c.comentario}
                        <button class="btn btn-primary" onclick="javascript:commentLike('/likeComment', '${c.id}', '${image.id}')"><span class="glyphicon glyphicon-thumbs-up"></span> ${c.likes}</button>
                        <button class="btn btn-primary" onclick="javascript:commentLike('/dislikeComment', '${c.id}', '${image.id}')"><span class="glyphicon glyphicon-thumbs-down"></span> ${c.dislikes}</button>
                        <button class="btn btn-primary" onclick="javascript:commentLike('/deleteComment', '${c.id}', '${image.id}')"><span class="glyphicon glyphicon-remove"></span> Borrar</button>
                    </div>
                </div>
            </#list>

        </div>

        <!-- Blog Sidebar Widgets Column -->
        <div class="col-md-4">

            <!-- Blog Categories Well -->
            <div class="well">
                <h4>Etiquetas:</h4>
                <div class="row">
                    <div class="col-lg-6">
                        <ul class="list-inline list-tags">
                            <#list etiquetas as etiqueta>
                                <li><button class="btn-danger"><span class="btn-xs">${etiqueta.etiqueta}</span></button></li>
                            </#list>
                        </ul>
                    </div>
                </div>
            </div>

        </div>

    </div>
    <!-- /.row -->

    <hr>


</@layout.master>