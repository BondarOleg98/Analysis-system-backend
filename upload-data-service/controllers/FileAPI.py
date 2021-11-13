import flask
import logging
from flask import Blueprint, request
from flask_jwt_extended import jwt_required
from services.FileService import upload, get_files, delete_file, get_file

upload_api = Blueprint('upload_api', __name__)


@upload_api.route('/api/v1/uploadFile/<string:username>', methods=['POST'])
@jwt_required()
def upload_files(username):
    if request.files:
        try:
            upload(request.files, username)
            return flask.Response(status=200)
        except Exception as ex:
            logging.debug(ex)
            return flask.Response(status=500)
    return flask.Response(status=200)


@upload_api.route('/api/v1/getFiles/<string:username>', methods=['GET'])
@jwt_required()
def get_files_by_username(username):
    try:
        get_files(username)
        return flask.Response(status=200)
    except Exception as ex:
        logging.debug(ex)
        return flask.Response(status=500)


@upload_api.route('/api/v1/getFile/<string:id>', methods=['GET'])
@jwt_required()
def get_file_by_id(username):
    try:
        get_file(username)
        return flask.Response(status=200)
    except Exception as ex:
        logging.debug(ex)
        return flask.Response(status=500)


@upload_api.route('/api/v1/deleteFile/<string:id>', methods=['DELETE'])
@jwt_required()
def delete_file_by_id(id):
    try:
        delete_file(id)
        return flask.Response(status=200)
    except Exception as ex:
        logging.debug(ex)
        return flask.Response(status=500)
