import flask
import logging
from flask import Blueprint, make_response, jsonify
from flask_jwt_extended import jwt_required
from services.ExecutorService import execute, get_result_executing

upload_api = Blueprint('upload_api', __name__)
ANALYSIS_SCRIPT = "analyze.py"
CHART_SCRIPT = "build_chart.py"


@upload_api.route('/api/v1/executeAnalysis/<string:id>', methods=['POST'])
@jwt_required()
def execute_analysis_file(id):
    try:
        execute(id, ANALYSIS_SCRIPT)
        return flask.Response(status=200)
    except Exception as ex:
        logging.debug(ex)
        return flask.Response(status=500)


@upload_api.route('/api/v1/buildChart/<string:id>', methods=['POST'])
@jwt_required()
def execute_chart_file(id):
    try:
        result = execute(id, CHART_SCRIPT)
        return make_response(jsonify(result), 200)
    except Exception as ex:
        logging.debug(ex)
        return flask.Response(status=500)


@upload_api.route('/api/v1/getResultAnalysis/<string:id>', methods=['GET'])
@jwt_required()
def get_result_by_id(id):
    try:
        result = get_result_executing(id)
        return make_response(jsonify(result), 200)
    except Exception as ex:
        logging.debug(ex)
        return flask.Response(status=500)
