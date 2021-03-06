IF EXISTS (SELECT name FROM sysindexes WHERE name = 'ACT_IDX_EXEC_BUSKEY') drop index ACT_RU_EXECUTION.ACT_IDX_EXEC_BUSKEY;
IF EXISTS (SELECT name FROM sysindexes WHERE name = 'ACT_IDX_TASK_CREATE') drop index ACT_RU_TASK.ACT_IDX_TASK_CREATE;
IF EXISTS (SELECT name FROM sysindexes WHERE name = 'ACT_IDX_IDENT_LNK_USER') drop index ACT_RU_IDENTITYLINK.ACT_IDX_IDENT_LNK_USER;
IF EXISTS (SELECT name FROM sysindexes WHERE name = 'ACT_IDX_IDENT_LNK_GROUP') drop index ACT_RU_IDENTITYLINK.ACT_IDX_IDENT_LNK_GROUP;
IF EXISTS (SELECT name FROM sysindexes WHERE name = 'ACT_IDX_VARIABLE_TASK_ID') drop index ACT_RU_VARIABLE.ACT_IDX_VARIABLE_TASK_ID;
IF EXISTS (SELECT name FROM sysindexes WHERE name = 'ACT_IDX_EVENT_SUBSCR_CONFIG_') drop index ACT_RU_EVENT_SUBSCR.ACT_IDX_EVENT_SUBSCR_CONFIG_;

if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_GE_BYTEARRAY') alter table ACT_GE_BYTEARRAY drop constraint ACT_FK_BYTEARR_DEPL;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RE_PROCDEF') alter table ACT_RE_PROCDEF drop constraint ACT_UNIQ_PROCDEF;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_EXECUTION') alter table ACT_RU_EXECUTION drop constraint ACT_FK_EXE_PROCDEF;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_EXECUTION') alter table ACT_RU_EXECUTION drop constraint ACT_FK_EXE_PARENT;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_EXECUTION') alter table ACT_RU_EXECUTION drop constraint ACT_FK_EXE_SUPER;  
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_IDENTITYLINK') alter table ACT_RU_IDENTITYLINK drop constraint ACT_FK_TSKASS_TASK;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_IDENTITYLINK') alter table ACT_RU_IDENTITYLINK drop constraint ACT_FK_ATHRZ_PROCEDEF;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_TASK') alter table ACT_RU_TASK drop constraint ACT_FK_TASK_EXE;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_TASK') alter table ACT_RU_TASK drop constraint ACT_FK_TASK_PROCINST;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_TASK') alter table ACT_RU_TASK drop constraint ACT_FK_TASK_PROCDEF;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_VARIABLE') alter table ACT_RU_VARIABLE drop constraint ACT_FK_VAR_EXE;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_VARIABLE') alter table ACT_RU_VARIABLE drop constraint ACT_FK_VAR_PROCINST;    
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_VARIABLE') alter table ACT_RU_VARIABLE drop constraint ACT_FK_VAR_BYTEARRAY;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_JOB') alter table ACT_RU_JOB drop constraint ACT_FK_JOB_EXCEPTION;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_EVENT_SUBSCR') alter table ACT_RU_EVENT_SUBSCR drop constraint ACT_FK_EVENT_EXEC;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RE_MODEL') alter table ACT_RE_MODEL drop constraint ACT_FK_MODEL_SOURCE;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RE_MODEL') alter table ACT_RE_MODEL drop constraint ACT_FK_MODEL_SOURCE_EXTRA;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RE_MODEL') alter table ACT_RE_MODEL drop constraint ACT_FK_MODEL_DEPLOYMENT;

