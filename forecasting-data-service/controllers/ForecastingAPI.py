import flask
import logging
from flask import Blueprint, make_response, jsonify
from flask_jwt_extended import jwt_required
from services.ExecutorService import execute, get_result_executing

forecasting_api = Blueprint('forecasting_api', __name__)


@forecasting_api.route('/api/v1/executeForecasting/<string:id>', methods=['POST'])
@jwt_required()
def execute_file(id):
    try:
        execute(id)
        return flask.Response(status=200)
    except Exception as ex:
        logging.debug(ex)
        return flask.Response(status=500)


@forecasting_api.route('/api/v1/getResultForecasting/<string:id>', methods=['GET'])
@jwt_required()
def get_result_by_id(id):
    try:
        result = get_result_executing(id)
        return make_response(jsonify(result), 200)
    except Exception as ex:
        logging.debug(ex)
        return flask.Response(status=500)

