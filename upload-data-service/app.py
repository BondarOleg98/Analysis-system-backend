from flask import Flask
from flask_jwt_extended import JWTManager
from controllers.FileAPI import upload_api
from models.dto.AccountDto import AccountDto
from models.dto.FileDto import FileDto
from services.database import db

upload_app = Flask(__name__)

upload_app.config['DEBUG'] = True
upload_app.config["JWT_SECRET_KEY"] = "atsSigningKey"
upload_app.config['JWT_IDENTITY_CLAIM'] = 'jti'
upload_app.config['SQLALCHEMY_DATABASE_URI'] = 'postgresql://postgres:12345678@localhost/analysis_system'
upload_app.config["SQLALCHEMY_TRACK_MODIFICATIONS"] = False

upload_app.register_blueprint(upload_api)
jwt = JWTManager(upload_app)

db.init_app(upload_app)

with upload_app.app_context():
    db.create_all()

if __name__ == "__main__":
    upload_app.run(host='0.0.0.0', port=5000)


