<#import "master.ftl" as layout/>
<@layout.master title="Login">

<!-- Page Content -->
<div class="container">

    <div class="row">
        <div class="col-lg-8">

            <form role="form" method="POST" action="/login" class="inline">

                <div class="row">

                    <div class="col-lg-offset-4 col-lg-4 col-md-offset-4 col-md-4 col-sm-offset-4 col-col-sm-6 col-xs-12">
                        <strong>Nombre de Usuario:</strong>
                    </div>
                    <div class="col-lg-3 col-md-3 col-sm-5 col-xs-12">
                        <input name="username" type="text" required>
                    </div>
                </div>

                <div class="row">
                    <div class="col-lg-offset-4 col-lg-4 col-md-offset-4 col-md-4 col-sm-offset-4 col-col-sm-6 col-xs-12">
                        <strong>Contraseña:</strong>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-4 col-xs-12">
                        <input name="password" type="password" required>
                    </div>
                </div>

                <hr>
                <div class="row">
                    <div class="col-lg-offset-4 col-lg-4 col-md-offset-4 col-md-4 col-sm-offset-4 col-col-sm-6 col-xs-12">
                        <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-log-in"></span> Iniciar Sesión</button>
                    </div>
                </div>

            </form>

        </div>

    </div>

</div>

</@layout.master>