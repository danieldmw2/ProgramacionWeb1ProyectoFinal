<#import "master.ftl" as layout/>
<@layout.master title="Edit your Image">

<div class="container">
    <div class="row">
        <div class="col-lg-12">
            <form role="form" method="POST" action="/editImage" class="inline">

                <div class="row">
                    <div class="col-lg-1 col-md-1 col-sm-3 col-xs-12">
                        Título:<span style="color: red">*</span>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-4 col-xs-12">
                        <input name="id" value="${image.id}" hidden></input>
                        <input name="title" type="text" value="${image.titulo}" required></input>
                    </div>
                </div>

                <br/>
                <div class="row">
                    <div class="col-lg-1 col-md-1 col-sm-3 col-xs-12">
                        Descripción:<span style="color: red">*</span>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-4 col-xs-12">
                        <textarea name="description" rows="4" cols="50">${image.descripcion}</textarea>
                    </div>
                </div>

                <br/>
                <div class="row">
                    <div class="col-lg-1 col-md-1 col-sm-3 col-xs-12">
                        Tags:<span style="color: red">*</span>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-4 col-xs-12">

                        <input name="tags" type="text" value="${etiquetas}" required>
                    </div>
                </div>


                <hr>
                <div class="row">
                    <div class="col-lg-offset-1 col-lg-3 col-md-offset-1 col-md-2 col-sm-offset-6 col-sm-4 col-xs-12">
                        <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-user"></span>
                            Submit
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</@layout.master>