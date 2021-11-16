from flask import Flask
from flask_jwt_extended import JWTManager
from controllers.ForecastingAPI import forecasting_api
from models.dto.AccountDto import AccountDto
from models.dto.FileDto import FileDto
from models.dto.ResultDto import ResultDto
from services.database import db

forecasting_app = Flask(__name__)

forecasting_app.config['DEBUG'] = True
forecasting_app.config["JWT_SECRET_KEY"] = "atsSigningKey"
forecasting_app.config['JWT_IDENTITY_CLAIM'] = 'jti'
forecasting_app.config['SQLALCHEMY_DATABASE_URI'] = 'postgresql://postgres:12345678@localhost/analysis_system'
forecasting_app.config["SQLALCHEMY_TRACK_MODIFICATIONS"] = False

forecasting_app.register_blueprint(forecasting_api)
jwt = JWTManager(forecasting_app)

db.init_app(forecasting_app)

with forecasting_app.app_context():
    db.create_all()

if __name__ == "__main__":
    forecasting_app.run(host='0.0.0.0', port=5002)


