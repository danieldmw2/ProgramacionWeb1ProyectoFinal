<#macro master title="Hello">
<!DOCTYPE html>
<html lang="en">
<head>
    <script src="https://www.dropbox.com/static/api/dropbox-datastores-1.0-latest.js"></script>
    <script type="text/javascript" src="/js/sound-mouseover.js"></script>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>${title}</title>

    <style>
        .loader {
            border: 16px solid #f3f3f3; /* Light grey */
            border-top: 16px solid #3498db; /* Blue */
            border-radius: 50%;
            width: 40px;
            height: 40px;
            animation: spin 2s linear infinite;
        }

        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
    </style>

    <!-- Bootstrap Core CSS -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">


    <!-- Custom Fonts -->
    <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800" rel="stylesheet" type="text/css">

</head>

<body style="padding-top: 65px">

<script language="javascript">

    function copyToClipboard(text, text2) {
        window.prompt("Copia el texto: Ctrl+C, Enter\n\nLink de la imagen en el blog ", text);
        window.prompt("Copia el texto: Ctrl+C, Enter\n\nLink de la imagen: ", text2);
    }

    var redirect = function(url) {
        var form = document.createElement('form');
        form.method = 'POST';
        form.action = url;
        form.submit();
    };

    var redirectWithData = function(url, name, value) {
        var form = document.createElement('form');
        form.method = 'POST';
        form.action = url;

        var input = document.createElement('input');
        input.name = name;
        input.value = value;
        input.type = 'hidden';

        form.appendChild(input);
        form.submit();
    };

    var commentLike = function(url, id, imageID) {
        var form = document.createElement('form');
        form.method = 'POST';
        form.action = url;

        var input = document.createElement('input');
        input.name = 'id';
        input.value = id;
        input.type = 'hidden';

        var input2 = document.createElement('input');
        input2.name = 'idImage';
        input2.value = imageID;
        input2.type = 'hidden';

        form.appendChild(input);
        form.appendChild(input2);
        form.submit();
    };
</script>

<div class="container">
    <div class="row">
        <nav class="navbar navbar-inverse navbar-custom navbar-fixed-top">
            <div class="container-fluid">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header page-scroll">
                    <button type="button" class="navbar-toggle" data-toggle="collapse"
                            data-target="#bs-example-navbar-collapse-1">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>


                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <form method="GET" action="/home">
                        <ul class="nav navbar-nav navbar-left">
                            <li>
                                <a href="/home" onmouseover="playclip();"> Home </a>
                            </li>
                            <li>
                                <#if user?has_content>
                                    <a>Hola ${user}!</a>
                                <#else>
                                    <a>Hola Anon!</a>
                                </#if>
                            </li>
                        </ul>
                        <ul class="nav navbar-nav navbar-right">
                            <li>
                                <a href="/upload" onmouseover="playclip();"><span class="glyphicon glyphicon-cloud-upload"></span> Agregar Imagen</a>
                            </li>
                            <li>
                                <a href="/zonaAdmin/listUsers" onmouseover="playclip();"><span class="glyphicon glyphicon-floppy-save"></span> Administración</a>
                            </li>
                            <li>
                                <a href="/MisFotos" onmouseover="playclip();"><span class="glyphicon glyphicon-camera"></span> Mis Fotos</a>
                            </li>
                            <li>
                                <a href="/sign-up" onmouseover="playclip();"><span class="glyphicon glyphicon-user"></span> ¡Regístrate!</a>
                            </li>
                            <li>
                                <#if iniciarSesion == "Cerrar Sesión">
                                    <a href="javascript:redirect('/logout')" onmouseover="playclip();" ><span class="glyphicon glyphicon-log-out"></span> ${iniciarSesion}</a>
                                <#else>
                                    <a href="/sign-in" onmouseover="playclip();"><span class="glyphicon glyphicon-log-in"></span> ${iniciarSesion}
                                    </a>
                                </#if>
                            </li>
                        </ul>
                    </form>
                </div>
                <!-- /.navbar-collapse -->
            </div>
            <!-- /.container -->
        </nav>
    </div>
</div>


<!-- Page Header -->
<!-- Set your background image for this header on the line below. -->
<header class="intro-header">
    <div class="container">
        <div class="row">
            <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1">
                <div class="site-heading">
                </div>
            </div>
        </div>
    </div>
</header>

    <#nested />

<audio>
    <source src="/audio/Anime SFX Naruto- Bunshin Seal.mp3">
    <source src="/audio/Anime SFX Naruto- Bunshin Seal.ogg">
</audio>
<div id="sounddiv"><bgsound id="sound"></div>
<!-- Bootstrap Core JavaScript -->
<script src="/js/bootstrap.min.js"></script>


</body>
</html>
</#macro>