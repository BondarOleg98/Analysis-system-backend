from services.database import db


class FileDto(db.Model):
    __tablename__ = "files"
    id = db.Column(db.String(255), primary_key=True, nullable=False)
    path = db.Column(db.String(255), nullable=False)
    origin_name = db.Column(db.String(255), nullable=False)
    filename = db.Column(db.String(255), nullable=False)
    extension = db.Column(db.String(255), nullable=False)
    run = db.Column(db.Boolean, nullable=False)
    account_id = db.Column(db.String(255), db.ForeignKey('accounts.id'),
                           nullable=False)
    results = db.relationship('ResultDto', cascade="all,delete", backref='files',
                              lazy='dynamic'),

    def __init__(self, id, path, origin_name, filename, extension, run, account_id):
        self.id = id,
        self.path = path
        self.origin_name = origin_name,
        self.filename = filename
        self.extension = extension,
        self.run = run
        self.account_id = account_id,
