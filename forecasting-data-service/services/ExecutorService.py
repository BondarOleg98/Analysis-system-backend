import os
import uuid
import logging
from zipfile import ZipFile
from services.database import db
from models.dto.FileDto import FileDto
from models.dto.ResultDto import ResultDto

logging.basicConfig(level=logging.DEBUG)

FORECASTING_SCRIPT = "forecasting.py"


def execute(file_id):
    try:
        file = FileDto.query.filter_by(id=file_id).first_or_404(
            description='There is no data by file_id {}'.format(file_id))
        if extract_archive(file):
            filename, file_extension = os.path.splitext(file.filename)
            work_dir = os.path.join(file.path, filename, "forecasting")
            for root, dirs, files in os.walk(work_dir):
                if FORECASTING_SCRIPT in files:
                    save_result_data(file, run_script(work_dir))
                else:
                    raise Exception("There is no script file")
    except Exception:
        raise


def extract_archive(file):
    filename, file_extension = os.path.splitext(file.filename)
    try:
        with ZipFile(os.path.join(file.path, file.filename), 'r') as archive:
            print('Extracting all the files now...')
            archive.extractall(os.path.join(file.path, filename))
        return True
    except Exception:
        raise


def run_script(work_dir):
    try:
        os.chdir(work_dir)
        exec(open(os.path.join(work_dir, FORECASTING_SCRIPT)).read(), globals(), globals())
        return globals().get("result")
    except Exception:
        raise


def save_result_data(file, result):
    try:
        if ResultDto.query.filter_by(file_id=file.id).first():
            ResultDto.query.filter_by(file_id=file.id).update(dict(result=result))
            FileDto.query.filter_by(id=file.id).update(dict(run=True))
        else:
            entry = ResultDto(str(uuid.uuid4()), result, file.id, file.account_Id)
            FileDto.query.filter_by(id=file.id).update(dict(run=True))
            db.session.add(entry)
        db.session.commit()
    except Exception as ex:
        db.session.rollback()
        logging.debug(ex)
        raise


def get_result_executing(file_id):
    try:
        result_operation = ResultDto.query.filter_by(file_id=file_id).first_or_404(
            description='There is no data by file_id {}'.format(file_id))
        return result_operation.result
    except Exception as ex:
        logging.debug(ex)
        db.session.rollback()
        raise
