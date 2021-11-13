from flask import Flask
from flask_jwt_extended import JWTManager
from controllers.FileAPI import upload_api
from models.dto.AccountDto import AccountDto
from models.dto.FileDto import FileDto
from services.database import db

app = Flask(__name__)

app.config['DEBUG'] = True
app.config["JWT_SECRET_KEY"] = "atsSigningKey"
app.config['JWT_IDENTITY_CLAIM'] = 'jti'
app.config['SQLALCHEMY_DATABASE_URI'] = 'postgresql://postgres:12345678@localhost/analysis_system'
app.config["SQLALCHEMY_TRACK_MODIFICATIONS"] = False

app.register_blueprint(upload_api)
jwt = JWTManager(app)

db.init_app(app)

with app.app_context():
    db.create_all()

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=5000)


