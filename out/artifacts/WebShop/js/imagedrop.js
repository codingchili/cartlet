/**
 * Reads a dropped image file and inserts the image into the view,
 * the image is also added to the form so that it may be uploaded.
 *
 * @author Robin Duda
 *
 * References:
 * HTML Image FileDrop: Robert Gravelle,
 * http://www.htmlgoodies.com/html5/javascript/drag-files-into-the-browser-from-the-desktop-HTML5.html
 */


if (window.File && window.FileReader && window.FileList) {
    document.getElementById('drop-area').addEventListener('drop', onImageDrop);

    document.getElementById('drop-area').addEventListener('dragover', function (event) {
        event.preventDefault();
    });

    document.getElementById('drop-area').addEventListener('dragenter', function (event) {
        event.preventDefault();
    });
}


function onImageDrop(event) {
    event = event || window.event;
    event.preventDefault();

    // single file upload only.
    var file = event.dataTransfer.files[0];
    var reader = new FileReader();

    reader.addEventListener('loadend', function (event, file) {
        $('#product-image').attr("src", this.result);
        $('#upload-file').val(this.result);
    });

    reader.readAsDataURL(file);
}