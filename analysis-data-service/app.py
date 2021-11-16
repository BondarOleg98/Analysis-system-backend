from flask import Flask
from flask_jwt_extended import JWTManager
from controllers.AnalysisAPI import analysis_api
from models.dto.AccountDto import AccountDto
from models.dto.FileDto import FileDto
from models.dto.ResultDto import ResultDto
from services.database import db

analysis_app = Flask(__name__)

analysis_app.config['DEBUG'] = True
analysis_app.config["JWT_SECRET_KEY"] = "atsSigningKey"
analysis_app.config['JWT_IDENTITY_CLAIM'] = 'jti'
analysis_app.config['SQLALCHEMY_DATABASE_URI'] = 'postgresql://postgres:12345678@localhost/analysis_system'
analysis_app.config["SQLALCHEMY_TRACK_MODIFICATIONS"] = False

analysis_app.register_blueprint(analysis_api)
jwt = JWTManager(analysis_app)

db.init_app(analysis_app)

with analysis_app.app_context():
    db.create_all()

if __name__ == "__main__":
    analysis_app.run(host='0.0.0.0', port=5001)


