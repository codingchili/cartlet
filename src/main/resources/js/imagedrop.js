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

let listenerDisabled = false;

function onImageDrop(event) {
    event = event || window.event;
    event.preventDefault();

    // single file upload only.
    let file = event.dataTransfer.files[0];
    let reader = new FileReader();

    reader.addEventListener('loadend', function (event, file) {
        listenerDisabled = true;
        document.getElementById("product-image").src = this.result;
        document.getElementById("upload-file").value = this.result;
        listenerDisabled = false;
    });

    reader.readAsDataURL(file);
}

document.getElementById('upload-file-dialog').onchange = function (evt) {
    let tgt = evt.target || window.event.srcElement;
    let files = tgt.files;

    if (!listenerDisabled) {
        if (FileReader && files && files.length) {

            let reader = new FileReader();
            reader.onload = function () {
                document.getElementById('product-image').src = this.result;
                document.getElementById("upload-file").value = this.result;
            };
            reader.readAsDataURL(files[0]);
        }
    }
};