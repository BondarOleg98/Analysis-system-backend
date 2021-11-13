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


def save_file_data(files, username):
    try:
        for file in files:
            file = files[file]
            origin_name, file_extension = os.path.splitext(file.name)
            filename = secure_filename(origin_name)
            account = AccountDto.query.filter_by(user_name=username).first()

            entry = FileDto(str(uuid.uuid4()), UPLOAD_FOLDER, origin_name, filename, file_extension,
                            False, account.id)
            db.session.add(entry)
            file.save(os.path.join(UPLOAD_FOLDER, filename))
        db.session.commit()
    except Exception as ex:
        db.session.rollback()
        logging.debug(ex)


def get_files(username):
    try:
        account = AccountDto.query.filter_by(user_name=username).first_or_404(
            description='There is no data by username {}'.format(username))
        files = FileDto.query.filter_by(account_Id=account.id)
        return files
    except Exception as ex:
        logging.debug(ex)
        db.session.rollback()


def get_file(file_id):
    try:
        file = FileDto.query.filter_by(id=file_id).first_or_404(
            description='There is no data with file_id {}'.format(file_id))
        return file
    except Exception as ex:
        logging.debug(ex)
        db.session.rollback()


def delete_file(file_id):
    try:
        file = FileDto.query.filter_by(id=file_id).first_or_404(
            description='There is no data with file_id {}'.format(file_id))
        os.remove(os.path.join(file.path, file.origin_name))
        file.delete()
        db.session.commit()
    except Exception as ex:
        db.session.rollback()
        logging.debug(ex)
