<#import "master.ftl" as layout/>
<@layout.master title="Comment on your friend's pictures!">

<!-- Page Content -->
<div class="container">

        <!-- Blog Post Content Column -->
        <div class="col-lg-12">

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

            <a href="/plainImage/${image.id}"><img class="center-block" width="600px" height="600px" src="data:image/png;base64,${image.getBase()}"></a>

            <!-- Post Content -->
            <h2>Descripcion:</h2>
            <div class="well"><p class="lead">${descripcion}</p></div>

            <div class="well center-block">
                <h4>Etiquetas:</h4>
                <div class="row">
                    <div class="col-lg-12">
                        <ul class="list-inline list-tags">
                            <#list etiquetas as etiqueta>
                                <li><button name="etiqueta" value="${etiqueta.etiqueta}" onclick="document.location='/home/${etiqueta.etiqueta}'" class="btn-danger"><span class="btn-xs">${etiqueta.etiqueta}</span></button></li>
                            </#list>
                        </ul>
                    </div>
                </div>
            </div>

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
                <div class="well">
                    <div class="media">
                        <a class="pull-left" href="#">
                        </a>
                        <div class="media-body">
                            <h3 class="media-heading"><a href="/MisFotos?user=${c.autor.username}">${c.autor.username}</a> en ${c.date} dijo: </h3>
                            <p></p>
                            <p class="lead">${c.comentario}</p>
                            <button class="btn btn-primary" onclick="javascript:commentLike('/likeComment', '${c.id}', '${image.id}')"><span class="glyphicon glyphicon-thumbs-up"></span> ${c.likes}</button>
                            <button class="btn btn-primary" onclick="javascript:commentLike('/dislikeComment', '${c.id}', '${image.id}')"><span class="glyphicon glyphicon-thumbs-down"></span> ${c.dislikes}</button>
                            <button class="btn btn-primary" onclick="javascript:commentLike('/deleteComment', '${c.id}', '${image.id}')"><span class="glyphicon glyphicon-remove"></span> Borrar</button>
                        </div>
                    </div>
                </div>
            </#list>

        <!-- Blog Sidebar Widgets Column -->

    <!-- /.row -->

    <hr>


</@layout.master>