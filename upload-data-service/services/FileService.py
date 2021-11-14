import os
import uuid
import logging
from pathlib import Path
from werkzeug.utils import secure_filename
from services.database import db
from models.dto.AccountDto import AccountDto
from models.dto.FileDto import FileDto

UPLOAD_FOLDER = Path.home() / "uploaded"

logging.basicConfig(level=logging.DEBUG)


def upload(files, username):
    try:
        save_file_data(files, username)
    except Exception as ex:
        logging.debug(ex)
        raise


def save_file_data(files, username):
    try:
        for file in files:
            file = files[file]
            origin_name, file_extension = os.path.splitext(file.filename)
            filename = str(uuid.uuid4()) + '_' + secure_filename(file.filename)
            account = AccountDto.query.filter_by(user_name=username).first()

            entry = FileDto(str(uuid.uuid4()), str(UPLOAD_FOLDER), origin_name, filename, file_extension,
                            False, account.id)
            db.session.add(entry)
            db.session.commit()
            file.save(os.path.join(UPLOAD_FOLDER, filename))
    except Exception as ex:
        db.session.rollback()
        logging.debug(ex)
        raise


def get_files(username):
    try:
        account = AccountDto.query.filter_by(user_name=username).first_or_404(
            description='There is no data by username {}'.format(username))
        files = FileDto.query.filter_by(account_Id=account.id).all()

        file_list = []
        for i in range(len(files)):
            file = files[i]
            json_data = {"id": file.id, "name": file.origin_name,
                         "type": file.extension, "run": file.run}
            file_list.append(json_data)

        return file_list
    except Exception as ex:
        logging.debug(ex)
        db.session.rollback()
        raise


def get_file(file_id):
    try:
        file = FileDto.query.filter_by(id=file_id).first_or_404(
            description='There is no data with file_id {}'.format(file_id))

        json_data = {"id": file.id, "name": file.origin_name,
                     "type": file.extension, "run": file.run}
        return json_data
    except Exception as ex:
        logging.debug(ex)
        db.session.rollback()
        raise


def delete_file(file_id):
    try:
        file = FileDto.query.filter_by(id=file_id).first_or_404(
            description='There is no data with file_id {}'.format(file_id))
        os.remove(os.path.join(file.path, file.filename))
        db.session.delete(file)
        db.session.commit()
    except Exception as ex:
        db.session.rollback()
        logging.debug(ex)
        raise
