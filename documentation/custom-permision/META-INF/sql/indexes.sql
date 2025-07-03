create index IX_126B01D3 on OFB_BulkOnbordingFile (unitId);
create index IX_13FA989A on OFB_BulkOnbordingFile (userId);
create index IX_4EACA54 on OFB_BulkOnbordingFile (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_7989D5D6 on OFB_BulkOnbordingFile (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_DDB4DBF7 on OFB_RegistrationRequest (bulkOnbordingFileId, status);
create index IX_F091AC74 on OFB_RegistrationRequest (emailAddress[$COLUMN_LENGTH:75$]);
create index IX_A41F44C8 on OFB_RegistrationRequest (groupId, status);
create index IX_50742EB2 on OFB_RegistrationRequest (loginId[$COLUMN_LENGTH:75$], status);
create index IX_445DD01D on OFB_RegistrationRequest (personalNo);
create index IX_7EAE02E on OFB_RegistrationRequest (recordNo);
create index IX_77DA64AE on OFB_RegistrationRequest (status);
create index IX_B9833A22 on OFB_RegistrationRequest (unit[$COLUMN_LENGTH:75$], state_[$COLUMN_LENGTH:75$]);
create index IX_B98ACB26 on OFB_RegistrationRequest (unit[$COLUMN_LENGTH:75$], status);
create index IX_B2EF2A1D on OFB_RegistrationRequest (unitId, state_[$COLUMN_LENGTH:75$]);
create index IX_B2F6BB21 on OFB_RegistrationRequest (unitId, status);
create index IX_19597E02 on OFB_RegistrationRequest (userId);
create index IX_7D980DBC on OFB_RegistrationRequest (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_B400D33E on OFB_RegistrationRequest (uuid_[$COLUMN_LENGTH:75$], groupId);