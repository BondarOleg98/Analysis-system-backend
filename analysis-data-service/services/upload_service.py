import os
from werkzeug.utils import secure_filename


def upload_files(files, config):
    for file in files:
        file = files[file]
        filename = secure_filename(file.filename)
        file.save(os.path.join(config, filename))
