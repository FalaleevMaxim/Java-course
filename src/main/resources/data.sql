INSERT INTO Countries (code, name) VALUES (643, 'Российская Федерация');
INSERT INTO Countries (code, name) VALUES (804, 'Украина');
INSERT INTO Countries (code, name) VALUES (840, 'Соединенные Штаты Америки');

INSERT INTO Documents (code, name) VALUES (21, 'Паспорт гражданина Российской Федерации');
INSERT INTO Documents (code, name) VALUES (10, 'Паспорт иностранного гражданина');

INSERT INTO Organizations (name, fullName, address, inn, kpp, phone, active) VALUES
  ('OrgToGet', 'Organization for get test', 'Address0', '0987654321', '987654321', '33-22-11', true),
  ('OrgToUpdate', 'Organization update test', 'Address3', '1653874632', '123456789', '11-22-33', true),
  ('OrgToFilter1', 'Organization 1 for filter test', 'Address1', '1635284673', '123456789', '11-22-33', true),
  ('OrgToFilter2', 'Organization 2 for filter test', 'Address2', '8476354789', '123456789', '11-22-33', true),
  ('OrgToFilter3', 'Organization 3 for filter test', 'Address3', '1234567890', '123456789', '11-22-33', true);

INSERT INTO Office (org_id, name, address, phone, active) VALUES
  (1, 'OfficeToGet', 'Address', '11-22-33', true),
  (2, 'OfficeToUpdate', 'Address', '33-22-11', true),
  (3, 'OfficeToFilter1', 'Address1', '12-34-56', true),
  (3, 'OfficeToFilter2', 'Address2', '65-43-21', true),
  (3, 'OfficeToFilter3', 'Address3', '98-87-76', true);

INSERT INTO Users (firstname, secondname, position, office_id, citizenship_code, identified) VALUES
  ('User1', 'NoDocGet', 'ForGetTest', 1, 643, true),
  ('User2', 'Get', 'ForGetTest', 1, 643, true),
  ('User3', 'Update', 'ForUpdateTest', 2, 804, true),
  ('User4', 'Filter1', 'ForFilterTest1', 4, 804, true),
  ('User5', 'Filter2', 'ForFilterTest1', 4, 840, true),
  ('User5', 'Filter3', 'ForFilterTest2', 4, 643, true);

INSERT INTO user_document (user_id, doc_code, docdate, docnumber) VALUES
  (2, 21, to_date('05/01/2016', 'DD/MM/YYYY'), '2534265374'),
  (3, 21, to_date('10/05/2010', 'DD/MM/YYYY'), '2636475763'),
  (4, 21, to_date('28/10/2017', 'DD/MM/YYYY'), '8574793726'),
  (5, 10, to_date('05/01/2015', 'DD/MM/YYYY'), '9574927294');