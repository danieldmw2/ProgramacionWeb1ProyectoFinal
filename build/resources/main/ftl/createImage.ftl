<#import "master.ftl" as layout/>
<@layout.master title="Create your own albums!">

<div class="container">
    <div class="row">
        <div class="col-lg-12">
            <form id="myForm" role="form" method="POST" action="/insertImage" class="inline">

                <div class="row">
                    <div class="col-lg-1 col-md-1 col-sm-3 col-xs-12">
                        Título:<span style="color: red">*</span>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-4 col-xs-12">
                        <input name="title" type="text" required></input>
                    </div>
                </div>

                <br/>
                <div class="row">
                    <div class="col-lg-1 col-md-1 col-sm-3 col-xs-12">
                        Descripción:<span style="color: red">*</span>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-4 col-xs-12">
                        <textarea name="description" rows="4" cols="50" required></textarea>
                    </div>
                </div>

                <br/>
                <div class="row">
                    <div class="col-lg-1 col-md-1 col-sm-3 col-xs-12">
                        Tags:<span style="color: red">*</span>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-4 col-xs-12">

                        <input name="tags" type="text" required>
                    </div>
                </div>

                <br/>
                <div class="row">
                    <div class="col-lg-1 col-md-1 col-sm-3 col-xs-12">
                        Image:<span style="color: red">*</span>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-4 col-xs-12">
                        <input type="file" id="files" name="files[]" required/>
                    </div>
                </div>

                <hr>
                <div class="col-lg-offset-1 col-lg-3 col-md-offset-1 col-md-2 col-sm-offset-6 col-sm-4 col-xs-12">
                        <button id="button" type="submit" class="btn btn-primary" disabled><span class="glyphicon glyphicon-user"></span>
                            Submit
                        </button>
                        <div id="loading" class="loader"></div>
                </div>
            </form>

            <script>
                var client = new Dropbox.Client({token: "IIFa6SoYfrUAAAAAAADKNTUwd_ir6fs2bg25BB2wYU48NxbzmZG_xDPLV1wnkJvl"});
                document.getElementById('loading').style.visibility = "hidden";

                function handleFileSelect(evt) {
                    var f = evt.target.files[0];

                    // Only process image files.
                    if (!f.type.match('image.*'))
                        return;

                    var reader = new FileReader();

                    // Closure to capture the file information.
                    reader.onload = (function (theFile) {
                        return function (e) {
                            document.getElementById('loading').style.visibility = "visible";
                            client.authenticate(function () {
                                client.writeFile('image.txt', e.target.result, function () {
                                    document.getElementById('button').disabled = false;
                                    document.getElementById('loading').style.visibility = "hidden";
                                    return null;
                                });
                            });
                        };
                    })(f);

                    // Read in the image file as a data URL.
                    reader.readAsDataURL(f);


                }
                document.getElementById('files').addEventListener('change', handleFileSelect, false);
            </script>

        </div>
    </div>
</div>
</@layout.master>