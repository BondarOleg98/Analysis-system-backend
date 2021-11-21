from services.database import db


class ResultDto(db.Model):
    __tablename__ = "result_operations"
    id = db.Column(db.String(255), primary_key=True, nullable=False)
    result = db.Column(db.JSON, nullable=False)
    account_id = db.Column(db.String(255), db.ForeignKey('accounts.id'),
                           nullable=False)
    file_id = db.Column(db.String(255), db.ForeignKey('files.id'),
                        nullable=False)

    def __init__(self, id, result, file_id, account_id):
        self.id = id,
        self.result = result
        self.file_id = file_id,
        self.account_id = account_id,
