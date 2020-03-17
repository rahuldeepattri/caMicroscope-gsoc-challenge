
function uploadImage() {

    let filesSelected = document.getElementById("fileToUpload");
    let formData = new FormData();
    formData.append('image', filesSelected.files[0]);

    const options = {
        method: 'POST',
        body: formData,
    };

    fetch(window.location.origin + '/server/api/splitRGB', options)
        .then((response) => response.json())
        .then((res) => {
            console.log('Success:', res);
            let imagesLoaded = []
            imagesLoaded.push(setImageOnCanvas('red-channel', res.red))
            imagesLoaded.push(setImageOnCanvas('green-channel', res.green))
            imagesLoaded.push(setImageOnCanvas('blue-channel', res.blue))
            return Promise.all(imagesLoaded);
        })
        .then((channels) => showCombined(channels))
        .catch((error) => {
            console.error('Error:', error);
            alert("Could not process this image, please report it");
        });


    async function setImageOnCanvas(id, src) {
        return new Promise((resolve, reject) => {
            var canvas = document.getElementById(id);
            var ctx = canvas.getContext('2d');
            let img = new Image()
            img.onload = () => {
                // get the scale
                var scale = Math.min(canvas.width / img.width, canvas.height / img.height);
                // get the top left position of the image
                var x = (canvas.width / 2) - (img.width / 2) * scale;
                var y = (canvas.height / 2) - (img.height / 2) * scale;
                ctx.drawImage(img, x, y, img.width * scale, img.height * scale);
                var imageData = ctx.getImageData(x, y, img.width * scale, img.height * scale);

                resolve(imageData)
            }
            img.onerror = reject
            img.src = src
        })
    }

    function showCombined(channels) {
        document.getElementById("results").style.display = "block";
        // Get the canvas and context
        var canvas = document.getElementById("combined-canvas");
        var context = canvas.getContext("2d");

        // Define the image dimensions
        var red = document.getElementById('red-channel');
        var width = red.width;
        var height = red.height;

        // Create an ImageData object
        var imagedata = context.createImageData(width, height);

        var redChannel = document.getElementById('red-channel').getContext('2d').getImageData(0, 0, canvas.width, canvas.height);
        var greenChannel = document.getElementById('green-channel').getContext('2d').getImageData(0, 0, canvas.width, canvas.height);
        var blueChannel = document.getElementById('blue-channel').getContext('2d').getImageData(0, 0, canvas.width, canvas.height);

        // Loop over all of the pixels
        for (var i = 0; i < imagedata.data.length; i += 1) {
            imagedata.data[i] = redChannel.data[i] + greenChannel.data[i] + blueChannel.data[i];
        }
        context.putImageData(imagedata, 0, 0)
    }
};

function showPreview(){
    document.getElementById("preview").style.display = "block";
    let filesSelected = document.getElementById("fileToUpload");

    let reader = new FileReader();

    reader.addEventListener("loadend", function (e) {
        var canvas = document.getElementById("previewImage");
        var ctx = canvas.getContext('2d');
        ctx.clearRect(0, 0, canvas.width, canvas.height);

        let img = new Image()
        img.src = e.srcElement.result;
        img.onload = () => {
            // get the scale
            var scale = Math.min(canvas.width / img.width, canvas.height / img.height);
            // get the top left position of the image
            var x = (canvas.width / 2) - (img.width / 2) * scale;
            var y = (canvas.height / 2) - (img.height / 2) * scale;
            ctx.drawImage(img, x, y, img.width * scale, img.height * scale);

        }
    })

    reader.readAsDataURL(filesSelected.files[0]);
}