IF EXISTS (SELECT name FROM sysindexes WHERE name = 'ACT_IDX_ATHRZ_PROCEDEF') drop index ACT_RU_IDENTITYLINK.ACT_IDX_ATHRZ_PROCEDEF;
    
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_GE_PROPERTY') drop table ACT_GE_PROPERTY;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_GE_BYTEARRAY') drop table ACT_GE_BYTEARRAY;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RE_PROCDEF') drop table ACT_RE_PROCDEF;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RE_DEPLOYMENT') drop table ACT_RE_DEPLOYMENT;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RE_MODEL') drop table ACT_RE_MODEL;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_IDENTITYLINK') drop table ACT_RU_IDENTITYLINK;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_TASK') drop table ACT_RU_TASK;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_VARIABLE') drop table ACT_RU_VARIABLE;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_EXECUTION') drop table ACT_RU_EXECUTION;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_EVENT_SUBSCR') drop table ACT_RU_EVENT_SUBSCR;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_RU_JOB') drop table ACT_RU_JOB;
IF EXISTS (SELECT name FROM sysindexes WHERE name = 'ACT_IDX_HI_PRO_INST_END') drop index ACT_HI_PROCINST.ACT_IDX_HI_PRO_INST_END;
IF EXISTS (SELECT name FROM sysindexes WHERE name = 'ACT_IDX_HI_PRO_I_BUSKEY') drop index ACT_HI_PROCINST.ACT_IDX_HI_PRO_I_BUSKEY;
IF EXISTS (SELECT name FROM sysindexes WHERE name = 'ACT_IDX_HI_ACT_INST_START') drop index ACT_HI_ACTINST.ACT_IDX_HI_ACT_INST_START;
IF EXISTS (SELECT name FROM sysindexes WHERE name = 'ACT_IDX_HI_ACT_INST_END') drop index ACT_HI_ACTINST.ACT_IDX_HI_ACT_INST_END;
IF EXISTS (SELECT name FROM sysindexes WHERE name = 'ACT_IDX_HI_DETAIL_PROC_INST') drop index ACT_HI_DETAIL.ACT_IDX_HI_DETAIL_PROC_INST;
IF EXISTS (SELECT name FROM sysindexes WHERE name = 'ACT_IDX_HI_DETAIL_ACT_INST') drop index ACT_HI_DETAIL.ACT_IDX_HI_DETAIL_ACT_INST;
IF EXISTS (SELECT name FROM sysindexes WHERE name = 'ACT_IDX_HI_DETAIL_TIME') drop index ACT_HI_DETAIL.ACT_IDX_HI_DETAIL_TIME;
IF EXISTS (SELECT name FROM sysindexes WHERE name = 'ACT_IDX_HI_DETAIL_NAME') drop index ACT_HI_DETAIL.ACT_IDX_HI_DETAIL_NAME;
IF EXISTS (SELECT name FROM sysindexes WHERE name = 'ACT_IDX_HI_DETAIL_TASK_ID') drop index ACT_HI_DETAIL.ACT_IDX_HI_DETAIL_TASK_ID;
IF EXISTS (SELECT name FROM sysindexes WHERE name = 'ACT_IDX_HI_PROCVAR_PROC_INST') drop index ACT_HI_VARINST.ACT_IDX_HI_PROCVAR_PROC_INST;
IF EXISTS (SELECT name FROM sysindexes WHERE name = 'ACT_IDX_HI_PROCVAR_NAME_TYPE') drop index ACT_HI_VARINST.ACT_IDX_HI_PROCVAR_NAME_TYPE;
IF EXISTS (SELECT name FROM sysindexes WHERE name = 'ACT_IDX_HI_ACT_INST_PROCINST') drop index ACT_HI_ACTINST.ACT_IDX_HI_ACT_INST_PROCINST;
IF EXISTS (SELECT name FROM sysindexes WHERE name = 'ACT_IDX_HI_IDENT_LNK_USER') drop index ACT_HI_IDENTITYLINK.ACT_IDX_HI_IDENT_LNK_USER;
IF EXISTS (SELECT name FROM sysindexes WHERE name = 'ACT_IDX_HI_IDENT_LNK_TASK') drop index ACT_HI_IDENTITYLINK.ACT_IDX_HI_IDENT_LNK_TASK;
IF EXISTS (SELECT name FROM sysindexes WHERE name = 'ACT_IDX_HI_IDENT_LNK_PROCINST') drop index ACT_HI_IDENTITYLINK.ACT_IDX_HI_IDENT_LNK_PROCINST;

if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_HI_PROCINST') drop table ACT_HI_PROCINST;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_HI_ACTINST') drop table ACT_HI_ACTINST;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_HI_VARINST') drop table ACT_HI_VARINST;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_HI_TASKINST') drop table ACT_HI_TASKINST;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_HI_DETAIL') drop table ACT_HI_DETAIL;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_HI_COMMENT') drop table ACT_HI_COMMENT;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_HI_ATTACHMENT') drop table ACT_HI_ATTACHMENT;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_HI_IDENTITYLINK') drop table ACT_HI_IDENTITYLINK;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_ID_MEMBERSHIP') alter table ACT_ID_MEMBERSHIP drop constraint ACT_FK_MEMB_GROUP;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_ID_MEMBERSHIP') alter table ACT_ID_MEMBERSHIP drop constraint ACT_FK_MEMB_USER;
    
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_ID_INFO') drop table ACT_ID_INFO;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_ID_MEMBERSHIP') drop table ACT_ID_MEMBERSHIP;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_ID_GROUP') drop table ACT_ID_GROUP;
if exists (select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'ACT_ID_USER') drop table ACT_ID_USER;
