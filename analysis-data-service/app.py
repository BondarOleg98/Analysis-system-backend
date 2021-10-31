import flask
from pathlib import Path
from flask import Flask, request

from services import upload_service

app = Flask(__name__)

UPLOAD_FOLDER = Path.home() / "uploaded"
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER


@app.route('/api/v1/uploadFile', methods=['POST'])
def upload_files():
    if request.method == 'POST':
        if request.files:
            try:
                upload_service.upload_files(request.files, app.config['UPLOAD_FOLDER'])
                return flask.Response(status=200)
            except Exception as ex:
                print(ex)
                return flask.Response(status=500)
    return flask.Response(status=400)


if __name__ == "__main__":
    app.run(host='0.0.0.0', port=5000)
