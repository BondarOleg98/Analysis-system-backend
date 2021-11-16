from services.database import db


class AccountDto(db.Model):
    __tablename__ = "accounts"
    id = db.Column(db.String(255), primary_key=True, nullable=False)
    user_name = db.Column(db.String(255), nullable=False)
    files = db.relationship('FileDto', backref='accounts',
                            lazy='dynamic')
