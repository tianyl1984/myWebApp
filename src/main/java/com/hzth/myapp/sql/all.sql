/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2005                    */
/* Created on:     2013/10/8 8:38:07                            */
/*==============================================================*/


/*==============================================================*/
/* Table: ac_acresultteachingweek                               */
/*==============================================================*/
create table ac_acresultteachingweek (
   id                   char(32)             not null,
   id_arrangecourseactivity char(32)             null,
   weekNumber           int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ac_acresultteachingweek
   add constraint PK_AC_ACRESULTTEACHINGWEEK primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ac_arrangecourseactivity                              */
/*==============================================================*/
create table ac_arrangecourseactivity (
   id                   char(32)             not null,
   id_parent            char(32)             null,
   id_schoolterm        char(32)             null,
   id_teacher           char(32)             null,
   name                 varchar(50)          null,
   applyDate            char(10)             null,
   description          varchar(200)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ac_arrangecourseactivity
   add constraint PK_AC_ARRANGECOURSEACTIVITY primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ac_arrangecoursegrade                                 */
/*==============================================================*/
create table ac_arrangecoursegrade (
   id                   char(32)             not null,
   id_arrangecourseactivity char(32)             null,
   id_school            char(32)             null,
   id_grade             char(32)             null,
   pmQuantity2          int                  null,
   pmQuantity           int                  null,
   amQuantity           int                  null,
   teachingDays         int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ac_arrangecoursegrade
   add constraint PK_AC_ARRANGECOURSEGRADE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ac_arrangecourseresult                                */
/*==============================================================*/
create table ac_arrangecourseresult (
   id                   char(32)             not null,
   id_arrangecourseactivity char(32)             null,
   id_eclass            char(32)             null,
   id_course            char(32)             null,
   id_teacher           char(32)             null,
   dayOfWeek            int                  null,
   lessonNumber         int                  null,
   startTime            char(5)              null,
   endTime              char(5)              null,
   advanceFlag          char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ac_arrangecourseresult
   add constraint PK_AC_ARRANGECOURSERESULT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ac_eclassorder                                        */
/*==============================================================*/
create table ac_eclassorder (
   id                   char(32)             not null,
   id_arrangecourseactivity char(32)             null,
   id_eclass            char(32)             null,
   orderNum             int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   lu                   char(32)             null,
   fu                   char(32)             null
)
go

alter table ac_eclassorder
   add constraint PK_AC_ECLASSORDER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ac_rule                                               */
/*==============================================================*/
create table ac_rule (
   id                   char(32)             not null,
   name                 varchar(100)         null,
   code                 varchar(50)          null,
   priorOrder           int                  null,
   showType             char(1)              null,
   enableFlag           char(1)              null,
   mustMeetFlag         char(1)              null,
   ruleType             char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ac_rule
   add constraint PK_AC_RULE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ac_ruleitem                                           */
/*==============================================================*/
create table ac_ruleitem (
   id                   char(32)             not null,
   id_rule              char(32)             null,
   displayName          varchar(100)         null,
   value                varchar(20)          null,
   defaultFlag          char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ac_ruleitem
   add constraint PK_AC_RULEITEM primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ac_rulevalue                                          */
/*==============================================================*/
create table ac_rulevalue (
   id                   char(32)             not null,
   id_schoolterm        char(32)             null,
   id_school            char(32)             null,
   id_grade             char(32)             null,
   id_eclass            char(32)             null,
   id_teacher           char(32)             null,
   id_course            char(32)             null,
   id_rule              char(32)             null,
   ruleValue            varchar(100)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ac_rulevalue
   add constraint PK_AC_RULEVALUE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: af_classification                                     */
/*==============================================================*/
create table af_classification (
   id                   char(32)             not null,
   id_parent            char(32)             null,
   name                 varchar(100)         null,
   description          varchar(200)         null,
   num                  int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table af_classification
   add constraint PK_AF_CLASSIFICATION primary key nonclustered (id)
go

/*==============================================================*/
/* Table: af_collectionrecord                                   */
/*==============================================================*/
create table af_collectionrecord (
   id                   char(32)             not null,
   id_resourcepackage   char(32)             null,
   id_collector         char(32)             null,
   collectionTime       char(19)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table af_collectionrecord
   add constraint PK_AF_COLLECTIONRECORD primary key nonclustered (id)
go

/*==============================================================*/
/* Table: af_evaluation                                         */
/*==============================================================*/
create table af_evaluation (
   id                   char(32)             not null,
   id_resourcepackage   char(32)             null,
   id_valuator          char(32)             null,
   evaluateTime         char(19)             null,
   updateTime           char(19)             null,
   score                int                  null,
   description          varchar(200)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table af_evaluation
   add constraint PK_AF_EVALUATION primary key nonclustered (id)
go

/*==============================================================*/
/* Table: af_filefolder                                         */
/*==============================================================*/
create table af_filefolder (
   id                   char(32)             not null,
   id_parent            char(32)             null,
   id_creater           char(32)             null,
   name                 varchar(100)         null,
   createTIme           char(19)             null,
   updateTime           char(19)             null,
   deleteMarker         char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table af_filefolder
   add constraint PK_AF_FILEFOLDER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: af_filerecord                                         */
/*==============================================================*/
create table af_filerecord (
   id                   char(32)             not null,
   id_uploaduser        char(32)             null,
   name                 varchar(200)         null,
   description          varchar(200)         null,
   uploadTime           char(19)             null,
   updateTime           char(19)             null,
   mediaFormat          char(1)              null,
   sourcefile           char(32)             null,
   icon                 char(32)             null,
   preview              char(32)             null,
   uploadMode           char(1)              null,
   externalVideo        varchar(500)         null,
   id_filefolder        char(32)             null,
   deleteMarker         char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null,
   extion               varchar(100)         null,
   filesize             varchar(100)         null
)
go

alter table af_filerecord
   add constraint PK_AF_FILERECORD primary key nonclustered (id)
go

/*==============================================================*/
/* Table: af_filerecord_resourcepackage                         */
/*==============================================================*/
create table af_filerecord_resourcepackage (
   id_filerecord        char(32)             null,
   id_resourcepackage   char(32)             null
)
go

/*==============================================================*/
/* Table: af_resourcepackage                                    */
/*==============================================================*/
create table af_resourcepackage (
   id                   char(32)             not null,
   id_course            char(32)             null,
   id_classification    char(32)             null,
   id_contributor       char(32)             null,
   contributeTime       char(19)             null,
   updateTime           char(19)             null,
   title                varchar(100)         null,
   briefIntroduction    varchar(200)         null,
   keywords             varchar(100)         null,
   shareRange           char(1)              null,
   browseNum            int                  null,
   downloadNum          int                  null,
   collectionNum        int                  null,
   mediaFormat          varchar(20)          null,
   checkStatus          char(1)              null,
   checkView            varchar(200)         null,
   resourcekind         varchar(20)          null,
   gradeLevel           varchar(20)          null,
   evalueScore          int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table af_resourcepackage
   add constraint PK_AF_RESOURCEPACKAGE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: al_aloffice                                           */
/*==============================================================*/
create table al_aloffice (
   id                   char(32)             not null,
   name                 varchar(100)         null,
   officeBuilding       varchar(100)         null,
   roomNum              varchar(200)         null,
   extension            varchar(200)         null,
   id_user              char(32)             null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   id_school            char(32)             null
)
go

alter table al_aloffice
   add constraint PK_AL_ALOFFICE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: al_classbook                                          */
/*==============================================================*/
create table al_classbook (
   id                   char(32)             not null,
   id_eclass            char(32)             null,
   name                 varchar(50)          null,
   unit                 varchar(50)          null,
   position             varchar(50)          null,
   phone                varchar(50)          null,
   note                 varchar(200)         null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table al_classbook
   add constraint PK_AL_CLASSBOOK primary key nonclustered (id)
go

/*==============================================================*/
/* Table: al_officeuser                                         */
/*==============================================================*/
create table al_officeuser (
   id                   char(32)             not null,
   id_aloffice          char(32)             null,
   id_teacher           char(32)             null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table al_officeuser
   add constraint PK_AL_OFFICEUSER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: am_answerdetail                                       */
/*==============================================================*/
create table am_answerdetail (
   id                   char(32)             not null,
   id_answersubject     char(32)             null,
   id_student           char(32)             null,
   number               int                  null,
   type                 char(32)             null,
   cope                 int                  null,
   answer               varchar(20)          null,
   studentAnswer        varchar(20)          null,
   nrightRate           int                  null,
   nanswerRate          int                  null,
   prightRate           int                  null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table am_answerdetail
   add constraint PK_AM_ANSWERDETAIL primary key nonclustered (id)
go

/*==============================================================*/
/* Table: am_answersubject                                      */
/*==============================================================*/
create table am_answersubject (
   id                   char(32)             not null,
   id_schoolterm        char(32)             null,
   id_school            char(32)             null,
   id_grade             char(32)             null,
   id_eclass            char(32)             null,
   id_course            char(32)             null,
   coursewareName       varchar(200)         null,
   answerRate           int                  null,
   rightRate            int                  null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table am_answersubject
   add constraint PK_AM_ANSWERSUBJECT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_applicationgroup                                   */
/*==============================================================*/
create table bd_applicationgroup (
   id                   char(32)             not null,
   id_user              char(32)             not null,
   name                 varchar(200)         null,
   num                  int                  null,
   defaultFlag          char(1)              null,
   delFlag              char(1)              null,
   editableFlag         char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_applicationgroup
   add constraint PK_BD_APPLICATIONGROUP primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_beadroll                                           */
/*==============================================================*/
create table bd_beadroll (
   id                   char(32)             not null,
   id_student           char(32)             null,
   inSchoolNum          varchar(10)          null,
   foundation           varchar(200)         null,
   proof                varchar(200)         null,
   createDate           char(10)             null,
   juniorNum            varchar(20)          null,
   seniorNum            varchar(20)          null,
   graduateNum          varchar(20)          null,
   note                 varchar(200)         null,
   status               char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_beadroll
   add constraint s_beadroll_PK primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_classcurriculum                                    */
/*==============================================================*/
create table bd_classcurriculum (
   id                   char(32)             not null,
   id_eclass            char(32)             null,
   id_course            char(32)             null,
   id_teacher           char(32)             null,
   id_classroom         char(32)             null,
   weekNumber           int                  null,
   dayOfWeek            int                  null,
   lessonNumber         int                  null,
   startTime            char(5)              null,
   endTime              char(5)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_classcurriculum
   add constraint PK_BD_CLASSCURRICULUM primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_classcurriculumchange                              */
/*==============================================================*/
create table bd_classcurriculumchange (
   id                   char(32)             not null,
   id_eclass            char(32)             null,
   id_fromcourse        char(32)             null,
   id_fromteacher       char(32)             null,
   id_tocourse          char(32)             null,
   id_toteacher         char(32)             null,
   weekNumber           int                  null,
   dayOfWeek            int                  null,
   lessonNumber         int                  null,
   startTime            char(5)              null,
   endTime              char(5)              null,
   reasonDict           varchar(20)          null,
   description          varchar(200)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_classcurriculumchange
   add constraint PK_BD_CLASSCURRICULUMCHANGE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_classroom                                          */
/*==============================================================*/
create table bd_classroom (
   id                   char(32)             not null,
   id_school            char(32)             null,
   code                 varchar(20)          null,
   name                 varchar(100)         null,
   position             varchar(200)         null,
   capacity             int                  null,
   status               char(1)              null,
   kindDict             varchar(20)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_classroom
   add constraint PK_BD_CLASSROOM primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_classroomcourse                                    */
/*==============================================================*/
create table bd_classroomcourse (
   id                   char(32)             not null,
   id_classroom         char(32)             null,
   id_eclass            char(32)             null,
   id_course            char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_classroomcourse
   add constraint PK_BD_CLASSROOMCOURSE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_classroomseat                                      */
/*==============================================================*/
create table bd_classroomseat (
   id                   char(32)             not null,
   id_classroom         char(32)             null,
   seatCode             varchar(10)          null,
   lineNum              int                  null,
   colNum               int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_classroomseat
   add constraint PK_BD_CLASSROOMSEAT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_classstudent                                       */
/*==============================================================*/
create table bd_classstudent (
   id                   char(32)             not null,
   id_eclass            char(32)             null,
   id_student           char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_classstudent
   add constraint PK_BD_CLASSSTUDENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_configitem_configresult                            */
/*==============================================================*/
create table bd_configitem_configresult (
   id_configurationitem char(32)             not null,
   id_configurationresult char(32)             not null
)
go

alter table bd_configitem_configresult
   add constraint PK_BD_CONFIGITEM_CONFIGRESULT primary key nonclustered (id_configurationitem, id_configurationresult)
go

/*==============================================================*/
/* Table: bd_configuration                                      */
/*==============================================================*/
create table bd_configuration (
   id                   char(32)             not null,
   id_module            char(32)             null,
   kind                 char(1)              null,
   configurationMode    char(1)              null,
   name                 varchar(50)          null,
   code                 varchar(50)          null,
   description          varchar(200)         null,
   visibleFlag          char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_configuration
   add constraint PK_BD_CONFIGURATION primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_configurationitem                                  */
/*==============================================================*/
create table bd_configurationitem (
   id                   char(32)             not null,
   id_configuration     char(32)             null,
   name                 varchar(50)          null,
   value                varchar(50)          null,
   defaultFlag          char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_configurationitem
   add constraint PK_BD_CONFIGURATIONITEM primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_configurationresult                                */
/*==============================================================*/
create table bd_configurationresult (
   id                   char(32)             not null,
   id_configuration     char(32)             null,
   id_user              char(32)             null,
   customValue          varchar(50)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_configurationresult
   add constraint PK_BD_CONFIGURATIONRESULT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_contract                                           */
/*==============================================================*/
create table bd_contract (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   id_course            char(32)             null,
   contractCode         varchar(50)          null,
   contractKindDict     varchar(20)          null,
   signDate             char(10)             null,
   positionKindDict     varchar(20)          null,
   classstandards       int                  null,
   positionName         varchar(100)         null,
   startDate            char(10)             null,
   endDate              char(10)             null,
   currentContractFlag  char(1)              null,
   ft                   char(19)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   lu                   char(32)             null
)
go

alter table bd_contract
   add constraint PK_BD_CONTRACT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_course                                             */
/*==============================================================*/
create table bd_course (
   id                   char(32)             not null,
   id_subject           char(32)             null,
   name                 varchar(200)         null,
   shortName            varchar(10)          null,
   displayName_en       varchar(200)         null,
   num                  int                  null,
   displayName_zh       varchar(200)         null,
   courseKindDict       varchar(20)          null,
   directionDict        varchar(20)          null,
   enableFlag           char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_course
   add constraint PK_BD_COURSE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_datascopeclass                                     */
/*==============================================================*/
create table bd_datascopeclass (
   id                   char(32)             not null,
   name                 varchar(20)          null,
   code                 varchar(20)          null,
   classPath            varchar(200)         null,
   num                  int                  null,
   description          varchar(200)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_datascopeclass
   add constraint PK_BD_DATASCOPECLASS primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_department                                         */
/*==============================================================*/
create table bd_department (
   id                   char(32)             not null,
   id_parent            char(32)             null,
   id_school            char(32)             null,
   name                 varchar(100)         null,
   num                  int                  null,
   departmentKind       char(1)              null,
   enableFlag           char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_department
   add constraint PK_BD_DEPARTMENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_departmentposition                                 */
/*==============================================================*/
create table bd_departmentposition (
   id                   char(32)             not null,
   id_department        char(32)             null,
   id_position          char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_departmentposition
   add constraint PK_BD_DEPARTMENTPOSITION primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_departmentscope                                    */
/*==============================================================*/
create table bd_departmentscope (
   id                   char(32)             not null,
   id_school            char(32)             null,
   id_department        char(32)             null,
   id_grade             char(32)             null,
   id_course            char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_departmentscope
   add constraint PK_BD_DEPARTMENTSCOPE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_departmentuser                                     */
/*==============================================================*/
create table bd_departmentuser (
   id                   char(32)             not null,
   id_department        char(32)             null,
   id_user              char(32)             null,
   headerFlag           char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_departmentuser
   add constraint PK_BD_DEPARTMENTUSER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_dictionary                                         */
/*==============================================================*/
create table bd_dictionary (
   id                   char(32)             not null,
   name                 varchar(200)         null,
   code                 varchar(20)          null,
   description          varchar(200)         null,
   kind                 char(1)              null,
   enableFlag           char(1)              null,
   editableFlag         char(1)              null,
   systemFlag           char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_dictionary
   add constraint PK_BD_DICTIONARY primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_dictionaryvalue                                    */
/*==============================================================*/
create table bd_dictionaryvalue (
   id                   char(32)             not null,
   id_dictionary        char(32)             null,
   id_parent            char(32)             null,
   code                 varchar(20)          null,
   value                varchar(50)          null,
   valueI18n            varchar(200)         null,
   num                  int                  null,
   description          varchar(200)         null,
   enableFlag           char(1)              null,
   editableFlag         char(1)              null,
   systemFlag           char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_dictionaryvalue
   add constraint PK_BD_DICTIONARYVALUE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_dimission                                          */
/*==============================================================*/
create table bd_dimission (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   leaveDate            char(10)             null,
   leaveReason          varchar(200)         null,
   goUnit               varchar(100)         null,
   description          varchar(500)         null,
   ft                   char(19)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   lu                   char(32)             null
)
go

alter table bd_dimission
   add constraint PK_BD_DIMISSION primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_dividescore                                        */
/*==============================================================*/
create table bd_dividescore (
   id                   char(32)             not null,
   id_student           char(32)             null,
   divideScore          int                  null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table bd_dividescore
   add constraint PK_BD_DIVIDESCORE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_eclass                                             */
/*==============================================================*/
create table bd_eclass (
   id                   char(32)             not null,
   uniqueCode           char(32)             null,
   id_schoolterm        char(32)             null,
   id_period            char(32)             null,
   id_school            char(32)             null,
   id_grade             char(32)             null,
   id_course            char(32)             null,
   num                  int                  null,
   name                 varchar(50)          null,
   classCode            varchar(20)          null,
   id_classteacher      char(32)             null,
   id_classteacher2     char(32)             null,
   classKindDict        varchar(20)          null,
   character1Dict       varchar(20)          null,
   character2Kind       char(1)              null,
   quantity             int                  null,
   enableFlag           char(1)              null,
   graduatedFlag        char(1)              null,
   englishName          varchar(50)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_eclass
   add constraint PK_BD_ECLASS primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_eclassteachingmaterial                             */
/*==============================================================*/
create table bd_eclassteachingmaterial (
   id                   char(32)             not null,
   id_schoolterm        char(32)             null,
   id_eclass            char(32)             null,
   id_teachingmaterial  char(32)             null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table bd_eclassteachingmaterial
   add constraint PK_BD_ECLASSTEACHINGMATERIAL primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_endwarning                                         */
/*==============================================================*/
create table bd_endwarning (
   id                   char(32)             not null,
   name                 varchar(50)          null,
   warningTime          int                  null,
   kind                 char(1)              null,
   status               char(1)              null,
   ft                   char(19)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   lu                   char(32)             null
)
go

alter table bd_endwarning
   add constraint PK_BD_ENDWARNING primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_grade                                              */
/*==============================================================*/
create table bd_grade (
   id                   char(32)             not null,
   num                  int                  null,
   name                 varchar(20)          null,
   shortName            varchar(100)         null,
   enableFlag           char(1)              null,
   englishName          varchar(50)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_grade
   add constraint PK_BD_GRADE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_gradecourse                                        */
/*==============================================================*/
create table bd_gradecourse (
   id                   char(32)             not null,
   id_schoolterm        char(32)             null,
   id_school            char(32)             null,
   id_grade             char(32)             null,
   id_course            char(32)             null,
   characterDict        varchar(20)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_gradecourse
   add constraint PK_BD_GRADECOURSE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_gradeteachingmaterial                              */
/*==============================================================*/
create table bd_gradeteachingmaterial (
   id                   char(32)             not null,
   id_schoolterm        char(32)             null,
   id_grade             char(32)             null,
   id_teachingmaterial  char(32)             null,
   quantity             int                  null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table bd_gradeteachingmaterial
   add constraint PK_BD_GRADETEACHINGMATERIAL primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_groupapplication                                   */
/*==============================================================*/
create table bd_groupapplication (
   id                   char(32)             not null,
   id_operation         char(32)             null,
   id_applicationgroup  char(32)             null,
   num                  int                  null,
   name                 varchar(200)         null,
   url                  varchar(200)         null,
   openMode             char(1)              null,
   icon                 varchar(100)         null,
   statusFlag           char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_groupapplication
   add constraint PK_BD_GROUPAPPLICATION primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_integrationcourse                                  */
/*==============================================================*/
create table bd_integrationcourse (
   id                   char(32)             not null,
   id_course            char(32)             null,
   id_schoolterm        char(32)             null,
   lessonQuantity       int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_integrationcourse
   add constraint PK_BD_INTEGRATIONCOURSE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_integrationcourseeclass                            */
/*==============================================================*/
create table bd_integrationcourseeclass (
   id                   char(32)             not null,
   id_eclass            char(32)             null,
   id_integrationcourse char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_integrationcourseeclass
   add constraint PK_BD_INTEGRATIONCOURSEECLASS primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_integrationcourseteacher                           */
/*==============================================================*/
create table bd_integrationcourseteacher (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   id_integrationcourse char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_integrationcourseteacher
   add constraint PK_BD_INTEGRATIONCOURSETEACHER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_knowledgepoint                                     */
/*==============================================================*/
create table bd_knowledgepoint (
   id                   char(32)             not null,
   id_parent            char(32)             null,
   id_course            char(32)             null,
   name                 varchar(100)         null,
   num                  int                  null,
   type                 char(1)              null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table bd_knowledgepoint
   add constraint PK_BD_KNOWLEDGEPOINT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_learningresume                                     */
/*==============================================================*/
create table bd_learningresume (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   nameDict             varchar(20)          null,
   graduateSchool       varchar(100)         null,
   specialty            varchar(100)         null,
   startDate            char(10)             null,
   endDate              char(10)             null,
   degreeDict           varchar(20)          null,
   firstEducationFlag   char(1)              null,
   currentEducationFlag char(1)              null,
   ft                   char(19)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   lu                   char(32)             null,
   reference            varchar(50)          null,
   description          varchar(500)         null
)
go

alter table bd_learningresume
   add constraint PK_BD_LEARNINGRESUME primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_lessonquantitychange                               */
/*==============================================================*/
create table bd_lessonquantitychange (
   id                   char(32)             not null,
   id_user              char(32)             null,
   id_teacher           char(32)             null,
   changeQuantity       int                  null,
   changeDate           char(19)             null,
   note                 varchar(1000)        null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_lessonquantitychange
   add constraint PK_BD_LESSONQUANTITYCHANGE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_message                                            */
/*==============================================================*/
create table bd_message (
   id                   char(32)             not null,
   id_senduser          char(32)             null,
   sendTime             char(19)             null,
   kind                 varchar(20)          null,
   title                varchar(100)         null,
   content              text                 null,
   hrefName             varchar(100)         null,
   hrefAddress          varchar(200)         null,
   deleteFlag           char(1)              null,
   clientRemindFlag     char(1)              null,
   id_source            char(32)             null,
   fedbackFlag          char(1)              null,
   classAddress         varchar(200)         null,
   methodName           varchar(100)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_message
   add constraint PK_BD_MESSAGE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_module                                             */
/*==============================================================*/
create table bd_module (
   id                   char(32)             not null,
   name                 varchar(20)          null,
   nameI18n             varchar(50)          null,
   code                 varchar(20)          null,
   num                  int                  null,
   deployFlag           char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_module
   add constraint PK_BD_MODULE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_operation                                          */
/*==============================================================*/
create table bd_operation (
   id                   char(32)             not null,
   id_module            char(32)             null,
   id_parent            char(32)             null,
   name                 varchar(100)         null,
   nameI18n             varchar(200)         null,
   code                 varchar(200)         null,
   operationDict        varchar(20)          null,
   customTypeDict       varchar(20)          null,
   url                  varchar(200)         null,
   openMode             char(1)              null,
   datascopeFlag        char(1)              null,
   num                  int                  null,
   icon                 varchar(200)         null,
   winHeight            varchar(20)          null,
   winWidth             varchar(20)          null,
   description          varchar(200)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_operation
   add constraint PK_BD_OPERATION primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_operationlog                                       */
/*==============================================================*/
create table bd_operationlog (
   id                   char(32)             not null,
   id_operation         char(32)             null,
   id_user              char(32)             null,
   operationTime        char(19)             null,
   loginIP              char(15)             null,
   computerName         varchar(50)          null,
   operationDict        varchar(20)          null,
   moduleCode           varchar(20)          null,
   descriptioni18n      text                 null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_operationlog
   add constraint PK_BD_OPERATIONLOG primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_parent                                             */
/*==============================================================*/
create table bd_parent (
   id                   char(32)             not null,
   name                 varchar(50)          null,
   pinyin               varchar(50)          null,
   displayName          varchar(50)          null,
   cardKindDict         varchar(20)          null,
   cardCode             varchar(50)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_parent
   add constraint PK_BD_PARENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_period                                             */
/*==============================================================*/
create table bd_period (
   id                   char(32)             not null,
   name                 varchar(20)          null,
   startDate            char(10)             null,
   endDate              char(10)             null,
   periodKindDict       varchar(20)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_period
   add constraint PK_BD_PERIOD primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_position                                           */
/*==============================================================*/
create table bd_position (
   id                   char(32)             not null,
   name                 varchar(100)         null,
   code                 varchar(200)         null,
   num                  int                  null,
   systemFlag           char(1)              null,
   enableFlag           char(1)              null,
   positionDict         varchar(20)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_position
   add constraint PK_BD_POSITION primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_positionchange                                     */
/*==============================================================*/
create table bd_positionchange (
   id                   char(32)             not null,
   id_schoolterm        char(32)             null,
   id_user              char(32)             null,
   id_fromdepartment    char(32)             null,
   id_todepartment      char(32)             null,
   id_fromposition      char(32)             null,
   id_toposition        char(32)             null,
   occurDate            char(10)             null,
   reason               varchar(50)          null,
   discription          varchar(200)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_positionchange
   add constraint PK_BD_POSITIONCHANGE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_postionprivilege                                   */
/*==============================================================*/
create table bd_postionprivilege (
   id_position          char(32)             not null,
   id_privilege         char(32)             not null
)
go

alter table bd_postionprivilege
   add constraint PK_BD_POSTIONPRIVILEGE primary key nonclustered (id_position, id_privilege)
go

/*==============================================================*/
/* Table: bd_privatedepartment                                  */
/*==============================================================*/
create table bd_privatedepartment (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   name                 varchar(200)         null,
   num                  int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_privatedepartment
   add constraint PK_BD_PRIVATEDEPARTMENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_privatedepartmentteacher                           */
/*==============================================================*/
create table bd_privatedepartmentteacher (
   id                   char(32)             not null,
   id_privatedepartment char(32)             null,
   id_teacher           char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_privatedepartmentteacher
   add constraint PK_BD_PRIVATEDEPARTMENTTEACHER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_privilege                                          */
/*==============================================================*/
create table bd_privilege (
   id                   char(32)             not null,
   id_module            char(32)             null,
   name                 varchar(100)         null,
   description          varchar(200)         null,
   num                  int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_privilege
   add constraint PK_BD_PRIVILEGE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_privilegeoperation                                 */
/*==============================================================*/
create table bd_privilegeoperation (
   id_privilege         char(32)             not null,
   id_operation         char(32)             not null
)
go

alter table bd_privilegeoperation
   add constraint PK_BD_PRIVILEGEOPERATION primary key nonclustered (id_privilege, id_operation)
go

/*==============================================================*/
/* Table: bd_professionaltitle                                  */
/*==============================================================*/
create table bd_professionaltitle (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   nameDict             varchar(20)          null,
   certificateCode      varchar(50)          null,
   reviewUnit           varchar(100)         null,
   passDate             char(10)             null,
   currentTitleFlag     char(1)              null,
   ft                   char(19)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   lu                   char(32)             null
)
go

alter table bd_professionaltitle
   add constraint PK_BD_PROFESSIONALTITLE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_publisher                                          */
/*==============================================================*/
create table bd_publisher (
   id                   char(32)             not null,
   name                 varchar(100)         null,
   num                  int                  null,
   shortName            varchar(20)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_publisher
   add constraint PK_BD_PUBLISHER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_questionresource                                   */
/*==============================================================*/
create table bd_questionresource (
   id                   char(32)             not null,
   id_course            char(32)             null,
   id_teachingmaterial  char(32)             null,
   id_grade             char(32)             null,
   id_teacher           char(32)             null,
   content              text                 null,
   topictype            char(1)              null,
   difficulty           int                  null,
   analysis             text                 null,
   writings             varchar(50)          null,
   uploadFlag           char(1)              null,
   uploadType           varchar(20)          null,
   commitDate           char(10)             null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   id_teachingunit      char(32)             null
)
go

alter table bd_questionresource
   add constraint PK_BD_QUESTIONRESOURCE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_questionresourcecollect                            */
/*==============================================================*/
create table bd_questionresourcecollect (
   id                   char(32)             not null,
   id_user              char(32)             null,
   id_questionresource  char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   lu                   char(32)             null
)
go

alter table bd_questionresourcecollect
   add constraint PK_BD_QUESTIONRESOURCECOLLECT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_questionresourceknowledge                          */
/*==============================================================*/
create table bd_questionresourceknowledge (
   id                   char(32)             not null,
   id_questionresource  char(32)             null,
   id_knowledgepoint    char(32)             null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table bd_questionresourceknowledge
   add constraint PK_BD_QUESTIONRESOURCEKNOWLEDG primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_questionresourceoption                             */
/*==============================================================*/
create table bd_questionresourceoption (
   id                   char(32)             not null,
   id_questionresource  char(32)             null,
   content              text                 null,
   rightFlag            char(1)              null,
   num                  int                  null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table bd_questionresourceoption
   add constraint PK_BD_QUESTIONRESOURCEOPTION primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_receiver                                           */
/*==============================================================*/
create table bd_receiver (
   id                   char(32)             not null,
   id_message           char(32)             null,
   id_receiveuser       char(32)             null,
   status               char(1)              null,
   deleteFlag           char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null,
   sendToOcsFlag        char(1)              null,
   timingMsgFlag        char(1)              null,
   timingTime           char(19)             null
)
go

alter table bd_receiver
   add constraint PK_BD_RECEIVER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_rehire                                             */
/*==============================================================*/
create table bd_rehire (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   rehireDate           char(10)             null,
   description          varchar(500)         null,
   ft                   char(19)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   lu                   char(32)             null
)
go

alter table bd_rehire
   add constraint PK_BD_REHIRE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_retired                                            */
/*==============================================================*/
create table bd_retired (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   retiredDate          char(10)             null,
   retiredKindDict      varchar(20)          null,
   description          varchar(500)         null,
   ft                   char(19)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   lu                   char(32)             null
)
go

alter table bd_retired
   add constraint PK_BD_RETIRED primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_schedule                                           */
/*==============================================================*/
create table bd_schedule (
   id                   char(32)             not null,
   name                 varchar(50)          null,
   teachingDays         int                  null,
   teachingQuantity     int                  null,
   amQuantity           int                  null,
   pmQuantity           int                  null,
   pmQuantity2          int                  null,
   description          varchar(800)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_schedule
   add constraint PK_BD_SCHEDULE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_schedulegrade                                      */
/*==============================================================*/
create table bd_schedulegrade (
   id                   char(32)             not null,
   id_schedule          char(32)             null,
   id_school            char(32)             null,
   id_grade             char(32)             null,
   startDate            char(10)             null,
   endDate              char(10)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_schedulegrade
   add constraint PK_BD_SCHEDULEGRADE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_scheduleholiday                                    */
/*==============================================================*/
create table bd_scheduleholiday (
   id                   char(32)             not null,
   name                 varchar(100)         null,
   startDate            char(10)             null,
   endDate              char(10)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_scheduleholiday
   add constraint PK_BD_SCHEDULEHOLIDAY primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_scheduleitem                                       */
/*==============================================================*/
create table bd_scheduleitem (
   id                   char(32)             not null,
   id_schedule          char(32)             null,
   name                 varchar(20)          null,
   lessonNumber         int                  null,
   startTime            char(5)              null,
   endTime              char(5)              null,
   kind                 char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_scheduleitem
   add constraint PK_BD_SCHEDULEITEM primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_school                                             */
/*==============================================================*/
create table bd_school (
   id                   char(32)             not null,
   id_parent            char(32)             null,
   name                 varchar(100)         null,
   code                 varchar(20)          null,
   schoolSystem         varchar(20)          null,
   schoolFlag           char(1)              null,
   website              varchar(100)         null,
   englishName          varchar(50)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_school
   add constraint PK_BD_SCHOOL primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_schoolgrade                                        */
/*==============================================================*/
create table bd_schoolgrade (
   id                   char(32)             not null,
   id_schoolterm        char(32)             null,
   id_period            char(32)             null,
   id_school            char(32)             null,
   id_grade             char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_schoolgrade
   add constraint PK_BD_SCHOOLGRADE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_schoolterm                                         */
/*==============================================================*/
create table bd_schoolterm (
   id                   char(32)             not null,
   id_schoolyear        char(32)             null,
   startDate            char(10)             null,
   endDate              char(10)             null,
   num                  int                  null,
   name                 varchar(20)          null,
   teachingStartDate    char(10)             null,
   teachingEndDate      char(10)             null,
   weekQuantity         int                  null,
   status               char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_schoolterm
   add constraint PK_BD_SCHOOLTERM primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_schoolyear                                         */
/*==============================================================*/
create table bd_schoolyear (
   id                   char(32)             not null,
   startDate            char(10)             null,
   endDate              char(10)             null,
   name                 varchar(50)          null,
   enableFlag           char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_schoolyear
   add constraint PK_BD_SCHOOLYEAR primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_software                                           */
/*==============================================================*/
create table bd_software (
   id                   char(32)             not null,
   name                 varchar(50)          null,
   icon                 varchar(200)         null,
   description          varchar(200)         null,
   num                  int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_software
   add constraint PK_BD_SOFTWARE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_student                                            */
/*==============================================================*/
create table bd_student (
   id                   char(32)             not null,
   id_period            char(32)             null,
   name                 varchar(50)          null,
   pinyin               varchar(50)          null,
   displayName          varchar(50)          null,
   enrollmentCode       varchar(30)          null,
   studentCode          varchar(20)          null,
   studentClassCode     varchar(20)          null,
   sex                  char(1)              null,
   status               char(1)              null,
   englishName          varchar(50)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null,
   entranceYear         char(4)              null,
   nationalityDict      varchar(20)          null,
   birthDate            char(10)             null,
   countryDict          varchar(20)          null,
   identityCardNum      varchar(100)         null,
   passportNum          varchar(100)         null,
   householdPlaceDict   varchar(20)          null,
   residence            varchar(100)         null,
   graduateOrPresentSchool varchar(50)          null,
   addressOrZipCode     varchar(50)          null,
   email                varchar(100)         null,
   phone                varchar(50)          null,
   specialties          varchar(200)         null,
   honours              varchar(200)         null,
   fatherName           varchar(100)         null,
   fatherOccupation     varchar(100)         null,
   fatherPhone          varchar(50)          null,
   motherName           varchar(100)         null,
   motherOccupation     varchar(100)         null,
   motherPhone          varchar(50)          null,
   emergencyContactPerson varchar(50)          null,
   emergencyCall        varchar(50)          null,
   specialRequest       varchar(100)         null,
   kind                 varchar(20)          null,
   countryCode          varchar(20)          null,
   nationCode           varchar(20)          null,
   telephone            varchar(30)          null,
   polity               varchar(60)          null,
   zipCode              varchar(6)           null,
   faith                varchar(60)          null,
   score                varchar(10)          null,
   cardNum              varchar(60)          null,
   cardKind             varchar(20)          null,
   oldName              varchar(60)          null,
   pid                  varchar(60)          null,
   educationid          varchar(60)          null,
   inSchoolMode         varchar(20)          null,
   healthCode           varchar(20)          null,
   colonyCode           varchar(20)          null,
   citizenCode          varchar(20)          null,
   nativeCode           varchar(20)          null,
   homePlaceCode        varchar(20)          null,
   addressCode          varchar(20)          null,
   citizenPlaceCode     varchar(20)          null,
   address              varchar(500)         null,
   citizenPlace         varchar(60)          null,
   flowFlag             char(1)              null,
   singleFlag           char(1)              null,
   strongPointFlag      char(1)              null,
   localFlag            char(1)              null,
   strongpoint          varchar(200)         null,
   linkAddress          varchar(500)         null,
   homePage             varchar(500)         null,
   note                 varchar(200)         null
)
go

alter table bd_student
   add constraint PK_BD_STUDENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_studentchange                                      */
/*==============================================================*/
create table bd_studentchange (
   id                   char(32)             not null,
   id_student           char(32)             null,
   id_fromclass         char(32)             null,
   id_toclass           char(32)             null,
   occurDate            char(10)             null,
   reasonKindDict       varchar(20)          null,
   content              text                 null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_studentchange
   add constraint PK_BD_STUDENTCHANGE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_studentfirstaid                                    */
/*==============================================================*/
create table bd_studentfirstaid (
   id                   char(32)             not null,
   id_student           char(32)             null,
   bloodKind            varchar(20)          null,
   allergicHistory      varchar(200)         null,
   medicalHistory       varchar(200)         null,
   urgentPhone          varchar(30)          null,
   linkMan              varchar(20)          null,
   note                 varchar(200)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_studentfirstaid
   add constraint s_firstaid_PK primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_studentparent                                      */
/*==============================================================*/
create table bd_studentparent (
   id                   char(32)             not null,
   id_student           char(32)             null,
   id_parent            char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_studentparent
   add constraint PK_BD_STUDENTPARENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_studentparents                                     */
/*==============================================================*/
create table bd_studentparents (
   id                   char(32)             not null,
   id_student           char(32)             null,
   name                 varchar(30)          null,
   relationCode         varchar(20)          null,
   degree               varchar(20)          null,
   unitName             varchar(200)         null,
   occupation           varchar(20)          null,
   techniqueCode        varchar(20)          null,
   occupationCode       varchar(20)          null,
   polityCode           varchar(20)          null,
   telephone            varchar(200)         null,
   marryCode            varchar(20)          null,
   addressCode          varchar(20)          null,
   togetherFlag         char(1)              null,
   guardianFlag         char(1)              null,
   email                varchar(30)          null,
   note                 varchar(200)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_studentparents
   add constraint s_familymemberinfo_PK primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_studentregistration                                */
/*==============================================================*/
create table bd_studentregistration (
   id                   char(32)             not null,
   id_user              char(32)             null,
   id_school            char(32)             null,
   id_grade             char(32)             null,
   name                 varchar(100)         null,
   registrationNumber   varchar(20)          null,
   birthDate            char(10)             null,
   sex                  char(1)              null,
   entranceYear         char(10)             null,
   nationalityDict      varchar(20)          null,
   informationSourceDict varchar(200)         null,
   phone                varchar(50)          null,
   mobilePhone          varchar(50)          null,
   countryDict          varchar(20)          null,
   identityCardNum      varchar(100)         null,
   passportNum          varchar(100)         null,
   householdPlaceDict   varchar(20)          null,
   householdPlaceDetail varchar(100)         null,
   residence            varchar(100)         null,
   graduateOrPresentSchool varchar(50)          null,
   graduateOrPresentGrade varchar(20)          null,
   specialties          varchar(200)         null,
   honours              varchar(200)         null,
   fatherName           varchar(100)         null,
   fatherOccupation     varchar(100)         null,
   fatherPhone          varchar(50)          null,
   motherName           varchar(100)         null,
   motherOccupation     varchar(100)         null,
   motherPhone          varchar(50)          null,
   fax                  varchar(50)          null,
   email                varchar(100)         null,
   emergencyContactPerson varchar(50)          null,
   emergencyCall        varchar(50)          null,
   familyAddress        varchar(200)         null,
   specialRequest       varchar(100)         null,
   mathScore            int                  null,
   englishScore         int                  null,
   chineseScore         int                  null,
   physicalScore        int                  null,
   chemicalScore        int                  null,
   totalScore           int                  null,
   localFullScore       int                  null,
   totalScorePercentage int                  null,
   chargeConfirmStatus  char(1)              null,
   recordTime           char(19)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null,
   admissionStatus      char(1)              null
)
go

alter table bd_studentregistration
   add constraint PK_BD_STUDENTREGISTRATION primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_studentseat                                        */
/*==============================================================*/
create table bd_studentseat (
   id                   char(32)             not null,
   id_student           char(32)             null,
   id_eclass            char(32)             null,
   id_classroom         char(32)             null,
   id_classroomseat     char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_studentseat
   add constraint PK_BD_STUDENTSEAT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_studentsource                                      */
/*==============================================================*/
create table bd_studentsource (
   id                   char(32)             not null,
   id_student           char(32)             null,
   oldSchoolName        varchar(60)          null,
   inSchoolDate         char(10)             null,
   source               varchar(20)          null,
   examNum              varchar(30)          null,
   inSchoolMode         varchar(20)          null,
   areaCode             varchar(20)          null,
   placeCode            varchar(20)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_studentsource
   add constraint PK_BD_STUDENTSOURCE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_studentteachingmaterial                            */
/*==============================================================*/
create table bd_studentteachingmaterial (
   id                   char(32)             not null,
   id_schoolterm        char(32)             null,
   id_student           char(32)             null,
   id_teachingmaterial  char(32)             null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table bd_studentteachingmaterial
   add constraint PK_BD_STUDENTTEACHINGMATERIAL primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_subject                                            */
/*==============================================================*/
create table bd_subject (
   id                   char(32)             not null,
   name                 varchar(20)          null,
   code                 varchar(20)          null,
   fieldDict            varchar(20)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null,
   num                  int                  null
)
go

alter table bd_subject
   add constraint PK_BD_SUBJECT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_teacher                                            */
/*==============================================================*/
create table bd_teacher (
   id                   char(32)             not null,
   id_school            char(32)             null,
   name                 varchar(50)          null,
   pinyin               varchar(50)          null,
   displayName          varchar(50)          null,
   teacherCode          varchar(20)          null,
   cardKindDict         varchar(20)          null,
   cardCode             varchar(50)          null,
   teacherKindDict      varchar(20)          null,
   status               char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null,
   sex                  char(1)              null,
   entryDate            char(10)             null,
   birthDate            char(10)             null,
   nationalityDict      varchar(20)          null,
   maritalStatus        varchar(20)          null,
   graduateSchool       varchar(100)         null,
   educationalLevel     varchar(20)          null,
   specialty            varchar(20)          null,
   titles               varchar(20)          null,
   mobilePhone          varchar(50)          null,
   phone                varchar(50)          null,
   hometown             varchar(20)          null,
   householdPlaceDict   varchar(20)          null,
   familyAddress        varchar(200)         null,
   englishName          varchar(50)          null,
   usedName             varchar(50)          null,
   politicalDict        varchar(20)          null,
   zipCode              varchar(10)          null,
   email                varchar(100)         null,
   entryWorkDate        char(7)              null,
   countryCodeDict      varchar(20)          null,
   birthPlaceDict       varchar(20)          null,
   specialPlaceDict     varchar(20)          null,
   healthDict           varchar(20)          null,
   religionDict         varchar(20)          null
)
go

alter table bd_teacher
   add constraint PK_BD_TEACHER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_teachercourse                                      */
/*==============================================================*/
create table bd_teachercourse (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   id_eclass            char(32)             null,
   id_course            char(32)             null,
   id_classroom         char(32)             null,
   lessonQuantity       int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_teachercourse
   add constraint PK_BD_TEACHERCOURSE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_teachercoursechange                                */
/*==============================================================*/
create table bd_teachercoursechange (
   id                   char(32)             not null,
   id_eclass            char(32)             null,
   id_fromcourse        char(32)             null,
   id_fromteacher       char(32)             null,
   id_tocourse          char(32)             null,
   id_toteacher         char(32)             null,
   occurDate            char(10)             null,
   startDate            char(10)             null,
   endDate              char(10)             null,
   reasonDict           varchar(20)          null,
   description          varchar(200)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_teachercoursechange
   add constraint PK_BD_TEACHERCOURSECHANGE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_teachingmaterial                                   */
/*==============================================================*/
create table bd_teachingmaterial (
   id                   char(32)             not null,
   id_grade             char(32)             null,
   id_course            char(32)             null,
   id_publisher         char(32)             null,
   title                varchar(200)         null,
   revision             varchar(100)         null,
   isbn                 varchar(20)          null,
   language             varchar(50)          null,
   author               varchar(100)         null,
   purpose              varchar(100)         null,
   kindDict             varchar(20)          null,
   enableFlag           char(1)              null,
   note                 varchar(200)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_teachingmaterial
   add constraint PK_BD_TEACHINGMATERIAL primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_teachingunit                                       */
/*==============================================================*/
create table bd_teachingunit (
   id                   char(32)             not null,
   id_parent            char(32)             null,
   id_teachingmaterial  char(32)             null,
   name                 varchar(200)         null,
   num                  int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_teachingunit
   add constraint PK_BD_TEACHINGUNIT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_teachingunitsubject                                */
/*==============================================================*/
create table bd_teachingunitsubject (
   id                   char(32)             not null,
   id_teachingunit      char(32)             null,
   name                 varchar(200)         null,
   num                  int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_teachingunitsubject
   add constraint PK_BD_TEACHINGUNITSUBJECT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_testpaper                                          */
/*==============================================================*/
create table bd_testpaper (
   id                   char(32)             not null,
   name                 varchar(200)         null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table bd_testpaper
   add constraint PK_BD_TESTPAPER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_testpaperquestion                                  */
/*==============================================================*/
create table bd_testpaperquestion (
   id                   char(32)             not null,
   id_testpaper         char(32)             null,
   id_questionresource  char(32)             null,
   num                  int                  null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   score                int                  null
)
go

alter table bd_testpaperquestion
   add constraint PK_BD_TESTPAPERQUESTION primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_user                                               */
/*==============================================================*/
create table bd_user (
   id                   char(32)             not null,
   name                 varchar(50)          null,
   displayName          varchar(50)          null,
   pinyin               varchar(50)          null,
   loginName            varchar(50)          null,
   password             varchar(50)          null,
   email                varchar(50)          null,
   userDict             varchar(20)          null,
   enableFlag           char(1)              null,
   num                  int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_user
   add constraint PK_BD_USER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_userecord                                          */
/*==============================================================*/
create table bd_userecord (
   id                   char(32)             not null,
   id_user              char(32)             null,
   id_operate           char(32)             null,
   useTime              char(19)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_userecord
   add constraint PK_BD_USERECORD primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_useroperation                                      */
/*==============================================================*/
create table bd_useroperation (
   id                   char(32)             not null,
   id_user              char(32)             null,
   id_position          char(32)             null,
   id_operation         char(32)             null,
   operationKind        char(1)              null,
   id_operationsource   char(32)             null,
   datascopeFlag        char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_useroperation
   add constraint PK_BD_USEROPERATION primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_useroperationchange                                */
/*==============================================================*/
create table bd_useroperationchange (
   id                   char(32)             not null,
   id_operation         char(32)             null,
   id_grantuser         char(32)             null,
   id_granteduser       char(32)             null,
   usedFlag             char(1)              null,
   startTime            char(19)             null,
   endTime              char(19)             null,
   grantFlag            char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_useroperationchange
   add constraint PK_BD_USEROPERATIONCHANGE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_useroperationdatascope                             */
/*==============================================================*/
create table bd_useroperationdatascope (
   id                   char(32)             not null,
   id_parent            char(32)             null,
   id_useroperation     char(32)             null,
   id_dataobject        char(32)             null,
   id_datascopeclass    char(32)             null,
   allFlag              char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_useroperationdatascope
   add constraint PK_BD_USEROPERATIONDATASCOPE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_userposition                                       */
/*==============================================================*/
create table bd_userposition (
   id                   char(32)             not null,
   id_position          char(32)             null,
   id_user              char(32)             null,
   id_department        char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bd_userposition
   add constraint PK_BD_USERPOSITION primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_visa                                               */
/*==============================================================*/
create table bd_visa (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   visaKindDict         varchar(20)          null,
   signDate             char(10)             null,
   endDate              char(10)             null,
   currentVisaFlag      char(1)              null,
   ft                   char(19)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   lu                   char(32)             null
)
go

alter table bd_visa
   add constraint PK_BD_VISA primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bd_workingresume                                      */
/*==============================================================*/
create table bd_workingresume (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   workingUnit          varchar(100)         null,
   startDate            char(10)             null,
   endDate              char(10)             null,
   positionKindDict     varchar(20)          null,
   teachingGrade        varchar(50)          null,
   teachingCourse       varchar(50)          null,
   currentJobFlag       char(1)              null,
   reference            varchar(50)          null,
   description          varchar(500)         null,
   ft                   char(19)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   lu                   char(32)             null
)
go

alter table bd_workingresume
   add constraint PK_BD_WORKINGRESUME primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bl_blog                                               */
/*==============================================================*/
create table bl_blog (
   id                   char(32)             not null,
   id_createuser        char(32)             null,
   name                 varchar(100)         null,
   status               char(1)              null,
   kind                 varchar(20)          null,
   styleCode            varchar(20)          null,
   articleTitle         varchar(100)         null,
   albumTitle           varchar(100)         null,
   videoTitle           varchar(100)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bl_blog
   add constraint PK_BL_BLOG primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bl_blogadmin                                          */
/*==============================================================*/
create table bl_blogadmin (
   id                   char(32)             not null,
   id_blog              char(32)             null,
   id_user              char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bl_blogadmin
   add constraint PK_BL_BLOGADMIN primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bl_comment                                            */
/*==============================================================*/
create table bl_comment (
   id                   char(32)             not null,
   id_parent            char(32)             null,
   id_content           char(32)             null,
   commentText          text                 null,
   id_user              char(32)             null,
   createdate           char(19)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bl_comment
   add constraint PK_BL_COMMENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bl_content                                            */
/*==============================================================*/
create table bl_content (
   id                   char(32)             not null,
   id_blog              char(32)             null,
   title                varchar(100)         null,
   replycnt             int                  null,
   praisecnt            int                  null,
   kind                 char(1)              null,
   createdate           char(19)             null,
   articleText          text                 null,
   articleStatus        char(1)              null,
   describtion          varchar(500)         null,
   videoUrl             varchar(400)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bl_content
   add constraint PK_BL_CONTENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bl_photo                                              */
/*==============================================================*/
create table bl_photo (
   id                   char(32)             not null,
   id_content           char(32)             null,
   title                varchar(200)         null,
   describtion          varchar(500)         null,
   coverFlag            char(1)              null,
   createdate           char(19)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bl_photo
   add constraint PK_BL_PHOTO primary key nonclustered (id)
go

/*==============================================================*/
/* Table: bl_praiseinfo                                         */
/*==============================================================*/
create table bl_praiseinfo (
   id                   char(32)             not null,
   id_user              char(32)             null,
   id_content           char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table bl_praiseinfo
   add constraint PK_BL_PRAISEINFO primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ca_attendanceitem                                     */
/*==============================================================*/
create table ca_attendanceitem (
   id                   char(32)             not null,
   name                 varchar(500)         null,
   englishName          varchar(500)         null,
   code                 varchar(50)          null,
   warningFlag          char(1)              null,
   dayValue             int                  null,
   weekValue            int                  null,
   monthValue           int                  null,
   schooltermValue      int                  null,
   absenceFlag          char(1)              null,
   absenceValue         int                  null,
   num                  int                  null,
   status               char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ca_attendanceitem
   add constraint PK_CA_ATTENDANCEITEM primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ca_attendancerecord                                   */
/*==============================================================*/
create table ca_attendancerecord (
   id                   char(32)             not null,
   id_classroomattendance char(32)             null,
   id_student           char(32)             null,
   id_attendanceitem    char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ca_attendancerecord
   add constraint PK_CA_ATTENDANCERECORD primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ca_classroomattendance                                */
/*==============================================================*/
create table ca_classroomattendance (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   tweek                int                  null,
   weekday              int                  null,
   lessonNumber         int                  null,
   id_eclass            char(32)             null,
   id_course            char(32)             null,
   attendanceTime       char(19)             null,
   kind                 char(1)              null,
   status               char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ca_classroomattendance
   add constraint PK_CA_CLASSROOMATTENDANCE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ca_leaverecord                                        */
/*==============================================================*/
create table ca_leaverecord (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   id_student           char(32)             null,
   id_schoolterm        char(32)             null,
   kind                 char(1)              null,
   reason               varchar(500)         null,
   startTime            char(10)             null,
   startSection         int                  null,
   endTime              char(10)             null,
   endSection           int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ca_leaverecord
   add constraint PK_CA_LEAVERECORD primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ca_performanceitem                                    */
/*==============================================================*/
create table ca_performanceitem (
   id                   char(32)             not null,
   id_performancekind   char(32)             null,
   name                 varchar(500)         null,
   englishName          varchar(500)         null,
   defaultScore         int                  null,
   num                  int                  null,
   status               char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ca_performanceitem
   add constraint PK_CA_PERFORMANCEITEM primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ca_performancekind                                    */
/*==============================================================*/
create table ca_performancekind (
   id                   char(32)             not null,
   name                 varchar(500)         null,
   englishName          varchar(500)         null,
   kind                 char(1)              null,
   minScore             int                  null,
   maxScore             int                  null,
   warningFlag          char(1)              null,
   dayValue             int                  null,
   weekValue            int                  null,
   monthValue           int                  null,
   schooltermValue      int                  null,
   num                  int                  null,
   status               char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ca_performancekind
   add constraint PK_CA_PERFORMANCEKIND primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ca_performancerecord                                  */
/*==============================================================*/
create table ca_performancerecord (
   id                   char(32)             not null,
   id_classroomattendance char(32)             null,
   id_student           char(32)             null,
   id_performanceitem   char(32)             null,
   score                int                  null,
   description          varchar(500)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ca_performancerecord
   add constraint PK_CA_PERFORMANCERECORD primary key nonclustered (id)
go

/*==============================================================*/
/* Table: cc_continuingcredit                                   */
/*==============================================================*/
create table cc_continuingcredit (
   id                   char(32)             not null,
   id_schoolterm        char(32)             null,
   credit               int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table cc_continuingcredit
   add constraint PK_CC_CONTINUINGCREDIT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: cc_creditmaterial                                     */
/*==============================================================*/
create table cc_creditmaterial (
   id                   char(32)             not null,
   id_baseoption        char(32)             null,
   id_materialskind     char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table cc_creditmaterial
   add constraint PK_CC_CREDITMATERIAL primary key nonclustered (id)
go

/*==============================================================*/
/* Table: cc_schoolouttrain                                     */
/*==============================================================*/
create table cc_schoolouttrain (
   id                   char(32)             not null,
   year                 char(4)              null,
   projectNo            varchar(100)         null,
   projectName          varchar(100)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table cc_schoolouttrain
   add constraint PK_CC_SCHOOLOUTTRAIN primary key nonclustered (id)
go

/*==============================================================*/
/* Table: cc_trainprincipal                                     */
/*==============================================================*/
create table cc_trainprincipal (
   id                   char(32)             not null,
   id_schoolouttrain    char(32)             null,
   id_user              char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table cc_trainprincipal
   add constraint PK_CC_TRAINPRINCIPAL primary key nonclustered (id)
go

/*==============================================================*/
/* Table: cc_trainuser                                          */
/*==============================================================*/
create table cc_trainuser (
   id                   char(32)             not null,
   id_user              char(32)             null,
   id_schoolouttrain    char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table cc_trainuser
   add constraint PK_CC_TRAINUSER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ce_evaluationactivity                                 */
/*==============================================================*/
create table ce_evaluationactivity (
   id                   char(32)             not null,
   id_semester          char(32)             null,
   id_evaluationstandard char(32)             null,
   name                 varchar(100)         null,
   type                 char(1)              null,
   releaseDate          char(10)             null,
   closeDate            char(10)             null,
   scoreFlag            char(1)              null,
   detailFlag           char(1)              null,
   statusFlag           char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ce_evaluationactivity
   add constraint PK_CE_EVALUATIONACTIVITY primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ce_evaluationdetail                                   */
/*==============================================================*/
create table ce_evaluationdetail (
   id                   char(32)             not null,
   id_evaluationstandard char(32)             null,
   id_parent            char(32)             null,
   name                 varchar(100)         null,
   minScore             int                  null,
   maxScore             int                  null,
   orderNum             int                  null,
   note                 varchar(500)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ce_evaluationdetail
   add constraint PK_CE_EVALUATIONDETAIL primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ce_evaluationrank                                     */
/*==============================================================*/
create table ce_evaluationrank (
   id                   char(32)             not null,
   id_evaluationdetail  char(32)             null,
   name                 varchar(100)         null,
   minScore             int                  null,
   maxScore             int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ce_evaluationrank
   add constraint PK_CE_EVALUATIONRANK primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ce_evaluationresult                                   */
/*==============================================================*/
create table ce_evaluationresult (
   id                   char(32)             not null,
   id_reviewer          char(32)             null,
   id_evalueUser        char(32)             null,
   id_evaluationdetail  char(32)             null,
   id_evaluationactivity char(32)             null,
   reviewDate           char(10)             null,
   note                 varchar(500)         null,
   score                int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ce_evaluationresult
   add constraint PK_CE_EVALUATIONRESULT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ce_evaluationstandard                                 */
/*==============================================================*/
create table ce_evaluationstandard (
   id                   char(32)             not null,
   name                 varchar(100)         null,
   statusFlag           char(1)              null,
   note                 varchar(500)         null,
   releaseTime          char(19)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ce_evaluationstandard
   add constraint PK_CE_EVALUATIONSTANDARD primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ce_modelsetting                                       */
/*==============================================================*/
create table ce_modelsetting (
   id                   char(32)             not null,
   id_statisticalmodel  char(32)             null,
   id_evaluationactivity char(32)             null,
   coefficient          int                  null,
   orderNum             int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ce_modelsetting
   add constraint PK_CE_MODELSETTING primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ce_reviewed                                           */
/*==============================================================*/
create table ce_reviewed (
   id                   char(32)             not null,
   id_evaluationactivity char(32)             null,
   id_user              char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ce_reviewed
   add constraint PK_CE_REVIEWED primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ce_reviewer                                           */
/*==============================================================*/
create table ce_reviewer (
   id                   char(32)             not null,
   id_evaluationactivity char(32)             null,
   id_user              char(32)             null,
   coefficient          int                  null,
   orderNum             int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ce_reviewer
   add constraint PK_CE_REVIEWER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ce_statisticalmodel                                   */
/*==============================================================*/
create table ce_statisticalmodel (
   id                   char(32)             not null,
   name                 varchar(200)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ce_statisticalmodel
   add constraint PK_CE_STATISTICALMODEL primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ce_statisticalmodel_user                              */
/*==============================================================*/
create table ce_statisticalmodel_user (
   id_statisticalmodel  char(32)             null,
   id_user              char(32)             null
)
go

/*==============================================================*/
/* Table: cm_assigncar                                          */
/*==============================================================*/
create table cm_assigncar (
   id                   char(32)             not null,
   id_user              char(32)             null,
   id_driver            char(32)             null,
   id_car               char(32)             null,
   leaveDate            char(10)             null,
   leaveTime            char(5)              null,
   supplementFlag       char(1)              null,
   realCount            int                  null,
   realAddress          varchar(100)         null,
   realTime             char(19)             null,
   note                 varchar(200)         null,
   ft                   char(19)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   lu                   char(32)             null
)
go

alter table cm_assigncar
   add constraint PK_CM_ASSIGNCAR primary key nonclustered (id)
go

/*==============================================================*/
/* Table: cm_car                                                */
/*==============================================================*/
create table cm_car (
   id                   char(32)             not null,
   name                 varchar(100)         null,
   carNum               varchar(100)         null,
   status               char(1)              null,
   limitCount           int                  null,
   ft                   char(19)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   lu                   char(32)             null
)
go

alter table cm_car
   add constraint PK_CM_CAR primary key nonclustered (id)
go

/*==============================================================*/
/* Table: cm_driver                                             */
/*==============================================================*/
create table cm_driver (
   id                   char(32)             not null,
   id_user              char(32)             null,
   contact              varchar(20)          null,
   driveAge             int                  null,
   status               char(1)              null,
   ft                   char(19)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   lu                   char(32)             null
)
go

alter table cm_driver
   add constraint PK_CM_DRIVER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: cm_orderassigncar                                     */
/*==============================================================*/
create table cm_orderassigncar (
   id                   char(32)             not null,
   id_assigncar         char(32)             null,
   id_ordercar          char(32)             null,
   count                int                  null,
   ft                   char(19)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   lu                   char(32)             null
)
go

alter table cm_orderassigncar
   add constraint PK_CM_ORDERASSIGNCAR primary key nonclustered (id)
go

/*==============================================================*/
/* Table: cm_ordercar                                           */
/*==============================================================*/
create table cm_ordercar (
   id                   char(32)             not null,
   id_carUser           char(32)             null,
   id_orderUser         char(32)             null,
   orderTime            char(19)             null,
   userDate             char(10)             null,
   arriveTime           char(5)              null,
   address              varchar(100)         null,
   userCount            int                  null,
   personList           varchar(400)         null,
   reason               varchar(500)         null,
   instruction          varchar(500)         null,
   backDate             char(10)             null,
   backCount            int                  null,
   backAddress          varchar(100)         null,
   backTime             char(5)              null,
   supplementFlag       char(1)              null,
   id_deptChecker       char(32)             null,
   id_checker           char(32)             null,
   deptCheckStatus      char(1)              null,
   checkStatus          char(1)              null,
   deptAdvice           varchar(200)         null,
   advice               varchar(200)         null,
   useTime              char(5)              null,
   useAddress           varchar(100)         null,
   satisfaction         int                  null,
   feedback             varchar(200)         null,
   ft                   char(19)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   lu                   char(32)             null,
   useDept              varchar(20)          null,
   backPersonList       varchar(400)         null
)
go

alter table cm_ordercar
   add constraint PK_CM_ORDERCAR primary key nonclustered (id)
go

/*==============================================================*/
/* Table: cm_privatecar                                         */
/*==============================================================*/
create table cm_privatecar (
   id                   char(32)             not null,
   id_user              char(32)             null,
   carNum               varchar(50)          null,
   brand                varchar(50)          null,
   color                varchar(20)          null,
   engineNum            varchar(50)          null,
   date                 char(10)             null,
   kind                 varchar(20)          null,
   ft                   char(19)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   lu                   char(32)             null,
   compulsoryInsuranceFlag char(1)              null,
   commerialInsuranceFlag char(1)              null,
   driver               varchar(50)          null,
   phoneNum             varchar(50)          null,
   address              varchar(100)         null,
   affiliatedUnit       varchar(20)          null
)
go

alter table cm_privatecar
   add constraint PK_CM_PRIVATECAR primary key nonclustered (id)
go

/*==============================================================*/
/* Table: cs_visitorregistration                                */
/*==============================================================*/
create table cs_visitorregistration (
   id                   char(32)             not null,
   id_receptionPersonnel char(32)             null,
   name                 varchar(50)          null,
   sexDict              varchar(20)          null,
   date                 char(10)             null,
   accessTime           char(5)              null,
   phone                varchar(30)          null,
   idNumber             varchar(50)          null,
   receptionPersonnelStatus char(1)              null,
   visitorStatus        char(1)              null,
   handlingMatters      varchar(50)          null,
   accessRemark         varchar(100)         null,
   leaveTime            char(5)              null,
   resultFeedback       varchar(100)         null,
   leaveRemark          varchar(100)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table cs_visitorregistration
   add constraint PK_CS_VISITORREGISTRATION primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ct_answerresult                                       */
/*==============================================================*/
create table ct_answerresult (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   id_trainingactivity  char(32)             null,
   id_trainingcourse    char(32)             null,
   id_trainingsubject   char(32)             null,
   score                int                  null,
   commitFlag           char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ct_answerresult
   add constraint PK_CT_ANSWERRESULT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ct_answerresultoption                                 */
/*==============================================================*/
create table ct_answerresultoption (
   id_answerresult      char(32)             null,
   id_ctoption          char(32)             null
)
go

/*==============================================================*/
/* Table: ct_ctoption                                           */
/*==============================================================*/
create table ct_ctoption (
   id                   char(32)             not null,
   id_trainingsubject   char(32)             null,
   content              varchar(100)         null,
   num                  int                  null,
   rightFlag            char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ct_ctoption
   add constraint PK_CT_CTOPTION primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ct_studyscore                                         */
/*==============================================================*/
create table ct_studyscore (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   id_trainingactivity  char(32)             null,
   id_trainingcourse    char(32)             null,
   score                int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ct_studyscore
   add constraint PK_CT_STUDYSCORE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ct_trainingactivity                                   */
/*==============================================================*/
create table ct_trainingactivity (
   id                   char(32)             not null,
   name                 varchar(50)          null,
   startTime            char(16)             null,
   endTime              char(16)             null,
   releaseFlag          char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ct_trainingactivity
   add constraint PK_CT_TRAININGACTIVITY primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ct_trainingactivitycourse                             */
/*==============================================================*/
create table ct_trainingactivitycourse (
   id_trainingactivity  char(32)             null,
   id_trainingcourse    char(32)             null
)
go

/*==============================================================*/
/* Table: ct_trainingactivityteacher                            */
/*==============================================================*/
create table ct_trainingactivityteacher (
   id_trainingactivity  char(32)             null,
   id_teacher           char(32)             null
)
go

/*==============================================================*/
/* Table: ct_trainingcourse                                     */
/*==============================================================*/
create table ct_trainingcourse (
   id                   char(32)             not null,
   name                 varchar(50)          null,
   learningTime         int                  null,
   score                int                  null,
   lecture              varchar(1000)        null,
   attachmentId         char(32)             null,
   previewId            char(32)             null,
   id_teacher           char(32)             null,
   uploadDate           char(10)             null,
   releaseStatue        char(1)              null,
   releaseDate          char(10)             null,
   type                 char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ct_trainingcourse
   add constraint PK_CT_TRAININGCOURSE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ct_trainingsubject                                    */
/*==============================================================*/
create table ct_trainingsubject (
   id                   char(32)             not null,
   id_trainingcourse    char(32)             null,
   question             varchar(100)         null,
   score                int                  null,
   type                 char(1)              null,
   num                  int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ct_trainingsubject
   add constraint PK_CT_TRAININGSUBJECT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ec_activitystudentgroup                               */
/*==============================================================*/
create table ec_activitystudentgroup (
   id                   char(32)             not null,
   id_ecactivity        char(32)             null,
   name                 varchar(100)         null,
   note                 varchar(200)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ec_activitystudentgroup
   add constraint PK_EC_ACTIVITYSTUDENTGROUP primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ec_alternativecourse                                  */
/*==============================================================*/
create table ec_alternativecourse (
   id                   char(32)             not null,
   id_ecactivity        char(32)             null,
   id_student           char(32)             null,
   id_alternative1      char(32)             null,
   id_alternative2      char(32)             null,
   id_alternative3      char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ec_alternativecourse
   add constraint PK_EC_ALTERNATIVECOURSE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ec_courseclassroom                                    */
/*==============================================================*/
create table ec_courseclassroom (
   id                   char(32)             not null,
   id_ecactivity        char(32)             null,
   id_classroom         char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ec_courseclassroom
   add constraint PK_EC_COURSECLASSROOM primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ec_coursestudentgroup                                 */
/*==============================================================*/
create table ec_coursestudentgroup (
   id                   char(32)             not null,
   id_studentgroup      char(32)             null,
   id_ecactivitycourse  char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ec_coursestudentgroup
   add constraint PK_EC_COURSESTUDENTGROUP primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ec_ecactivity                                         */
/*==============================================================*/
create table ec_ecactivity (
   id                   char(32)             not null,
   id_schoolterm        char(32)             null,
   id_teacher           char(32)             null,
   name                 varchar(50)          null,
   startTime            char(19)             null,
   endTime              char(19)             null,
   status               char(1)              null,
   note                 text                 null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null,
   kind                 char(1)              null,
   applyStartTime       char(19)             null,
   applyEndTime         char(19)             null,
   applyNote            text                 null,
   studentFlag          char(1)              null,
   classTime            varchar(200)         null,
   score                int                  null,
   hour                 int                  null,
   studentScope         char(1)              null,
   maxHour              int                  null,
   maxScore             int                  null,
   maxCount             int                  null,
   classTimeFlag        char(1)              null,
   resultStatus         char(1)              null,
   id_school            char(32)             null
)
go

alter table ec_ecactivity
   add constraint PK_EC_ECACTIVITY primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ec_ecactivitycourse                                   */
/*==============================================================*/
create table ec_ecactivitycourse (
   id                   char(32)             not null,
   id_ecactivity        char(32)             null,
   id_course            char(32)             null,
   status               char(1)              null,
   minCount             int                  null,
   maxCount             int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null,
   hour                 int                  null,
   score                int                  null,
   time                 varchar(200)         null,
   id_classroom         char(32)             null,
   note                 varchar(200)         null,
   id_beforeCourse      char(32)             null,
   courseCycle          char(1)              null,
   id_group             char(32)             null,
   id_checkUser         char(32)             null,
   applyTime            char(19)             null,
   checkStatus          char(1)              null,
   checkAdvice          varchar(500)         null,
   modifyAdvice         varchar(500)         null,
   checkTime            char(19)             null,
   id_eccourse          char(32)             null
)
go

alter table ec_ecactivitycourse
   add constraint PK_EC_ECACTIVITYCOURSE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ec_ecactivityeclass                                   */
/*==============================================================*/
create table ec_ecactivityeclass (
   id                   char(32)             not null,
   id_ecactivity        char(32)             null,
   id_eclass            char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null,
   id_grade             char(32)             null
)
go

alter table ec_ecactivityeclass
   add constraint PK_EC_ECACTIVITYECLASS primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ec_eccourse                                           */
/*==============================================================*/
create table ec_eccourse (
   id                   char(32)             not null,
   id_applyUser         char(32)             null,
   id_checkUser         char(32)             null,
   id_course            char(32)             null,
   id_subject           char(32)             null,
   name                 varchar(50)          null,
   minCount             int                  null,
   maxCount             int                  null,
   courseKind           varchar(100)         null,
   address              varchar(25)          null,
   video                varchar(200)         null,
   courseIntroduce      text                 null,
   coursePicture        varchar(200)         null,
   tbaseinfoIntroduce   text                 null,
   picture              varchar(200)         null,
   academicHour         int                  null,
   applyTime            char(19)             null,
   disable              char(1)              null,
   status               char(1)              null,
   checkAdvice          varchar(500)         null,
   modifyAdvice         varchar(500)         null,
   checkTime            char(19)             null,
   teacherCase          varchar(100)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null,
   hierarchical         varchar(50)          null,
   classTime            varchar(200)         null,
   id_grade             char(32)             null,
   id_classroom         char(32)             null
)
go

alter table ec_eccourse
   add constraint PK_EC_ECCOURSE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ec_ececlass                                           */
/*==============================================================*/
create table ec_ececlass (
   id                   char(32)             not null,
   id_eclass            char(32)             null,
   id_ecactivitycourse  char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null,
   hierarchical         varchar(50)          null
)
go

alter table ec_ececlass
   add constraint PK_EC_ECECLASS primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ec_ecrule                                             */
/*==============================================================*/
create table ec_ecrule (
   id                   char(32)             not null,
   id_ecactivity        char(32)             null,
   name                 varchar(40)          null,
   ecQuantity           int                  null,
   minQuantity          int                  null,
   num                  int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ec_ecrule
   add constraint PK_EC_ECRULE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ec_ecruleclass                                        */
/*==============================================================*/
create table ec_ecruleclass (
   id                   char(32)             not null,
   id_ecrule            char(32)             null,
   id_eclass            char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ec_ecruleclass
   add constraint PK_EC_ECRULECLASS primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ec_ecrulecourse                                       */
/*==============================================================*/
create table ec_ecrulecourse (
   id                   char(32)             not null,
   id_ecerule           char(32)             null,
   id_course            char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ec_ecrulecourse
   add constraint PK_EC_ECRULECOURSE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ec_ecstudentcourse                                    */
/*==============================================================*/
create table ec_ecstudentcourse (
   id                   char(32)             not null,
   id_ecactivity        char(32)             null,
   id_schoolterm        char(32)             null,
   id_student           char(32)             null,
   id_course            char(32)             null,
   id_ecclass           char(32)             null,
   evaluate             varchar(20)          null,
   comment              varchar(500)         null,
   unit                 int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null,
   flag                 char(1)              null
)
go

alter table ec_ecstudentcourse
   add constraint PK_EC_ECSTUDENTCOURSE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ec_ecstudentcoursechange                              */
/*==============================================================*/
create table ec_ecstudentcoursechange (
   id                   char(32)             not null,
   id_student           char(32)             null,
   id_ecactivity        char(32)             null,
   id_fromcourse        char(32)             null,
   id_tocourse          char(32)             null,
   changeTime           char(19)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null,
   id_user              char(32)             null
)
go

alter table ec_ecstudentcoursechange
   add constraint PK_EC_ECSTUDENTCOURSECHANGE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ec_ecstudentevaluate                                  */
/*==============================================================*/
create table ec_ecstudentevaluate (
   id                   char(32)             not null,
   id_ecstudentcourse   char(32)             null,
   week                 int                  null,
   content              varchar(200)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ec_ecstudentevaluate
   add constraint PK_EC_ECSTUDENTEVALUATE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ec_electivegroup                                      */
/*==============================================================*/
create table ec_electivegroup (
   id                   char(32)             not null,
   name                 varchar(100)         null,
   num                  int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ec_electivegroup
   add constraint PK_EC_ELECTIVEGROUP primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ec_groupstudent                                       */
/*==============================================================*/
create table ec_groupstudent (
   id                   char(32)             not null,
   id_studentgroup      char(32)             null,
   id_student           char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ec_groupstudent
   add constraint PK_EC_GROUPSTUDENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ec_subjectlevel                                       */
/*==============================================================*/
create table ec_subjectlevel (
   id                   char(32)             not null,
   id_subject           char(32)             not null,
   hierarchical         varchar(50)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

/*==============================================================*/
/* Table: er_recruitexaminfo                                    */
/*==============================================================*/
create table er_recruitexaminfo (
   id                   char(32)             not null,
   id_schoolterm        char(32)             null,
   name                 varchar(100)         null,
   startDate            char(16)             null,
   endDate              char(16)             null,
   announcement         text                 null,
   precautions          text                 null,
   status               char(1)              null,
   scoreStatus          char(1)              null,
   examRoomStatus       char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table er_recruitexaminfo
   add constraint PK_ER_RECRUITEXAMINFO primary key nonclustered (id)
go

/*==============================================================*/
/* Table: er_recruitexamroom                                    */
/*==============================================================*/
create table er_recruitexamroom (
   id                   char(32)             not null,
   id_recruitexaminfo   char(32)             null,
   id_recruitexamsubject char(32)             null,
   id_recruitexamsubjectgrade char(32)             null,
   subjectDict          varchar(20)          null,
   gradeDict            varchar(20)          null,
   num                  int                  null,
   capacity             int                  null,
   place                varchar(200)         null,
   remark               varchar(200)         null,
   invigilator1         text                 null,
   invigilator2         varchar(200)         null,
   patrolInvigilator    text                 null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table er_recruitexamroom
   add constraint PK_ER_RECRUITEXAMROOM primary key nonclustered (id)
go

/*==============================================================*/
/* Table: er_recruitexamroomstudent                             */
/*==============================================================*/
create table er_recruitexamroomstudent (
   id                   char(32)             not null,
   id_recruitexaminfo   char(32)             null,
   id_recruitexamroom   char(32)             null,
   id_recruitexamstudent char(32)             null,
   id_recruitexamsubject char(32)             null,
   id_recruitexamsubjectgrade char(32)             null,
   subjectDict          varchar(20)          null,
   gradeDict            varchar(20)          null,
   examRoomNum          int                  null,
   SeatNum              int                  null,
   score                int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table er_recruitexamroomstudent
   add constraint PK_ER_RECRUITEXAMROOMSTUDENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: er_recruitexamstudent                                 */
/*==============================================================*/
create table er_recruitexamstudent (
   id                   char(32)             not null,
   id_recruitexaminfo   char(32)             null,
   name                 varchar(100)         null,
   sexDict              varchar(20)          null,
   schoolDict           varchar(20)          null,
   gradeDict            varchar(20)          null,
   classDict            varchar(20)          null,
   counselingTeacher    varchar(100)         null,
   guardianPhone        varchar(50)          null,
   email                varchar(100)         null,
   password             varchar(50)          null,
   submitStatus         char(1)              null,
   checkStatus          char(1)              null,
   checkRemark          varchar(1000)        null,
   permitNum            varchar(30)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table er_recruitexamstudent
   add constraint PK_ER_RECRUITEXAMSTUDENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: er_recruitexamstudentsubject                          */
/*==============================================================*/
create table er_recruitexamstudentsubject (
   id                   char(32)             not null,
   id_recruitexaminfo   char(32)             null,
   id_recruitexamstudent char(32)             null,
   id_recruitexamsubject char(32)             null,
   id_recruitexamsubjectgrade char(32)             null,
   subjectDict          varchar(20)          null,
   gradeDict            varchar(20)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table er_recruitexamstudentsubject
   add constraint PK_ER_RECRUITEXAMSTUDENTSUBJEC primary key nonclustered (id)
go

/*==============================================================*/
/* Table: er_recruitexamsubject                                 */
/*==============================================================*/
create table er_recruitexamsubject (
   id                   char(32)             not null,
   id_recruitexaminfo   char(32)             null,
   subjectDict          varchar(20)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table er_recruitexamsubject
   add constraint PK_ER_RECRUITEXAMSUBJECT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: er_recruitexamsubjectgrade                            */
/*==============================================================*/
create table er_recruitexamsubjectgrade (
   id                   char(32)             not null,
   id_recruitexaminfo   char(32)             null,
   id_recruitexamsubject char(32)             null,
   subjectDict          varchar(20)          null,
   gradeDict            varchar(20)          null,
   endTime              char(5)              null,
   startTime            char(5)              null,
   examDate             char(10)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table er_recruitexamsubjectgrade
   add constraint PK_ER_RECRUITEXAMSUBJECTGRADE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: es_program                                            */
/*==============================================================*/
create table es_program (
   id                   char(32)             not null,
   id_user              char(32)             null,
   name                 varchar(50)          null,
   discription          varchar(200)         null,
   pkgId                varchar(100)         null,
   versionName          varchar(20)          null,
   versionNum           int                  null,
   iconId               char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table es_program
   add constraint PK_ES_PROGRAM primary key nonclustered (id)
go

/*==============================================================*/
/* Table: et_etactivitie                                        */
/*==============================================================*/
create table et_etactivitie (
   id                   char(32)             not null,
   id_schoolyear        char(32)             null,
   name                 varchar(100)         null,
   status               char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table et_etactivitie
   add constraint PK_ET_ETACTIVITIE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: et_etgrade                                            */
/*==============================================================*/
create table et_etgrade (
   id                   char(32)             not null,
   id_etactivitie       char(32)             null,
   id_schoolgrade       char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table et_etgrade
   add constraint PK_ET_ETGRADE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: et_etgradingpeople                                    */
/*==============================================================*/
create table et_etgradingpeople (
   id                   char(32)             not null,
   id_etactivitie       char(32)             null,
   id_user              char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table et_etgradingpeople
   add constraint PK_ET_ETGRADINGPEOPLE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: et_otherevaluation                                    */
/*==============================================================*/
create table et_otherevaluation (
   id                   char(32)             not null,
   id_etactivitie       char(32)             null,
   name                 varchar(100)         null,
   description          varchar(200)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table et_otherevaluation
   add constraint PK_ET_OTHEREVALUATION primary key nonclustered (id)
go

/*==============================================================*/
/* Table: et_otherevaluationscore                               */
/*==============================================================*/
create table et_otherevaluationscore (
   id                   char(32)             not null,
   id_otherEvaluation   char(32)             null,
   id_teacher           char(32)             null,
   score                varchar(20)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table et_otherevaluationscore
   add constraint PK_ET_OTHEREVALUATIONSCORE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: et_tievaluation                                       */
/*==============================================================*/
create table et_tievaluation (
   id                   char(32)             not null,
   id_etactivitie       char(32)             null,
   name                 varchar(100)         null,
   description          varchar(200)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table et_tievaluation
   add constraint PK_ET_TEACHINGINSPECTIONEVALU1 primary key nonclustered (id)
go

/*==============================================================*/
/* Table: et_tievaluationscore                                  */
/*==============================================================*/
create table et_tievaluationscore (
   id                   char(32)             not null,
   id_tievaluation      char(32)             null,
   id_teacher           char(32)             null,
   score                varchar(20)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table et_tievaluationscore
   add constraint PK_ET_TEACHINGINSPECTIONEVALU2 primary key nonclustered (id)
go

/*==============================================================*/
/* Table: et_tpevaluation                                       */
/*==============================================================*/
create table et_tpevaluation (
   id                   char(32)             not null,
   id_etactivitie       char(32)             null,
   name                 varchar(100)         null,
   applicableExam       varchar(100)         null,
   evaluationClassTeacherFlag char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table et_tpevaluation
   add constraint PK_ET_TEACHINGPERFORMANCEEVAL1 primary key nonclustered (id)
go

/*==============================================================*/
/* Table: et_tpevaluationcourse                                 */
/*==============================================================*/
create table et_tpevaluationcourse (
   id                   char(32)             not null,
   id_tpevaluation      char(32)             null,
   id_course            char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table et_tpevaluationcourse
   add constraint PK_ET_TPEVALUATIONCOURSE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: et_tpevaluationelement                                */
/*==============================================================*/
create table et_tpevaluationelement (
   id                   char(32)             not null,
   id_tpevaluation      char(32)             null,
   name                 varchar(100)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table et_tpevaluationelement
   add constraint PK_ET_TEACHINGPERFORMANCEEVAL2 primary key nonclustered (id)
go

/*==============================================================*/
/* Table: et_tpevaluationgrade                                  */
/*==============================================================*/
create table et_tpevaluationgrade (
   id                   char(32)             not null,
   id_tpevaluation      char(32)             null,
   id_schoolgrade       char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table et_tpevaluationgrade
   add constraint PK_ET_TEACHINGPERFORMANCEEVAL3 primary key nonclustered (id)
go

/*==============================================================*/
/* Table: et_tpevaluationscore                                  */
/*==============================================================*/
create table et_tpevaluationscore (
   id                   char(32)             not null,
   id_tpevaluation      char(32)             null,
   id_teacher           char(32)             null,
   score                varchar(20)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table et_tpevaluationscore
   add constraint PK_ET_TEACHINGPERFORMANCEEVAL4 primary key nonclustered (id)
go

/*==============================================================*/
/* Table: et_tpevaluationstandard                               */
/*==============================================================*/
create table et_tpevaluationstandard (
   id                   char(32)             not null,
   id_tpevaluationelement char(32)             null,
   name                 varchar(100)         null,
   calculateLogicFlag1  char(1)              null,
   calculateLogicFlag2  char(1)              null,
   calculateCoefficient varchar(20)          null,
   score                varchar(20)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

/*==============================================================*/
/* Table: et_tpevaluationtarget                                 */
/*==============================================================*/
create table et_tpevaluationtarget (
   id                   char(32)             not null,
   id_tpevaluationelement char(32)             null,
   id_eclass            char(32)             null,
   value                varchar(20)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

/*==============================================================*/
/* Table: et_workloadevaluation                                 */
/*==============================================================*/
create table et_workloadevaluation (
   id                   char(32)             not null,
   id_etactivitie       char(32)             null,
   name                 varchar(100)         null,
   score                varchar(20)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table et_workloadevaluation
   add constraint PK_ET_WORKLOADEVALUATION primary key nonclustered (id)
go

/*==============================================================*/
/* Table: et_workloadevaluationcourse                           */
/*==============================================================*/
create table et_workloadevaluationcourse (
   id                   char(32)             not null,
   id_workloadEvaluation char(32)             null,
   id_course            char(32)             null,
   workload             varchar(20)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table et_workloadevaluationcourse
   add constraint PK_ET_WORKLOADEVALUATIONCOURSE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: et_workloadevaluationgrade                            */
/*==============================================================*/
create table et_workloadevaluationgrade (
   id                   char(32)             not null,
   id_workloadEvaluation char(32)             null,
   id_schoolgrade       char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table et_workloadevaluationgrade
   add constraint PK_ET_WORKLOADEVALUATIONGRADE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: et_workloadevaluationscore                            */
/*==============================================================*/
create table et_workloadevaluationscore (
   id                   char(32)             not null,
   id_workloadEvaluation char(32)             null,
   id_teacher           char(32)             null,
   score                varchar(20)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table et_workloadevaluationscore
   add constraint PK_ET_WORKLOADEVALUATIONSCORE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_coursestandard                                     */
/*==============================================================*/
create table ex_coursestandard (
   id                   char(32)             not null,
   id_examdetail        char(32)             null,
   id_course            char(32)             null,
   id_scoresegment      char(32)             null,
   fullScore            int                  null,
   excelRate            int                  null,
   wellRate             int                  null,
   passRate             int                  null,
   lowRate              int                  null,
   firstBatchLine       int                  null,
   secondBatchLine      int                  null,
   thirdBatchLine       int                  null,
   collegeLine          int                  null,
   areaLine             int                  null,
   cityLine             int                  null,
   provinceLine         int                  null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   standardLine         int                  null
)
go

alter table ex_coursestandard
   add constraint PK_EX_COURSESTANDARD primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_coursestandardtemplate                             */
/*==============================================================*/
create table ex_coursestandardtemplate (
   id                   char(32)             not null,
   id_standardtemplate  char(32)             null,
   id_grade             char(32)             null,
   id_course            char(32)             null,
   id_scoresegment      char(32)             null,
   excelRate            int                  null,
   wellRate             int                  null,
   passRate             int                  null,
   lowRate              int                  null,
   fullScore            int                  null,
   firstBatchLine       int                  null,
   secondBatchLine      int                  null,
   thirdBatchLine       int                  null,
   collegeLine          int                  null,
   areaLine             int                  null,
   cityLine             int                  null,
   provinceLine         int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null,
   standardLine         int                  null
)
go

alter table ex_coursestandardtemplate
   add constraint PK_EX_COURSESTANDARDTEMPLATE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_defaultcoursestandard                              */
/*==============================================================*/
create table ex_defaultcoursestandard (
   id                   char(32)             not null,
   id_exam              char(32)             null,
   id_scoresegment      char(32)             null,
   id_course            char(32)             null,
   id_grade             char(32)             null,
   provinceLine         int                  null,
   cityLine             int                  null,
   areaLine             int                  null,
   collegeLine          int                  null,
   lowRate              int                  null,
   passRate             int                  null,
   wellRate             int                  null,
   thirdBatchLine       int                  null,
   secondBatchLine      int                  null,
   firstBatchLine       int                  null,
   fullScore            int                  null,
   excelRate            int                  null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   standardLine         int                  null
)
go

alter table ex_defaultcoursestandard
   add constraint PK_EX_DEFAULTCOURSESTANDARD primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_entryscorecharger                                  */
/*==============================================================*/
create table ex_entryscorecharger (
   id                   char(32)             not null,
   id_examgrade         char(32)             null,
   id_teacher           char(32)             null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table ex_entryscorecharger
   add constraint PK_EX_ENTRYSCORECHARGER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_entryscoreteacher                                  */
/*==============================================================*/
create table ex_entryscoreteacher (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   id_examdetail        char(32)             null,
   id_eclass            char(32)             null,
   id_examroom          char(32)             null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table ex_entryscoreteacher
   add constraint PK_EX_ENTRYSCORETEACHER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_exam                                               */
/*==============================================================*/
create table ex_exam (
   id                   char(32)             not null,
   id_schoolterm        char(32)             null,
   name                 varchar(50)          null,
   startDate            char(10)             null,
   kindDict             varchar(20)          null,
   description          text                 null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table ex_exam
   add constraint PK_EX_EXAM primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_examdetail                                         */
/*==============================================================*/
create table ex_examdetail (
   id                   char(32)             not null,
   id_examgrade         char(32)             null,
   name                 varchar(200)         null,
   startTime            char(16)             null,
   endTime              char(16)             null,
   entryMode            char(1)              null,
   entryType            char(1)              null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table ex_examdetail
   add constraint PK_EX_EXAMDETAIL primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_examdetailcourse                                   */
/*==============================================================*/
create table ex_examdetailcourse (
   id_course            char(32)             null,
   id_examdetail        char(32)             null
)
go

/*==============================================================*/
/* Table: ex_examdetaileclass                                   */
/*==============================================================*/
create table ex_examdetaileclass (
   id_eclass            char(32)             null,
   id_examdetail        char(32)             null
)
go

/*==============================================================*/
/* Table: ex_examgrade                                          */
/*==============================================================*/
create table ex_examgrade (
   id                   char(32)             not null,
   id_exam              char(32)             null,
   id_grade             char(32)             null,
   releaseFlag          char(1)              null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table ex_examgrade
   add constraint PK_EX_EXAMGRADE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_examknowledgepoint                                 */
/*==============================================================*/
create table ex_examknowledgepoint (
   id                   char(32)             not null,
   id_examdetail        char(32)             null,
   id_knowledgepoint    char(32)             null,
   name                 varchar(50)          null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table ex_examknowledgepoint
   add constraint PK_EX_EXAMKNOWLEDGEPOINT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_examknowledgepointsubject                          */
/*==============================================================*/
create table ex_examknowledgepointsubject (
   id_examknowledgepoint char(32)             null,
   id_examsubject       char(32)             null
)
go

/*==============================================================*/
/* Table: ex_exampaper                                          */
/*==============================================================*/
create table ex_exampaper (
   id                   char(32)             not null,
   id_examdetail        char(32)             null,
   questionTypeFlag     char(1)              null,
   knowledgePointFlag   char(1)              null,
   difficultyFlag       char(1)              null,
   acknowledgeFlag      char(1)              null,
   subPaperFlag         char(1)              null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table ex_exampaper
   add constraint PK_EX_EXAMPAPER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_exampaperanalyze                                   */
/*==============================================================*/
create table ex_exampaperanalyze (
   id                   char(32)             not null,
   id_examdetail        char(32)             null,
   id_examsubject       char(32)             null,
   wrongExample         varchar(2000)        null,
   reason               varchar(2000)        null,
   improvement          varchar(2000)        null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table ex_exampaperanalyze
   add constraint PK_EX_EXAMPAPERANALYZE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_exampaperanalyzenote                               */
/*==============================================================*/
create table ex_exampaperanalyzenote (
   id                   char(32)             not null,
   id_examdetail        char(32)             null,
   id_course            char(32)             null,
   title                varchar(2000)        null,
   content              varchar(2000)        null,
   num                  int                  null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table ex_exampaperanalyzenote
   add constraint PK_EX_EXAMPAPERANALYZENOTE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_examroom                                           */
/*==============================================================*/
create table ex_examroom (
   id                   char(32)             not null,
   id_exam              char(32)             null,
   id_school            char(32)             null,
   id_grade             char(32)             null,
   name                 varchar(50)          null,
   num                  int                  null,
   location             varchar(200)         null,
   capacity             int                  null,
   characterDict        varchar(20)          null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table ex_examroom
   add constraint PK_EX_EXAMROOM primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_examstudent                                        */
/*==============================================================*/
create table ex_examstudent (
   id                   char(32)             not null,
   id_student           char(32)             null,
   id_exam              char(32)             null,
   id_examroom          char(32)             null,
   id_eclass            char(32)             null,
   id_grade             char(32)             null,
   characterDict        varchar(20)          null,
   seatNum              int                  null,
   ft                   char(19)             null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null
)
go

alter table ex_examstudent
   add constraint PK_EX_EXAMSTUDENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_examstudentexamdetail                              */
/*==============================================================*/
create table ex_examstudentexamdetail (
   id                   char(32)             not null,
   id_examdetail        char(32)             null,
   id_examstudent       char(32)             null,
   id_eclass            char(32)             null,
   id_course            char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   lu                   char(32)             null
)
go

alter table ex_examstudentexamdetail
   add constraint PK_EX_EXAMSTUDENTEXAMDETAIL primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_examsubject                                        */
/*==============================================================*/
create table ex_examsubject (
   id                   char(32)             not null,
   id_parent            char(32)             null,
   id_examdetail        char(32)             null,
   id_course            char(32)             null,
   name                 varchar(200)         null,
   num                  int                  null,
   childMode            char(1)              null,
   score                int                  null,
   level                int                  null,
   leafFlag             char(1)              null,
   acknowledgeDict      varchar(20)          null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   id_examsubpaper      char(32)             null,
   questionTypeDict     varchar(20)          null,
   difficultyDict       varchar(20)          null,
   fromNum              int                  null
)
go

alter table ex_examsubject
   add constraint PK_EX_EXAMSUBJECT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_examsubjectknowledgepoint                          */
/*==============================================================*/
create table ex_examsubjectknowledgepoint (
   id_examsubject       char(32)             null,
   id_knowledgepoint    char(32)             null
)
go

/*==============================================================*/
/* Table: ex_examsubpaper                                       */
/*==============================================================*/
create table ex_examsubpaper (
   id                   char(32)             not null,
   id_exampaper         char(32)             null,
   name                 varchar(32)          null,
   num                  int                  null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table ex_examsubpaper
   add constraint PK_EX_EXAMSUBPAPER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_examteachingevaluation                             */
/*==============================================================*/
create table ex_examteachingevaluation (
   id                   char(32)             not null,
   id_examdetail        char(32)             null,
   id_course            char(32)             null,
   characterDict        varchar(20)          null,
   evaluation           varchar(2000)        null,
   experience           varchar(2000)        null,
   question             varchar(2000)        null,
   suggestion           varchar(2000)        null,
   note                 varchar(2000)        null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table ex_examteachingevaluation
   add constraint PK_EX_EXAMTEACHINGEVALUATION primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_excludedstudent                                    */
/*==============================================================*/
create table ex_excludedstudent (
   id                   char(32)             not null,
   id_exam              char(32)             null,
   id_student           char(32)             null,
   id_eclass            char(32)             null,
   id_grade             char(32)             null,
   description          varchar(200)         null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   lu                   char(32)             null
)
go

alter table ex_excludedstudent
   add constraint PK_EX_EXCLUDEDSTUDENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_extendschoolgroup                                  */
/*==============================================================*/
create table ex_extendschoolgroup (
   id                   char(32)             not null,
   id_exam              char(32)             null,
   name                 varchar(50)          null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table ex_extendschoolgroup
   add constraint PK_EX_EXTENDSCHOOLGROUP primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_extendschoolitem                                   */
/*==============================================================*/
create table ex_extendschoolitem (
   id                   char(32)             not null,
   id_extendschoolgroup char(32)             null,
   id_grade             char(32)             null,
   id_course            char(32)             null,
   schoolName           varchar(100)         null,
   excelRate            int                  null,
   passRate             int                  null,
   avgScore             int                  null,
   type                 char(1)              null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table ex_extendschoolitem
   add constraint PK_EX_EXTENDSCHOOLITEM primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_extendschoolscore                                  */
/*==============================================================*/
create table ex_extendschoolscore (
   id                   char(32)             not null,
   id_extendschoolgroup char(32)             null,
   id_grade             char(32)             null,
   id_course            char(32)             null,
   name                 varchar(50)          null,
   score                int                  null,
   schoolName           varchar(100)         null,
   enrollmentCode       varchar(100)         null,
   type                 char(1)              null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table ex_extendschoolscore
   add constraint PK_EX_EXTENDSCHOOLSCORE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_invigilator                                        */
/*==============================================================*/
create table ex_invigilator (
   id                   char(32)             not null,
   id_examroom          char(32)             null,
   id_examdetail        char(32)             null,
   id_teacher           char(32)             null,
   typeFlag             char(1)              null,
   num                  int                  null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table ex_invigilator
   add constraint PK_EX_INVIGILATOR primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_multischoolscore                                   */
/*==============================================================*/
create table ex_multischoolscore (
   id                   char(32)             not null,
   id_multischoolstudent char(32)             null,
   courseName           varchar(20)          null,
   score                int                  null,
   rank                 int                  null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table ex_multischoolscore
   add constraint PK_EX_MULTISCHOOLSCORE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_multischoolsource                                  */
/*==============================================================*/
create table ex_multischoolsource (
   id                   char(32)             not null,
   id_exam              char(32)             null,
   id_grade             char(32)             null,
   name                 varchar(20)          null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table ex_multischoolsource
   add constraint PK_EX_MULTISCHOOLSOURCE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_multischoolstandard                                */
/*==============================================================*/
create table ex_multischoolstandard (
   id                   char(32)             not null,
   id_multischoolsource char(32)             null,
   courseName           varchar(20)          null,
   id_coursestandard    char(32)             null,
   id_totalscore        char(32)             null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table ex_multischoolstandard
   add constraint PK_EX_MULTISCHOOLSTANDARD primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_multischoolstudent                                 */
/*==============================================================*/
create table ex_multischoolstudent (
   id                   char(32)             not null,
   id_multischoolsource char(32)             null,
   studentCode          varchar(50)          null,
   school               varchar(50)          null,
   name                 varchar(20)          null,
   eclass               varchar(20)          null,
   num                  int                  null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table ex_multischoolstudent
   add constraint PK_EX_MULTISCHOOLSTUDENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_ranksegment                                        */
/*==============================================================*/
create table ex_ranksegment (
   id                   char(32)             not null,
   name                 varchar(20)          null,
   content              varchar(500)         null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table ex_ranksegment
   add constraint PK_EX_RANKSEGMENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_rankstandard                                       */
/*==============================================================*/
create table ex_rankstandard (
   id                   char(32)             not null,
   id_extendschoolgroup char(32)             null,
   id_grade             char(32)             null,
   id_course            char(32)             null,
   passRankStd          int                  null,
   excelRankStd         int                  null,
   avgRankStd           int                  null,
   type                 char(1)              null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table ex_rankstandard
   add constraint PK_EX_RANKSTANDARD primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_reportsegment                                      */
/*==============================================================*/
create table ex_reportsegment (
   id                   char(32)             not null,
   id_course            char(32)             null,
   id_scoresegment      char(32)             null,
   id_studentscorereport char(32)             null,
   type                 char(1)              null,
   scoreSegmentType     char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ex_reportsegment
   add constraint PK_EX_REPORTSEGMENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_scoresegment                                       */
/*==============================================================*/
create table ex_scoresegment (
   id                   char(32)             not null,
   id_course            char(32)             null,
   id_grade             char(32)             null,
   name                 varchar(100)         null,
   status               char(1)              null,
   description          text                 null,
   type                 char(1)              null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table ex_scoresegment
   add constraint PK_EX_SCORESEGMENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_scoresegmentitem                                   */
/*==============================================================*/
create table ex_scoresegmentitem (
   id                   char(32)             not null,
   id_scoresegment      char(32)             null,
   name                 varchar(100)         null,
   upperLimit           int                  null,
   lowerLimit           int                  null,
   num                  int                  null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table ex_scoresegmentitem
   add constraint PK_EX_SCORESEGMENTITEM primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_standardtemplate                                   */
/*==============================================================*/
create table ex_standardtemplate (
   id                   char(32)             not null,
   name                 varchar(100)         null,
   status               char(1)              null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   lu                   char(32)             null,
   fu                   char(32)             null
)
go

alter table ex_standardtemplate
   add constraint PK_EX_STANDARDTEMPLATE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_studentscore                                       */
/*==============================================================*/
create table ex_studentscore (
   id                   char(32)             not null,
   id_school            char(32)             null,
   id_grade             char(32)             null,
   id_normaleclass      char(32)             null,
   id_leveleclass       char(32)             null,
   id_course            char(32)             null,
   id_teacher           char(32)             null,
   id_student           char(32)             null,
   id_exam              char(32)             null,
   id_examdetail        char(32)             null,
   id_examstudent       char(32)             null,
   score                int                  null,
   status               char(1)              null,
   absentFlag           char(1)              null,
   excludedFlag         char(1)              null,
   scoreOriginFlag      char(1)              null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table ex_studentscore
   add constraint PK_EX_STUDENTSCORE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_studentscorereport                                 */
/*==============================================================*/
create table ex_studentscorereport (
   id                   char(32)             not null,
   id_grade             char(32)             null,
   classAvgFlag         char(1)              null,
   gradeAvgFlag         char(1)              null,
   classMaxFlag         char(1)              null,
   gradeMaxFlag         char(1)              null,
   classRankFlag        char(1)              null,
   gradeRankFlag        char(1)              null,
   classChartFlag       char(1)              null,
   gradeChartFlag       char(1)              null,
   classTrendFlag       char(1)              null,
   gradeTrendFlag       char(1)              null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ex_studentscorereport
   add constraint PK_EX_STUDENTSCOREREPORT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_subjectscore                                       */
/*==============================================================*/
create table ex_subjectscore (
   id                   char(32)             not null,
   id_examsubject       char(32)             null,
   id_student           char(32)             null,
   id_normaleclass      char(32)             null,
   id_leveleclass       char(32)             null,
   id_school            char(32)             null,
   id_grade             char(32)             null,
   id_teacher           char(32)             null,
   id_examdetail        char(32)             null,
   id_examstudent       char(32)             null,
   score                int                  null,
   status               char(1)              null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table ex_subjectscore
   add constraint PK_EX_SUBJECTSCORE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_totalscore                                         */
/*==============================================================*/
create table ex_totalscore (
   id                   char(32)             not null,
   id_exam              char(32)             null,
   id_grade             char(32)             null,
   id_scoresegment      char(32)             null,
   name                 varchar(50)          null,
   characterDict        varchar(20)          null,
   excelRate            int                  null,
   wellRate             int                  null,
   passRate             int                  null,
   lowRate              int                  null,
   firstBatchLine       int                  null,
   secondBatchLine      int                  null,
   thirdBatchLine       int                  null,
   collegeLine          int                  null,
   areaLine             int                  null,
   cityLine             int                  null,
   provinceLine         int                  null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   standardLine         int                  null
)
go

alter table ex_totalscore
   add constraint PK_EX_TOTALSCORE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_totalscorecourse                                   */
/*==============================================================*/
create table ex_totalscorecourse (
   id_totalscore        char(32)             null,
   id_course            char(32)             null
)
go

/*==============================================================*/
/* Table: ex_totalscoretemplate                                 */
/*==============================================================*/
create table ex_totalscoretemplate (
   id                   char(32)             not null,
   id_standardtemplate  char(32)             null,
   id_grade             char(32)             null,
   id_scoresegment      char(32)             null,
   name                 varchar(50)          null,
   characterDict        varchar(20)          null,
   excelRate            int                  null,
   wellRate             int                  null,
   passRate             int                  null,
   lowRate              int                  null,
   firstBatchLine       int                  null,
   secondBatchLine      int                  null,
   thirdBatchLine       int                  null,
   collegeLine          int                  null,
   areaLine             int                  null,
   cityLine             int                  null,
   provinceLine         int                  null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   standardLine         int                  null
)
go

alter table ex_totalscoretemplate
   add constraint PK_EX_TOTALSCORETEMPLATE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ex_totalscoretemplatecourse                           */
/*==============================================================*/
create table ex_totalscoretemplatecourse (
   id_totalscoretemplate char(32)             null,
   id_course            char(32)             null
)
go

/*==============================================================*/
/* Table: fa_applypurchaserecord                                */
/*==============================================================*/
create table fa_applypurchaserecord (
   id                   char(32)             not null,
   id_assetskind        char(32)             null,
   id_applyUser         char(32)             null,
   id_department        char(32)             null,
   applyDate            char(10)             null,
   number               int                  null,
   checkStatus          char(1)              null,
   demand               varchar(2000)        null,
   reason               varchar(2000)        null,
   checkReason          varchar(2000)        null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table fa_applypurchaserecord
   add constraint PK_FA_APPLYPURCHASERECORD primary key nonclustered (id)
go

/*==============================================================*/
/* Table: fa_assetskind                                         */
/*==============================================================*/
create table fa_assetskind (
   id                   char(32)             not null,
   id_parent            char(32)             null,
   code                 varchar(50)          null,
   name                 varchar(100)         null,
   usefulLife           int                  null,
   netResidualOfRate    int                  null,
   unit                 varchar(20)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table fa_assetskind
   add constraint PK_FA_ASSETSKIND primary key nonclustered (id)
go

/*==============================================================*/
/* Table: fa_borrowrecord                                       */
/*==============================================================*/
create table fa_borrowrecord (
   id                   char(32)             not null,
   id_department        char(32)             null,
   id_borrower          char(32)             null,
   id_operator          char(32)             null,
   borrowDate           char(10)             null,
   contact              varchar(100)         null,
   detail               text                 null,
   reason               varchar(2000)        null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table fa_borrowrecord
   add constraint PK_FA_BORROWRECORD primary key nonclustered (id)
go

/*==============================================================*/
/* Table: fa_fixedassets                                        */
/*==============================================================*/
create table fa_fixedassets (
   id                   char(32)             not null,
   id_assetsKind        char(32)             null,
   id_department        char(32)             null,
   id_user              char(32)             null,
   code                 varchar(50)          null,
   name                 varchar(100)         null,
   brandSpecifications  varchar(100)         null,
   usefulLife           int                  null,
   netResidualOfRate    int                  null,
   unit                 varchar(20)          null,
   unitPrice            int                  null,
   originalPrice        int                  null,
   increaseWay          varchar(20)          null,
   purchaseFactory      varchar(100)         null,
   warrantyPeriod       int                  null,
   contact              varchar(100)         null,
   appropriateCode      varchar(50)          null,
   purchaseDate         char(10)             null,
   registrationDate     char(10)             null,
   status               char(1)              null,
   note                 varchar(200)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table fa_fixedassets
   add constraint PK_FA_FIXEDASSETS primary key nonclustered (id)
go

/*==============================================================*/
/* Table: fa_maintenancerecord                                  */
/*==============================================================*/
create table fa_maintenancerecord (
   id                   char(32)             not null,
   id_fixedassets       char(32)             null,
   id_user              char(32)             null,
   id_operator          char(32)             null,
   cost                 int                  null,
   maintenanceDate      char(10)             null,
   maintenancePerson    varchar(200)         null,
   reason               varchar(2000)        null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table fa_maintenancerecord
   add constraint PK_FA_MAINTENANCERECORD primary key nonclustered (id)
go

/*==============================================================*/
/* Table: fa_reducerecord                                       */
/*==============================================================*/
create table fa_reducerecord (
   id                   char(32)             not null,
   id_fixedassets       char(32)             null,
   id_operator          char(32)             null,
   reduceDate           char(10)             null,
   reduceWay            varchar(20)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table fa_reducerecord
   add constraint PK_FA_REDUCERECORD primary key nonclustered (id)
go

/*==============================================================*/
/* Table: fa_transferrecord                                     */
/*==============================================================*/
create table fa_transferrecord (
   id                   char(32)             not null,
   id_fixedassets       char(32)             null,
   id_custodian         char(32)             null,
   id_user              char(32)             null,
   custodyStartDate     char(10)             null,
   useStartDate         char(10)             null,
   location             varchar(100)         null,
   note                 varchar(200)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table fa_transferrecord
   add constraint PK_FA_TRANSFERRECORD primary key nonclustered (id)
go

/*==============================================================*/
/* Table: fe_collectionsurplus                                  */
/*==============================================================*/
create table fe_collectionsurplus (
   id                   char(32)             not null,
   id_schoolyear        char(32)             null,
   id_student           char(32)             null,
   id_grade             char(32)             null,
   id_eclass            char(32)             null,
   surplus              bigint               null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table fe_collectionsurplus
   add constraint PK_FE_COLLECTIONSURPLUS primary key nonclustered (id)
go

/*==============================================================*/
/* Table: fe_feesrecord                                         */
/*==============================================================*/
create table fe_feesrecord (
   id                   char(32)             not null,
   id_feesstandard      char(32)             null,
   feeKind              char(1)              null,
   feeStatus            char(1)              null,
   id_operator          char(32)             null,
   billTime             char(19)             null,
   id_payee             char(32)             null,
   payTime              char(19)             null,
   id_invalidUser       char(32)             null,
   invalidTime          char(19)             null,
   tuitionFees          bigint               null,
   dormFees             bigint               null,
   collectionFees       bigint               null,
   mealsFees            bigint               null,
   busFees              bigint               null,
   summerCampFees       bigint               null,
   degreeDepositFees    bigint               null,
   chargeItem1Fees      bigint               null,
   chargeItem2Fees      bigint               null,
   chargeItem3Fees      bigint               null,
   chargeItem4Fees      bigint               null,
   chargeItem5Fees      bigint               null,
   icbcFlag             char(1)              null,
   icbcFees             bigint               null,
   bocomFlag            char(1)              null,
   bocomFees            bigint               null,
   hxbFlag              char(1)              null,
   hxbFees              bigint               null,
   cashFlag             char(1)              null,
   cashFees             bigint               null,
   transferAccountsFlag char(1)              null,
   transferAccountsFees bigint               null,
   paymentForm1         char(1)              null,
   paymentForm1Fees     bigint               null,
   paymentForm2         char(1)              null,
   paymentForm2Fees     bigint               null,
   paymentForm3         char(1)              null,
   paymentForm3Fees     bigint               null,
   receiptNum           varchar(200)         null,
   receiveReceiptFlag   char(1)              null,
   id_checkUser         char(32)             null,
   checkTime            char(19)             null,
   checkStatus          char(1)              null,
   checkView            varchar(500)         null,
   remittanceConfirmFlag char(1)              null,
   balanceFlag          char(1)              null,
   summerCampTransactionFlag char(1)              null,
   degreeDeposiTransactionFlag char(1)              null,
   subtotal             bigint               null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   surplusFees          bigint               null,
   surplusFlag          char(1)              null
)
go

alter table fe_feesrecord
   add constraint PK_FE_FEESRECORD primary key nonclustered (id)
go

/*==============================================================*/
/* Table: fe_feesstandard                                       */
/*==============================================================*/
create table fe_feesstandard (
   id                   char(32)             not null,
   id_gradefeesstandard char(32)             null,
   id_schoolyear        char(32)             null,
   studentKind          char(1)              null,
   id_studentregistration char(32)             null,
   id_student           char(32)             null,
   id_receivablesSettingUser char(32)             null,
   receivablesSettingTime char(19)             null,
   tuitionReceivables   bigint               null,
   dormReceivables      bigint               null,
   collectionReceivables bigint               null,
   mealsReceivables     bigint               null,
   busReceivables       bigint               null,
   summerCampReceivables bigint               null,
   degreeDepositReceivables bigint               null,
   chargeItem1Receivables bigint               null,
   chargeItem2Receivables bigint               null,
   chargeItem3Receivables bigint               null,
   chargeItem4Receivables bigint               null,
   chargeItem5Receivables bigint               null,
   id_receivablesCheckUser char(32)             null,
   receivablesCheckTime char(19)             null,
   receivablesCheckStatus char(1)              null,
   receivablesCheckView varchar(500)         null,
   id_remissionSettingUser char(32)             null,
   remissionSettingTime char(19)             null,
   tuitionRemission     bigint               null,
   dormRemission        bigint               null,
   collectionRemission  bigint               null,
   mealsRemission       bigint               null,
   busRemission         bigint               null,
   summerCampRemission  bigint               null,
   degreeDepositRemission bigint               null,
   chargeItem1Remission bigint               null,
   chargeItem2Remission bigint               null,
   chargeItem3Remission bigint               null,
   chargeItem4Remission bigint               null,
   chargeItem5Remission bigint               null,
   requestNumber        varchar(200)         null,
   remarks              varchar(500)         null,
   paymentKind          char(1)              null,
   id_retreatSettingUser char(32)             null,
   retreatSettingTime   char(19)             null,
   tuitionRetreat       bigint               null,
   dormRetreat          bigint               null,
   collectionRetreat    bigint               null,
   mealsRetreat         bigint               null,
   busRetreat           bigint               null,
   summerCampRetreat    bigint               null,
   degreeDepositRetreat bigint               null,
   chargeItem1Retreat   bigint               null,
   chargeItem2Retreat   bigint               null,
   chargeItem3Retreat   bigint               null,
   chargeItem4Retreat   bigint               null,
   chargeItem5Retreat   bigint               null,
   id_retreatCheckUser  char(32)             null,
   retreatCheckTime     char(19)             null,
   retreatCheckStatus   char(1)              null,
   retreatCheckView     varchar(500)         null,
   receivablesSubtotal  bigint               null,
   retreatSubtotal      bigint               null,
   status               char(1)              null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   postponementDate     char(10)             null
)
go

alter table fe_feesstandard
   add constraint PK_FE_FEESSTANDARD primary key nonclustered (id)
go

/*==============================================================*/
/* Table: fe_gradefeesstandard                                  */
/*==============================================================*/
create table fe_gradefeesstandard (
   id                   char(32)             not null,
   id_schoolyear        char(32)             null,
   id_school            char(32)             null,
   id_grade             char(32)             null,
   id_standardSettingUser char(32)             null,
   standardSettingTime  char(19)             null,
   tuitionStandard      bigint               null,
   dormStandard         bigint               null,
   collectionStandard   bigint               null,
   mealsStandard        bigint               null,
   busStandard          bigint               null,
   summerCampStandard   bigint               null,
   degreeDepositStandard bigint               null,
   chargeItem1Standard  bigint               null,
   chargeItem2Standard  bigint               null,
   chargeItem3Standard  bigint               null,
   chargeItem4Standard  bigint               null,
   chargeItem5Standard  bigint               null,
   totalStandard        bigint               null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table fe_gradefeesstandard
   add constraint PK_FE_GRADEFEESSTANDARD primary key nonclustered (id)
go

/*==============================================================*/
/* Table: fe_summercamplist                                     */
/*==============================================================*/
create table fe_summercamplist (
   id                   char(32)             not null,
   id_feesstandard      char(32)             null,
   phase                char(1)              null,
   participateFlag      char(1)              null,
   enteringSchoolFlag   char(1)              null,
   fees                 bigint               null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table fe_summercamplist
   add constraint PK_FE_SUMMERCAMPLIST primary key nonclustered (id)
go

/*==============================================================*/
/* Table: fi_salary                                             */
/*==============================================================*/
create table fi_salary (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   payDate              char(10)             null,
   name                 varchar(50)          null,
   salaryFlag           char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table fi_salary
   add constraint PK_FI_SALARY primary key nonclustered (id)
go

/*==============================================================*/
/* Table: fi_salaryamount                                       */
/*==============================================================*/
create table fi_salaryamount (
   id                   char(32)             not null,
   id_salary            char(32)             null,
   id_salaryitem        char(32)             null,
   count                int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table fi_salaryamount
   add constraint PK_FI_SALARYAMOUNT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: fi_salaryitem                                         */
/*==============================================================*/
create table fi_salaryitem (
   id                   char(32)             not null,
   id_parent            char(32)             null,
   itemName             varchar(50)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table fi_salaryitem
   add constraint PK_FI_SALARYITEM primary key nonclustered (id)
go

/*==============================================================*/
/* Table: fi_wage                                               */
/*==============================================================*/
create table fi_wage (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   payDate              char(10)             null,
   total                int                  null,
   detail               text                 null,
   wageKind             varchar(50)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table fi_wage
   add constraint PK_FI_WAGE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: fw_attachment                                         */
/*==============================================================*/
create table fw_attachment (
   id                   char(32)             not null,
   id_owner             char(32)             null,
   path                 varchar(200)         null,
   serverFilename       varchar(200)         null,
   sourceFilename       varchar(200)         null,
   fileNum              int                  null,
   fileSize             varchar(50)          null,
   fileKind             varchar(20)          null,
   status               char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table fw_attachment
   add constraint PK_FW_ATTACHMENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: fw_attachmentconfig                                   */
/*==============================================================*/
create table fw_attachmentconfig (
   id                   char(32)             not null,
   code                 varchar(50)          null,
   saveDir              varchar(200)         null,
   dirLevel             int                  null,
   fileSize             int                  null,
   fileCount            int                  null,
   extension            varchar(200)         null,
   description          varchar(200)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table fw_attachmentconfig
   add constraint PK_FW_ATTACHMENTCONFIG primary key nonclustered (id)
go

/*==============================================================*/
/* Table: fw_attachmentsetting                                  */
/*==============================================================*/
create table fw_attachmentsetting (
   id                   char(32)             not null,
   limitFileExtention   varchar(500)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table fw_attachmentsetting
   add constraint PK_FW_ATTACHMENTSETTING primary key nonclustered (id)
go

/*==============================================================*/
/* Table: il_learningtask                                       */
/*==============================================================*/
create table il_learningtask (
   id                   char(32)             not null,
   id_schoolterm        char(32)             null,
   id_testpaper         char(32)             null,
   id_course            char(32)             null,
   id_teacher           char(32)             null,
   startTime            char(19)             null,
   endTime              char(19)             null,
   otherrequirements    text                 null,
   duration             int                  null,
   status               char(1)              null,
   submitDate           char(16)             null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table il_learningtask
   add constraint PK_IL_LEARNINGTASK primary key nonclustered (id)
go

/*==============================================================*/
/* Table: il_learningtaskeclass                                 */
/*==============================================================*/
create table il_learningtaskeclass (
   id                   char(32)             not null,
   id_learningtask      char(32)             null,
   id_eclass            char(32)             null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table il_learningtaskeclass
   add constraint PK_IL_LEARNINGTASKECLASS primary key nonclustered (id)
go

/*==============================================================*/
/* Table: il_learningtaskstudent                                */
/*==============================================================*/
create table il_learningtaskstudent (
   id                   char(32)             not null,
   id_learningtask      char(32)             null,
   id_eclass            char(32)             null,
   id_student           char(32)             null,
   comment              varchar(500)         null,
   totalScore           int                  null,
   publicStatus         char(1)              null,
   submitStatus         char(1)              null,
   markStatus           char(1)              null,
   submitDate           char(16)             null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table il_learningtaskstudent
   add constraint PK_IL_LEARNINGTASKSTUDENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: il_studenttaskresult                                  */
/*==============================================================*/
create table il_studenttaskresult (
   id                   char(32)             not null,
   id_learningtaskstudent char(32)             null,
   id_testpaperquestion char(32)             null,
   result               text                 null,
   score                int                  null,
   comment              varchar(500)         null,
   pictureId            char(32)             null,
   videoId              char(32)             null,
   audioId              char(32)             null,
   wordId               char(32)             null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table il_studenttaskresult
   add constraint PK_IL_STUDENTTASKRESULT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: im_eclassrule                                         */
/*==============================================================*/
create table im_eclassrule (
   id                   char(32)             not null,
   weeknum              int                  null,
   id_scheduledetail    char(32)             null,
   id_eclass            char(32)             null,
   weekday              int                  null,
   name                 varchar(200)         null,
   note                 varchar(500)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table im_eclassrule
   add constraint PK_IM_ECLASSRULE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: im_managementitem                                     */
/*==============================================================*/
create table im_managementitem (
   id                   char(32)             not null,
   kind                 char(1)              null,
   name                 varchar(200)         null,
   teacherLimit         int                  null,
   studentLimit         int                  null,
   status               char(1)              null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table im_managementitem
   add constraint PK_IM_MANAGEMENTITEM primary key nonclustered (id)
go

/*==============================================================*/
/* Table: im_managementschedule                                 */
/*==============================================================*/
create table im_managementschedule (
   id                   char(32)             not null,
   id_school            char(32)             null,
   id_schoolyear        char(32)             null,
   id_schoolterm        char(32)             null,
   weeknum              int                  null,
   showdays             int                  null,
   status               char(1)              null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table im_managementschedule
   add constraint PK_IM_MANAGEMENTSCHEDULE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: im_schedulearrangement                                */
/*==============================================================*/
create table im_schedulearrangement (
   id                   char(32)             not null,
   weeknum              int                  null,
   weekday              int                  null,
   id_scheduledetail    char(32)             null,
   id_managementItem    char(32)             null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table im_schedulearrangement
   add constraint PK_IM_SCHEDULEARRANGEMENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: im_scheduledetail                                     */
/*==============================================================*/
create table im_scheduledetail (
   id                   char(32)             not null,
   id_managementSchedule char(32)             null,
   name                 varchar(200)         null,
   starttime            char(5)              null,
   endtime              char(5)              null,
   sortNum              int                  null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table im_scheduledetail
   add constraint PK_IM_SCHEDULEDETAIL primary key nonclustered (id)
go

/*==============================================================*/
/* Table: im_studentappointment                                 */
/*==============================================================*/
create table im_studentappointment (
   id                   char(32)             not null,
   id_student           char(32)             null,
   id_eclass            char(32)             null,
   id_school            char(32)             null,
   id_schoolyear        char(32)             null,
   id_schoolterm        char(32)             null,
   weeknum              int                  null,
   weekday              int                  null,
   id_scheduleDetail    char(32)             null,
   kind                 char(1)              null,
   id_managementItem    char(32)             null,
   id_teacher           char(32)             null,
   id_course            char(32)             null,
   status               char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table im_studentappointment
   add constraint PK_IM_STUDENTAPPOINTMENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: im_teacherappointment                                 */
/*==============================================================*/
create table im_teacherappointment (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   id_schoolyear        char(32)             null,
   id_schoolterm        char(32)             null,
   id_school            char(32)             null,
   weeknum              int                  null,
   weekday              int                  null,
   id_scheduleDetail    char(32)             null,
   id_managementItem    char(32)             null,
   id_course            char(32)             null,
   studentLimit         int                  null,
   kind                 char(1)              null,
   status               char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table im_teacherappointment
   add constraint PK_IM_TEACHERAPPOINTMENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: im_tutorstudent                                       */
/*==============================================================*/
create table im_tutorstudent (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   id_student           char(32)             null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table im_tutorstudent
   add constraint PK_IM_TUTORSTUDENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: la_applyinstrument                                    */
/*==============================================================*/
create table la_applyinstrument (
   id                   char(32)             not null,
   id_instrumentform    char(32)             null,
   id_instrument        char(32)             null,
   num                  int                  null,
   ft                   char(19)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   lu                   char(32)             null
)
go

alter table la_applyinstrument
   add constraint PK_LA_APPLYINSTRUMENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: la_experiment                                         */
/*==============================================================*/
create table la_experiment (
   id                   char(32)             not null,
   name                 varchar(100)         null,
   id_course            char(32)             null,
   id_grade             char(32)             null,
   chapter              varchar(100)         null,
   experimentType       varchar(20)          null,
   ft                   char(19)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   lu                   char(32)             null
)
go

alter table la_experiment
   add constraint PK_LA_EXPERIMENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: la_experimentalcourses                                */
/*==============================================================*/
create table la_experimentalcourses (
   id                   char(32)             not null,
   id_lab               char(32)             null,
   id_course            char(32)             null,
   lu                   char(32)             null,
   ft                   char(19)             null,
   fu                   char(32)             null,
   lt                   char(19)             null
)
go

alter table la_experimentalcourses
   add constraint PK_LA_EXPERIMENTALCOURSES primary key nonclustered (id)
go

/*==============================================================*/
/* Table: la_experimentalteachers                               */
/*==============================================================*/
create table la_experimentalteachers (
   id                   char(32)             not null,
   id_lab               char(32)             null,
   id_teacher           char(32)             null,
   lu                   char(32)             null,
   ft                   char(19)             null,
   fu                   char(32)             null,
   lt                   char(19)             null
)
go

alter table la_experimentalteachers
   add constraint PK_LA_EXPERIMENTALTEACHERS primary key nonclustered (id)
go

/*==============================================================*/
/* Table: la_instrument                                         */
/*==============================================================*/
create table la_instrument (
   id                   char(32)             not null,
   name                 varchar(100)         null,
   id_instrumentType    char(32)             null,
   id_course            char(32)             null,
   specification        varchar(100)         null,
   ft                   char(19)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   lu                   char(32)             null
)
go

alter table la_instrument
   add constraint PK_LA_INSTRUMENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: la_instrumentform                                     */
/*==============================================================*/
create table la_instrumentform (
   id                   char(32)             not null,
   id_schoolterm        char(32)             null,
   id_school            char(32)             null,
   id_course            char(32)             null,
   experimentType       varchar(20)          null,
   experimentCategory   varchar(20)          null,
   experimentProperty   varchar(20)          null,
   id_experiment        char(32)             null,
   name                 varchar(100)         null,
   sets                 int                  null,
   planDate             char(10)             null,
   planLesson           varchar(50)          null,
   actualDate           char(10)             null,
   actualLesson         varchar(50)          null,
   useStatus            char(1)              null,
   id_fillingFormTeacher char(32)             null,
   status               char(1)              null,
   id_signFormTeacher   char(32)             null,
   supplementStatus     char(1)              null,
   withoutMakingReason  varchar(20)          null,
   ft                   char(19)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   lu                   char(32)             null
)
go

alter table la_instrumentform
   add constraint PK_LA_INSTRUMENTFORM primary key nonclustered (id)
go

/*==============================================================*/
/* Table: la_instrumentformeclass                               */
/*==============================================================*/
create table la_instrumentformeclass (
   id                   char(32)             not null,
   id_instrumentform    char(32)             null,
   id_eclass            char(32)             null,
   ft                   char(19)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   lu                   char(32)             null
)
go

alter table la_instrumentformeclass
   add constraint PK_LA_INSTRUMENTFORMECLASS primary key nonclustered (id)
go

/*==============================================================*/
/* Table: la_instrumentformexperteacher                         */
/*==============================================================*/
create table la_instrumentformexperteacher (
   id                   char(32)             not null,
   id_instrumentform    char(32)             null,
   id_teacher           char(32)             null,
   ft                   char(19)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   lu                   char(32)             null
)
go

alter table la_instrumentformexperteacher
   add constraint PK_LA_INSTRUMENTFORMEXPERTEACH primary key nonclustered (id)
go

/*==============================================================*/
/* Table: la_instrumentformteacher                              */
/*==============================================================*/
create table la_instrumentformteacher (
   id                   char(32)             not null,
   id_instrumentform    char(32)             null,
   id_teacher           char(32)             null,
   ft                   char(19)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   lu                   char(32)             null
)
go

alter table la_instrumentformteacher
   add constraint PK_LA_INSTRUMENTFORMTEACHER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: la_instrumenttype                                     */
/*==============================================================*/
create table la_instrumenttype (
   id                   char(32)             not null,
   name                 varchar(100)         null,
   remark               text                 null,
   num                  int                  null,
   ft                   char(19)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   lu                   char(32)             null
)
go

alter table la_instrumenttype
   add constraint PK_LA_INSTRUMENTTYPE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: la_lab                                                */
/*==============================================================*/
create table la_lab (
   id                   char(32)             not null,
   name                 varchar(100)         null,
   id_school            char(32)             null,
   remark               text                 null,
   ft                   char(19)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   lu                   char(32)             null
)
go

alter table la_lab
   add constraint PK_LA_LAB primary key nonclustered (id)
go

/*==============================================================*/
/* Table: la_reportedLossinstrument                             */
/*==============================================================*/
create table la_reportedLossinstrument (
   id                   char(32)             not null,
   id_instrumentform    char(32)             null,
   id_instrument        char(32)             null,
   id_eclass            char(32)             null,
   reportedLossType     varchar(20)          null,
   num                  int                  null,
   ft                   char(19)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   lu                   char(32)             null
)
go

alter table la_reportedLossinstrument
   add constraint PK_LA_REPORTEDLOSSINSTRUMENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: lu_activitydetail                                     */
/*==============================================================*/
create table lu_activitydetail (
   id                   char(32)             not null,
   name                 varchar(40)          null,
   id_activityType      char(32)             null,
   content              text                 null,
   date                 char(10)             null,
   num                  int                  null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table lu_activitydetail
   add constraint PK_LU_ACTIVITYDETAIL primary key nonclustered (id)
go

/*==============================================================*/
/* Table: lu_activitytype                                       */
/*==============================================================*/
create table lu_activitytype (
   id                   char(32)             not null,
   name                 varchar(50)          null,
   tempCode             varchar(40)          null,
   num                  int                  null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table lu_activitytype
   add constraint PK_LU_ACTIVITYTYPE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: lu_partyinfo                                          */
/*==============================================================*/
create table lu_partyinfo (
   id                   char(32)             not null,
   name                 varchar(40)          null,
   id_teacher           char(32)             null,
   political            varchar(40)          null,
   partyPosts           varchar(40)          null,
   date                 char(10)             null,
   cardNum              varchar(50)          null,
   introducer           varchar(40)          null,
   contactTel           varchar(40)          null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table lu_partyinfo
   add constraint PK_LU_PARTYINFO primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ma_materials                                          */
/*==============================================================*/
create table ma_materials (
   id                   char(32)             not null,
   id_materialshandinuser char(32)             null,
   id_materialskind     char(32)             null,
   name                 varchar(200)         null,
   description          varchar(200)         null,
   handinTime           char(19)             null,
   id_approvaluser      char(32)             null,
   approvalView         varchar(200)         null,
   approvalTime         char(19)             null,
   approvalStatus       char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ma_materials
   add constraint PK_MA_MATERIALS primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ma_materialsapprovaluser                              */
/*==============================================================*/
create table ma_materialsapprovaluser (
   id                   char(32)             not null,
   id_materialskind     char(32)             null,
   id_materialsnotice   char(32)             null,
   id_user              char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ma_materialsapprovaluser
   add constraint PK_MA_MATERIALSAPPROVALUSER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ma_materialshandinuser                                */
/*==============================================================*/
create table ma_materialshandinuser (
   id                   char(32)             not null,
   id_materialsnotice   char(32)             null,
   id_user              char(32)             null,
   status               char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ma_materialshandinuser
   add constraint PK_MA_MATERIALSHANDINUSER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ma_materialskind                                      */
/*==============================================================*/
create table ma_materialskind (
   id                   char(32)             not null,
   id_parent            char(32)             null,
   name                 varchar(100)         null,
   num                  int                  null,
   description          varchar(200)         null,
   enableFlag           char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ma_materialskind
   add constraint PK_MA_MATERIALSKIND primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ma_materialsnotice                                    */
/*==============================================================*/
create table ma_materialsnotice (
   id                   char(32)             not null,
   id_user              char(32)             null,
   id_department        char(32)             null,
   title                varchar(200)         null,
   content              text                 null,
   sendTime             char(19)             null,
   deadline             char(10)             null,
   approvalFlag         char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null,
   timelimitFlag        char(1)              null
)
go

alter table ma_materialsnotice
   add constraint PK_MA_MATERIALSNOTICE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ma_viewrecord                                         */
/*==============================================================*/
create table ma_viewrecord (
   id                   char(32)             not null,
   id_materials         char(32)             null,
   id_viewUser          char(32)             null,
   id_attachment        char(32)             null,
   kind                 char(1)              null,
   viewTime             char(19)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ma_viewrecord
   add constraint PK_MA_VIEWRECORD primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ma_viewuser                                           */
/*==============================================================*/
create table ma_viewuser (
   id_materialsnotice   char(32)             null,
   id_user              char(32)             null
)
go

/*==============================================================*/
/* Table: mc_checkrecord                                        */
/*==============================================================*/
create table mc_checkrecord (
   id                   char(32)             not null,
   id_subitemdetail     char(32)             null,
   id_eclass            char(32)             null,
   id_user              char(32)             null,
   recordTime           char(19)             null,
   score                int                  null,
   description          varchar(500)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table mc_checkrecord
   add constraint PK_MC_CHECKRECORD primary key nonclustered (id)
go

/*==============================================================*/
/* Table: mc_mainstandard                                       */
/*==============================================================*/
create table mc_mainstandard (
   id                   char(32)             not null,
   id_ranksign          char(32)             null,
   id_schoolterm        char(32)             null,
   name                 varchar(500)         null,
   baseScore            int                  null,
   countCycle           int                  null,
   rankScore            int                  null,
   rankMultiple         int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table mc_mainstandard
   add constraint PK_MC_MAINSTANDARD primary key nonclustered (id)
go

/*==============================================================*/
/* Table: mc_mainstandardschoolgrade                            */
/*==============================================================*/
create table mc_mainstandardschoolgrade (
   id                   char(32)             not null,
   id_mainstandard      char(32)             null,
   id_schoolgrade       char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table mc_mainstandardschoolgrade
   add constraint PK_MC_MAINSTANDARDSCHOOLGRADE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: mc_ranksign                                           */
/*==============================================================*/
create table mc_ranksign (
   id                   char(32)             not null,
   code                 varchar(20)          null,
   num                  int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table mc_ranksign
   add constraint PK_MC_RANKSIGN primary key nonclustered (id)
go

/*==============================================================*/
/* Table: mc_recorduser                                         */
/*==============================================================*/
create table mc_recorduser (
   id                   char(32)             not null,
   id_subitemstandard   char(32)             null,
   id_user              char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table mc_recorduser
   add constraint PK_MC_RECORDUSER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: mc_relatedstudent                                     */
/*==============================================================*/
create table mc_relatedstudent (
   id                   char(32)             not null,
   id_checkrecord       char(32)             null,
   id_student           char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table mc_relatedstudent
   add constraint PK_MC_RELATEDSTUDENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: mc_scorestandard                                      */
/*==============================================================*/
create table mc_scorestandard (
   id                   char(32)             not null,
   id_subitemdetail     char(32)             null,
   minScore             int                  null,
   maxScore             int                  null,
   defaultScore         int                  null,
   intervalScore        int                  null,
   tickScore            int                  null,
   tickFlag             char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table mc_scorestandard
   add constraint PK_MC_SCORESTANDARD primary key nonclustered (id)
go

/*==============================================================*/
/* Table: mc_subitemdetail                                      */
/*==============================================================*/
create table mc_subitemdetail (
   id                   char(32)             not null,
   id_subitemstandard   char(32)             null,
   id_subitemtype       char(32)             null,
   name                 varchar(500)         null,
   description          varchar(500)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table mc_subitemdetail
   add constraint PK_MC_SUBITEMDETAIL primary key nonclustered (id)
go

/*==============================================================*/
/* Table: mc_subitemstandard                                    */
/*==============================================================*/
create table mc_subitemstandard (
   id                   char(32)             not null,
   id_mainstandard      char(32)             null,
   id_ranksign          char(32)             null,
   name                 varchar(500)         null,
   checkObject          char(1)              null,
   baseScore            int                  null,
   recordMode           char(1)              null,
   description          varchar(500)         null,
   rankScore            int                  null,
   rankMultiple         int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table mc_subitemstandard
   add constraint PK_MC_SUBITEMSTANDARD primary key nonclustered (id)
go

/*==============================================================*/
/* Table: mc_subitemstandardschoolgrade                         */
/*==============================================================*/
create table mc_subitemstandardschoolgrade (
   id                   char(32)             not null,
   id_subitemstandard   char(32)             null,
   id_schoolgrade       char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table mc_subitemstandardschoolgrade
   add constraint PK_MC_SUBITEMSTANDARDSCHOOLGRA primary key nonclustered (id)
go

/*==============================================================*/
/* Table: mc_subitemtype                                        */
/*==============================================================*/
create table mc_subitemtype (
   id                   char(32)             not null,
   id_subitemstandard   char(32)             null,
   name                 varchar(500)         null,
   baseScore            int                  null,
   description          varchar(500)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table mc_subitemtype
   add constraint PK_MC_SUBITEMTYPE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: me_memo                                               */
/*==============================================================*/
create table me_memo (
   id                   char(32)             not null,
   id_user              char(32)             null,
   content              varchar(500)         null,
   status               char(1)              null,
   createTime           char(19)             null,
   modifyTime           char(19)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table me_memo
   add constraint PK_ME_MEMO primary key nonclustered (id)
go

/*==============================================================*/
/* Table: me_memomemotemplate                                   */
/*==============================================================*/
create table me_memomemotemplate (
   id_memo              char(32)             not null,
   id_memotemplate      char(32)             null
)
go

/*==============================================================*/
/* Table: me_memotemplate                                       */
/*==============================================================*/
create table me_memotemplate (
   id                   char(32)             not null,
   name                 varchar(20)          null,
   description          varchar(500)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table me_memotemplate
   add constraint PK_ME_MEMOTEMPLATE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: mh_dinnertime                                         */
/*==============================================================*/
create table mh_dinnertime (
   id                   char(32)             not null,
   id_messhall          char(32)             null,
   dinnerKind           char(1)              null,
   startTime            char(5)              null,
   endTime              char(5)              null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table mh_dinnertime
   add constraint PK_MH_DINNERTIME primary key nonclustered (id)
go

/*==============================================================*/
/* Table: mh_dish                                               */
/*==============================================================*/
create table mh_dish (
   id                   char(32)             not null,
   name                 varchar(100)         null,
   kind                 char(1)              null,
   status               char(1)              null,
   description          varchar(500)         null,
   price                int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table mh_dish
   add constraint PK_MH_DISH primary key nonclustered (id)
go

/*==============================================================*/
/* Table: mh_menu                                               */
/*==============================================================*/
create table mh_menu (
   id                   char(32)             not null,
   date                 char(10)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table mh_menu
   add constraint PK_MH_MENU primary key nonclustered (id)
go

/*==============================================================*/
/* Table: mh_menudish                                           */
/*==============================================================*/
create table mh_menudish (
   id_menu              char(32)             null,
   id_dish              char(32)             null
)
go

/*==============================================================*/
/* Table: mh_messhall                                           */
/*==============================================================*/
create table mh_messhall (
   id                   char(32)             not null,
   name                 varchar(100)         null,
   capacity             int                  null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table mh_messhall
   add constraint PK_MH_MESSHALL primary key nonclustered (id)
go

/*==============================================================*/
/* Table: mh_messhallmenu                                       */
/*==============================================================*/
create table mh_messhallmenu (
   id                   char(32)             not null,
   id_messhall          char(32)             null,
   startDate            char(10)             null,
   endDate              char(10)             null,
   status               char(1)              null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table mh_messhallmenu
   add constraint PK_MH_MESSHALLMENU primary key nonclustered (id)
go

/*==============================================================*/
/* Table: mh_messhallmenudish                                   */
/*==============================================================*/
create table mh_messhallmenudish (
   id                   char(32)             not null,
   id_messhall          char(32)             null,
   id_dish              char(32)             null,
   dinnerKind           char(1)              null,
   price                int                  null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table mh_messhallmenudish
   add constraint PK_MH_MESSHALLMENUDISH primary key nonclustered (id)
go

/*==============================================================*/
/* Table: mh_order                                              */
/*==============================================================*/
create table mh_order (
   id                   char(32)             not null,
   date                 char(10)             null,
   id_user              char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table mh_order
   add constraint PK_MH_ORDER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: mh_orderdish                                          */
/*==============================================================*/
create table mh_orderdish (
   id                   char(32)             not null,
   id_order             char(32)             null,
   id_dish              char(32)             null,
   date                 char(10)             null,
   cnt                  int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table mh_orderdish
   add constraint PK_MH_ORDERDISH primary key nonclustered (id)
go

/*==============================================================*/
/* Table: mh_studentorder                                       */
/*==============================================================*/
create table mh_studentorder (
   id                   char(32)             not null,
   id_messhall          char(32)             null,
   id_user              char(32)             null,
   dinnerTime           char(11)             null,
   orderDate            char(10)             null,
   dinnerKind           char(1)              null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table mh_studentorder
   add constraint PK_MH_STUDENTORDER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: mh_stuentmenudish                                     */
/*==============================================================*/
create table mh_stuentmenudish (
   id                   char(32)             not null,
   id_studentorder      char(32)             null,
   id_dish              char(32)             null,
   cnt                  int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table mh_stuentmenudish
   add constraint PK_MH_STUENTMENUDISH primary key nonclustered (id)
go

/*==============================================================*/
/* Table: mo_mood                                               */
/*==============================================================*/
create table mo_mood (
   id                   char(32)             not null,
   id_user              char(32)             null,
   note                 varchar(500)         null,
   loginTime            char(19)             null,
   loginStatus          char(1)              null,
   loginIp              char(15)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table mo_mood
   add constraint PK_MO_MOOD primary key nonclustered (id)
go

/*==============================================================*/
/* Table: mo_moodkind                                           */
/*==============================================================*/
create table mo_moodkind (
   id                   char(32)             not null,
   name                 varchar(20)          null,
   description          varchar(500)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table mo_moodkind
   add constraint PK_MO_MOODKIND primary key nonclustered (id)
go

/*==============================================================*/
/* Table: mo_moodmoodkind                                       */
/*==============================================================*/
create table mo_moodmoodkind (
   id_mood              char(32)             null,
   id_moodkind          char(32)             null
)
go

/*==============================================================*/
/* Table: no_kindsetting                                        */
/*==============================================================*/
create table no_kindsetting (
   id                   char(32)             not null,
   kind                 varchar(20)          null,
   name                 varchar(100)         null,
   id_user              char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table no_kindsetting
   add constraint PK_NO_KINDSETTING primary key nonclustered (id)
go

/*==============================================================*/
/* Table: no_notice                                             */
/*==============================================================*/
create table no_notice (
   id                   char(32)             not null,
   id_user              char(32)             null,
   id_department        char(32)             null,
   noticeKindDict       varchar(20)          null,
   subject              varchar(100)         null,
   content              text                 null,
   sendTime             char(19)             null,
   priority             char(1)              null,
   replyFlag            char(1)              null,
   readReplyFlag        char(1)              null,
   attentionFlag        char(1)              null,
   status               char(1)              null,
   withdrawReason       varchar(200)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table no_notice
   add constraint PK_NO_NOTICE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: no_noticereply                                        */
/*==============================================================*/
create table no_noticereply (
   id                   char(32)             not null,
   id_receivenotice     char(32)             null,
   id_user              char(32)             null,
   replyTime            char(19)             null,
   content              text                 null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table no_noticereply
   add constraint PK_NO_NOTICEREPLY primary key nonclustered (id)
go

/*==============================================================*/
/* Table: no_receivenotice                                      */
/*==============================================================*/
create table no_receivenotice (
   id                   char(32)             not null,
   id_notice            char(32)             null,
   id_user              char(32)             null,
   id_department        char(32)             null,
   readFlag             char(1)              null,
   readingTime          char(19)             null,
   attentionFlag        char(1)              null,
   deleteFlag           char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table no_receivenotice
   add constraint PK_NO_RECEIVENOTICE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ns_entrancetestcourse                                 */
/*==============================================================*/
create table ns_entrancetestcourse (
   id                   char(32)             not null,
   id_course            char(32)             null,
   entranceYear         char(10)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ns_entrancetestcourse
   add constraint PK_NS_ENTRANCETESTCOURSE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ns_entrancetestscore                                  */
/*==============================================================*/
create table ns_entrancetestscore (
   id                   char(32)             not null,
   id_studentregistration char(32)             null,
   id_entrancetestcourse char(32)             null,
   kind                 char(32)             null,
   score                int                  null,
   description          varchar(500)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ns_entrancetestscore
   add constraint PK_NS_ENTRANCETESTSCORE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: od_documentauditor                                    */
/*==============================================================*/
create table od_documentauditor (
   id                   char(32)             not null,
   id_auditor           char(32)             null,
   id_officialdocument  char(32)             null,
   examineStatus        char(1)              null,
   examineSuggestion    varchar(500)         null,
   examineTime          char(19)             null,
   examineOrder         varchar(1)           null,
   lt                   char(19)             null,
   lu                   char(32)             null,
   ft                   char(19)             null,
   fu                   char(32)             null
)
go

alter table od_documentauditor
   add constraint PK_OD_DOCUMENTAUDITOR primary key nonclustered (id)
go

/*==============================================================*/
/* Table: od_documentreceive                                    */
/*==============================================================*/
create table od_documentreceive (
   id                   char(32)             not null,
   id_officialdocument  char(32)             null,
   id_receiver          char(32)             null,
   id_retransmitor      char(32)             null,
   status               char(1)              null,
   fu                   char(32)             null,
   ft                   char(19)             null,
   lu                   char(32)             null,
   lt                   char(19)             null
)
go

alter table od_documentreceive
   add constraint PK_OD_DOCUMENTRECEIVE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: od_officialdocument                                   */
/*==============================================================*/
create table od_officialdocument (
   id                   char(32)             not null,
   id_registrant        char(32)             null,
   documentAttachment   char(32)             null,
   name                 varchar(100)         null,
   documentNum          varchar(50)          null,
   sendUnit             varchar(50)          null,
   receiveUnit          varchar(50)          null,
   unitMail             varchar(100)         null,
   sendTime             char(10)             null,
   registrationTime     char(19)             null,
   status               char(1)              null,
   documentKind         varchar(20)          null,
   type                 char(1)              null,
   remindTime           char(19)             null,
   remindContent        varchar(200)         null,
   note                 varchar(500)         null,
   directorExamine      char(1)              null,
   ft                   char(19)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   lu                   char(32)             null
)
go

alter table od_officialdocument
   add constraint PK_OD_OFFICIALDOCUMENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ol_learninganswer                                     */
/*==============================================================*/
create table ol_learninganswer (
   id                   char(32)             not null,
   id_learningsubject   char(32)             null,
   id_student           char(32)             null,
   id_teacher           char(32)             null,
   id_responsibleStudent char(32)             null,
   content              varchar(1000)        null,
   teacherReply         varchar(500)         null,
   studentReply         varchar(500)         null,
   correctFlag          char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ol_learninganswer
   add constraint PK_OL_LEARNINGANSWER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ol_learningsubject                                    */
/*==============================================================*/
create table ol_learningsubject (
   id                   char(32)             not null,
   id_learningtype      char(32)             null,
   id_teacher           char(32)             null,
   id_student           char(32)             null,
   id_attachment        char(32)             null,
   content              text                 null,
   viewOtherAnswersFlag char(1)              null,
   releaseStatus        char(1)              null,
   releaseTime          char(16)             null,
   finishlReleaseTime   char(16)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ol_learningsubject
   add constraint PK_OL_LEARNINGSUBJECT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ol_learningsubjectclass                               */
/*==============================================================*/
create table ol_learningsubjectclass (
   id                   char(32)             not null,
   id_learningsubject   char(32)             null,
   id_eclass            char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ol_learningsubjectclass
   add constraint PK_OL_LEARNINGSUBJECTCLASS primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ol_learningtype                                       */
/*==============================================================*/
create table ol_learningtype (
   id                   char(32)             not null,
   name                 varchar(200)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ol_learningtype
   add constraint PK_OL_LEARNINGTYPE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: pl_annotation                                         */
/*==============================================================*/
create table pl_annotation (
   id                   char(32)             not null,
   id_teachingplan      char(32)             null,
   comment              varchar(500)         null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table pl_annotation
   add constraint PK_PL_ANNOTATION primary key nonclustered (id)
go

/*==============================================================*/
/* Table: pl_curriculumstandard                                 */
/*==============================================================*/
create table pl_curriculumstandard (
   id                   char(32)             not null,
   id_course            char(32)             null,
   name                 varchar(50)          null,
   content              text                 null,
   status               char(1)              null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table pl_curriculumstandard
   add constraint PK_PL_CURRICULUMSTANDARD primary key nonclustered (id)
go

/*==============================================================*/
/* Table: pl_floweruser                                         */
/*==============================================================*/
create table pl_floweruser (
   id                   char(32)             not null,
   id_teachingplan      char(32)             null,
   id_user              char(32)             null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   content              varchar(200)         null
)
go

alter table pl_floweruser
   add constraint PK_PL_FLOWERUSER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: pl_mainprepare                                        */
/*==============================================================*/
create table pl_mainprepare (
   id                   char(32)             not null,
   id_teachingmaterial  char(32)             null,
   id_teachingsetting   char(32)             null,
   id_unit              char(32)             null,
   id_user              char(32)             null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table pl_mainprepare
   add constraint PK_PL_MAINPREPARE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: pl_plancontent                                        */
/*==============================================================*/
create table pl_plancontent (
   id                   char(32)             not null,
   id_teachingplan      char(32)             null,
   name                 varchar(50)          null,
   content              text                 null,
   num                  int                  null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table pl_plancontent
   add constraint PK_PL_PLANCONTENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: pl_plcourseware                                       */
/*==============================================================*/
create table pl_plcourseware (
   id                   char(32)             not null,
   id_teachingplan      char(32)             null,
   name                 varchar(50)          null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table pl_plcourseware
   add constraint PK_PL_PLCOURSEWARE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: pl_plknowledge                                        */
/*==============================================================*/
create table pl_plknowledge (
   id                   char(32)             not null,
   id_teachingplan      char(32)             null,
   id_knowledge         char(32)             null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table pl_plknowledge
   add constraint PK_PL_PLKNOWLEDGE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: pl_plotherresource                                    */
/*==============================================================*/
create table pl_plotherresource (
   id                   char(32)             not null,
   id_teachingplan      char(32)             null,
   name                 varchar(50)          null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   content              text                 null
)
go

alter table pl_plotherresource
   add constraint PK_PL_PLOTHERRESOURCE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: pl_plwork                                             */
/*==============================================================*/
create table pl_plwork (
   id                   char(32)             not null,
   id_teachingplan      char(32)             null,
   name                 varchar(50)          null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   content              text                 null
)
go

alter table pl_plwork
   add constraint PK_PL_PLWORK primary key nonclustered (id)
go

/*==============================================================*/
/* Table: pl_practice                                           */
/*==============================================================*/
create table pl_practice (
   id                   char(32)             not null,
   id_teachingplan      char(32)             null,
   name                 varchar(50)          null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   question             text                 null,
   answer               text                 null
)
go

alter table pl_practice
   add constraint PK_PL_PRACTICE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: pl_shareuser                                          */
/*==============================================================*/
create table pl_shareuser (
   id                   char(32)             not null,
   id_teachingplan      char(32)             null,
   id_user              char(32)             null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table pl_shareuser
   add constraint PK_PL_SHAREUSER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: pl_standardgrade                                      */
/*==============================================================*/
create table pl_standardgrade (
   id                   char(32)             not null,
   id_grade             char(32)             null,
   id_curriculumstandard char(32)             null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table pl_standardgrade
   add constraint PK_PL_STANDARDGRADE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: pl_teachingplan                                       */
/*==============================================================*/
create table pl_teachingplan (
   id                   char(32)             not null,
   id_teachingmaterial  char(32)             null,
   id_user              char(32)             null,
   id_grade             char(32)             null,
   id_course            char(32)             null,
   chapter              int                  null,
   section              int                  null,
   title                varchar(100)         null,
   keyWord              varchar(100)         null,
   time                 char(19)             null,
   shareScope           char(1)              null,
   resourceFlag         char(1)              null,
   fileFlag             char(1)              null,
   excellentFlag        char(1)              null,
   id_excellentUser     char(32)             null,
   excellentTime        char(19)             null,
   prepareFlag          char(1)              null,
   flowerAmount         int                  null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   id_examiner          char(32)             null,
   examineFlag          char(1)              null,
   examineTime          char(19)             null,
   suggestion           varchar(200)         null,
   id_teachingunit      char(32)             null,
   id_teachingunitsubject char(32)             null,
   kind                 char(1)              null
)
go

alter table pl_teachingplan
   add constraint PK_PL_TEACHINGPLAN primary key nonclustered (id)
go

/*==============================================================*/
/* Table: pl_teachingsetting                                    */
/*==============================================================*/
create table pl_teachingsetting (
   id                   char(32)             not null,
   id_grade             char(32)             null,
   id_course            char(32)             null,
   id_teachingmaterial  char(32)             null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table pl_teachingsetting
   add constraint PK_PL_TEACHINGSETTING primary key nonclustered (id)
go

/*==============================================================*/
/* Table: pl_teachingtemplate                                   */
/*==============================================================*/
create table pl_teachingtemplate (
   id                   char(32)             not null,
   id_teachingsetting   char(32)             null,
   name                 varchar(50)          null,
   kind                 char(1)              null,
   num                  int                  null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table pl_teachingtemplate
   add constraint PK_PL_TEACHINGTEMPLATE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ps_appraiser                                          */
/*==============================================================*/
create table ps_appraiser (
   id                   char(32)             not null,
   id_habit             char(32)             null,
   appraiser            varchar(20)          null,
   proportion           int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ps_appraiser
   add constraint PK_PS_APPRAISER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ps_coursedecomposition                                */
/*==============================================================*/
create table ps_coursedecomposition (
   id                   char(32)             not null,
   kind                 varchar(20)          null,
   id_course            char(32)             null,
   id_habit             char(32)             null,
   courseTask           varchar(1000)        null,
   courseForm           varchar(1000)        null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ps_coursedecomposition
   add constraint PK_PS_COURSEDECOMPOSITION primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ps_coursedecompositionplan                            */
/*==============================================================*/
create table ps_coursedecompositionplan (
   id                   char(32)             not null,
   id_gradeplan         char(32)             null,
   id_coursedecomposition char(32)             null,
   courseTask           varchar(1000)        null,
   courseForm           varchar(1000)        null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ps_coursedecompositionplan
   add constraint PK_PS_COURSEDECOMPOSITIONPLAN primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ps_cultivatedetail                                    */
/*==============================================================*/
create table ps_cultivatedetail (
   id                   char(32)             not null,
   id_habit             char(32)             null,
   id_student           char(32)             null,
   id_schoolterm        char(32)             null,
   score                int                  null,
   description          text                 null,
   caseMark             char(1)              null,
   id_appraiser         char(32)             null,
   appraiseTime         char(19)             null,
   appraisePosition     varchar(100)         null,
   editMark             char(1)              null,
   previousScore        int                  null,
   id_applicant         char(32)             null,
   applicationTime      char(19)             null,
   applicationReason    varchar(200)         null,
   id_auditor           char(32)             null,
   auditTime            char(19)             null,
   auditAdvice          varchar(200)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ps_cultivatedetail
   add constraint PK_PS_CULTIVATEDETAIL primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ps_emphasis                                           */
/*==============================================================*/
create table ps_emphasis (
   id                   char(32)             not null,
   id_grade             char(32)             null,
   id_student           char(32)             null,
   id_habit             char(32)             null,
   setTime              char(19)             null,
   mark                 char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ps_emphasis
   add constraint PK_PS_EMPHASIS primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ps_gradedecomposition                                 */
/*==============================================================*/
create table ps_gradedecomposition (
   id                   char(32)             not null,
   id_habit             char(32)             null,
   id_grade             char(32)             null,
   indicator            varchar(1000)        null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ps_gradedecomposition
   add constraint PK_PS_GRADEDECOMPOSITION primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ps_gradeindicatorplan                                 */
/*==============================================================*/
create table ps_gradeindicatorplan (
   id                   char(32)             not null,
   id_gradeDecomposition char(32)             null,
   id_gradePlan         char(32)             null,
   startTime            int                  null,
   endTime              int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ps_gradeindicatorplan
   add constraint PK_PS_GRADEINDICATORPLAN primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ps_gradeplan                                          */
/*==============================================================*/
create table ps_gradeplan (
   id                   char(32)             not null,
   id_grade             char(32)             null,
   id_schoolterm        char(32)             null,
   status               char(1)              null,
   id_user              char(32)             null,
   publishTime          char(19)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ps_gradeplan
   add constraint PK_PS_GRADEPLAN primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ps_habit                                              */
/*==============================================================*/
create table ps_habit (
   id                   char(32)             not null,
   name                 varchar(100)         null,
   indicator            text                 null,
   kind                 varchar(20)          null,
   way                  char(1)              null,
   veryGood             int                  null,
   good                 int                  null,
   normal               int                  null,
   bad                  int                  null,
   status               char(1)              null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table ps_habit
   add constraint PK_PS_HABIT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ps_teachplan                                          */
/*==============================================================*/
create table ps_teachplan (
   id                   char(32)             not null,
   id_user              char(32)             null,
   id_coursedecompositionplan char(32)             null,
   teachPlan            varchar(500)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ps_teachplan
   add constraint PK_PS_TEACHPLAN primary key nonclustered (id)
go

/*==============================================================*/
/* Table: pu_publicity                                          */
/*==============================================================*/
create table pu_publicity (
   id                   char(32)             not null,
   id_publishuser       char(32)             null,
   id_department        char(32)             null,
   receiveRange         varchar(20)          null,
   title                varchar(500)         null,
   content              text                 null,
   createTime           char(19)             null,
   startDate            char(10)             null,
   endDate              char(10)             null,
   status               char(1)              null,
   feedbackFlag         char(1)              null,
   approvalFlag         char(1)              null,
   id_dealuser          char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table pu_publicity
   add constraint PK_PU_PUBLICITY primary key nonclustered (id)
go

/*==============================================================*/
/* Table: pu_publicityapproval                                  */
/*==============================================================*/
create table pu_publicityapproval (
   id                   char(32)             not null,
   id_publicity         char(32)             null,
   id_user              char(32)             null,
   num                  varchar(10)          null,
   status               char(1)              null,
   approvalView         varchar(200)         null,
   approvalTime         char(19)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table pu_publicityapproval
   add constraint PK_PU_PUBLICITYAPPROVAL primary key nonclustered (id)
go

/*==============================================================*/
/* Table: pu_publicityfeedback                                  */
/*==============================================================*/
create table pu_publicityfeedback (
   id                   char(32)             not null,
   id_publicity         char(32)             null,
   id_user              char(32)             null,
   content              varchar(200)         null,
   feedbackTime         char(19)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table pu_publicityfeedback
   add constraint PK_PU_PUBLICITYFEEDBACK primary key nonclustered (id)
go

/*==============================================================*/
/* Table: qn_option                                             */
/*==============================================================*/
create table qn_option (
   id                   char(32)             not null,
   id_question          char(32)             null,
   num                  int                  null,
   viewNum              varchar(10)          null,
   name                 varchar(100)         null,
   fillinFlag           char(1)              null,
   alternativeFlag      char(1)              null,
   format               char(1)              null,
   minimumWords         varchar(10)          null,
   maximumWords         varchar(10)          null,
   score                int                  null,
   pictureFlag          char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table qn_option
   add constraint PK_QN_OPTION primary key nonclustered (id)
go

/*==============================================================*/
/* Table: qn_optionresults                                      */
/*==============================================================*/
create table qn_optionresults (
   id                   char(32)             not null,
   id_questionresults   char(32)             null,
   id_option            char(32)             null,
   result               varchar(2000)        null,
   score                int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table qn_optionresults
   add constraint PK_QN_OPTIONRESULTS primary key nonclustered (id)
go

/*==============================================================*/
/* Table: qn_participatenaire                                   */
/*==============================================================*/
create table qn_participatenaire (
   id                   char(32)             not null,
   id_questionnaire     char(32)             null,
   id_participant       char(32)             null,
   id_evaluateduser     char(32)             null,
   participantKind      char(1)              null,
   id_participantschool char(32)             null,
   id_participantgrade  char(32)             null,
   id_participanteclass char(32)             null,
   evaluatedKind        char(1)              null,
   id_evaluatedschool   char(32)             null,
   id_evaluatedgrade    char(32)             null,
   id_evaluatedeclass   char(32)             null,
   id_evaluatedcourse   char(32)             null,
   completeFlag         char(1)              null,
   completeTime         char(19)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table qn_participatenaire
   add constraint PK_QN_PARTICIPATENAIRE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: qn_question                                           */
/*==============================================================*/
create table qn_question (
   id                   char(32)             not null,
   id_questionnaire     char(32)             null,
   id_parent            char(32)             null,
   kind                 char(1)              null,
   num                  int                  null,
   viewNum              int                  null,
   name                 varchar(100)         null,
   mandatoryFlag        char(1)              null,
   description          varchar(200)         null,
   descriptionFlag      char(1)              null,
   orientation          char(1)              null,
   minimumChoices       varchar(10)          null,
   maximumChoices       varchar(10)          null,
   minimumWords         varchar(10)          null,
   maximumWords         varchar(10)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table qn_question
   add constraint PK_QN_QUESTION primary key nonclustered (id)
go

/*==============================================================*/
/* Table: qn_questionnaire                                      */
/*==============================================================*/
create table qn_questionnaire (
   id                   char(32)             not null,
   id_schoolTerm        char(32)             null,
   id_user              char(32)             null,
   name                 varchar(100)         null,
   kind                 char(1)              null,
   status               char(1)              null,
   welcomeRemarksFlag   char(1)              null,
   welcomeRemarks       varchar(200)         null,
   thanksRemarksFlag    char(1)              null,
   thanksRemarks        varchar(200)         null,
   deadline             char(19)             null,
   dimension            char(1)              null,
   range                char(1)              null,
   createTime           char(19)             null,
   updateTime           char(19)             null,
   anonymousFlag        char(1)              null,
   scoreFlag            char(1)              null,
   remindFlag           char(1)              null,
   remindContent        varchar(200)         null,
   skin                 char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table qn_questionnaire
   add constraint PK_QN_QUESTIONNAIRE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: qn_questionresults                                    */
/*==============================================================*/
create table qn_questionresults (
   id                   char(32)             not null,
   id_participatenaire  char(32)             null,
   id_question          char(32)             null,
   result               varchar(2000)        null,
   score                int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table qn_questionresults
   add constraint PK_QN_QUESTIONRESULTS primary key nonclustered (id)
go

/*==============================================================*/
/* Table: re_earlywarningsetting                                */
/*==============================================================*/
create table re_earlywarningsetting (
   id                   char(32)             not null,
   warningTime          int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null,
   kind                 char(1)              null,
   content              varchar(200)         null
)
go

alter table re_earlywarningsetting
   add constraint PK_RE_EARLYWARNINGSETTING primary key nonclustered (id)
go

/*==============================================================*/
/* Table: re_goodsconsume                                       */
/*==============================================================*/
create table re_goodsconsume (
   id                   char(32)             not null,
   id_earlywarningsetting char(32)             null,
   id_repairrecord      char(32)             null,
   sum                  int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table re_goodsconsume
   add constraint PK_RE_GOODSCONSUME primary key nonclustered (id)
go

/*==============================================================*/
/* Table: re_maintanancestaff                                   */
/*==============================================================*/
create table re_maintanancestaff (
   id                   char(32)             not null,
   id_user              char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null,
   id_earlywarningsetting char(32)             null
)
go

alter table re_maintanancestaff
   add constraint PK_RE_MAINTANANCESTAFF primary key nonclustered (id)
go

/*==============================================================*/
/* Table: re_repairsrecord                                      */
/*==============================================================*/
create table re_repairsrecord (
   id                   char(32)             not null,
   num                  char(11)             null,
   reportDate           char(10)             null,
   thing                varchar(200)         null,
   faultPosition        varchar(200)         null,
   faultDescription     varchar(500)         null,
   id_user              char(32)             null,
   telephoneNum         varchar(30)          null,
   repairDate           char(10)             null,
   faultCause           varchar(500)         null,
   id_maintanancestaff  char(32)             null,
   status               char(1)              null,
   deleteFlag           char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null,
   id_assigner          char(32)             null,
   assignTime           char(19)             null,
   warningFlag          char(1)              null,
   type                 varchar(20)          null,
   id_auditor           char(32)             null,
   suggestion           varchar(200)         null,
   checkTime            char(19)             null,
   id_earlywarningsetting char(32)             null
)
go

alter table re_repairsrecord
   add constraint PK_RE_REPAIRSRECORD primary key nonclustered (id)
go

/*==============================================================*/
/* Table: rs_activityuser                                       */
/*==============================================================*/
create table rs_activityuser (
   id                   char(32)             not null,
   id_researchstudyactivity char(32)             null,
   id_user              char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table rs_activityuser
   add constraint PK_RS_ACTIVITYUSER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: rs_basesetting                                        */
/*==============================================================*/
create table rs_basesetting (
   id                   char(32)             not null,
   kind                 char(1)              null,
   name                 varchar(20)          null,
   score                int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table rs_basesetting
   add constraint PK_RS_BASESETTING primary key nonclustered (id)
go

/*==============================================================*/
/* Table: rs_researchstudyactivity                              */
/*==============================================================*/
create table rs_researchstudyactivity (
   id                   char(32)             not null,
   id_user              char(32)             null,
   id_schoolterm        char(32)             null,
   name                 varchar(100)         null,
   status               char(1)              null,
   issueDate            char(10)             null,
   startTime            char(10)             null,
   endTime              char(10)             null,
   startTime2           char(10)             null,
   endTime2             char(10)             null,
   startTime3           char(10)             null,
   endTime3             char(10)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table rs_researchstudyactivity
   add constraint PK_RS_RESEARCHSTUDYACTIVITY primary key nonclustered (id)
go

/*==============================================================*/
/* Table: rs_researchstudyissue                                 */
/*==============================================================*/
create table rs_researchstudyissue (
   id                   char(32)             not null,
   id_user              char(32)             null,
   id_researchstudyactivity char(32)             null,
   kind                 varchar(20)          null,
   name                 varchar(100)         null,
   issueNum             varchar(20)          null,
   score                int                  null,
   time                 char(10)             null,
   status               char(1)              null,
   advice               varchar(200)         null,
   resource             char(32)             null,
   mark                 int                  null,
   time2                char(10)             null,
   status2              char(1)              null,
   advice2              varchar(200)         null,
   resource2            char(32)             null,
   mark2                int                  null,
   time3                char(10)             null,
   status3              char(1)              null,
   advice3              varchar(200)         null,
   resource3            char(32)             null,
   mark3                int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table rs_researchstudyissue
   add constraint PK_RS_RESEARCHSTUDYISSUE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: rs_researchstudymember                                */
/*==============================================================*/
create table rs_researchstudymember (
   id                   char(32)             not null,
   id_researchstudyissue char(32)             null,
   id_user              char(32)             null,
   kind                 char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table rs_researchstudymember
   add constraint PK_RS_RESEARCHSTUDYMEMBER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sa_assessmentitem                                     */
/*==============================================================*/
create table sa_assessmentitem (
   id                   char(32)             not null,
   id_assessmentkind    char(32)             null,
   name                 varchar(20)          null,
   frequency            char(1)              null,
   description          varchar(200)         null,
   enableFlag           char(1)              null,
   maxScore             int                  null,
   minScore             int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sa_assessmentitem
   add constraint PK_SA_ASSESSMENTITEM primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sa_assessmentitempostion                              */
/*==============================================================*/
create table sa_assessmentitempostion (
   id_assessmentitem    char(32)             null,
   id_position          char(32)             null
)
go

/*==============================================================*/
/* Table: sa_assessmentkind                                     */
/*==============================================================*/
create table sa_assessmentkind (
   id                   char(32)             not null,
   name                 varchar(20)          null,
   note                 varchar(200)         null,
   num                  int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sa_assessmentkind
   add constraint PK_SA_ASSESSMENTKIND primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sa_assessmentlevel                                    */
/*==============================================================*/
create table sa_assessmentlevel (
   id                   char(32)             not null,
   id_assessmentitem    char(32)             null,
   name                 varchar(20)          null,
   num                  int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sa_assessmentlevel
   add constraint PK_SA_ASSESSMENTLEVEL primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sa_assessmentreply                                    */
/*==============================================================*/
create table sa_assessmentreply (
   id                   char(32)             not null,
   id_studentassessment char(32)             null,
   id_assessmentreply   char(32)             null,
   id_user              char(32)             null,
   content              varchar(200)         null,
   num                  int                  null,
   replyTime            char(19)             null,
   readFlag             char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sa_assessmentreply
   add constraint PK_SA_ASSESSMENTREPLY primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sa_studentassessment                                  */
/*==============================================================*/
create table sa_studentassessment (
   id                   char(32)             not null,
   id_assessmentitem    char(32)             null,
   id_user              char(32)             null,
   id_student           char(32)             null,
   content              text                 null,
   id_assessmentlevel   char(32)             null,
   score                int                  null,
   createTime           char(19)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sa_studentassessment
   add constraint PK_SA_STUDENTASSESSMENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sc_featuredsubject                                    */
/*==============================================================*/
create table sc_featuredsubject (
   id                   char(32)             not null,
   id_specialtopic      char(32)             null,
   id_teacher           char(32)             null,
   title                varchar(100)         null,
   summary              varchar(400)         null,
   applyEndingDate      char(10)             null,
   maximumNumber        int                  null,
   learningSource       text                 null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sc_featuredsubject
   add constraint PK_SC_FEATUREDSUBJECT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sc_gambit                                             */
/*==============================================================*/
create table sc_gambit (
   id                   char(32)             not null,
   id_specialtopic      char(32)             null,
   id_initiator         char(32)             null,
   title                varchar(100)         null,
   detail               text                 null,
   sort                 char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sc_gambit
   add constraint PK_SC_GAMBIT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sc_messagecenter                                      */
/*==============================================================*/
create table sc_messagecenter (
   id                   char(32)             not null,
   id_receiver          char(32)             null,
   id_featuredsubject   char(32)             null,
   id_gambit            char(32)             null,
   id_subjectwork       char(32)             null,
   detail               varchar(400)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null,
   id_trigger           char(32)             null,
   openFlag             char(1)              null
)
go

alter table sc_messagecenter
   add constraint PK_SC_MESSAGECENTER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sc_privateletter                                      */
/*==============================================================*/
create table sc_privateletter (
   id                   char(32)             not null,
   id_featuredsubject   char(32)             null,
   id_student           char(32)             null,
   id_teacher           char(32)             null,
   detail               varchar(400)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sc_privateletter
   add constraint PK_SC_PRIVATELETTER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sc_reply                                              */
/*==============================================================*/
create table sc_reply (
   id                   char(32)             not null,
   id_gambit            char(32)             null,
   id_reply             char(32)             null,
   id_replier           char(32)             null,
   detail               text                 null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sc_reply
   add constraint PK_SC_REPLY primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sc_replyapproval                                      */
/*==============================================================*/
create table sc_replyapproval (
   id                   char(32)             not null,
   id_reply             char(32)             null,
   id_user              char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sc_replyapproval
   add constraint PK_SC_REPLYAPPROVAL primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sc_specialtopic                                       */
/*==============================================================*/
create table sc_specialtopic (
   id                   char(32)             not null,
   id_topic             char(32)             null,
   title                varchar(100)         null,
   summary              varchar(1000)        null,
   bulletin             varchar(400)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sc_specialtopic
   add constraint PK_SC_SPECIALTOPIC primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sc_subjectstudent                                     */
/*==============================================================*/
create table sc_subjectstudent (
   id                   char(32)             not null,
   id_featuredsubject   char(32)             null,
   id_student           char(32)             null,
   outstandingFlag      char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sc_subjectstudent
   add constraint PK_SC_SUBJECTSTUDENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sc_subjectwork                                        */
/*==============================================================*/
create table sc_subjectwork (
   id                   char(32)             not null,
   id_student           char(32)             null,
   id_featuredsubject   char(32)             null,
   title                varchar(100)         null,
   result               varchar(20)          null,
   comment              varchar(400)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sc_subjectwork
   add constraint PK_SC_SUBJECTWORK primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sc_topic                                              */
/*==============================================================*/
create table sc_topic (
   id                   char(32)             not null,
   title                varchar(100)         null,
   englishtitle         varchar(100)         null,
   summary              varchar(400)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sc_topic
   add constraint PK_SC_TOPIC primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sc_userspace                                          */
/*==============================================================*/
create table sc_userspace (
   id                   char(32)             not null,
   id_user              char(32)             null,
   summary              varchar(400)         null,
   fu                   char(32)             null,
   lu                   char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null
)
go

alter table sc_userspace
   add constraint PK_SC_USERSPACE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sd_activity                                           */
/*==============================================================*/
create table sd_activity (
   id                   char(32)             not null,
   id_schoolterm        char(32)             null,
   name                 varchar(50)          null,
   kind                 varchar(20)          null,
   startDate            char(10)             null,
   endDate              char(10)             null,
   status               char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sd_activity
   add constraint PK_SD_ACTIVITY primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sd_activitygrade                                      */
/*==============================================================*/
create table sd_activitygrade (
   id                   char(32)             not null,
   id_activity          char(32)             null,
   id_school            char(32)             null,
   id_grade             char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sd_activitygrade
   add constraint PK_SD_ACTIVITYGRADE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sd_works                                              */
/*==============================================================*/
create table sd_works (
   id                   char(32)             not null,
   id_activity          char(32)             null,
   id_student           char(32)             null,
   id_eclass            char(32)             null,
   name                 varchar(50)          null,
   kind                 varchar(20)          null,
   score                int                  null,
   status               char(1)              null,
   note                 varchar(200)         null,
   preview              char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sd_works
   add constraint PK_SD_WORKS primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sd_worksscore                                         */
/*==============================================================*/
create table sd_worksscore (
   id                   char(32)             not null,
   id_works             char(32)             null,
   id_user              char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sd_worksscore
   add constraint PK_SD_WORKSSCORE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sm_compareculture                                     */
/*==============================================================*/
create table sm_compareculture (
   id                   char(32)             not null,
   id_sportsmeet        char(32)             null,
   id_eclass            char(32)             null,
   ranking              int                  null,
   result               int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sm_compareculture
   add constraint PK_SM_COMPARECULTURE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sm_matchclass                                         */
/*==============================================================*/
create table sm_matchclass (
   id                   char(32)             not null,
   id_matchgroup        char(32)             null,
   id_eclass            char(32)             null,
   id_lead              char(32)             null,
   id_user              char(32)             null,
   phone                varchar(100)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sm_matchclass
   add constraint PK_SM_MATCHCLASS primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sm_matchdepartment                                    */
/*==============================================================*/
create table sm_matchdepartment (
   id                   char(32)             not null,
   id_matchgroup        char(32)             null,
   id_department        char(32)             null,
   id_user              char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sm_matchdepartment
   add constraint PK_SM_MATCHDEPARTMENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sm_matchgroup                                         */
/*==============================================================*/
create table sm_matchgroup (
   id                   char(32)             not null,
   id_sportsmeet        char(32)             null,
   name                 varchar(100)         null,
   type                 char(1)              null,
   limitCount           int                  null,
   single               int                  null,
   singleMan            int                  null,
   isTeam               char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sm_matchgroup
   add constraint PK_SM_MATCHGROUP primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sm_matchpeople                                        */
/*==============================================================*/
create table sm_matchpeople (
   id                   char(32)             not null,
   id_project           char(32)             null,
   id_sportsman         char(32)             null,
   id_matchsmallgroup   char(32)             null,
   result               int                  null,
   recordFlag           char(1)              null,
   remark               varchar(100)         null,
   sort                 int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sm_matchpeople
   add constraint PK_SM_MATCHPEOPLE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sm_matchproject                                       */
/*==============================================================*/
create table sm_matchproject (
   id                   char(32)             not null,
   id_matchgroup        char(32)             null,
   id_project           char(32)             null,
   matchPlan            int                  null,
   groupType            char(1)              null,
   sort                 int                  null,
   date                 char(10)             null,
   startTime            char(5)              null,
   id_sportsmeet        char(32)             null,
   type                 char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sm_matchproject
   add constraint PK_SM_MATCHPROJECT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sm_matchsmallgroup                                    */
/*==============================================================*/
create table sm_matchsmallgroup (
   id                   char(32)             not null,
   id_matchproject      char(32)             null,
   sort                 int                  null,
   name                 varchar(100)         null,
   matchPlan            int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sm_matchsmallgroup
   add constraint PK_SM_MATCHSMALLGROUP primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sm_scoringtemplate                                    */
/*==============================================================*/
create table sm_scoringtemplate (
   id                   char(32)             not null,
   id_sportsmeet        char(32)             null,
   type                 char(1)              null,
   place                int                  null,
   mark                 int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sm_scoringtemplate
   add constraint PK_SM_SCORINGTEMPLATE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sm_sportsman                                          */
/*==============================================================*/
create table sm_sportsman (
   id                   char(32)             not null,
   id_user              char(32)             null,
   id_sportsmeet        char(32)             null,
   id_matchorganization char(32)             null,
   id_matchclass        char(32)             null,
   sort                 varchar(20)          null,
   totalRecord          int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sm_sportsman
   add constraint PK_SM_SPORTSMAN primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sm_sportsmeet                                         */
/*==============================================================*/
create table sm_sportsmeet (
   id                   char(32)             not null,
   name                 varchar(100)         null,
   id_schoolyear        char(32)             null,
   startDate            char(10)             null,
   endDate              char(10)             null,
   address              varchar(300)         null,
   startTime            char(10)             null,
   endTime              char(10)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sm_sportsmeet
   add constraint PK_SM_SPORTSMEET primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sm_sportsmeetdocument                                 */
/*==============================================================*/
create table sm_sportsmeetdocument (
   id                   char(32)             not null,
   id_sportsmeet        char(32)             null,
   name                 varchar(100)         null,
   flag                 char(1)              null,
   type                 char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sm_sportsmeetdocument
   add constraint PK_SM_SPORTSMEETDOCUMENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sm_sportsproject                                      */
/*==============================================================*/
create table sm_sportsproject (
   id                   char(32)             not null,
   name                 varchar(100)         null,
   type                 varchar(20)          null,
   unitType             char(1)              null,
   isTeam               char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sm_sportsproject
   add constraint PK_SM_SPORTSPROJECT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sm_sportsprojectrecord                                */
/*==============================================================*/
create table sm_sportsprojectrecord (
   id                   char(32)             not null,
   id_project           char(32)             null,
   record               int                  null,
   breaker              varchar(50)          null,
   type                 char(1)              null,
   recordType           char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sm_sportsprojectrecord
   add constraint PK_SM_SPORTSPROJECTRECORD primary key nonclustered (id)
go

/*==============================================================*/
/* Table: so_society                                            */
/*==============================================================*/
create table so_society (
   id                   char(32)             not null,
   name                 varchar(50)          null,
   id_startschoolterm   char(32)             null,
   id_endschoolterm     char(32)             null,
   startDate            char(10)             null,
   endDate              char(10)             null,
   status               char(1)              null,
   note                 varchar(200)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table so_society
   add constraint PK_SO_SOCIETY primary key nonclustered (id)
go

/*==============================================================*/
/* Table: so_societyActivity                                    */
/*==============================================================*/
create table so_societyActivity (
   id                   char(32)             not null,
   name                 varchar(50)          null,
   id_society           char(32)             null,
   id_schoolTerm        char(32)             null,
   kind                 varchar(20)          null,
   startDate            char(10)             null,
   endDate              char(10)             null,
   place                varchar(100)         null,
   number               int                  null,
   status               char(1)              null,
   icon                 char(32)             null,
   note                 varchar(200)         null,
   auditNote            varchar(200)         null,
   id_user              char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table so_societyActivity
   add constraint PK_SO_SOCIETYACTIVITY primary key nonclustered (id)
go

/*==============================================================*/
/* Table: so_societyAssessment                                  */
/*==============================================================*/
create table so_societyAssessment (
   id                   char(32)             not null,
   id_schoolTerm        char(32)             null,
   id_society           char(32)             null,
   status               char(1)              null,
   score                char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table so_societyAssessment
   add constraint PK_SO_SOCIETYASSESSMENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: so_societyMaterial                                    */
/*==============================================================*/
create table so_societyMaterial (
   id                   char(32)             not null,
   name                 varchar(50)          null,
   id_society           char(32)             null,
   id_societyActivity   char(32)             null,
   icon                 char(32)             null,
   preview              char(32)             null,
   materialKind         char(1)              null,
   attachmentKind       char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null,
   id_societyRecruitActivity char(32)             null,
   id_societyaudit      char(32)             null,
   id_schoolterm        char(32)             null
)
go

alter table so_societyMaterial
   add constraint PK_SO_SOCIETYMATERIAL primary key nonclustered (id)
go

/*==============================================================*/
/* Table: so_societyMember                                      */
/*==============================================================*/
create table so_societyMember (
   id                   char(32)             not null,
   id_society           char(32)             null,
   id_societyRecruitActivity char(32)             null,
   id_user              char(32)             null,
   id_eclass            char(32)             null,
   id_tenureSchoolTerm  char(32)             null,
   id_enterSchoolTerm   char(32)             null,
   tenureDate           char(10)             null,
   outgoingDate         char(10)             null,
   enterDate            char(10)             null,
   exitDate             char(10)             null,
   position             char(1)              null,
   kind                 char(1)              null,
   auditStatus          char(1)              null,
   status               char(1)              null,
   auditNote            varchar(200)         null,
   exitNote             varchar(200)         null,
   note                 varchar(200)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table so_societyMember
   add constraint PK_SO_SOCIETYMEMBER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: so_societyRecruitActivity                             */
/*==============================================================*/
create table so_societyRecruitActivity (
   id                   char(32)             not null,
   id_society           char(32)             null,
   id_schoolTerm        char(32)             null,
   name                 varchar(50)          null,
   status               char(1)              null,
   maxNumber            int                  null,
   minNumber            int                  null,
   startDate            char(10)             null,
   endDate              char(10)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table so_societyRecruitActivity
   add constraint PK_SO_SOCIETYRECRUITACTIVITY primary key nonclustered (id)
go

/*==============================================================*/
/* Table: so_societyaudit                                       */
/*==============================================================*/
create table so_societyaudit (
   id                   char(32)             not null,
   id_society           char(32)             null,
   id_schoolTerm        char(32)             null,
   id_user              char(32)             null,
   planNumber           int                  null,
   registerStatus       char(1)              null,
   registerNote         varchar(200)         null,
   establishStatus      char(1)              null,
   establishNote        varchar(200)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table so_societyaudit
   add constraint PK_SO_SOCIETYAUDIT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sr_goodsinfo                                          */
/*==============================================================*/
create table sr_goodsinfo (
   id                   char(32)             not null,
   code                 varchar(40)          null,
   name                 varchar(200)         null,
   id_goodskind         char(32)             null,
   brand                varchar(200)         null,
   unit                 varchar(200)         null,
   size                 varchar(200)         null,
   inventory            varchar(10)          null,
   status               char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sr_goodsinfo
   add constraint PK_SR_GOODSINFO primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sr_goodskind                                          */
/*==============================================================*/
create table sr_goodskind (
   id                   char(32)             not null,
   code                 varchar(40)          null,
   name                 varchar(200)         null,
   id_goodskind         char(32)             null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table sr_goodskind
   add constraint PK_SR_GOODSKIND primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sr_warehouserecord                                    */
/*==============================================================*/
create table sr_warehouserecord (
   id                   char(32)             not null,
   code                 varchar(40)          null,
   kind                 char(1)              null,
   intokind             varchar(20)          null,
   outkind              varchar(20)          null,
   date                 char(10)             null,
   id_user              char(32)             null,
   productdate          char(10)             null,
   note                 varchar(200)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sr_warehouserecord
   add constraint PK_SR_WAREHOUSERECORD primary key nonclustered (id)
go

/*==============================================================*/
/* Table: sr_warehouserecordgoods                               */
/*==============================================================*/
create table sr_warehouserecordgoods (
   id                   char(32)             not null,
   id_warehouserecord   char(32)             null,
   id_goodsinfo         char(32)             null,
   id_user              char(32)             null,
   count                varchar(10)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table sr_warehouserecordgoods
   add constraint PK_SR_WAREHOUSERECORDGOODS primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ss_guidanceteacher                                    */
/*==============================================================*/
create table ss_guidanceteacher (
   id                   char(32)             not null,
   id_specialstudent    char(32)             null,
   id_teacher           char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ss_guidanceteacher
   add constraint PK_SS_GUIDANCETEACHER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ss_specialstudent                                     */
/*==============================================================*/
create table ss_specialstudent (
   id                   char(32)             not null,
   id_student           char(32)             null,
   kindDict             varchar(200)         null,
   nominateDate         char(10)             null,
   id_nominateUser      char(32)             null,
   checkDate            char(10)             null,
   checkStatus          char(1)              null,
   id_checkUser         char(32)             null,
   checkOpinion         varchar(200)         null,
   cancelDate           char(10)             null,
   cancelStatus         char(1)              null,
   id_cancelUser        char(32)             null,
   nominateReason       varchar(200)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ss_specialstudent
   add constraint PK_SS_SPECIALSTUDENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ta_teacherattendanceprogram                           */
/*==============================================================*/
create table ta_teacherattendanceprogram (
   id                   char(32)             not null,
   startTime            char(5)              null,
   endTime              char(5)              null,
   name                 varchar(100)         null,
   workDay              varchar(100)         null,
   ipLimit              varchar(1000)        null,
   status               char(1)              null,
   startDate            char(10)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ta_teacherattendanceprogram
   add constraint PK_TA_TEACHERATTENDANCEPROGRAM primary key nonclustered (id)
go

/*==============================================================*/
/* Table: ta_teacherattendancerecord                            */
/*==============================================================*/
create table ta_teacherattendancerecord (
   id                   char(32)             not null,
   id_user              char(32)             null,
   id_checkUser         char(32)             null,
   attendanceDate       char(10)             null,
   kind                 char(1)              null,
   startTime            char(5)              null,
   startSupplement      char(5)              null,
   startStatus          char(1)              null,
   endTime              char(5)              null,
   endSupplement        char(5)              null,
   endStatus            char(1)              null,
   attendanceKind       char(1)              null,
   checkStatus          char(1)              null,
   supplementKind       char(1)              null,
   note                 varchar(100)         null,
   time                 char(19)             null,
   ip                   varchar(50)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table ta_teacherattendancerecord
   add constraint PK_TA_TEACHERATTENDANCERECORD primary key nonclustered (id)
go

/*==============================================================*/
/* Table: tf_abroadstudy                                        */
/*==============================================================*/
create table tf_abroadstudy (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   name                 varchar(50)          null,
   abroadDate           char(10)             null,
   countryCode          varchar(20)          null,
   enname               varchar(50)          null,
   zhname               varchar(50)          null,
   sendingUnits         varchar(50)          null,
   partName             varchar(50)          null,
   abroadFunds          varchar(20)          null,
   approvalUnits        varchar(50)          null,
   actualDate           char(10)             null,
   auditType            char(1)              null,
   advice               varchar(200)         null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   id_auditteacher      char(32)             null,
   id_schoolitemAdd     char(32)             null
)
go

alter table tf_abroadstudy
   add constraint PK_TF_ABROADSTUDY primary key nonclustered (id)
go

/*==============================================================*/
/* Table: tf_awardwining                                        */
/*==============================================================*/
create table tf_awardwining (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   id_subject           char(32)             null,
   name                 varchar(50)          null,
   date                 char(10)             null,
   type                 varchar(20)          null,
   level                varchar(20)          null,
   grade                varchar(20)          null,
   grantedunit          varchar(100)         null,
   note                 text                 null,
   auditType            char(1)              null,
   advice               varchar(200)         null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   id_auditteacher      char(32)             null,
   id_schoolitemAdd     char(32)             null
)
go

alter table tf_awardwining
   add constraint PK_TF_AWARDWINING primary key nonclustered (id)
go

/*==============================================================*/
/* Table: tf_courseware                                         */
/*==============================================================*/
create table tf_courseware (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   id_schoolitem        char(32)             null,
   id_course            char(32)             null,
   teachingdate         char(10)             null,
   date                 char(10)             null,
   teachingsection      varchar(50)          null,
   name                 varchar(50)          null,
   auditType            char(1)              null,
   advice               varchar(200)         null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   id_auditteacher      char(32)             null,
   id_schoolitemAdd     char(32)             null
)
go

alter table tf_courseware
   add constraint PK_TF_COURSEWARE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: tf_coursewareeclass                                   */
/*==============================================================*/
create table tf_coursewareeclass (
   id                   char(32)             not null,
   id_courseware        char(32)             null,
   id_eclass            char(32)             null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table tf_coursewareeclass
   add constraint PK_TF_COURSEWAREECLASS primary key nonclustered (id)
go

/*==============================================================*/
/* Table: tf_customdetail                                       */
/*==============================================================*/
create table tf_customdetail (
   id                   char(32)             not null,
   id_filesitem         char(32)             null,
   id_teacher           char(32)             null,
   name                 varchar(50)          null,
   auditType            char(1)              null,
   advice               varchar(200)         null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   id_auditteacher      char(32)             null,
   id_schoolitemAdd     char(32)             null
)
go

alter table tf_customdetail
   add constraint PK_TF_CUSTOMDETAIL primary key nonclustered (id)
go

/*==============================================================*/
/* Table: tf_developmentplan                                    */
/*==============================================================*/
create table tf_developmentplan (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   id_auditteacher      char(32)             null,
   id_schoolyear        char(32)             null,
   date                 char(10)             null,
   content              text                 null,
   auditType            char(1)              null,
   advice               varchar(200)         null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   id_schoolitemAdd     char(32)             null,
   name                 varchar(50)          null
)
go

alter table tf_developmentplan
   add constraint PK_TF_DEVELOPMENTPLAN primary key nonclustered (id)
go

/*==============================================================*/
/* Table: tf_domesticstudy                                      */
/*==============================================================*/
create table tf_domesticstudy (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   name                 varchar(50)          null,
   trainingtype         varchar(20)          null,
   studytype            varchar(20)          null,
   startTime            char(7)              null,
   endTime              char(7)              null,
   totalPeriod          int                  null,
   classname            varchar(50)          null,
   sponsorUnit          varchar(100)         null,
   auditType            char(1)              null,
   advice               varchar(200)         null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   id_auditteacher      char(32)             null,
   id_schoolitemAdd     char(32)             null
)
go

alter table tf_domesticstudy
   add constraint PK_TF_DOMESTICSTUDY primary key nonclustered (id)
go

/*==============================================================*/
/* Table: tf_examinationsubject                                 */
/*==============================================================*/
create table tf_examinationsubject (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   name                 varchar(50)          null,
   id_schoolitem        char(32)             null,
   id_course            char(32)             null,
   auditType            char(1)              null,
   advice               varchar(200)         null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   id_auditteacher      char(32)             null,
   id_schoolitemAdd     char(32)             null,
   examType             varchar(20)          null,
   date                 char(10)             null,
   id_grade             char(32)             null
)
go

alter table tf_examinationsubject
   add constraint PK_TF_EXAMINATIONSUBJECT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: tf_examsubjecteclass                                  */
/*==============================================================*/
create table tf_examsubjecteclass (
   id                   char(32)             not null,
   id_examinationsubject char(32)             null,
   id_eclass            char(32)             null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table tf_examsubjecteclass
   add constraint PK_TF_EXAMSUBJECTECLASS primary key nonclustered (id)
go

/*==============================================================*/
/* Table: tf_filesitem                                          */
/*==============================================================*/
create table tf_filesitem (
   id                   char(32)             not null,
   name                 varchar(50)          null,
   id_parent            char(32)             null,
   syscode              varchar(20)          null,
   type                 char(1)              null,
   kind                 char(1)              null,
   iswrite              char(1)              null,
   isaudit              char(1)              null,
   note                 text                 null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table tf_filesitem
   add constraint PK_TF_FILESITEM primary key nonclustered (id)
go

/*==============================================================*/
/* Table: tf_filesitemteacher                                   */
/*==============================================================*/
create table tf_filesitemteacher (
   id_filesitem         char(32)             null,
   id_teacher           char(32)             null
)
go

/*==============================================================*/
/* Table: tf_honorarytitle                                      */
/*==============================================================*/
create table tf_honorarytitle (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   name                 varchar(50)          null,
   level                varchar(20)          null,
   date                 char(10)             null,
   grantedunit          varchar(100)         null,
   note                 text                 null,
   auditType            char(1)              null,
   advice               varchar(200)         null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   id_auditteacher      char(32)             null,
   id_schoolitemAdd     char(32)             null
)
go

alter table tf_honorarytitle
   add constraint PK_TF_HONORARYTITLE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: tf_publicclass                                        */
/*==============================================================*/
create table tf_publicclass (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   id_schoolitem        char(32)             null,
   name                 varchar(50)          null,
   courseLevel          varchar(20)          null,
   courseType           varchar(20)          null,
   locale               varchar(30)          null,
   date                 char(10)             null,
   studentNum           int                  null,
   auditType            char(1)              null,
   advice               varchar(200)         null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   id_auditteacher      char(32)             null,
   id_schoolitemAdd     char(32)             null
)
go

alter table tf_publicclass
   add constraint PK_TF_PUBLICCLASS primary key nonclustered (id)
go

/*==============================================================*/
/* Table: tf_readingessay                                       */
/*==============================================================*/
create table tf_readingessay (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   bookname             varchar(30)          null,
   date                 char(10)             null,
   author               varchar(20)          null,
   notes                text                 null,
   auditType            char(1)              null,
   advice               varchar(200)         null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   id_auditteacher      char(32)             null,
   id_schoolitemAdd     char(32)             null
)
go

alter table tf_readingessay
   add constraint PK_TF_READINGESSAY primary key nonclustered (id)
go

/*==============================================================*/
/* Table: tf_sbcd                                               */
/*==============================================================*/
create table tf_sbcd (
   id                   char(32)             not null,
   name                 varchar(50)          null,
   id_teacher           char(32)             null,
   id_subject           char(32)             null,
   id_schoolitemAdd     char(32)             null,
   id_auditteacher      char(32)             null,
   courseType           varchar(20)          null,
   minNum               int                  null,
   maxNum               int                  null,
   credit               int                  null,
   auditType            char(1)              null,
   advice               varchar(200)         null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table tf_sbcd
   add constraint PK_TF_SBCD primary key nonclustered (id)
go

/*==============================================================*/
/* Table: tf_subjectstudy                                       */
/*==============================================================*/
create table tf_subjectstudy (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   name                 varchar(50)          null,
   startTime            char(10)             null,
   endTime              char(10)             null,
   subjectLevel         varchar(20)          null,
   roles                varchar(20)          null,
   auditType            char(1)              null,
   advice               varchar(200)         null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   id_auditteacher      char(32)             null,
   id_schoolitemAdd     char(32)             null
)
go

alter table tf_subjectstudy
   add constraint PK_TF_SUBJECTSTUDY primary key nonclustered (id)
go

/*==============================================================*/
/* Table: tf_teachingdesign                                     */
/*==============================================================*/
create table tf_teachingdesign (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   id_schoolitem        char(32)             null,
   id_course            char(32)             null,
   teachingdate         char(10)             null,
   date                 char(10)             null,
   teachingsection      varchar(100)         null,
   name                 varchar(50)          null,
   auditType            char(1)              null,
   advice               varchar(200)         null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   id_auditteacher      char(32)             null,
   id_schoolitemAdd     char(32)             null
)
go

alter table tf_teachingdesign
   add constraint PK_TF_TEACHINGDESIGN primary key nonclustered (id)
go

/*==============================================================*/
/* Table: tf_teachingeclass                                     */
/*==============================================================*/
create table tf_teachingeclass (
   id                   char(32)             not null,
   id_teachingdesign    char(32)             null,
   id_eclass            char(32)             null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table tf_teachingeclass
   add constraint PK_TF_TEACHINGECLASS primary key nonclustered (id)
go

/*==============================================================*/
/* Table: tf_teachingnotes                                      */
/*==============================================================*/
create table tf_teachingnotes (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   name                 varchar(50)          null,
   id_schoolitem        char(32)             null,
   date                 char(10)             null,
   content              text                 null,
   auditType            char(1)              null,
   advice               varchar(200)         null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   id_auditteacher      char(32)             null,
   id_schoolitemAdd     char(32)             null
)
go

alter table tf_teachingnotes
   add constraint PK_TF_TEACHINGNOTES primary key nonclustered (id)
go

/*==============================================================*/
/* Table: tf_teachingprogram                                    */
/*==============================================================*/
create table tf_teachingprogram (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   id_schoolitem        char(32)             null,
   date                 char(10)             null,
   id_course            char(32)             null,
   id_grade             char(32)             null,
   auditType            char(1)              null,
   advice               varchar(200)         null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   id_auditteacher      char(32)             null,
   id_schoolitemAdd     char(32)             null,
   name                 varchar(50)          null
)
go

alter table tf_teachingprogram
   add constraint PK_TF_TEACHINGPROGRAM primary key nonclustered (id)
go

/*==============================================================*/
/* Table: tf_teachingreflection                                 */
/*==============================================================*/
create table tf_teachingreflection (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   name                 varchar(50)          null,
   id_schoolitem        char(32)             null,
   date                 char(10)             null,
   content              text                 null,
   auditType            char(1)              null,
   advice               varchar(200)         null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   id_auditteacher      char(32)             null,
   id_schoolitemAdd     char(32)             null
)
go

alter table tf_teachingreflection
   add constraint PK_TF_TEACHINGREFLECTION primary key nonclustered (id)
go

/*==============================================================*/
/* Table: tf_tutorteaching                                      */
/*==============================================================*/
create table tf_tutorteaching (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   startTime            char(10)             null,
   endTime              char(10)             null,
   note                 text                 null,
   auditType            char(1)              null,
   advice               varchar(200)         null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   id_auditteacher      char(32)             null,
   id_schoolitemAdd     char(32)             null
)
go

alter table tf_tutorteaching
   add constraint PK_TF_TUTORTEACHING primary key nonclustered (id)
go

/*==============================================================*/
/* Table: tf_tutorteachingteacher                               */
/*==============================================================*/
create table tf_tutorteachingteacher (
   id_teacher           char(32)             null,
   id_tutorteaching     char(32)             null
)
go

/*==============================================================*/
/* Table: tf_worksummary                                        */
/*==============================================================*/
create table tf_worksummary (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   name                 varchar(50)          null,
   id_schoolitem        char(32)             null,
   date                 char(10)             null,
   content              text                 null,
   auditType            char(1)              null,
   advice               varchar(200)         null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null,
   id_auditteacher      char(32)             null,
   id_schoolitemAdd     char(32)             null
)
go

alter table tf_worksummary
   add constraint PK_TF_WORKSUMMARY primary key nonclustered (id)
go

/*==============================================================*/
/* Table: tr_issueguide                                         */
/*==============================================================*/
create table tr_issueguide (
   id                   char(32)             not null,
   id_user              char(32)             null,
   name                 varchar(100)         null,
   level                varchar(20)          null,
   issueUnit            varchar(100)         null,
   issueDate            char(10)             null,
   content              text                 null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table tr_issueguide
   add constraint PK_TR_ISSUEGUIDE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: tr_issuemember                                        */
/*==============================================================*/
create table tr_issuemember (
   id                   char(32)             not null,
   id_teachingissue     char(32)             null,
   id_user              char(32)             null,
   id_course            char(32)             null,
   title                varchar(20)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table tr_issuemember
   add constraint PK_TR_ISSUEMEMBER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: tr_issueresource                                      */
/*==============================================================*/
create table tr_issueresource (
   id                   char(32)             not null,
   id_teachingissue     char(32)             null,
   id_user              char(32)             null,
   resourceKind         char(1)              null,
   resourceName         varchar(100)         null,
   resourceDate         char(10)             null,
   resourceContent      text                 null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table tr_issueresource
   add constraint PK_TR_ISSUERESOURCE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: tr_teachingissue                                      */
/*==============================================================*/
create table tr_teachingissue (
   id                   char(32)             not null,
   id_issueguide        char(32)             null,
   name                 varchar(100)         null,
   issueNum             varchar(20)          null,
   declareDate          char(10)             null,
   content              text                 null,
   status               char(1)              null,
   advice               varchar(200)         null,
   projectNotice        varchar(100)         null,
   projectDate          char(10)             null,
   projectContent       text                 null,
   projectFile          char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null,
   middleStatus         char(1)              null,
   middleAdvice         varchar(500)         null,
   middleTime           char(19)             null,
   endTime              char(19)             null,
   endStatus            char(1)              null,
   endAdvice            varchar(500)         null,
   id_declareUser       char(32)             null
)
go

alter table tr_teachingissue
   add constraint PK_TR_TEACHINGISSUE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: va_voluntaryactivities                                */
/*==============================================================*/
create table va_voluntaryactivities (
   id                   char(32)             not null,
   id_school            char(32)             null,
   id_grade             char(32)             null,
   id_eclass            char(32)             null,
   id_student           char(32)             null,
   id_schoolterm        char(32)             null,
   kind                 varchar(20)          null,
   content              varchar(1000)        null,
   date                 char(10)             null,
   address              varchar(200)         null,
   status               char(1)              null,
   note                 varchar(1000)        null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table va_voluntaryactivities
   add constraint PK_VA_VOLUNTARYACTIVITIES primary key nonclustered (id)
go

/*==============================================================*/
/* Table: wc_warning                                            */
/*==============================================================*/
create table wc_warning (
   id                   char(32)             not null,
   id_user              char(32)             null,
   id_wcrecord          char(32)             null,
   warningTime          char(19)             null,
   warningFlag          char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table wc_warning
   add constraint PK_WC_WARNING primary key nonclustered (id)
go

/*==============================================================*/
/* Table: wc_wcrecord                                           */
/*==============================================================*/
create table wc_wcrecord (
   id                   char(32)             not null,
   id_weekcalendar      char(32)             null,
   id_user              char(32)             null,
   dayOfWeek            int                  null,
   content              varchar(500)         null,
   occurTime            varchar(50)          null,
   place                varchar(50)          null,
   header               varchar(50)          null,
   description          varchar(200)         null,
   num                  int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null,
   attendPerson         varchar(50)          null,
   specialShowFlag      char(1)              null
)
go

alter table wc_wcrecord
   add constraint PK_WC_WCRECORD primary key nonclustered (id)
go

/*==============================================================*/
/* Table: wc_wcteacher                                          */
/*==============================================================*/
create table wc_wcteacher (
   id                   char(32)             not null,
   id_weekcalendar      char(32)             null,
   dayOfWeek            int                  null,
   mainTeacher          varchar(50)          null,
   viceTeacher          varchar(50)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table wc_wcteacher
   add constraint PK_WC_WCTEACHER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: wc_weekcalendar                                       */
/*==============================================================*/
create table wc_weekcalendar (
   id                   char(32)             not null,
   id_school            char(32)             null,
   id_schoolterm        char(32)             null,
   weekNumber           int                  null,
   status               char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null,
   keyWork              text                 null
)
go

alter table wc_weekcalendar
   add constraint PK_WC_WEEKCALENDAR primary key nonclustered (id)
go

/*==============================================================*/
/* Table: wk_coursework                                         */
/*==============================================================*/
create table wk_coursework (
   id                   char(32)             not null,
   id_schoolterm        char(32)             null,
   id_course            char(32)             null,
   id_user              char(32)             null,
   type                 char(1)              null,
   startDate            char(10)             null,
   endDate              char(10)             null,
   content              text                 null,
   workAmount           int                  null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table wk_coursework
   add constraint PK_WK_COURSEWORK primary key nonclustered (id)
go

/*==============================================================*/
/* Table: wk_workeclass                                         */
/*==============================================================*/
create table wk_workeclass (
   id                   char(32)             not null,
   id_coursework        char(32)             null,
   id_eclass            char(32)             null,
   lu                   char(32)             null,
   fu                   char(32)             null,
   lt                   char(19)             null,
   ft                   char(19)             null
)
go

alter table wk_workeclass
   add constraint PK_WK_WORKECLASS primary key nonclustered (id)
go

/*==============================================================*/
/* Table: wl_makeloguser                                        */
/*==============================================================*/
create table wl_makeloguser (
   id                   char(32)             not null,
   id_user              char(32)             null,
   predate              int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table wl_makeloguser
   add constraint PK_WL_MAKELOGUSER primary key nonclustered (id)
go

/*==============================================================*/
/* Table: wl_worklog                                            */
/*==============================================================*/
create table wl_worklog (
   id                   char(32)             not null,
   id_user              char(32)             null,
   makedate             char(10)             null,
   content              varchar(200)         null,
   evaluation           varchar(200)         null,
   status               char(1)              null,
   schedule             int                  null,
   hours                int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table wl_worklog
   add constraint PK_WL_WORKLOG primary key nonclustered (id)
go

/*==============================================================*/
/* Table: wp_boarditem                                          */
/*==============================================================*/
create table wp_boarditem (
   id                   char(32)             not null,
   id_workplan          char(32)             null,
   boardType            varchar(50)          null,
   num                  int                  null,
   title                varchar(200)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table wp_boarditem
   add constraint PK_WP_BOARDITEM primary key nonclustered (id)
go

/*==============================================================*/
/* Table: wp_executionAssistance                                */
/*==============================================================*/
create table wp_executionAssistance (
   id                   char(32)             not null,
   id_workplan          char(32)             null,
   id_supportDepartment char(32)             null,
   id_demandDepartment  char(32)             null,
   id_demandPlan        char(32)             null,
   id_owner             char(32)             null,
   boardType            varchar(50)          null,
   demandDepartmentType varchar(50)          null,
   content              varchar(400)         null,
   advice               varchar(400)         null,
   status               char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table wp_executionAssistance
   add constraint PK_WP_EXECUTIONASSISTANCE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: wp_planchange                                         */
/*==============================================================*/
create table wp_planchange (
   id                   char(32)             not null,
   id_workplan          char(32)             null,
   id_promptObject      char(32)             null,
   boardType            varchar(50)          null,
   changeDetail         text                 null,
   status               char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table wp_planchange
   add constraint PK_WP_PLANCHANGE primary key nonclustered (id)
go

/*==============================================================*/
/* Table: wp_schoolopinions                                     */
/*==============================================================*/
create table wp_schoolopinions (
   id                   char(32)             not null,
   id_workplan          char(32)             null,
   content              varchar(1000)        null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table wp_schoolopinions
   add constraint PK_WP_SCHOOLOPINIONS primary key nonclustered (id)
go

/*==============================================================*/
/* Table: wp_teachingideas                                      */
/*==============================================================*/
create table wp_teachingideas (
   id                   char(32)             not null,
   id_workplan          char(32)             null,
   content              varchar(1000)        null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table wp_teachingideas
   add constraint PK_WP_TEACHINGIDEAS primary key nonclustered (id)
go

/*==============================================================*/
/* Table: wp_timearrangement                                    */
/*==============================================================*/
create table wp_timearrangement (
   id                   char(32)             not null,
   id_workplan          char(32)             null,
   workItem             varchar(400)         null,
   monthNum             int                  null,
   num                  int                  null,
   phaseWork            varchar(200)         null,
   gradeEvents          varchar(200)         null,
   schoolEvents         varchar(20)          null,
   subjectEvents        varchar(200)         null,
   studentActivities    varchar(200)         null,
   eclassEvents         varchar(200)         null,
   otherEvents          varchar(200)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table wp_timearrangement
   add constraint PK_WP_TIMEARRANGEMENT primary key nonclustered (id)
go

/*==============================================================*/
/* Table: wp_workideas                                          */
/*==============================================================*/
create table wp_workideas (
   id                   char(32)             not null,
   id_workplan          char(32)             null,
   content              varchar(1000)        null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table wp_workideas
   add constraint PK_WP_WORKIDEAS primary key nonclustered (id)
go

/*==============================================================*/
/* Table: wp_workobjectives                                     */
/*==============================================================*/
create table wp_workobjectives (
   id                   char(32)             not null,
   id_workplan          char(32)             null,
   content              varchar(1000)        null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table wp_workobjectives
   add constraint PK_WP_WORKOBJECTIVES primary key nonclustered (id)
go

/*==============================================================*/
/* Table: wp_workplan                                           */
/*==============================================================*/
create table wp_workplan (
   id                   char(32)             not null,
   id_schoolterm        char(32)             null,
   id_department        char(32)             null,
   id_school            char(32)             null,
   id_grade             char(32)             null,
   id_eclass            char(32)             null,
   id_course            char(32)             null,
   id_teacher           char(32)             null,
   monthNum             int                  null,
   teachingWeekNum      int                  null,
   weekNum              int                  null,
   planType             char(1)              null,
   templateType         char(1)              null,
   publishStatus        char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table wp_workplan
   add constraint PK_WP_WORKPLAN primary key nonclustered (id)
go

/*==============================================================*/
/* Table: wp_workpoint                                          */
/*==============================================================*/
create table wp_workpoint (
   id                   char(32)             not null,
   id_workplan          char(32)             null,
   id_boarditem         char(32)             null,
   num                  int                  null,
   content              varchar(400)         null,
   Column_9             varchar(200)         null,
   mainWorkFlag         char(1)              null,
   workForm             varchar(20)          null,
   workObjects          varchar(100)         null,
   Column_13            varchar(100)         null,
   Column_14            varchar(100)         null,
   schedule             varchar(20)          null,
   explanation          varchar(400)         null,
   nextSteps            varchar(1000)        null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
go

alter table wp_workpoint
   add constraint PK_WP_WORKPOINT primary key nonclustered (id)
go

alter table ac_acresultteachingweek
   add constraint FK_AC_ACRES_REFERENCE_AC_ARRAN foreign key (id_arrangecourseactivity)
      references ac_arrangecourseactivity (id)
go

alter table ac_arrangecourseactivity
   add constraint FK_AC_ARRAN_REFERENCE_AC_ARRA2 foreign key (id_parent)
      references ac_arrangecourseactivity (id)
go

alter table ac_arrangecourseactivity
   add constraint FK_AC_ARRAN_REFERENCE_BD_SCHOO foreign key (id_schoolterm)
      references bd_schoolterm (id)
go

alter table ac_arrangecourseactivity
   add constraint FK_AC_ARRAN_REFERENCE_BD_TEAC1 foreign key (id_teacher)
      references bd_teacher (id)
go

alter table ac_arrangecoursegrade
   add constraint FK_AC_ARRAN_RELATIONS_AC_ARRAN foreign key (id_arrangecourseactivity)
      references ac_arrangecourseactivity (id)
go

alter table ac_arrangecoursegrade
   add constraint FK_AC_ARRAN_RELATIONS_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
go

alter table ac_arrangecoursegrade
   add constraint FK_AC_ARRAN_RELATIONS_BD_SCHOO foreign key (id_school)
      references bd_school (id)
go

alter table ac_arrangecourseresult
   add constraint FK_AC_ARRAN_REFERENCE_AC_ARRA1 foreign key (id_arrangecourseactivity)
      references ac_arrangecourseactivity (id)
go

alter table ac_arrangecourseresult
   add constraint FK_AC_ARRAN_REFERENCE_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
go

alter table ac_arrangecourseresult
   add constraint FK_AC_ARRAN_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table ac_arrangecourseresult
   add constraint FK_AC_ARRAN_REFERENCE_BD_TEAC2 foreign key (id_teacher)
      references bd_teacher (id)
go

alter table ac_eclassorder
   add constraint FK_AC_ECLAS_REFERENCE_AC_ARRAN foreign key (id_arrangecourseactivity)
      references ac_arrangecourseactivity (id)
go

alter table ac_eclassorder
   add constraint FK_AC_ECLAS_REFERENCE_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
go

alter table ac_ruleitem
   add constraint FK_RULEVALUE_RULE foreign key (id_rule)
      references ac_rule (id)
go

alter table ac_rulevalue
   add constraint FK_COURSERULE_RULE foreign key (id_rule)
      references ac_rule (id)
go

alter table ac_rulevalue
   add constraint FK_RULEVALUE_COURS2 foreign key (id_course)
      references bd_course (id)
go

alter table ac_rulevalue
   add constraint FK_RULEVALUE_ECLAS2 foreign key (id_eclass)
      references bd_eclass (id)
go

alter table ac_rulevalue
   add constraint FK_RULEVALUE_GRAD2 foreign key (id_grade)
      references bd_grade (id)
go

alter table ac_rulevalue
   add constraint FK_RULEVALUE_SCHOOL foreign key (id_school)
      references bd_school (id)
go

alter table ac_rulevalue
   add constraint FK_RULEVALUE_SCHOOLTERM foreign key (id_schoolterm)
      references bd_schoolterm (id)
go

alter table ac_rulevalue
   add constraint FK_RULEVALUE_TEACHER foreign key (id_teacher)
      references bd_teacher (id)
go

alter table af_classification
   add constraint FK_AF_CLASS_REFERENCE_AF_CLASS foreign key (id_parent)
      references af_classification (id)
go

alter table af_collectionrecord
   add constraint FK_AF_COLLE_REFERENCE_AF_RESOU foreign key (id_resourcepackage)
      references af_resourcepackage (id)
go

alter table af_collectionrecord
   add constraint FK_AF_COLLE_REFERENCE_BD_USER foreign key (id_collector)
      references bd_user (id)
go

alter table af_evaluation
   add constraint FK_AF_EVALU_REFERENCE_AF_RESOU foreign key (id_resourcepackage)
      references af_resourcepackage (id)
go

alter table af_evaluation
   add constraint FK_AF_EVALU_REFERENCE_BD_USER foreign key (id_valuator)
      references bd_user (id)
go

alter table af_filefolder
   add constraint FK_AF_FILEF_REFERENCE_AF_FILEF foreign key (id_parent)
      references af_filefolder (id)
go

alter table af_filefolder
   add constraint FK_AF_FILEF_REFERENCE_BD_USER foreign key (id_creater)
      references bd_user (id)
go

alter table af_filerecord
   add constraint FK_AF_FILER_REFERENCE_AF_FILEF foreign key (id_filefolder)
      references af_filefolder (id)
go

alter table af_filerecord
   add constraint FK_AF_FILER_REFERENCE_BD_USER foreign key (id_uploaduser)
      references bd_user (id)
go

alter table af_filerecord_resourcepackage
   add constraint FK_AF_FILER_REFERENCE_AF_RESOU foreign key (id_resourcepackage)
      references af_resourcepackage (id)
go

alter table af_filerecord_resourcepackage
   add constraint FK_AF_FILER_REFERENCE_AF_FILER foreign key (id_filerecord)
      references af_filerecord (id)
go

alter table af_resourcepackage
   add constraint FK_AF_RESOU_REFERENCE_AF_CLASS foreign key (id_classification)
      references af_classification (id)
go

alter table af_resourcepackage
   add constraint FK_AF_RESOU_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table af_resourcepackage
   add constraint FK_AF_RESOU_REFERENCE_BD_USER foreign key (id_contributor)
      references bd_user (id)
go

alter table al_aloffice
   add constraint FK_AL_ALOFF_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table al_aloffice
   add constraint FK_AL_ALOFF_REFERENCE_BD_SCHOO foreign key (id_school)
      references bd_school (id)
go

alter table al_classbook
   add constraint FK_AL_CLASS_REFERENCE_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
go

alter table al_officeuser
   add constraint FK_AL_OFFIC_REFERENCE_AL_ALOFF foreign key (id_aloffice)
      references al_aloffice (id)
go

alter table al_officeuser
   add constraint FK_AL_OFFIC_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table am_answerdetail
   add constraint FK_AM_ANSWE_REFERENCE_AM_ANSWE foreign key (id_answersubject)
      references am_answersubject (id)
go

alter table am_answerdetail
   add constraint FK_AM_ANSWE_REFERENCE_BD_STUDE foreign key (id_student)
      references bd_student (id)
go

alter table am_answersubject
   add constraint Reference_817 foreign key (id_schoolterm)
      references bd_schoolterm (id)
go

alter table am_answersubject
   add constraint Reference_818 foreign key (id_school)
      references bd_school (id)
go

alter table am_answersubject
   add constraint FK_AM_ANSWE_REFERENCE_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
go

alter table am_answersubject
   add constraint FK_AM_ANSWE_REFERENCE_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
go

alter table am_answersubject
   add constraint FK_AM_ANSWE_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table bd_applicationgroup
   add constraint FK_BD_APPLI_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table bd_beadroll
   add constraint FK_BEADROLL_STUDENT foreign key (id_student)
      references bd_student (id)
go

alter table bd_classcurriculum
   add constraint FK_BD_CLASS_REFERENCE_BD_CLAS2 foreign key (id_classroom)
      references bd_classroom (id)
go

alter table bd_classcurriculum
   add constraint FK_AC_CLASS_RELATIONS_BD_ECLA2 foreign key (id_eclass)
      references bd_eclass (id)
go

alter table bd_classcurriculum
   add constraint FK_AC_CLASS_RELATIONS_BD_COUR2 foreign key (id_course)
      references bd_course (id)
go

alter table bd_classcurriculum
   add constraint FK_AC_CLASS_RELATIONS_BD_TEAC2 foreign key (id_teacher)
      references bd_teacher (id)
go

alter table bd_classcurriculumchange
   add constraint FK_AC_CLASS_RELATIONS_BD_COUR3 foreign key (id_fromcourse)
      references bd_course (id)
go

alter table bd_classcurriculumchange
   add constraint FK_AC_CLASS_RELATIONS_BD_TEAC3 foreign key (id_fromteacher)
      references bd_teacher (id)
go

alter table bd_classcurriculumchange
   add constraint FK_BD_CLASS_RELATIONS_BD_ECLA3 foreign key (id_eclass)
      references bd_eclass (id)
go

alter table bd_classcurriculumchange
   add constraint FK_BD_CLASS_RELATIONS_BD_COURS foreign key (id_tocourse)
      references bd_course (id)
go

alter table bd_classcurriculumchange
   add constraint FK_BD_CLASS_RELATIONS_BD_TEACH foreign key (id_toteacher)
      references bd_teacher (id)
go

alter table bd_classroom
   add constraint FK_BD_CLASS_REFERENCE_BD_SCHOO foreign key (id_school)
      references bd_school (id)
go

alter table bd_classroomcourse
   add constraint FK_BD_CLASS_REFERENCE_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
go

alter table bd_classroomcourse
   add constraint FK_BD_CLASS_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table bd_classroomcourse
   add constraint FK_BD_CLASS_REFERENCE_BD_CLASS foreign key (id_classroom)
      references bd_classroom (id)
go

alter table bd_classroomseat
   add constraint FK_BD_CLASS_REFERENCE_BD_CLAS1 foreign key (id_classroom)
      references bd_classroom (id)
go

alter table bd_classstudent
   add constraint FK_BD_CLASS_RELATIONS_BD_STUDE foreign key (id_student)
      references bd_student (id)
go

alter table bd_classstudent
   add constraint FK_BD_CLASS_RELATIONS_BD_ECLA1 foreign key (id_eclass)
      references bd_eclass (id)
go

alter table bd_configitem_configresult
   add constraint FK_BD_CONFI_REFERENCE_BD_CONF3 foreign key (id_configurationitem)
      references bd_configurationitem (id)
go

alter table bd_configitem_configresult
   add constraint FK_BD_CONFI_REFERENCE_BD_CONF4 foreign key (id_configurationresult)
      references bd_configurationresult (id)
go

alter table bd_configuration
   add constraint FK_BD_CONFI_REFERENCE_BD_MODUL foreign key (id_module)
      references bd_module (id)
go

alter table bd_configurationitem
   add constraint FK_BD_CONFI_REFERENCE_BD_CONF2 foreign key (id_configuration)
      references bd_configuration (id)
go

alter table bd_configurationresult
   add constraint FK_BD_CONFI_REFERENCE_BD_CONFI foreign key (id_configuration)
      references bd_configuration (id)
go

alter table bd_configurationresult
   add constraint FK_BD_CONFI_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table bd_contract
   add constraint FK_BD_CONTR_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table bd_contract
   add constraint FK_BD_CONTR_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table bd_course
   add constraint FK_BD_COURS_RELATIONS_BD_SUBJE foreign key (id_subject)
      references bd_subject (id)
go

alter table bd_department
   add constraint FK_BD_DEPAR_REFERENCE_BD_SCHO2 foreign key (id_school)
      references bd_school (id)
go

alter table bd_department
   add constraint FK_BD_DEPAR_RELATIONS_BD_DEPA2 foreign key (id_parent)
      references bd_department (id)
go

alter table bd_departmentposition
   add constraint FK_BD_DEPAR_RELATIONS_BD_DEPA4 foreign key (id_department)
      references bd_department (id)
go

alter table bd_departmentposition
   add constraint FK_BD_DEPAR_RELATIONS_BD_POSIT foreign key (id_position)
      references bd_position (id)
go

alter table bd_departmentscope
   add constraint FK_BD_DEPAR_REFERENCE_BD_SCHOO foreign key (id_school)
      references bd_school (id)
go

alter table bd_departmentscope
   add constraint FK_BD_DEPAR_RELATIONS_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table bd_departmentscope
   add constraint FK_BD_DEPAR_RELATIONS_BD_DEPA3 foreign key (id_department)
      references bd_department (id)
go

alter table bd_departmentscope
   add constraint FK_BD_DEPAR_RELATIONS_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
go

alter table bd_departmentuser
   add constraint FK_BD_DEPAR_RELATIONS_BD_DEPAR foreign key (id_department)
      references bd_department (id)
go

alter table bd_departmentuser
   add constraint FK_BD_DEPAR_RELATIONS_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table bd_dictionaryvalue
   add constraint FK_BD_DICTI_REFERENCE_BD_DI110 foreign key (id_parent)
      references bd_dictionaryvalue (id)
go

alter table bd_dictionaryvalue
   add constraint FK_BD_DICTI_REFERENCE_BD_DI122 foreign key (id_dictionary)
      references bd_dictionary (id)
go

alter table bd_dimission
   add constraint FK_BD_DIMIS_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table bd_dividescore
   add constraint FK_BD_DIVID_REFERENCE_BD_STUDE foreign key (id_student)
      references bd_student (id)
go

alter table bd_eclass
   add constraint FK_BD_ECLAS_RELATIONS_BD_TEAC2 foreign key (id_classteacher2)
      references bd_teacher (id)
go

alter table bd_eclass
   add constraint FK_BD_ECLAS_RELATIONS_BD_TEACH foreign key (id_classteacher)
      references bd_teacher (id)
go

alter table bd_eclass
   add constraint FK_BD_ECLAS_RELATIONS_BD_SCHO2 foreign key (id_school)
      references bd_school (id)
go

alter table bd_eclass
   add constraint FK_BD_ECLAS_RELATIONS_BD_SCHOO foreign key (id_schoolterm)
      references bd_schoolterm (id)
go

alter table bd_eclass
   add constraint FK_BD_ECLAS_RELATIONS_BD_PERIO foreign key (id_period)
      references bd_period (id)
go

alter table bd_eclass
   add constraint FK_BD_ECLAS_RELATIONS_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
go

alter table bd_eclassteachingmaterial
   add constraint FK_BD_ECLAS_REFERENCE_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
go

alter table bd_eclassteachingmaterial
   add constraint FK_BD_ECLAS_REFERENCE_BD_SCHOO foreign key (id_schoolterm)
      references bd_schoolterm (id)
go

alter table bd_eclassteachingmaterial
   add constraint FK_BD_ECLAS_REFERENCE_BD_TEACH foreign key (id_teachingmaterial)
      references bd_teachingmaterial (id)
go

alter table bd_gradecourse
   add constraint FK_BD_GRADE_REFERENCE_BD_SCHO2 foreign key (id_school)
      references bd_school (id)
go

alter table bd_gradecourse
   add constraint FK_BD_GRADE_RELATIONS_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table bd_gradecourse
   add constraint FK_BD_GRADE_RELATIONS_BD_GRAD2 foreign key (id_grade)
      references bd_grade (id)
go

alter table bd_gradecourse
   add constraint FK_BD_GRADE_RELATIONS_BD_SCHOO foreign key (id_schoolterm)
      references bd_schoolterm (id)
go

alter table bd_gradeteachingmaterial
   add constraint FK_BD_GRADE_REFERENCE_BD_SCHOO foreign key (id_schoolterm)
      references bd_schoolterm (id)
go

alter table bd_gradeteachingmaterial
   add constraint FK_BD_GRADE_REFERENCE_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
go

alter table bd_gradeteachingmaterial
   add constraint FK_BD_GRADE_REFERENCE_BD_TEACH foreign key (id_teachingmaterial)
      references bd_teachingmaterial (id)
go

alter table bd_groupapplication
   add constraint FK_BD_GROUP_REFERENCE_BD_APPLI foreign key (id_applicationgroup)
      references bd_applicationgroup (id)
go

alter table bd_groupapplication
   add constraint FK_BD_GROUP_REFERENCE_BD_OPERA foreign key (id_operation)
      references bd_operation (id)
go

alter table bd_integrationcourse
   add constraint FK_BD_INTEG_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table bd_integrationcourse
   add constraint FK_BD_INTEG_REFERENCE_BD_SCHOO foreign key (id_schoolterm)
      references bd_schoolterm (id)
go

alter table bd_integrationcourseeclass
   add constraint FK_BD_INTEG_REFERENCE_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
go

alter table bd_integrationcourseeclass
   add constraint FK_BD_INTEG_REFERENCE_BD_INTE2 foreign key (id_integrationcourse)
      references bd_integrationcourse (id)
go

alter table bd_integrationcourseteacher
   add constraint FK_BD_INTEG_REFERENCE_BD_INTE1 foreign key (id_integrationcourse)
      references bd_integrationcourse (id)
go

alter table bd_integrationcourseteacher
   add constraint FK_BD_INTEG_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table bd_knowledgepoint
   add constraint FK_BD_KNOWL_REFERENCE_BD_KNOWL foreign key (id_parent)
      references bd_knowledgepoint (id)
go

alter table bd_knowledgepoint
   add constraint FK_BD_KNOWL_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table bd_learningresume
   add constraint FK_BD_LEARN_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table bd_lessonquantitychange
   add constraint FK_BD_LESSO_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table bd_lessonquantitychange
   add constraint FK_BD_LESSO_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table bd_message
   add constraint FK_BD_MESSA_REFERENCE_BD_USER foreign key (id_senduser)
      references bd_user (id)
go

alter table bd_operation
   add constraint FK_BD_OPERA_REFERENCE_BD_OPERA foreign key (id_parent)
      references bd_operation (id)
go

alter table bd_operation
   add constraint FK_BD_OPERA_RELATIONS_BD_MODUL foreign key (id_module)
      references bd_module (id)
go

alter table bd_operationlog
   add constraint FK_BD_OPERA_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table bd_operationlog
   add constraint FK_BD_OPERA_RELATIONS_BD_OPERA foreign key (id_operation)
      references bd_operation (id)
go

alter table bd_positionchange
   add constraint FK_BD_POSIT_RELATIONS_BD_SCHOO foreign key (id_schoolterm)
      references bd_schoolterm (id)
go

alter table bd_positionchange
   add constraint FK_BD_POSIT_RELATIONS_BD_DEPAR foreign key (id_fromdepartment)
      references bd_department (id)
go

alter table bd_positionchange
   add constraint FK_BD_POSIT_RELATIONS_BD_POSI2 foreign key (id_fromposition)
      references bd_position (id)
go

alter table bd_positionchange
   add constraint FK_BD_POSIT_RELATIONS_BD_DEPA2 foreign key (id_todepartment)
      references bd_department (id)
go

alter table bd_positionchange
   add constraint FK_BD_POSIT_RELATIONS_BD_POSIT foreign key (id_toposition)
      references bd_position (id)
go

alter table bd_positionchange
   add constraint FK_BD_POSIT_RELATIONS_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table bd_postionprivilege
   add constraint FK_BD_POSTI_RELATIONS_BD_POSIT foreign key (id_position)
      references bd_position (id)
go

alter table bd_postionprivilege
   add constraint FK_BD_POSTI_RELATIONS_BD_PRIVI foreign key (id_privilege)
      references bd_privilege (id)
go

alter table bd_privatedepartment
   add constraint FK_BD_PRIVA_RELATIONS_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table bd_privatedepartmentteacher
   add constraint FK_PC_PRIVA_RELATIONS_BD_TEAC2 foreign key (id_teacher)
      references bd_teacher (id)
go

alter table bd_privilege
   add constraint FK_BD_PRIVI_RELATIONS_BD_MODUL foreign key (id_module)
      references bd_module (id)
go

alter table bd_privilegeoperation
   add constraint FK_BD_PRIVI_RELATIONS_BD_PRIVI foreign key (id_privilege)
      references bd_privilege (id)
go

alter table bd_privilegeoperation
   add constraint FK_BD_PRIVI_RELATIONS_BD_OPERA foreign key (id_operation)
      references bd_operation (id)
go

alter table bd_professionaltitle
   add constraint FK_BD_PROFE_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table bd_questionresource
   add constraint FK_BD_QUEST_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table bd_questionresource
   add constraint Reference_808 foreign key (id_teachingmaterial)
      references bd_teachingmaterial (id)
go

alter table bd_questionresource
   add constraint FK_BD_QUEST_REFERENCE_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
go

alter table bd_questionresource
   add constraint Reference_810 foreign key (id_teacher)
      references bd_teacher (id)
go

alter table bd_questionresource
   add constraint FK_BD_QUEST_REFERENCE_BD_TEACH foreign key (id_teachingunit)
      references bd_teachingunit (id)
go

alter table bd_questionresourcecollect
   add constraint Reference_440 foreign key (id_questionresource)
      references bd_questionresource (id)
go

alter table bd_questionresourcecollect
   add constraint FK_BD_QUEST_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table bd_questionresourceknowledge
   add constraint Reference_442 foreign key (id_questionresource)
      references bd_questionresource (id)
go

alter table bd_questionresourceknowledge
   add constraint FK_BD_QUEST_REFERENCE_BD_KNOWL foreign key (id_knowledgepoint)
      references bd_knowledgepoint (id)
go

alter table bd_questionresourceoption
   add constraint Reference_441 foreign key (id_questionresource)
      references bd_questionresource (id)
go

alter table bd_receiver
   add constraint FK_BD_RECEI_REFERENCE_BD_USER foreign key (id_receiveuser)
      references bd_user (id)
go

alter table bd_receiver
   add constraint FK_BD_RECEI_REFERENCE_BD_MESSA foreign key (id_message)
      references bd_message (id)
go

alter table bd_rehire
   add constraint FK_BD_REHIR_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table bd_retired
   add constraint FK_BD_RETIR_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table bd_schedulegrade
   add constraint FK_BD_SCHED_REFERENCE_BD_SC129 foreign key (id_schedule)
      references bd_schedule (id)
go

alter table bd_schedulegrade
   add constraint FK_BD_SCHED_REFERENCE_BD_SCHOO foreign key (id_school)
      references bd_school (id)
go

alter table bd_schedulegrade
   add constraint FK_BD_SCHED_REFERENCE_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
go

alter table bd_scheduleitem
   add constraint FK_BD_SCHED_REFERENCE_BD_SC128 foreign key (id_schedule)
      references bd_schedule (id)
go

alter table bd_school
   add constraint FK_BD_SCHOO_RELATIONS_BD_SCHO2 foreign key (id_parent)
      references bd_school (id)
go

alter table bd_schoolgrade
   add constraint FK_BD_SCHOO_REFERENCE_BD_SCHOO foreign key (id_school)
      references bd_school (id)
go

alter table bd_schoolgrade
   add constraint FK_BD_SCHOO_RELATIONS_BD_PERIO foreign key (id_period)
      references bd_period (id)
go

alter table bd_schoolgrade
   add constraint FK_BD_SCHOO_RELATIONS_BD_SCHOO foreign key (id_schoolterm)
      references bd_schoolterm (id)
go

alter table bd_schoolgrade
   add constraint FK_BD_SCHOO_RELATIONS_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
go

alter table bd_schoolterm
   add constraint FK_BD_SCHOO_RELATIONS_BD_SCHO3 foreign key (id_schoolyear)
      references bd_schoolyear (id)
go

alter table bd_studentchange
   add constraint FK_BD_STUDE_REFERENCE_BD_ECLA2 foreign key (id_toclass)
      references bd_eclass (id)
go

alter table bd_studentchange
   add constraint FK_BD_STUDE_RELATIONS_BD_ECLAS foreign key (id_fromclass)
      references bd_eclass (id)
go

alter table bd_studentchange
   add constraint FK_BD_STUDE_RELATIONS_BD_STUDE foreign key (id_student)
      references bd_student (id)
go

alter table bd_studentfirstaid
   add constraint FK_STUDENTFIRSTAID_STUDENT foreign key (id_student)
      references bd_student (id)
go

alter table bd_studentparent
   add constraint FK_BD_STUDE_REFERENCE_BD_STUDE foreign key (id_student)
      references bd_student (id)
go

alter table bd_studentparent
   add constraint FK_BD_STUDE_REFERENCE_BD_PAREN foreign key (id_parent)
      references bd_parent (id)
go

alter table bd_studentparents
   add constraint FK_STUDENTPARENTS_STUDENT foreign key (id_student)
      references bd_student (id)
go

alter table bd_studentregistration
   add constraint FK_BD_STUDE_REFERENCE_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
go

alter table bd_studentregistration
   add constraint FK_BD_STUDE_REFERENCE_BD_SCHO1 foreign key (id_school)
      references bd_school (id)
go

alter table bd_studentregistration
   add constraint FK_BD_STUDE_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table bd_studentseat
   add constraint FK_BD_STUDE_REFERENCE_BD_CL133 foreign key (id_classroomseat)
      references bd_classroomseat (id)
go

alter table bd_studentseat
   add constraint FK_BD_STUDE_REFERENCE_BD_CL134 foreign key (id_classroom)
      references bd_classroom (id)
go

alter table bd_studentseat
   add constraint FK_BD_STUDE_REFERENCE_BD_STUD2 foreign key (id_student)
      references bd_student (id)
go

alter table bd_studentseat
   add constraint FK_BD_STUDE_REFERENCE_BD_ECLA1 foreign key (id_eclass)
      references bd_eclass (id)
go

alter table bd_studentsource
   add constraint FK_STUDENTSOURC_STUDENT foreign key (id_student)
      references bd_student (id)
go

alter table bd_studentteachingmaterial
   add constraint FK_BD_STUDE_REFERENCE_BD_SCHOO foreign key (id_schoolterm)
      references bd_schoolterm (id)
go

alter table bd_studentteachingmaterial
   add constraint FK_BD_STUDE_REFERENCE_BD_STUD3 foreign key (id_student)
      references bd_student (id)
go

alter table bd_studentteachingmaterial
   add constraint FK_BD_STUDE_REFERENCE_BD_TEACH foreign key (id_teachingmaterial)
      references bd_teachingmaterial (id)
go

alter table bd_teacher
   add constraint FK_BD_TEACH_RELATIONS_BD_SCHOO foreign key (id_school)
      references bd_school (id)
go

alter table bd_teachercourse
   add constraint FK_BD_TEACH_REFERENCE_BD_CLASS foreign key (id_classroom)
      references bd_classroom (id)
go

alter table bd_teachercourse
   add constraint FK_BD_TEACH_RELATIONS_BD_ECLA2 foreign key (id_eclass)
      references bd_eclass (id)
go

alter table bd_teachercourse
   add constraint FK_BD_TEACH_RELATIONS_BD_COUR2 foreign key (id_course)
      references bd_course (id)
go

alter table bd_teachercourse
   add constraint FK_BD_TEACH_RELATIONS_BD_TEAC2 foreign key (id_teacher)
      references bd_teacher (id)
go

alter table bd_teachercoursechange
   add constraint FK_BD_TEACH_REFERENCE_BD_TEACH foreign key (id_toteacher)
      references bd_teacher (id)
go

alter table bd_teachercoursechange
   add constraint FK_BD_TEACH_REFERENCE_BD_COURS foreign key (id_tocourse)
      references bd_course (id)
go

alter table bd_teachercoursechange
   add constraint FK_BD_TEACH_RELATIONS_BD_COURS foreign key (id_fromcourse)
      references bd_course (id)
go

alter table bd_teachercoursechange
   add constraint FK_BD_TEACH_RELATIONS_BD_TEACH foreign key (id_fromteacher)
      references bd_teacher (id)
go

alter table bd_teachercoursechange
   add constraint FK_BD_TEACH_RELATIONS_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
go

alter table bd_teachingmaterial
   add constraint FK_BD_TEACH_REFERENCE_BD_COUR2 foreign key (id_course)
      references bd_course (id)
go

alter table bd_teachingmaterial
   add constraint FK_BD_TEACH_REFERENCE_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
go

alter table bd_teachingmaterial
   add constraint FK_BD_TEACH_REFERENCE_BD_PUBLI foreign key (id_publisher)
      references bd_publisher (id)
go

alter table bd_teachingunit
   add constraint FK_BD_TEACHINGUNIT_PARENT foreign key (id_parent)
      references bd_teachingunit (id)
go

alter table bd_teachingunit
   add constraint Reference_926 foreign key (id_teachingmaterial)
      references bd_teachingmaterial (id)
go

alter table bd_teachingunitsubject
   add constraint FK_BD_TEACHINGUNIT_TEACHINGUNITSUBJECT foreign key (id_teachingunit)
      references bd_teachingunit (id)
go

alter table bd_testpaperquestion
   add constraint FK_BD_TESTP_REFERENCE_BD_TESTP foreign key (id_testpaper)
      references bd_testpaper (id)
go

alter table bd_testpaperquestion
   add constraint FK_BD_TESTP_REFERENCE_BD_QUEST foreign key (id_questionresource)
      references bd_questionresource (id)
go

alter table bd_userecord
   add constraint FK_BD_USERE_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table bd_userecord
   add constraint FK_BD_USERE_REFERENCE_BD_OPERA foreign key (id_operate)
      references bd_operation (id)
go

alter table bd_useroperation
   add constraint FK_BD_USERO_RELATIONS_BD_POSIT foreign key (id_position)
      references bd_position (id)
go

alter table bd_useroperation
   add constraint FK_BD_USERO_RELATIONS_BD_USER3 foreign key (id_user)
      references bd_user (id)
go

alter table bd_useroperation
   add constraint FK_BD_USERO_RELATIONS_BD_OPER2 foreign key (id_operation)
      references bd_operation (id)
go

alter table bd_useroperationchange
   add constraint FK_BD_USERO_RELATIONS_BD_OPERA foreign key (id_operation)
      references bd_operation (id)
go

alter table bd_useroperationchange
   add constraint FK_BD_USERO_RELATIONS_BD_USER foreign key (id_granteduser)
      references bd_user (id)
go

alter table bd_useroperationchange
   add constraint FK_BD_USERO_RELATIONS_BD_USER2 foreign key (id_grantuser)
      references bd_user (id)
go

alter table bd_useroperationdatascope
   add constraint FK_BD_USERO_RELATIONS_BD_USERO foreign key (id_useroperation)
      references bd_useroperation (id)
go

alter table bd_useroperationdatascope
   add constraint FK_BD_USERO_RELATIONS_BD_DATAS foreign key (id_datascopeclass)
      references bd_datascopeclass (id)
go

alter table bd_useroperationdatascope
   add constraint FK_BD_USERO_RELATIONS_BD_USER1 foreign key (id_parent)
      references bd_useroperationdatascope (id)
go

alter table bd_userposition
   add constraint FK_BD_USERP_RELATIONS_BD_USER2 foreign key (id_user)
      references bd_user (id)
go

alter table bd_userposition
   add constraint FK_BD_USERP_RELATIONS_BD_POSIT foreign key (id_position)
      references bd_position (id)
go

alter table bd_userposition
   add constraint FK_BD_USERP_RELATIONS_BD_DEPAR foreign key (id_department)
      references bd_department (id)
go

alter table bd_visa
   add constraint FK_BD_VISA_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table bd_workingresume
   add constraint FK_BD_WORKI_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table bl_blog
   add constraint FK_BL_BLOG_REFERENCE_BD_USER foreign key (id_createuser)
      references bd_user (id)
go

alter table bl_blogadmin
   add constraint FK_BL_BLOGA_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table bl_blogadmin
   add constraint FK_BL_BLOGA_REFERENCE_BL_BLOG foreign key (id_blog)
      references bl_blog (id)
go

alter table bl_comment
   add constraint FK_BL_COMME_REFERENCE_BL_CONTE foreign key (id_content)
      references bl_content (id)
go

alter table bl_comment
   add constraint FK_BL_COMME_REFERENCE_BL_COMME foreign key (id_parent)
      references bl_comment (id)
go

alter table bl_comment
   add constraint FK_BL_COMME_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table bl_content
   add constraint FK_BL_CONTE_REFERENCE_BL_BLOG foreign key (id_blog)
      references bl_blog (id)
go

alter table bl_photo
   add constraint FK_BL_PHOTO_REFERENCE_BL_CONTE foreign key (id_content)
      references bl_content (id)
go

alter table bl_praiseinfo
   add constraint FK_BL_PRAIS_REFERENCE_BL_CONTE foreign key (id_content)
      references bl_content (id)
go

alter table bl_praiseinfo
   add constraint FK_BL_PRAIS_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table ca_attendancerecord
   add constraint FK_CA_ATTEN_REFERENCE_BD_STUDE foreign key (id_student)
      references bd_student (id)
go

alter table ca_attendancerecord
   add constraint FK_CA_ATTEN_REFERENCE_CA_CLASS foreign key (id_classroomattendance)
      references ca_classroomattendance (id)
go

alter table ca_attendancerecord
   add constraint FK_CA_ATTEN_REFERENCE_CA_ATTEN foreign key (id_attendanceitem)
      references ca_attendanceitem (id)
go

alter table ca_classroomattendance
   add constraint FK_CA_CLASS_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table ca_leaverecord
   add constraint FK_CA_LEAVE_REFERENCE_BD_STUDE foreign key (id_student)
      references bd_student (id)
go

alter table ca_leaverecord
   add constraint FK_CA_LEAVE_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table ca_leaverecord
   add constraint FK_CA_LEAVE_REFERENCE_BD_SCHOO foreign key (id_schoolterm)
      references bd_schoolterm (id)
go

alter table ca_performanceitem
   add constraint FK_CA_PERFO_REFERENCE_CA_PERFO foreign key (id_performancekind)
      references ca_performancekind (id)
go

alter table ca_performancerecord
   add constraint FK_CA_PERFO_REFERENCE_BD_STUDE foreign key (id_student)
      references bd_student (id)
go

alter table ca_performancerecord
   add constraint FK_CA_PERFO_REFERENCE_CA_CLASS foreign key (id_classroomattendance)
      references ca_classroomattendance (id)
go

alter table ca_performancerecord
   add constraint FK_CA_PERFO_REFERENCE_CA_PERF2 foreign key (id_performanceitem)
      references ca_performanceitem (id)
go

alter table cc_continuingcredit
   add constraint FK_CC_CONTI_REFERENCE_BD_SCHOO foreign key (id_schoolterm)
      references bd_schoolterm (id)
go

alter table cc_creditmaterial
   add constraint FK_CC_CREDI_REFERENCE_MA_MATER foreign key (id_materialskind)
      references ma_materialskind (id)
go

alter table cc_creditmaterial
   add constraint FK_CC_CREDI_REFERENCE_CC_CONTI foreign key (id_baseoption)
      references cc_continuingcredit (id)
go

alter table cc_trainprincipal
   add constraint Reference_422 foreign key (id_schoolouttrain)
      references cc_schoolouttrain (id)
go

alter table cc_trainprincipal
   add constraint Reference_425 foreign key (id_user)
      references bd_user (id)
go

alter table cc_trainuser
   add constraint Reference_419 foreign key (id_schoolouttrain)
      references cc_schoolouttrain (id)
go

alter table cc_trainuser
   add constraint Reference_420 foreign key (id_user)
      references bd_user (id)
go

alter table ce_evaluationactivity
   add constraint FK_EVALUATIONACTIVITY_ITEM foreign key (id_evaluationstandard)
      references ce_evaluationstandard (id)
go

alter table ce_evaluationactivity
   add constraint FK_CE_EVALU_REFERENCE_BD_SCHOO foreign key (id_semester)
      references bd_schoolterm (id)
go

alter table ce_evaluationdetail
   add constraint FK_EVALUATIONDETAIL_EVALUATIONDETAIL foreign key (id_parent)
      references ce_evaluationdetail (id)
go

alter table ce_evaluationdetail
   add constraint FK_EVALUATIONDETAIL_EVALUATIONSTANDARD foreign key (id_evaluationstandard)
      references ce_evaluationstandard (id)
go

alter table ce_evaluationrank
   add constraint FK_EVALUATIONRANK_EVALUATIONDETAIL foreign key (id_evaluationdetail)
      references ce_evaluationdetail (id)
go

alter table ce_evaluationresult
   add constraint FK_EVALUATIONRESULT_EVALUATIONDETAIL foreign key (id_evaluationdetail)
      references ce_evaluationdetail (id)
go

alter table ce_evaluationresult
   add constraint FK_EVALUATIONRESULT_REVIEWER foreign key (id_reviewer)
      references ce_reviewer (id)
go

alter table ce_evaluationresult
   add constraint FK_CE_EVALU_REFERENCE_CE_REVIE foreign key (id_evalueUser)
      references ce_reviewed (id)
go

alter table ce_evaluationresult
   add constraint FK_CE_EVALU_REFERENCE_CE_EVALU foreign key (id_evaluationactivity)
      references ce_evaluationactivity (id)
go

alter table ce_modelsetting
   add constraint FK_MODELSETTING_EVALUATIONACTIVITY foreign key (id_evaluationactivity)
      references ce_evaluationactivity (id)
go

alter table ce_modelsetting
   add constraint FK_MODELSETTING_STATISTICALMODEL foreign key (id_statisticalmodel)
      references ce_statisticalmodel (id)
go

alter table ce_reviewed
   add constraint FK_REVIEWED_EVALUATIONACTIVITY foreign key (id_evaluationactivity)
      references ce_evaluationactivity (id)
go

alter table ce_reviewed
   add constraint FK_CE_REVIE_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table ce_reviewer
   add constraint FK_REVIEWER_EVALUATIONACTIVITY foreign key (id_evaluationactivity)
      references ce_evaluationactivity (id)
go

alter table ce_reviewer
   add constraint Reference_232 foreign key (id_user)
      references bd_user (id)
go

alter table ce_statisticalmodel_user
   add constraint FK_STATISTICALMODELUSER_STATISTICALMODEL foreign key (id_statisticalmodel)
      references ce_statisticalmodel (id)
go

alter table ce_statisticalmodel_user
   add constraint FK_CE_STATI_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table cm_assigncar
   add constraint FK_CM_ASSIGNCAR_USER foreign key (id_user)
      references bd_user (id)
go

alter table cm_assigncar
   add constraint FK_CM_ASSIGNCAR_CAR foreign key (id_car)
      references cm_car (id)
go

alter table cm_assigncar
   add constraint FK_CM_ASSIGNCAR_DRIVER foreign key (id_driver)
      references cm_driver (id)
go

alter table cm_driver
   add constraint FK_CM_DRIVER_USER foreign key (id_user)
      references bd_user (id)
go

alter table cm_orderassigncar
   add constraint FK_CM_ORDERRASSIGN_RASSIGN foreign key (id_assigncar)
      references cm_assigncar (id)
go

alter table cm_orderassigncar
   add constraint FK_CM_ORDERRASSIGN_ORDER foreign key (id_ordercar)
      references cm_ordercar (id)
go

alter table cm_ordercar
   add constraint FK_CM_ORDERCAR_CARUSER_USER foreign key (id_carUser)
      references bd_user (id)
go

alter table cm_ordercar
   add constraint FK_CM_ORDERCAR_ORDERUSER_USER foreign key (id_orderUser)
      references bd_user (id)
go

alter table cm_privatecar
   add constraint FK_CM_PRIVA_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table cs_visitorregistration
   add constraint FK_CS_VISIT_REFERENCE_BD_TEACH foreign key (id_receptionPersonnel)
      references bd_teacher (id)
go

alter table ct_answerresult
   add constraint Reference_323 foreign key (id_trainingactivity)
      references ct_trainingactivity (id)
go

alter table ct_answerresult
   add constraint Reference_324 foreign key (id_trainingcourse)
      references ct_trainingcourse (id)
go

alter table ct_answerresult
   add constraint Reference_325 foreign key (id_trainingsubject)
      references ct_trainingsubject (id)
go

alter table ct_answerresult
   add constraint FK_CT_ANSWE_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table ct_answerresultoption
   add constraint FK_CT_ANSWE_REFERENCE_CT_CTOPT foreign key (id_ctoption)
      references ct_ctoption (id)
go

alter table ct_answerresultoption
   add constraint FK_CT_ANSWE_REFERENCE_CT_ANSWE foreign key (id_answerresult)
      references ct_answerresult (id)
go

alter table ct_ctoption
   add constraint FK_CT_CTOPT_REFERENCE_CT_TRAIN foreign key (id_trainingsubject)
      references ct_trainingsubject (id)
go

alter table ct_studyscore
   add constraint Reference_630 foreign key (id_trainingactivity)
      references ct_trainingactivity (id)
go

alter table ct_studyscore
   add constraint FK_CT_STUDY_REFERENCE_CT_TRAIN foreign key (id_trainingcourse)
      references ct_trainingcourse (id)
go

alter table ct_studyscore
   add constraint FK_CT_STUDY_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table ct_trainingactivitycourse
   add constraint Reference_319 foreign key (id_trainingactivity)
      references ct_trainingactivity (id)
go

alter table ct_trainingactivitycourse
   add constraint Reference_320 foreign key (id_trainingcourse)
      references ct_trainingcourse (id)
go

alter table ct_trainingactivityteacher
   add constraint Reference_321 foreign key (id_trainingactivity)
      references ct_trainingactivity (id)
go

alter table ct_trainingactivityteacher
   add constraint Reference_620 foreign key (id_teacher)
      references bd_teacher (id)
go

alter table ct_trainingcourse
   add constraint Reference_618 foreign key (id_teacher)
      references bd_teacher (id)
go

alter table ct_trainingsubject
   add constraint Reference_317 foreign key (id_trainingcourse)
      references ct_trainingcourse (id)
go

alter table ec_activitystudentgroup
   add constraint FK_EC_ACTIV_REFERENCE_EC_ECACT foreign key (id_ecactivity)
      references ec_ecactivity (id)
go

alter table ec_alternativecourse
   add constraint Reference_863 foreign key (id_ecactivity)
      references ec_ecactivity (id)
go

alter table ec_alternativecourse
   add constraint FK_EC_ALTER_REFERENCE_BD_STUDE foreign key (id_student)
      references bd_student (id)
go

alter table ec_alternativecourse
   add constraint Reference_873 foreign key (id_alternative2)
      references ec_ecactivitycourse (id)
go

alter table ec_alternativecourse
   add constraint Reference_874 foreign key (id_alternative3)
      references ec_ecactivitycourse (id)
go

alter table ec_alternativecourse
   add constraint Reference_875 foreign key (id_alternative1)
      references ec_ecactivitycourse (id)
go

alter table ec_courseclassroom
   add constraint Reference_871 foreign key (id_ecactivity)
      references ec_ecactivity (id)
go

alter table ec_courseclassroom
   add constraint FK_EC_COURS_REFERENCE_BD_CLASS foreign key (id_classroom)
      references bd_classroom (id)
go

alter table ec_coursestudentgroup
   add constraint FK_EC_COURS_REFERENCE_EC_ACTIV foreign key (id_studentgroup)
      references ec_activitystudentgroup (id)
go

alter table ec_coursestudentgroup
   add constraint Reference_870 foreign key (id_ecactivitycourse)
      references ec_ecactivitycourse (id)
go

alter table ec_ecactivity
   add constraint FK_EC_ECACT_REFERENCE_BD_SCHOO foreign key (id_school)
      references bd_school (id)
go

alter table ec_ecactivitycourse
   add constraint Reference_106 foreign key (id_course)
      references bd_course (id)
go

alter table ec_ecactivitycourse
   add constraint FK_EC_ECACT_REFERENCE_EC_ELECT foreign key (id_group)
      references ec_electivegroup (id)
go

alter table ec_ecactivitycourse
   add constraint FK_EC_ECACT_REFERENCE_EC_ECCOU foreign key (id_eccourse)
      references ec_eccourse (id)
go

alter table ec_ecactivitycourse
   add constraint FK_EC_ECACT_REFERENCE_BD_USER foreign key (id_checkUser)
      references bd_user (id)
go

alter table ec_ecactivitycourse
   add constraint Reference_880 foreign key (id_beforeCourse)
      references bd_course (id)
go

alter table ec_ecactivitycourse
   add constraint FK_EC_ECACT_REFERENCE_BD_CLASS foreign key (id_classroom)
      references bd_classroom (id)
go

alter table ec_ecactivitycourse
   add constraint FK_EC_ECACT_RELATIONS_EC_ECAC2 foreign key (id_ecactivity)
      references ec_ecactivity (id)
go

alter table ec_ecactivityeclass
   add constraint FK_EC_ECACT_REFERENCE_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
go

alter table ec_ecactivityeclass
   add constraint FK_EC_ECACT_REFERENCE_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
go

alter table ec_ecactivityeclass
   add constraint FK_EC_ECACT_RELATIONS_EC_ECACT foreign key (id_ecactivity)
      references ec_ecactivity (id)
go

alter table ec_eccourse
   add constraint FK_EC_ECCOU_REFERENCE_BD_USER foreign key (id_checkUser)
      references bd_user (id)
go

alter table ec_eccourse
   add constraint FK_EC_ECCOU_REFERENCE_BD_USE2 foreign key (id_applyUser)
      references bd_user (id)
go

alter table ec_eccourse
   add constraint FK_EC_ECCOU_REFERENCE_BD_SUBJE foreign key (id_subject)
      references bd_subject (id)
go

alter table ec_eccourse
   add constraint FK_EC_ECCOU_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table ec_eccourse
   add constraint FK_EC_ECCOU_REFERENCE_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
go

alter table ec_eccourse
   add constraint FK_EC_ECCOU_REFERENCE_BD_CLASS foreign key (id_classroom)
      references bd_classroom (id)
go

alter table ec_ececlass
   add constraint FK_EC_ECECL_REFERENCE_EC_ECACT foreign key (id_ecactivitycourse)
      references ec_ecactivitycourse (id)
go

alter table ec_ececlass
   add constraint FK_EC_ECECL_RELATIONS_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
go

alter table ec_ecrule
   add constraint FK_EC_ECRUL_RELATIONS_EC_ECACT foreign key (id_ecactivity)
      references ec_ecactivity (id)
go

alter table ec_ecruleclass
   add constraint FK_EC_ECRUL_REFERENCE_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
go

alter table ec_ecruleclass
   add constraint FK_EC_ECRUL_RELATIONS_EC_ECRUL foreign key (id_ecrule)
      references ec_ecrule (id)
go

alter table ec_ecrulecourse
   add constraint FK_EC_ECRUL_RELATIONS_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table ec_ecrulecourse
   add constraint FK_EC_ECRUL_RELATIONS_EC_ECRU2 foreign key (id_ecerule)
      references ec_ecrule (id)
go

alter table ec_ecstudentcourse
   add constraint FK_EC_ECSTU_REFERENCE_BD_STUDE foreign key (id_student)
      references bd_student (id)
go

alter table ec_ecstudentcourse
   add constraint FK_EC_ECSTU_REFERENCE_BD_SCHOO foreign key (id_schoolterm)
      references bd_schoolterm (id)
go

alter table ec_ecstudentcourse
   add constraint FK_EC_ECSTU_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table ec_ecstudentcourse
   add constraint FK_EC_ECSTU_REFERENCE_EC_ECECL foreign key (id_ecclass)
      references ec_ececlass (id)
go

alter table ec_ecstudentcourse
   add constraint FK_EC_ECSTU_RELATIONS_EC_ECACT foreign key (id_ecactivity)
      references ec_ecactivity (id)
go

alter table ec_ecstudentcoursechange
   add constraint FK_EC_ECSTU_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table ec_ecstudentevaluate
   add constraint FK_EC_ECSTU_REFERENCE_EC_ECSTU foreign key (id_ecstudentcourse)
      references ec_ecstudentcourse (id)
go

alter table ec_groupstudent
   add constraint FK_EC_GROUP_REFERENCE_EC_ACTIV foreign key (id_studentgroup)
      references ec_activitystudentgroup (id)
go

alter table ec_groupstudent
   add constraint FK_EC_GROUP_REFERENCE_BD_STUDE foreign key (id_student)
      references bd_student (id)
go

alter table ec_subjectlevel
   add constraint FK_EC_SUBJE_REFERENCE_BD_SUBJE foreign key (id_subject)
      references bd_subject (id)
go

alter table er_recruitexaminfo
   add constraint FK_ER_RECRU_REFERENCE_BD_SCHOO foreign key (id_schoolterm)
      references bd_schoolterm (id)
go

alter table er_recruitexamroom
   add constraint fk_custom_key_12 foreign key (id_recruitexaminfo)
      references er_recruitexaminfo (id)
go

alter table er_recruitexamroom
   add constraint fk_custom_key_10 foreign key (id_recruitexamsubject)
      references er_recruitexamsubject (id)
go

alter table er_recruitexamroom
   add constraint fk_custom_key_11 foreign key (id_recruitexamsubjectgrade)
      references er_recruitexamsubjectgrade (id)
go

alter table er_recruitexamroomstudent
   add constraint fk_custom_key_16 foreign key (id_recruitexaminfo)
      references er_recruitexaminfo (id)
go

alter table er_recruitexamroomstudent
   add constraint fk_custom_key_13 foreign key (id_recruitexamroom)
      references er_recruitexamroom (id)
go

alter table er_recruitexamroomstudent
   add constraint fk_custom_key_14 foreign key (id_recruitexamstudent)
      references er_recruitexamstudent (id)
go

alter table er_recruitexamroomstudent
   add constraint fk_custom_key_17 foreign key (id_recruitexamsubject)
      references er_recruitexamsubject (id)
go

alter table er_recruitexamroomstudent
   add constraint fk_custom_key_15 foreign key (id_recruitexamsubjectgrade)
      references er_recruitexamsubjectgrade (id)
go

alter table er_recruitexamstudent
   add constraint fk_custom_key_2 foreign key (id_recruitexaminfo)
      references er_recruitexaminfo (id)
go

alter table er_recruitexamstudentsubject
   add constraint fk_custom_key_6 foreign key (id_recruitexaminfo)
      references er_recruitexaminfo (id)
go

alter table er_recruitexamstudentsubject
   add constraint fk_custom_key_7 foreign key (id_recruitexamstudent)
      references er_recruitexamstudent (id)
go

alter table er_recruitexamstudentsubject
   add constraint fk_custom_key_8 foreign key (id_recruitexamsubject)
      references er_recruitexamsubject (id)
go

alter table er_recruitexamstudentsubject
   add constraint fk_custom_key_9 foreign key (id_recruitexamsubjectgrade)
      references er_recruitexamsubjectgrade (id)
go

alter table er_recruitexamsubject
   add constraint fk_custom_key_3 foreign key (id_recruitexaminfo)
      references er_recruitexaminfo (id)
go

alter table er_recruitexamsubjectgrade
   add constraint fk_custom_key_4 foreign key (id_recruitexaminfo)
      references er_recruitexaminfo (id)
go

alter table er_recruitexamsubjectgrade
   add constraint fk_custom_key_5 foreign key (id_recruitexamsubject)
      references er_recruitexamsubject (id)
go

alter table et_etactivitie
   add constraint Reference_945 foreign key (id_schoolyear)
      references bd_schoolyear (id)
go

alter table et_etgrade
   add constraint Reference_641 foreign key (id_etactivitie)
      references et_etactivitie (id)
go

alter table et_etgrade
   add constraint Reference_946 foreign key (id_schoolgrade)
      references bd_schoolgrade (id)
go

alter table et_etgradingpeople
   add constraint Reference_640 foreign key (id_etactivitie)
      references et_etactivitie (id)
go

alter table et_etgradingpeople
   add constraint FK_ET_ETGRA_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table et_otherevaluation
   add constraint FK_ET_OTHER_REFERENCE_ET_ETACT foreign key (id_etactivitie)
      references et_etactivitie (id)
go

alter table et_otherevaluationscore
   add constraint FK_ET_OTHER_REFERENCE_ET_OTHER foreign key (id_otherEvaluation)
      references et_otherevaluation (id)
go

alter table et_otherevaluationscore
   add constraint FK_ET_OTHER_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table et_tievaluation
   add constraint Reference_613 foreign key (id_etactivitie)
      references et_etactivitie (id)
go

alter table et_tievaluationscore
   add constraint Reference_615 foreign key (id_tievaluation)
      references et_tievaluation (id)
go

alter table et_tievaluationscore
   add constraint Reference_948 foreign key (id_teacher)
      references bd_teacher (id)
go

alter table et_tpevaluation
   add constraint Reference_612 foreign key (id_etactivitie)
      references et_etactivitie (id)
go

alter table et_tpevaluationcourse
   add constraint FK_ET_TPEVA_REFERENCE_ET_TPEVA foreign key (id_tpevaluation)
      references et_tpevaluation (id)
go

alter table et_tpevaluationcourse
   add constraint FK_ET_TPEVA_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table et_tpevaluationelement
   add constraint Reference_621 foreign key (id_tpevaluation)
      references et_tpevaluation (id)
go

alter table et_tpevaluationgrade
   add constraint Reference_637 foreign key (id_tpevaluation)
      references et_tpevaluation (id)
go

alter table et_tpevaluationgrade
   add constraint FK_ET_TPEVA_REFERENCE_BD_SCHOO foreign key (id_schoolgrade)
      references bd_schoolgrade (id)
go

alter table et_tpevaluationscore
   add constraint Reference_639 foreign key (id_tpevaluation)
      references et_tpevaluation (id)
go

alter table et_tpevaluationscore
   add constraint Reference_953 foreign key (id_teacher)
      references bd_teacher (id)
go

alter table et_tpevaluationstandard
   add constraint Reference_623 foreign key (id)
      references et_tpevaluationelement (id)
go

alter table et_tpevaluationtarget
   add constraint Reference_622 foreign key (id)
      references et_tpevaluationelement (id)
go

alter table et_tpevaluationtarget
   add constraint FK_ET_TPEVA_REFERENCE_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
go

alter table et_workloadevaluation
   add constraint FK_ET_WORKL_REFERENCE_ET_ETACT foreign key (id_etactivitie)
      references et_etactivitie (id)
go

alter table et_workloadevaluationcourse
   add constraint Reference_616 foreign key (id_workloadEvaluation)
      references et_workloadevaluation (id)
go

alter table et_workloadevaluationcourse
   add constraint FK_ET_WORKL_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table et_workloadevaluationgrade
   add constraint Reference_642 foreign key (id_workloadEvaluation)
      references et_workloadevaluation (id)
go

alter table et_workloadevaluationgrade
   add constraint FK_ET_WORKL_REFERENCE_BD_SCHOO foreign key (id_schoolgrade)
      references bd_schoolgrade (id)
go

alter table et_workloadevaluationscore
   add constraint Reference_617 foreign key (id_workloadEvaluation)
      references et_workloadevaluation (id)
go

alter table et_workloadevaluationscore
   add constraint FK_ET_WORKL_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table ex_coursestandard
   add constraint FK_EX_COURS_REFERENCE_BD_COUR2 foreign key (id_course)
      references bd_course (id)
go

alter table ex_coursestandard
   add constraint FK_EX_COURS_REFERENCE_EX_SCOR2 foreign key (id_scoresegment)
      references ex_scoresegment (id)
go

alter table ex_coursestandard
   add constraint FK_EX_COURS_REFERENCE_EX_EXAMD foreign key (id_examdetail)
      references ex_examdetail (id)
go

alter table ex_coursestandardtemplate
   add constraint FK_EX_COURS_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table ex_coursestandardtemplate
   add constraint FK_EX_COURS_REFERENCE_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
go

alter table ex_coursestandardtemplate
   add constraint FK_EX_COURS_REFERENCE_EX_STAND foreign key (id_standardtemplate)
      references ex_standardtemplate (id)
go

alter table ex_coursestandardtemplate
   add constraint FK_EX_COURS_REFERENCE_EX_SCORE foreign key (id_scoresegment)
      references ex_scoresegment (id)
go

alter table ex_defaultcoursestandard
   add constraint FK_EX_DEFAU_REFERENCE_EX_SCORE foreign key (id_scoresegment)
      references ex_scoresegment (id)
go

alter table ex_defaultcoursestandard
   add constraint FK_EX_DEFAU_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table ex_defaultcoursestandard
   add constraint FK_EX_DEFAU_REFERENCE_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
go

alter table ex_defaultcoursestandard
   add constraint FK_EX_DEFAU_REFERENCE_EX_EXAM foreign key (id_exam)
      references ex_exam (id)
go

alter table ex_entryscorecharger
   add constraint FK_EX_ENTRY_REFERENCE_BD_TEAC3 foreign key (id_teacher)
      references bd_teacher (id)
go

alter table ex_entryscorecharger
   add constraint FK_EX_ENTRY_REFERENCE_EX_EXAMG foreign key (id_examgrade)
      references ex_examgrade (id)
go

alter table ex_entryscoreteacher
   add constraint FK_EX_ENTRY_REFERENCE_BD_TEAC2 foreign key (id_teacher)
      references bd_teacher (id)
go

alter table ex_entryscoreteacher
   add constraint FK_EX_ENTRY_REFERENCE_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
go

alter table ex_entryscoreteacher
   add constraint FK_EX_ENTRY_REFERENCE_EX_EXAMD foreign key (id_examdetail)
      references ex_examdetail (id)
go

alter table ex_entryscoreteacher
   add constraint FK_EX_ENTRY_REFERENCE_EX_EXAMR foreign key (id_examroom)
      references ex_examroom (id)
go

alter table ex_exam
   add constraint FK_EX_EXAM_REFERENCE_BD_SCHOO foreign key (id_schoolterm)
      references bd_schoolterm (id)
go

alter table ex_examdetail
   add constraint FK_EX_EXAMD_REFERENCE_EX_EXAMG foreign key (id_examgrade)
      references ex_examgrade (id)
go

alter table ex_examdetailcourse
   add constraint FK_EX_EXAMD_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table ex_examdetailcourse
   add constraint FK_EX_EXAMD_REFERENCE_EX_EXAMD foreign key (id_examdetail)
      references ex_examdetail (id)
go

alter table ex_examdetaileclass
   add constraint FK_EX_EXAMD_REFERENCE_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
go

alter table ex_examdetaileclass
   add constraint FK_EX_EXAMD_REFERENCE_EX_EXAM0 foreign key (id_examdetail)
      references ex_examdetail (id)
go

alter table ex_examgrade
   add constraint FK_EX_EXAMG_REFERENCE_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
go

alter table ex_examgrade
   add constraint FK_EX_EXAMG_REFERENCE_EX_EXAM foreign key (id_exam)
      references ex_exam (id)
go

alter table ex_examknowledgepoint
   add constraint FK_EX_EXAMK_REFERENCE_BD_KNOWL foreign key (id_knowledgepoint)
      references bd_knowledgepoint (id)
go

alter table ex_examknowledgepoint
   add constraint FK_EX_EXAMK_REFERENCE_EX_EXAMD foreign key (id_examdetail)
      references ex_examdetail (id)
go

alter table ex_examknowledgepointsubject
   add constraint FK_EX_EXAMK_REFERENCE_EX_EXAMK foreign key (id_examknowledgepoint)
      references ex_examknowledgepoint (id)
go

alter table ex_examknowledgepointsubject
   add constraint FK_EX_EXAMK_REFERENCE_EX_EXAMS foreign key (id_examsubject)
      references ex_examsubject (id)
go

alter table ex_exampaper
   add constraint Reference_850 foreign key (id_examdetail)
      references ex_examdetail (id)
go

alter table ex_exampaperanalyze
   add constraint Reference_858 foreign key (id_examdetail)
      references ex_examdetail (id)
go

alter table ex_exampaperanalyze
   add constraint FK_EX_EXAMP_REFERENCE_EX_EXAMS foreign key (id_examsubject)
      references ex_examsubject (id)
go

alter table ex_exampaperanalyzenote
   add constraint Reference_859 foreign key (id_examdetail)
      references ex_examdetail (id)
go

alter table ex_exampaperanalyzenote
   add constraint Reference_861 foreign key (id_course)
      references bd_course (id)
go

alter table ex_examroom
   add constraint FK_EX_EXAMR_REFERENCE_BD_SCHOO foreign key (id_school)
      references bd_school (id)
go

alter table ex_examroom
   add constraint FK_EX_EXAMR_REFERENCE_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
go

alter table ex_examroom
   add constraint FK_EX_EXAMR_REFERENCE_EX_EXAM foreign key (id_exam)
      references ex_exam (id)
go

alter table ex_examstudent
   add constraint FK_EX_EXAMS_REFERENCE_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
go

alter table ex_examstudent
   add constraint FK_EX_EXAMS_REFERENCE_BD_ECLA2 foreign key (id_eclass)
      references bd_eclass (id)
go

alter table ex_examstudent
   add constraint FK_EX_EXAMS_REFERENCE_BD_STUDE foreign key (id_student)
      references bd_student (id)
go

alter table ex_examstudent
   add constraint FK_EX_EXAMS_REFERENCE_EX_EXAMR foreign key (id_examroom)
      references ex_examroom (id)
go

alter table ex_examstudent
   add constraint FK_EX_EXAMS_REFERENCE_EX_EXAM foreign key (id_exam)
      references ex_exam (id)
go

alter table ex_examstudentexamdetail
   add constraint FK_EX_EXAMS_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table ex_examstudentexamdetail
   add constraint FK_EX_EXAMS_REFERENCE_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
go

alter table ex_examstudentexamdetail
   add constraint FK_EX_EXAMS_REFERENCE_EX_EXAM3 foreign key (id_examdetail)
      references ex_examdetail (id)
go

alter table ex_examstudentexamdetail
   add constraint FK_EX_EXAMS_REFERENCE_EX_EXAM5 foreign key (id_examstudent)
      references ex_examstudent (id)
go

alter table ex_examsubject
   add constraint FK_EX_EXAMS_REFERENCE_BD_COUR6 foreign key (id_course)
      references bd_course (id)
go

alter table ex_examsubject
   add constraint FK_EX_EXAMS_REFERENCE_EX_EXAMD foreign key (id_examdetail)
      references ex_examdetail (id)
go

alter table ex_examsubject
   add constraint FK_EX_EXAMS_REFERENCE_EX_EXAM2 foreign key (id_parent)
      references ex_examsubject (id)
go

alter table ex_examsubject
   add constraint Reference_854 foreign key (id_examsubpaper)
      references ex_examsubpaper (id)
go

alter table ex_examsubjectknowledgepoint
   add constraint FK_EX_EXAMS_REFERENCE_EX_EXAMS foreign key (id_examsubject)
      references ex_examsubject (id)
go

alter table ex_examsubjectknowledgepoint
   add constraint FK_EX_EXAMS_REFERENCE_BD_KNOWL foreign key (id_knowledgepoint)
      references bd_knowledgepoint (id)
go

alter table ex_examsubpaper
   add constraint FK_EX_EXAMS_REFERENCE_EX_EXAMP foreign key (id_exampaper)
      references ex_exampaper (id)
go

alter table ex_examteachingevaluation
   add constraint FK_EX_EXAMT_REFERENCE_EX_EXAMD foreign key (id_examdetail)
      references ex_examdetail (id)
go

alter table ex_examteachingevaluation
   add constraint FK_EX_EXAMT_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table ex_excludedstudent
   add constraint FK_EX_EXCLU_REFERENCE_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
go

alter table ex_excludedstudent
   add constraint FK_EX_EXCLU_REFERENCE_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
go

alter table ex_excludedstudent
   add constraint FK_EX_EXCLU_REFERENCE_BD_STUDE foreign key (id_student)
      references bd_student (id)
go

alter table ex_excludedstudent
   add constraint FK_EX_EXCLU_REFERENCE_EX_EXAM foreign key (id_exam)
      references ex_exam (id)
go

alter table ex_extendschoolgroup
   add constraint FK_EX_EXTEN_REFERENCE_EX_EXAM foreign key (id_exam)
      references ex_exam (id)
go

alter table ex_extendschoolitem
   add constraint FK_EX_EXTEN_REFERENCE_BD_COUR2 foreign key (id_course)
      references bd_course (id)
go

alter table ex_extendschoolitem
   add constraint FK_EX_EXTEN_REFERENCE_BD_GRAD3 foreign key (id_grade)
      references bd_grade (id)
go

alter table ex_extendschoolitem
   add constraint FK_EX_EXTEN_REFERENCE_EX_EXTE2 foreign key (id_extendschoolgroup)
      references ex_extendschoolgroup (id)
go

alter table ex_extendschoolscore
   add constraint FK_EX_EXTEN_REFERENCE_BD_COUR5 foreign key (id_course)
      references bd_course (id)
go

alter table ex_extendschoolscore
   add constraint FK_EX_EXTEN_REFERENCE_BD_GRAD5 foreign key (id_grade)
      references bd_grade (id)
go

alter table ex_extendschoolscore
   add constraint FK_EX_EXTEN_REFERENCE_EX_EXTE3 foreign key (id_extendschoolgroup)
      references ex_extendschoolgroup (id)
go

alter table ex_invigilator
   add constraint FK_EX_INVIG_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table ex_invigilator
   add constraint FK_EX_INVIG_REFERENCE_EX_EXAMR foreign key (id_examroom)
      references ex_examroom (id)
go

alter table ex_invigilator
   add constraint FK_EX_INVIG_REFERENCE_EX_EXAMD foreign key (id_examdetail)
      references ex_examdetail (id)
go

alter table ex_multischoolscore
   add constraint FK_EX_MULTI_REFERENCE_EX_MULT3 foreign key (id_multischoolstudent)
      references ex_multischoolstudent (id)
go

alter table ex_multischoolsource
   add constraint FK_EX_MULTI_REFERENCE_EX_EXAM foreign key (id_exam)
      references ex_exam (id)
go

alter table ex_multischoolsource
   add constraint FK_EX_MULTI_REFERENCE_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
go

alter table ex_multischoolstandard
   add constraint FK_EX_MULTI_REFERENCE_EX_MULTI foreign key (id_multischoolsource)
      references ex_multischoolsource (id)
go

alter table ex_multischoolstandard
   add constraint FK_EX_MULTI_REFERENCE_EX_TOTAL foreign key (id_totalscore)
      references ex_totalscore (id)
go

alter table ex_multischoolstandard
   add constraint FK_EX_MULTI_REFERENCE_EX_COURS foreign key (id_coursestandard)
      references ex_coursestandard (id)
go

alter table ex_multischoolstudent
   add constraint FK_EX_MULTI_REFERENCE_EX_MULT2 foreign key (id_multischoolsource)
      references ex_multischoolsource (id)
go

alter table ex_rankstandard
   add constraint FK_EX_RANKS_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table ex_rankstandard
   add constraint FK_EX_RANKS_REFERENCE_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
go

alter table ex_rankstandard
   add constraint FK_EX_RANKS_REFERENCE_EX_EXTEN foreign key (id_extendschoolgroup)
      references ex_extendschoolgroup (id)
go

alter table ex_reportsegment
   add constraint FK_EX_REPOR_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table ex_reportsegment
   add constraint FK_EX_REPOR_REFERENCE_EX_STUDE foreign key (id_studentscorereport)
      references ex_studentscorereport (id)
go

alter table ex_reportsegment
   add constraint FK_EX_REPOR_REFERENCE_EX_SCORE foreign key (id_scoresegment)
      references ex_scoresegment (id)
go

alter table ex_scoresegment
   add constraint FK_EX_SCORE_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table ex_scoresegment
   add constraint FK_EX_SCORE_REFERENCE_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
go

alter table ex_scoresegmentitem
   add constraint FK_EX_SCORE_REFERENCE_EX_SCORE foreign key (id_scoresegment)
      references ex_scoresegment (id)
go

alter table ex_studentscore
   add constraint FK_EX_STUDE_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table ex_studentscore
   add constraint FK_EX_STUDE_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table ex_studentscore
   add constraint FK_EX_STUDE_REFERENCE_BD_GRAD2 foreign key (id_grade)
      references bd_grade (id)
go

alter table ex_studentscore
   add constraint FK_EX_STUDE_REFERENCE_BD_STUDE foreign key (id_student)
      references bd_student (id)
go

alter table ex_studentscore
   add constraint FK_EX_STUDE_REFERENCE_BD_ECLA5 foreign key (id_normaleclass)
      references bd_eclass (id)
go

alter table ex_studentscore
   add constraint FK_EX_STUDE_REFERENCE_BD_ECLAS foreign key (id_leveleclass)
      references bd_eclass (id)
go

alter table ex_studentscore
   add constraint FK_EX_STUDE_REFERENCE_EX_EXAMS foreign key (id_examstudent)
      references ex_examstudent (id)
go

alter table ex_studentscore
   add constraint FK_EX_STUDE_REFERENCE_BD_SCHOO foreign key (id_school)
      references bd_school (id)
go

alter table ex_studentscore
   add constraint FK_EX_STUDE_REFERENCE_EX_EXAM foreign key (id_exam)
      references ex_exam (id)
go

alter table ex_studentscore
   add constraint FK_EX_STUDE_REFERENCE_EX_EXAMD foreign key (id_examdetail)
      references ex_examdetail (id)
go

alter table ex_studentscorereport
   add constraint FK_EX_STUDE_REFERENCE_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
go

alter table ex_subjectscore
   add constraint FK_EX_SUBJE_REFERENCE_EX_EXAMD foreign key (id_examdetail)
      references ex_examdetail (id)
go

alter table ex_subjectscore
   add constraint FK_EX_SUBJE_REFERENCE_EX_EXAM4 foreign key (id_examstudent)
      references ex_examstudent (id)
go

alter table ex_subjectscore
   add constraint FK_EX_SUBJE_REFERENCE_BD_ECLA3 foreign key (id_normaleclass)
      references bd_eclass (id)
go

alter table ex_subjectscore
   add constraint FK_EX_SUBJE_REFERENCE_BD_ECLA2 foreign key (id_leveleclass)
      references bd_eclass (id)
go

alter table ex_subjectscore
   add constraint FK_EX_SUBJE_REFERENCE_BD_STUDE foreign key (id_student)
      references bd_student (id)
go

alter table ex_subjectscore
   add constraint FK_EX_SUBJE_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table ex_subjectscore
   add constraint FK_EX_SUBJE_REFERENCE_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
go

alter table ex_subjectscore
   add constraint FK_EX_SUBJE_REFERENCE_BD_SCHOO foreign key (id_school)
      references bd_school (id)
go

alter table ex_subjectscore
   add constraint FK_EX_SUBJE_REFERENCE_EX_EXAM5 foreign key (id_examsubject)
      references ex_examsubject (id)
go

alter table ex_totalscore
   add constraint FK_EX_TOTAL_REFERENCE_BD_GRAD7 foreign key (id_grade)
      references bd_grade (id)
go

alter table ex_totalscore
   add constraint FK_EX_TOTAL_REFERENCE_EX_SCORE foreign key (id_scoresegment)
      references ex_scoresegment (id)
go

alter table ex_totalscore
   add constraint FK_EX_TOTAL_REFERENCE_EX_EXAM foreign key (id_exam)
      references ex_exam (id)
go

alter table ex_totalscorecourse
   add constraint FK_EX_TOTAL_REFERENCE_BD_COUR9 foreign key (id_course)
      references bd_course (id)
go

alter table ex_totalscorecourse
   add constraint FK_EX_TOTAL_REFERENCE_EX_TOTAL foreign key (id_totalscore)
      references ex_totalscore (id)
go

alter table ex_totalscoretemplate
   add constraint FK_EX_TOTAL_REFERENCE_BD_GRAD6 foreign key (id_grade)
      references bd_grade (id)
go

alter table ex_totalscoretemplate
   add constraint FK_EX_TOTAL_REFERENCE_EX_STAND foreign key (id_standardtemplate)
      references ex_standardtemplate (id)
go

alter table ex_totalscoretemplate
   add constraint FK_EX_TOTAL_REFERENCE_EX_SCOR2 foreign key (id_scoresegment)
      references ex_scoresegment (id)
go

alter table ex_totalscoretemplatecourse
   add constraint FK_EX_TOTAL_REFERENCE_BD_COUR7 foreign key (id_course)
      references bd_course (id)
go

alter table ex_totalscoretemplatecourse
   add constraint FK_EX_TOTAL_REFERENCE_EX_TOTA2 foreign key (id_totalscoretemplate)
      references ex_totalscoretemplate (id)
go

alter table fa_applypurchaserecord
   add constraint FK_APPLYPURCHASERECORD_ASSETSK foreign key (id_assetskind)
      references fa_assetskind (id)
go

alter table fa_applypurchaserecord
   add constraint FK_APPLYPURCHASE_DEPARTMENT foreign key (id_department)
      references bd_department (id)
go

alter table fa_applypurchaserecord
   add constraint FK_APPLYPURCHASE_USER foreign key (id_applyUser)
      references bd_user (id)
go

alter table fa_assetskind
   add constraint FK_ASSETSKIND_ASSETSKIND foreign key (id_parent)
      references fa_assetskind (id)
go

alter table fa_borrowrecord
   add constraint FK_BORROW_DEPARTMENT foreign key (id_department)
      references bd_department (id)
go

alter table fa_borrowrecord
   add constraint FK_BORROW_USER2 foreign key (id_operator)
      references bd_user (id)
go

alter table fa_borrowrecord
   add constraint FK_BORROW_USER foreign key (id_borrower)
      references bd_user (id)
go

alter table fa_fixedassets
   add constraint FK_FIXEDASSETS_ASSETSKIND foreign key (id_assetsKind)
      references fa_assetskind (id)
go

alter table fa_fixedassets
   add constraint FK_FIXEDASSETS_DEPARTMENT foreign key (id_department)
      references bd_department (id)
go

alter table fa_fixedassets
   add constraint FK_FIXEDASSETS_USER foreign key (id_user)
      references bd_user (id)
go

alter table fa_maintenancerecord
   add constraint FK_MAINTENANCE_FIXEDASSETS foreign key (id_fixedassets)
      references fa_fixedassets (id)
go

alter table fa_maintenancerecord
   add constraint FK_MAINTENANCE_USER2 foreign key (id_operator)
      references bd_user (id)
go

alter table fa_maintenancerecord
   add constraint FK_MAINTENANCE_USER foreign key (id_user)
      references bd_user (id)
go

alter table fa_reducerecord
   add constraint FK_REDUCE_FIXEDASSETS foreign key (id_fixedassets)
      references fa_fixedassets (id)
go

alter table fa_reducerecord
   add constraint FK_REDUCE_USER foreign key (id_operator)
      references bd_user (id)
go

alter table fa_transferrecord
   add constraint FK_TRANSFER_FIXEDASSETS foreign key (id_fixedassets)
      references fa_fixedassets (id)
go

alter table fa_transferrecord
   add constraint FK_TRANSFER_USER2 foreign key (id_custodian)
      references bd_user (id)
go

alter table fa_transferrecord
   add constraint FK_TRANSFER_USER foreign key (id_user)
      references bd_user (id)
go

alter table fe_collectionsurplus
   add constraint FK_FE_COLLE_REFERENCE_BD_SCHOO foreign key (id_schoolyear)
      references bd_schoolyear (id)
go

alter table fe_collectionsurplus
   add constraint FK_FE_COLLE_REFERENCE_BD_STUDE foreign key (id_student)
      references bd_student (id)
go

alter table fe_collectionsurplus
   add constraint FK_FE_COLLE_REFERENCE_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
go

alter table fe_collectionsurplus
   add constraint FK_FE_COLLE_REFERENCE_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
go

alter table fe_feesrecord
   add constraint Reference_722 foreign key (id_invalidUser)
      references bd_user (id)
go

alter table fe_feesrecord
   add constraint Reference_723 foreign key (id_operator)
      references bd_user (id)
go

alter table fe_feesrecord
   add constraint Reference_724 foreign key (id_checkUser)
      references bd_user (id)
go

alter table fe_feesrecord
   add constraint Reference_725 foreign key (id_payee)
      references bd_user (id)
go

alter table fe_feesrecord
   add constraint FK_FE_FEESR_REFERENCE_FE_FEESS foreign key (id_feesstandard)
      references fe_feesstandard (id)
go

alter table fe_feesstandard
   add constraint Reference_717 foreign key (id_receivablesCheckUser)
      references bd_user (id)
go

alter table fe_feesstandard
   add constraint Reference_718 foreign key (id_receivablesSettingUser)
      references bd_user (id)
go

alter table fe_feesstandard
   add constraint Reference_719 foreign key (id_retreatCheckUser)
      references bd_user (id)
go

alter table fe_feesstandard
   add constraint Reference_720 foreign key (id_remissionSettingUser)
      references bd_user (id)
go

alter table fe_feesstandard
   add constraint Reference_721 foreign key (id_retreatSettingUser)
      references bd_user (id)
go

alter table fe_feesstandard
   add constraint Reference_728 foreign key (id_student)
      references bd_student (id)
go

alter table fe_feesstandard
   add constraint Reference_729 foreign key (id_studentregistration)
      references bd_studentregistration (id)
go

alter table fe_feesstandard
   add constraint FK_FE_FEESS_REFERENCE_BD_SCHOO foreign key (id_schoolyear)
      references bd_schoolyear (id)
go

alter table fe_feesstandard
   add constraint FK_FE_FEESS_REFERENCE_FE_GRADE foreign key (id_gradefeesstandard)
      references fe_gradefeesstandard (id)
go

alter table fe_summercamplist
   add constraint FK_FE_SUMME_REFERENCE_FE_FEESS foreign key (id_feesstandard)
      references fe_feesstandard (id)
go

alter table fi_salary
   add constraint FK_FI_SALAR_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table fi_salaryamount
   add constraint Reference_364 foreign key (id_salary)
      references fi_salary (id)
go

alter table fi_salaryamount
   add constraint Reference_365 foreign key (id_salaryitem)
      references fi_salaryitem (id)
go

alter table fi_salaryitem
   add constraint Reference_363 foreign key (id_parent)
      references fi_salaryitem (id)
go

alter table fi_wage
   add constraint FK_FI_WAGE_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table il_learningtask
   add constraint FK_IL_LEARN_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table il_learningtask
   add constraint FK_IL_LEARN_REFERENCE_BD_TESTP foreign key (id_testpaper)
      references bd_testpaper (id)
go

alter table il_learningtask
   add constraint FK_IL_LEARN_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table il_learningtask
   add constraint FK_IL_LEARN_REFERENCE_BD_SCHOO foreign key (id_schoolterm)
      references bd_schoolterm (id)
go

alter table il_learningtaskeclass
   add constraint Reference_882 foreign key (id_learningtask)
      references il_learningtask (id)
go

alter table il_learningtaskeclass
   add constraint Reference_884 foreign key (id_eclass)
      references bd_eclass (id)
go

alter table il_learningtaskstudent
   add constraint Reference_883 foreign key (id_learningtask)
      references il_learningtask (id)
go

alter table il_learningtaskstudent
   add constraint Reference_887 foreign key (id_eclass)
      references bd_eclass (id)
go

alter table il_learningtaskstudent
   add constraint FK_IL_LEARN_REFERENCE_BD_STUDE foreign key (id_student)
      references bd_student (id)
go

alter table il_studenttaskresult
   add constraint FK_IL_STUDE_REFERENCE_IL_LEARN foreign key (id_learningtaskstudent)
      references il_learningtaskstudent (id)
go

alter table il_studenttaskresult
   add constraint FK_IL_STUDE_REFERENCE_BD_TESTP foreign key (id_testpaperquestion)
      references bd_testpaperquestion (id)
go

alter table im_eclassrule
   add constraint FK_IM_ECLAS_REFERENCE_IM_SCHED foreign key (id_scheduledetail)
      references im_scheduledetail (id)
go

alter table im_eclassrule
   add constraint FK_IM_ECLAS_REFERENCE_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
go

alter table im_managementschedule
   add constraint Reference_575 foreign key (id_school)
      references bd_school (id)
go

alter table im_managementschedule
   add constraint Reference_576 foreign key (id_schoolyear)
      references bd_schoolyear (id)
go

alter table im_managementschedule
   add constraint Reference_577 foreign key (id_schoolterm)
      references bd_schoolterm (id)
go

alter table im_schedulearrangement
   add constraint Reference_583 foreign key (id_managementItem)
      references im_managementitem (id)
go

alter table im_schedulearrangement
   add constraint FK_IM_SCHED_REFERENCE_IM_SCHED foreign key (id_scheduledetail)
      references im_scheduledetail (id)
go

alter table im_scheduledetail
   add constraint Reference_580 foreign key (id_managementSchedule)
      references im_managementschedule (id)
go

alter table im_studentappointment
   add constraint FK_IM_STUDE_REFERENCE_BD_STUDE foreign key (id_student)
      references bd_student (id)
go

alter table im_studentappointment
   add constraint Reference_594 foreign key (id_school)
      references bd_school (id)
go

alter table im_studentappointment
   add constraint Reference_595 foreign key (id_schoolyear)
      references bd_schoolyear (id)
go

alter table im_studentappointment
   add constraint Reference_596 foreign key (id_schoolterm)
      references bd_schoolterm (id)
go

alter table im_studentappointment
   add constraint FK_IM_STUDE_REFERENCE_IM_SCHED foreign key (id_scheduleDetail)
      references im_scheduledetail (id)
go

alter table im_studentappointment
   add constraint FK_IM_STUDE_REFERENCE_IM_MANAG foreign key (id_managementItem)
      references im_managementitem (id)
go

alter table im_studentappointment
   add constraint FK_IM_STUDE_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table im_studentappointment
   add constraint FK_IM_STUDE_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table im_studentappointment
   add constraint FK_IM_STUDE_REFERENCE_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
go

alter table im_teacherappointment
   add constraint FK_IM_TEACH_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table im_teacherappointment
   add constraint Reference_586 foreign key (id_schoolyear)
      references bd_schoolyear (id)
go

alter table im_teacherappointment
   add constraint Reference_587 foreign key (id_schoolterm)
      references bd_schoolterm (id)
go

alter table im_teacherappointment
   add constraint Reference_588 foreign key (id_school)
      references bd_school (id)
go

alter table im_teacherappointment
   add constraint FK_IM_TEACH_REFERENCE_IM_SCHED foreign key (id_scheduleDetail)
      references im_scheduledetail (id)
go

alter table im_teacherappointment
   add constraint FK_IM_TEACH_REFERENCE_IM_MANAG foreign key (id_managementItem)
      references im_managementitem (id)
go

alter table im_teacherappointment
   add constraint FK_IM_TEACH_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table im_tutorstudent
   add constraint FK_IM_TUTOR_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table im_tutorstudent
   add constraint FK_IM_TUTOR_REFERENCE_BD_STUDE foreign key (id_student)
      references bd_student (id)
go

alter table la_applyinstrument
   add constraint Reference_13 foreign key (id_instrumentform)
      references la_instrumentform (id)
go

alter table la_applyinstrument
   add constraint Reference_353 foreign key (id_instrument)
      references la_instrument (id)
go

alter table la_experiment
   add constraint Reference_685 foreign key (id_course)
      references bd_course (id)
go

alter table la_experiment
   add constraint FK_LA_EXPER_REFERENCE_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
go

alter table la_experimentalcourses
   add constraint FK_LA_EXPERCOURSE_REFERENCE_LA_LAB foreign key (id_lab)
      references la_lab (id)
go

alter table la_experimentalcourses
   add constraint Reference_684 foreign key (id_course)
      references bd_course (id)
go

alter table la_experimentalteachers
   add constraint FK_LA_EXPER_REFERENCE_LA_LAB foreign key (id_lab)
      references la_lab (id)
go

alter table la_experimentalteachers
   add constraint FK_LA_EXPER_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table la_instrument
   add constraint FK_LA_INSTR_REFERENCE_LA_INSTR foreign key (id_instrumentType)
      references la_instrumenttype (id)
go

alter table la_instrument
   add constraint Reference_691 foreign key (id_course)
      references bd_course (id)
go

alter table la_instrumentform
   add constraint FK_LA_INSTR_REFERENCE_LA_EXPER foreign key (id_experiment)
      references la_experiment (id)
go

alter table la_instrumentform
   add constraint Reference_692 foreign key (id_course)
      references bd_course (id)
go

alter table la_instrumentform
   add constraint Reference_693 foreign key (id_schoolterm)
      references bd_schoolterm (id)
go

alter table la_instrumentform
   add constraint Reference_694 foreign key (id_fillingFormTeacher)
      references bd_teacher (id)
go

alter table la_instrumentform
   add constraint Reference_695 foreign key (id_signFormTeacher)
      references bd_teacher (id)
go

alter table la_instrumentform
   add constraint FK_LA_INSTR_REFERENCE_BD_SCHOO foreign key (id_school)
      references bd_school (id)
go

alter table la_instrumentformeclass
   add constraint FK_LA_INSTR_REFERENCE_LA_ECLASS foreign key (id_instrumentform)
      references la_instrumentform (id)
go

alter table la_instrumentformeclass
   add constraint FK_LA_INSTR_REFERENCE_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
go

alter table la_instrumentformexperteacher
   add constraint FK_LA_INSTR_REFERENCE_LA_EXPERTEA foreign key (id_instrumentform)
      references la_instrumentform (id)
go

alter table la_instrumentformexperteacher
   add constraint Reference_689 foreign key (id_teacher)
      references bd_teacher (id)
go

alter table la_instrumentformteacher
   add constraint FK_LA_INSTR_REFERENCE_LA_TEACHER foreign key (id_instrumentform)
      references la_instrumentform (id)
go

alter table la_instrumentformteacher
   add constraint Reference_688 foreign key (id_teacher)
      references bd_teacher (id)
go

alter table la_lab
   add constraint FK_LA_LAB_REFERENCE_BD_SCHOO foreign key (id_school)
      references bd_school (id)
go

alter table la_reportedLossinstrument
   add constraint FK_LA_REPOR_REFERENCE_LA_REPORTED foreign key (id_instrumentform)
      references la_instrumentform (id)
go

alter table la_reportedLossinstrument
   add constraint FK_LA_REPOR_REFERENCE_LA_INSTR foreign key (id_instrument)
      references la_instrument (id)
go

alter table la_reportedLossinstrument
   add constraint FK_LA_REPOR_REFERENCE_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
go

alter table lu_activitydetail
   add constraint FK_LU_ACTIV_REFERENCE_LU_ACTIV foreign key (id_activityType)
      references lu_activitytype (id)
go

alter table lu_partyinfo
   add constraint FK_LU_PARTY_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table ma_materials
   add constraint FK_MA_MATER_REFERENCE_BD_USER4 foreign key (id_approvaluser)
      references bd_user (id)
go

alter table ma_materials
   add constraint FK_MA_MATER_REFERENCE_MA_MATE4 foreign key (id_materialskind)
      references ma_materialskind (id)
go

alter table ma_materials
   add constraint FK_MA_MATER_REFERENCE_MA_MATE5 foreign key (id_materialshandinuser)
      references ma_materialshandinuser (id)
go

alter table ma_materialsapprovaluser
   add constraint FK_MA_MATER_REFERENCE_BD_USER2 foreign key (id_user)
      references bd_user (id)
go

alter table ma_materialsapprovaluser
   add constraint FK_MA_MATER_REFERENCE_MA_MATE3 foreign key (id_materialskind)
      references ma_materialskind (id)
go

alter table ma_materialsapprovaluser
   add constraint FK_MA_MATER_REFERENCE_MA_MATE1 foreign key (id_materialsnotice)
      references ma_materialsnotice (id)
go

alter table ma_materialshandinuser
   add constraint FK_MA_MATER_REFERENCE_BD_USER3 foreign key (id_user)
      references bd_user (id)
go

alter table ma_materialshandinuser
   add constraint FK_MA_MATER_REFERENCE_MA_MATE2 foreign key (id_materialsnotice)
      references ma_materialsnotice (id)
go

alter table ma_materialskind
   add constraint FK_MA_MATER_REFERENCE_MA_MATE6 foreign key (id_parent)
      references ma_materialskind (id)
go

alter table ma_materialsnotice
   add constraint FK_MA_MATER_REFERENCE_BD_DEPAR foreign key (id_department)
      references bd_department (id)
go

alter table ma_materialsnotice
   add constraint FK_MA_MATER_REFERENCE_BD_USER1 foreign key (id_user)
      references bd_user (id)
go

alter table ma_viewrecord
   add constraint FK_MA_VIEWR_REFERENCE_MA_MATER foreign key (id_materials)
      references ma_materials (id)
go

alter table ma_viewrecord
   add constraint FK_MA_VIEWR_REFERENCE_FW_ATTAC foreign key (id_attachment)
      references fw_attachment (id)
go

alter table ma_viewrecord
   add constraint FK_MA_VIEWR_REFERENCE_BD_USER foreign key (id_viewUser)
      references bd_user (id)
go

alter table ma_viewuser
   add constraint FK_MA_VIEWU_REFERENCE_MA_MATER foreign key (id_materialsnotice)
      references ma_materialsnotice (id)
go

alter table ma_viewuser
   add constraint FK_MA_VIEWU_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table mc_checkrecord
   add constraint Reference_83 foreign key (id_subitemdetail)
      references mc_subitemdetail (id)
go

alter table mc_checkrecord
   add constraint Reference_84 foreign key (id_eclass)
      references bd_eclass (id)
go

alter table mc_checkrecord
   add constraint FK_MC_CHECK_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table mc_mainstandard
   add constraint Reference_78 foreign key (id_schoolterm)
      references bd_schoolterm (id)
go

alter table mc_mainstandard
   add constraint Reference_86 foreign key (id_ranksign)
      references mc_ranksign (id)
go

alter table mc_mainstandardschoolgrade
   add constraint Reference_90 foreign key (id_mainstandard)
      references mc_mainstandard (id)
go

alter table mc_mainstandardschoolgrade
   add constraint FK_MC_MAINS_REFERENCE_BD_SCHOO foreign key (id_schoolgrade)
      references bd_schoolgrade (id)
go

alter table mc_recorduser
   add constraint Reference_94 foreign key (id_subitemstandard)
      references mc_subitemstandard (id)
go

alter table mc_recorduser
   add constraint FK_MC_RECOR_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table mc_relatedstudent
   add constraint Reference_88 foreign key (id_checkrecord)
      references mc_checkrecord (id)
go

alter table mc_relatedstudent
   add constraint Reference_89 foreign key (id_student)
      references bd_student (id)
go

alter table mc_scorestandard
   add constraint Reference_85 foreign key (id_subitemdetail)
      references mc_subitemdetail (id)
go

alter table mc_subitemdetail
   add constraint Reference_81 foreign key (id_subitemstandard)
      references mc_subitemstandard (id)
go

alter table mc_subitemdetail
   add constraint Reference_82 foreign key (id_subitemtype)
      references mc_subitemtype (id)
go

alter table mc_subitemstandard
   add constraint Reference_79 foreign key (id_mainstandard)
      references mc_mainstandard (id)
go

alter table mc_subitemstandard
   add constraint Reference_87 foreign key (id_ranksign)
      references mc_ranksign (id)
go

alter table mc_subitemstandardschoolgrade
   add constraint Reference_92 foreign key (id_subitemstandard)
      references mc_subitemstandard (id)
go

alter table mc_subitemstandardschoolgrade
   add constraint FK_MC_SUBIT_REFERENCE_BD_SCHOO foreign key (id_schoolgrade)
      references bd_schoolgrade (id)
go

alter table mc_subitemtype
   add constraint Reference_80 foreign key (id_subitemstandard)
      references mc_subitemstandard (id)
go

alter table me_memo
   add constraint FK_ME_MEMO_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table me_memomemotemplate
   add constraint FK_MEMOMEMOTEMPL_MEMOTEMPL foreign key (id_memotemplate)
      references me_memotemplate (id)
go

alter table me_memomemotemplate
   add constraint FK_SP_MEMOMEMOTEMPLATE_SP_MEMO foreign key (id_memo)
      references me_memo (id)
go

alter table mh_dinnertime
   add constraint FK_MH_DINNE_REFERENCE_MH_MESSH foreign key (id_messhall)
      references mh_messhall (id)
go

alter table mh_menudish
   add constraint FK_MH_MENUD_REFERENCE_MH_MENU foreign key (id_menu)
      references mh_menu (id)
go

alter table mh_menudish
   add constraint FK_MH_MENUD_REFERENCE_MH_DISH foreign key (id_dish)
      references mh_dish (id)
go

alter table mh_messhallmenu
   add constraint FK_MH_MESSH_REFERENCE_MH_MESSH foreign key (id_messhall)
      references mh_messhall (id)
go

alter table mh_messhallmenudish
   add constraint Reference_437 foreign key (id_messhall)
      references mh_messhallmenu (id)
go

alter table mh_messhallmenudish
   add constraint FK_MH_MESSH_REFERENCE_MH_DISH foreign key (id_dish)
      references mh_dish (id)
go

alter table mh_order
   add constraint FK_MH_ORDER_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table mh_orderdish
   add constraint FK_MH_ORDER_REFERENCE_MH_DISH foreign key (id_dish)
      references mh_dish (id)
go

alter table mh_orderdish
   add constraint FK_MH_ORDER_REFERENCE_MH_ORDER foreign key (id_order)
      references mh_order (id)
go

alter table mh_studentorder
   add constraint FK_MH_STUDE_REFERENCE_MH_MESSH foreign key (id_messhall)
      references mh_messhall (id)
go

alter table mh_studentorder
   add constraint FK_MH_STUDE_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table mh_stuentmenudish
   add constraint FK_MH_STUEN_REFERENCE_MH_STUDE foreign key (id_studentorder)
      references mh_studentorder (id)
go

alter table mh_stuentmenudish
   add constraint FK_MH_STUEN_REFERENCE_MH_DISH foreign key (id_dish)
      references mh_dish (id)
go

alter table mo_mood
   add constraint FK_MO_MOOD_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table mo_moodmoodkind
   add constraint FK_SP_MOODMOODCATEGORY_SP_MOOD foreign key (id_mood)
      references mo_mood (id)
go

alter table mo_moodmoodkind
   add constraint FK_MOODMOODCATEG_MOODCATEG foreign key (id_moodkind)
      references mo_moodkind (id)
go

alter table no_kindsetting
   add constraint FK_NO_KINDS_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table no_notice
   add constraint FK_NO_NOTIC_REFERENCE_BD_USE2 foreign key (id_user)
      references bd_user (id)
go

alter table no_notice
   add constraint FK_NO_NOTIC_REFERENCE_BD_DEPAR foreign key (id_department)
      references bd_department (id)
go

alter table no_noticereply
   add constraint FK_NO_NOTIC_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table no_noticereply
   add constraint FK_NO_NOTIC_REFERENCE_NO_RECEI foreign key (id_receivenotice)
      references no_receivenotice (id)
go

alter table no_receivenotice
   add constraint FK_NO_RECEI_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table no_receivenotice
   add constraint FK_NO_RECEI_REFERENCE_BD_DEPAR foreign key (id_department)
      references bd_department (id)
go

alter table no_receivenotice
   add constraint FK_NO_RECEI_REFERENCE_NO_NOTIC foreign key (id_notice)
      references no_notice (id)
go

alter table ns_entrancetestcourse
   add constraint FK_NS_ENTRA_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table ns_entrancetestscore
   add constraint FK_NS_ENTRA_REFERENCE_BD_STUDE foreign key (id_studentregistration)
      references bd_studentregistration (id)
go

alter table ns_entrancetestscore
   add constraint FK_NS_ENTRA_REFERENCE_NS_ENTRA foreign key (id_entrancetestcourse)
      references ns_entrancetestcourse (id)
go

alter table od_documentauditor
   add constraint FK_OD_DOCUM_REFERENCE_BD_USER foreign key (id_auditor)
      references bd_user (id)
go

alter table od_documentauditor
   add constraint FK_OD_DOCUM_REFERENCE_OD_OFFI2 foreign key (id_officialdocument)
      references od_officialdocument (id)
go

alter table od_documentreceive
   add constraint FK_OD_DOCUM_REFERENCE_BD_USE1 foreign key (id_retransmitor)
      references bd_user (id)
go

alter table od_documentreceive
   add constraint FK_OD_DOCUM_REFERENCE_BD_USE2 foreign key (id_receiver)
      references bd_user (id)
go

alter table od_documentreceive
   add constraint FK_OD_DOCUM_REFERENCE_OD_OFFIC foreign key (id_officialdocument)
      references od_officialdocument (id)
go

alter table od_officialdocument
   add constraint FK_OD_OFFIC_REFERENCE_BD_USER foreign key (id_registrant)
      references bd_user (id)
go

alter table ol_learninganswer
   add constraint FK_OL_LEARNINGANSWER_LEARNINGSUBJECT foreign key (id_learningsubject)
      references ol_learningsubject (id)
go

alter table ol_learninganswer
   add constraint Reference_627 foreign key (id_teacher)
      references bd_teacher (id)
go

alter table ol_learninganswer
   add constraint Reference_628 foreign key (id_student)
      references bd_student (id)
go

alter table ol_learninganswer
   add constraint Reference_629 foreign key (id_responsibleStudent)
      references bd_student (id)
go

alter table ol_learningsubject
   add constraint FK_OL_LEARNINGSUBJECT_LEARNINGTYPE foreign key (id_learningtype)
      references ol_learningtype (id)
go

alter table ol_learningsubject
   add constraint Reference_624 foreign key (id_teacher)
      references bd_teacher (id)
go

alter table ol_learningsubject
   add constraint Reference_625 foreign key (id_student)
      references bd_student (id)
go

alter table ol_learningsubjectclass
   add constraint FK_OL_LEARNINGSUBJECTCLASS_LEARNINGSUBJECT foreign key (id_learningsubject)
      references ol_learningsubject (id)
go

alter table ol_learningsubjectclass
   add constraint FK_OL_LEARN_REFERENCE_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
go

alter table pl_annotation
   add constraint FK_PL_ANNOT_REFERENCE_PL_TEACH foreign key (id_teachingplan)
      references pl_teachingplan (id)
go

alter table pl_curriculumstandard
   add constraint FK_PL_CURRI_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table pl_floweruser
   add constraint FK_PL_FLOWE_REFERENCE_PL_TEACH foreign key (id_teachingplan)
      references pl_teachingplan (id)
go

alter table pl_floweruser
   add constraint FK_PL_FLOWE_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table pl_mainprepare
   add constraint Reference_1005 foreign key (id_teachingmaterial)
      references bd_teachingmaterial (id)
go

alter table pl_mainprepare
   add constraint FK_PL_MAINP_REFERENCE_PL_TEACH foreign key (id_teachingsetting)
      references pl_teachingsetting (id)
go

alter table pl_mainprepare
   add constraint FK_PL_MAINP_REFERENCE_BD_TEACH foreign key (id_unit)
      references bd_teachingunit (id)
go

alter table pl_mainprepare
   add constraint FK_PL_MAINP_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table pl_plancontent
   add constraint FK_PL_PLANC_REFERENCE_PL_TEACH foreign key (id_teachingplan)
      references pl_teachingplan (id)
go

alter table pl_plcourseware
   add constraint FK_PL_PLCOU_REFERENCE_PL_TEACH foreign key (id_teachingplan)
      references pl_teachingplan (id)
go

alter table pl_plknowledge
   add constraint FK_PL_PLKNO_REFERENCE_PL_TEACH foreign key (id_teachingplan)
      references pl_teachingplan (id)
go

alter table pl_plknowledge
   add constraint FK_PL_PLKNO_REFERENCE_BD_KNOWL foreign key (id_knowledge)
      references bd_knowledgepoint (id)
go

alter table pl_plotherresource
   add constraint FK_PL_PLOTH_REFERENCE_PL_TEACH foreign key (id_teachingplan)
      references pl_teachingplan (id)
go

alter table pl_plwork
   add constraint FK_PL_PLWOR_REFERENCE_PL_TEACH foreign key (id_teachingplan)
      references pl_teachingplan (id)
go

alter table pl_practice
   add constraint FK_PL_PRACT_REFERENCE_PL_TEACH foreign key (id_teachingplan)
      references pl_teachingplan (id)
go

alter table pl_shareuser
   add constraint FK_PL_SHARE_REFERENCE_PL_TEACH foreign key (id_teachingplan)
      references pl_teachingplan (id)
go

alter table pl_shareuser
   add constraint FK_PL_SHARE_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table pl_standardgrade
   add constraint FK_PL_STAND_REFERENCE_PL_CURRI foreign key (id_curriculumstandard)
      references pl_curriculumstandard (id)
go

alter table pl_standardgrade
   add constraint FK_PL_STAND_REFERENCE_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
go

alter table pl_teachingplan
   add constraint FK_PL_TEACH_REFERENCE_BD_USE2 foreign key (id_excellentUser)
      references bd_user (id)
go

alter table pl_teachingplan
   add constraint FK_PL_TEACH_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table pl_teachingplan
   add constraint FK_PL_TEACH_REFERENCE_BD_TEACH foreign key (id_teachingmaterial)
      references bd_teachingmaterial (id)
go

alter table pl_teachingplan
   add constraint FK_PL_TEACH_REFERENCE_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
go

alter table pl_teachingplan
   add constraint FK_PL_TEACH_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table pl_teachingplan
   add constraint Reference_964 foreign key (id_teachingunitsubject)
      references bd_teachingunitsubject (id)
go

alter table pl_teachingplan
   add constraint Reference_965 foreign key (id_teachingunit)
      references bd_teachingunit (id)
go

alter table pl_teachingplan
   add constraint Reference_966 foreign key (id_examiner)
      references bd_user (id)
go

alter table pl_teachingsetting
   add constraint Reference_967 foreign key (id_grade)
      references bd_grade (id)
go

alter table pl_teachingsetting
   add constraint Reference_968 foreign key (id_course)
      references bd_course (id)
go

alter table pl_teachingsetting
   add constraint Reference_969 foreign key (id_teachingmaterial)
      references bd_teachingmaterial (id)
go

alter table pl_teachingtemplate
   add constraint FK_PL_TEACH_REFERENCE_PL_TEACH foreign key (id_teachingsetting)
      references pl_teachingsetting (id)
go

alter table ps_appraiser
   add constraint FK_PS_APPRA_REFERENCE_PS_HABIT foreign key (id_habit)
      references ps_habit (id)
go

alter table ps_coursedecomposition
   add constraint FK_PS_COURS_REFERENCE_PS_HABIT foreign key (id_habit)
      references ps_habit (id)
go

alter table ps_coursedecomposition
   add constraint FK_PS_COURS_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table ps_coursedecompositionplan
   add constraint FK_PS_COURS_REFERENCE_PS_GRADE foreign key (id_gradeplan)
      references ps_gradeplan (id)
go

alter table ps_coursedecompositionplan
   add constraint FK_PS_COURS_REFERENCE_PS_COURS foreign key (id_coursedecomposition)
      references ps_coursedecomposition (id)
go

alter table ps_cultivatedetail
   add constraint FK_PS_CULTI_REFERENCE_PS_HABIT foreign key (id_habit)
      references ps_habit (id)
go

alter table ps_cultivatedetail
   add constraint FK_PS_CULTI_REFERENCE_BD_STUDE foreign key (id_student)
      references bd_student (id)
go

alter table ps_cultivatedetail
   add constraint FK_PS_CULTI_REFERENCE_BD_SCHOO foreign key (id_schoolterm)
      references bd_schoolterm (id)
go

alter table ps_cultivatedetail
   add constraint Reference_657 foreign key (id_appraiser)
      references bd_user (id)
go

alter table ps_cultivatedetail
   add constraint Reference_658 foreign key (id_applicant)
      references bd_user (id)
go

alter table ps_cultivatedetail
   add constraint Reference_659 foreign key (id_auditor)
      references bd_user (id)
go

alter table ps_emphasis
   add constraint FK_PS_EMPHA_REFERENCE_PS_HABIT foreign key (id_habit)
      references ps_habit (id)
go

alter table ps_emphasis
   add constraint FK_PS_EMPHA_REFERENCE_BD_ECLAS foreign key (id_grade)
      references bd_eclass (id)
go

alter table ps_emphasis
   add constraint FK_PS_EMPHA_REFERENCE_BD_STUDE foreign key (id_student)
      references bd_student (id)
go

alter table ps_gradedecomposition
   add constraint FK_PS_GRADE_REFERENCE_PS_HABIT foreign key (id_habit)
      references ps_habit (id)
go

alter table ps_gradedecomposition
   add constraint Reference_652 foreign key (id_grade)
      references bd_grade (id)
go

alter table ps_gradeindicatorplan
   add constraint FK_PE_gradeIndicatorPlan_REFERENCE_PE_gradePlan foreign key (id_gradePlan)
      references ps_gradeplan (id)
go

alter table ps_gradeindicatorplan
   add constraint FK_PE_gradeIndicatorPlan_REFERENCE_PE_gradeDecomposition foreign key (id_gradeDecomposition)
      references ps_gradedecomposition (id)
go

alter table ps_gradeplan
   add constraint FK_PS_GRADE_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table ps_gradeplan
   add constraint Reference_650 foreign key (id_grade)
      references bd_grade (id)
go

alter table ps_gradeplan
   add constraint FK_PS_GRADE_REFERENCE_BD_SCHOO foreign key (id_schoolterm)
      references bd_schoolterm (id)
go

alter table ps_teachplan
   add constraint FK_PS_TEACH_REFERENCE_PS_COURS foreign key (id_coursedecompositionplan)
      references ps_coursedecompositionplan (id)
go

alter table ps_teachplan
   add constraint FK_PS_TEACH_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table pu_publicity
   add constraint FK_PU_PUBLI_REFERENCE_BD_USER1 foreign key (id_publishuser)
      references bd_user (id)
go

alter table pu_publicity
   add constraint FK_PU_PUBLI_REFERENCE_BD_USER2 foreign key (id_dealuser)
      references bd_user (id)
go

alter table pu_publicity
   add constraint FK_PU_PUBLI_REFERENCE_BD_DEPAR foreign key (id_department)
      references bd_department (id)
go

alter table pu_publicityapproval
   add constraint FK_PU_PUBLI_REFERENCE_PU_PUBL2 foreign key (id_publicity)
      references pu_publicity (id)
go

alter table pu_publicityapproval
   add constraint FK_PU_PUBLI_REFERENCE_BD_USER3 foreign key (id_user)
      references bd_user (id)
go

alter table pu_publicityfeedback
   add constraint FK_PU_PUBLI_REFERENCE_PU_PUBL1 foreign key (id_publicity)
      references pu_publicity (id)
go

alter table pu_publicityfeedback
   add constraint FK_PU_PUBLI_REFERENCE_BD_USER4 foreign key (id_user)
      references bd_user (id)
go

alter table qn_option
   add constraint FK_QN_OPTIO_REFERENCE_QN_QUEST foreign key (id_question)
      references qn_question (id)
go

alter table qn_optionresults
   add constraint FK_QN_OPTIO_REFERENCE_QN_QUES9 foreign key (id_questionresults)
      references qn_questionresults (id)
go

alter table qn_optionresults
   add constraint FK_QN_OPTIO_REFERENCE_QN_OPTIO foreign key (id_option)
      references qn_option (id)
go

alter table qn_participatenaire
   add constraint FK_QN_PARTI_REFERENCE_BD_SCHO1 foreign key (id_participantschool)
      references bd_school (id)
go

alter table qn_participatenaire
   add constraint FK_QN_PARTI_REFERENCE_BD_USE1 foreign key (id_participant)
      references bd_user (id)
go

alter table qn_participatenaire
   add constraint FK_QN_PARTI_REFERENCE_BD_GRAD2 foreign key (id_participantgrade)
      references bd_grade (id)
go

alter table qn_participatenaire
   add constraint FK_QN_PARTI_REFERENCE_BD_ECLA3 foreign key (id_participanteclass)
      references bd_eclass (id)
go

alter table qn_participatenaire
   add constraint FK_QN_PARTI_REFERENCE_BD_USER foreign key (id_evaluateduser)
      references bd_user (id)
go

alter table qn_participatenaire
   add constraint FK_QN_PARTI_REFERENCE_BD_ECLA4 foreign key (id_evaluatedeclass)
      references bd_eclass (id)
go

alter table qn_participatenaire
   add constraint FK_QN_PARTI_REFERENCE_BD_GRAD5 foreign key (id_evaluatedgrade)
      references bd_grade (id)
go

alter table qn_participatenaire
   add constraint FK_QN_PARTI_REFERENCE_BD_SCHOO foreign key (id_evaluatedschool)
      references bd_school (id)
go

alter table qn_participatenaire
   add constraint FK_QN_PARTI_REFERENCE_BD_COURS foreign key (id_evaluatedcourse)
      references bd_course (id)
go

alter table qn_participatenaire
   add constraint FK_QN_PARTI_REFERENCE_QN_QUEST foreign key (id_questionnaire)
      references qn_questionnaire (id)
go

alter table qn_question
   add constraint FK_QN_QUEST_REFERENCE_QN_QUES7 foreign key (id_parent)
      references qn_question (id)
go

alter table qn_question
   add constraint FK_QN_QUEST_REFERENCE_QN_QUES6 foreign key (id_questionnaire)
      references qn_questionnaire (id)
go

alter table qn_questionnaire
   add constraint FK_QN_QUEST_REFERENCE_BD_SCHOO foreign key (id_schoolTerm)
      references bd_schoolterm (id)
go

alter table qn_questionnaire
   add constraint FK_QN_QUEST_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table qn_questionresults
   add constraint FK_QN_QUEST_REFERENCE_QN_PARTI foreign key (id_participatenaire)
      references qn_participatenaire (id)
go

alter table qn_questionresults
   add constraint FK_QN_QUEST_REFERENCE_QN_QUES8 foreign key (id_question)
      references qn_question (id)
go

alter table re_goodsconsume
   add constraint FK_RE_GOODS_REFERENCE_RE_REPAI foreign key (id_repairrecord)
      references re_repairsrecord (id)
go

alter table re_goodsconsume
   add constraint FK_RE_GOODS_REFERENCE_RE_EARLY foreign key (id_earlywarningsetting)
      references re_earlywarningsetting (id)
go

alter table re_maintanancestaff
   add constraint FK_RE_MAINT_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table re_maintanancestaff
   add constraint FK_RE_MAINT_REFERENCE_RE_EARLY foreign key (id_earlywarningsetting)
      references re_earlywarningsetting (id)
go

alter table re_repairsrecord
   add constraint FK_RE_REPAI_REFERENCE_RE_MAINT foreign key (id_maintanancestaff)
      references re_maintanancestaff (id)
go

alter table re_repairsrecord
   add constraint Reference_755 foreign key (id_user)
      references bd_user (id)
go

alter table re_repairsrecord
   add constraint Reference_756 foreign key (id_assigner)
      references bd_user (id)
go

alter table re_repairsrecord
   add constraint FK_RE_REPAI_REFERENCE_RE_EARLY foreign key (id_earlywarningsetting)
      references re_earlywarningsetting (id)
go

alter table re_repairsrecord
   add constraint FK_RE_REPAI_REFERENCE_BD_USER foreign key (id_auditor)
      references bd_user (id)
go

alter table rs_activityuser
   add constraint FK_RS_ACTIV_REFERENCE_RS_RESEA foreign key (id_researchstudyactivity)
      references rs_researchstudyactivity (id)
go

alter table rs_activityuser
   add constraint FK_RS_ACTIV_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table rs_researchstudyactivity
   add constraint Reference_780 foreign key (id_user)
      references bd_user (id)
go

alter table rs_researchstudyactivity
   add constraint FK_RS_RESEA_REFERENCE_BD_SCHOO foreign key (id_schoolterm)
      references bd_schoolterm (id)
go

alter table rs_researchstudyissue
   add constraint Reference_433 foreign key (id_researchstudyactivity)
      references rs_researchstudyactivity (id)
go

alter table rs_researchstudyissue
   add constraint Reference_784 foreign key (id_user)
      references bd_user (id)
go

alter table rs_researchstudymember
   add constraint Reference_434 foreign key (id_researchstudyissue)
      references rs_researchstudyissue (id)
go

alter table rs_researchstudymember
   add constraint Reference_783 foreign key (id_user)
      references bd_user (id)
go

alter table sa_assessmentitem
   add constraint FK_SA_ASSES_REFERENCE_SA_ASSE7 foreign key (id_assessmentkind)
      references sa_assessmentkind (id)
go

alter table sa_assessmentitempostion
   add constraint FK_SA_ASSES_REFERENCE_BD_POSIT foreign key (id_position)
      references bd_position (id)
go

alter table sa_assessmentitempostion
   add constraint FK_SA_ASSES_REFERENCE_SA_ASSES foreign key (id_assessmentitem)
      references sa_assessmentitem (id)
go

alter table sa_assessmentlevel
   add constraint FK_SA_ASSES_REFERENCE_SA_ASSE5 foreign key (id_assessmentitem)
      references sa_assessmentitem (id)
go

alter table sa_assessmentreply
   add constraint FK_SA_ASSES_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table sa_assessmentreply
   add constraint FK_SA_ASSES_REFERENCE_SA_ASSE6 foreign key (id_studentassessment)
      references sa_studentassessment (id)
go

alter table sa_assessmentreply
   add constraint FK_SA_ASSES_REFERENCE_SA_ASSE2 foreign key (id_assessmentreply)
      references sa_assessmentreply (id)
go

alter table sa_studentassessment
   add constraint FK_SA_ASSES_REFERENCE_SA_ASSE3 foreign key (id_assessmentitem)
      references sa_assessmentitem (id)
go

alter table sa_studentassessment
   add constraint FK_SA_STUDE_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table sa_studentassessment
   add constraint FK_SA_STUDE_REFERENCE_BD_STUDE foreign key (id_student)
      references bd_student (id)
go

alter table sa_studentassessment
   add constraint FK_SA_ASSES_REFERENCE_SA_ASSE4 foreign key (id_assessmentlevel)
      references sa_assessmentlevel (id)
go

alter table sc_featuredsubject
   add constraint FK_SC_FEATU_REFERENCE_SC_SPECI foreign key (id_specialtopic)
      references sc_specialtopic (id)
go

alter table sc_featuredsubject
   add constraint FK_SC_FEATU_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table sc_gambit
   add constraint FK_SC_GAMBI_REFERENCE_SC_SPECI foreign key (id_specialtopic)
      references sc_specialtopic (id)
go

alter table sc_gambit
   add constraint FK_SC_GAMBI_REFERENCE_BD_USER foreign key (id_initiator)
      references bd_user (id)
go

alter table sc_messagecenter
   add constraint FK_SC_MESSA_REFERENCE_SC_SUBJE2 foreign key (id_featuredsubject)
      references sc_featuredsubject (id)
go

alter table sc_messagecenter
   add constraint Reference_396 foreign key (id_receiver)
      references bd_user (id)
go

alter table sc_messagecenter
   add constraint FK_SC_MESSA_REFERENCE_SC_GAMBI foreign key (id_gambit)
      references sc_gambit (id)
go

alter table sc_messagecenter
   add constraint FK_SC_MESSA_REFERENCE_SC_SUBJE foreign key (id_subjectwork)
      references sc_subjectwork (id)
go

alter table sc_messagecenter
   add constraint Reference_752 foreign key (id_trigger)
      references bd_user (id)
go

alter table sc_privateletter
   add constraint FK_SC_PRIVA_REFERENCE_SC_FEATU foreign key (id_featuredsubject)
      references sc_featuredsubject (id)
go

alter table sc_privateletter
   add constraint FK_SC_PRIVA_REFERENCE_BD_STUDE foreign key (id_student)
      references bd_student (id)
go

alter table sc_privateletter
   add constraint FK_SC_PRIVA_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table sc_reply
   add constraint FK_SC_REPLY_REFERENCE_SC_GAMBI foreign key (id_gambit)
      references sc_gambit (id)
go

alter table sc_reply
   add constraint FK_SC_REPLY_REFERENCE_SC_REPLY foreign key (id_reply)
      references sc_reply (id)
go

alter table sc_reply
   add constraint FK_SC_REPLY_REFERENCE_BD_USER foreign key (id_replier)
      references bd_user (id)
go

alter table sc_replyapproval
   add constraint FK_SC_REPLY_REFERENCE_SC_REPLY2 foreign key (id_reply)
      references sc_reply (id)
go

alter table sc_replyapproval
   add constraint FK_SC_REPLY_REFERENCE_BD_USER2 foreign key (id_user)
      references bd_user (id)
go

alter table sc_specialtopic
   add constraint FK_SC_SPECI_REFERENCE_SC_TOPIC foreign key (id_topic)
      references sc_topic (id)
go

alter table sc_subjectstudent
   add constraint FK_SC_SUBJE_REFERENCE_SC_SUBJE2 foreign key (id_featuredsubject)
      references sc_featuredsubject (id)
go

alter table sc_subjectstudent
   add constraint FK_SC_SUBJE_REFERENCE_BD_STUDE2 foreign key (id_student)
      references bd_student (id)
go

alter table sc_subjectwork
   add constraint FK_SC_SUBJE_REFERENCE_SC_FEATU foreign key (id_featuredsubject)
      references sc_featuredsubject (id)
go

alter table sc_subjectwork
   add constraint FK_SC_SUBJE_REFERENCE_BD_STUDE foreign key (id_student)
      references bd_student (id)
go

alter table sc_userspace
   add constraint FK_SC_USERS_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table sd_activity
   add constraint FK_SD_ACTIV_REFERENCE_BD_SCHO2 foreign key (id_schoolterm)
      references bd_schoolterm (id)
go

alter table sd_activitygrade
   add constraint FK_SD_ACTIV_REFERENCE_SD_ACTIV foreign key (id_activity)
      references sd_activity (id)
go

alter table sd_activitygrade
   add constraint FK_SD_ACTIV_REFERENCE_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
go

alter table sd_activitygrade
   add constraint FK_SD_ACTIV_REFERENCE_BD_SCHOO foreign key (id_school)
      references bd_school (id)
go

alter table sd_works
   add constraint FK_SD_WORKS_REFERENCE_SD_ACTIV foreign key (id_activity)
      references sd_activity (id)
go

alter table sd_works
   add constraint FK_SD_WORKS_REFERENCE_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
go

alter table sd_works
   add constraint FK_SD_WORKS_REFERENCE_BD_STUDE foreign key (id_student)
      references bd_student (id)
go

alter table sd_worksscore
   add constraint FK_SD_WORKS_REFERENCE_SD_WORKS foreign key (id_works)
      references sd_works (id)
go

alter table sd_worksscore
   add constraint FK_SD_WORKS_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table sm_compareculture
   add constraint FK_SM_COMPA_REFERENCE_SM_SPORT foreign key (id_sportsmeet)
      references sm_sportsmeet (id)
go

alter table sm_compareculture
   add constraint FK_SM_COMPA_REFERENCE_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
go

alter table sm_matchclass
   add constraint FK_SM_MATCH_REFERENCE_SM_MATCHU foreign key (id_matchgroup)
      references sm_matchgroup (id)
go

alter table sm_matchclass
   add constraint FK_SM_MATCH_REFERENCE_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
go

alter table sm_matchclass
   add constraint FK_SM_MATCH_REFERENCE_BD_USER2 foreign key (id_lead)
      references bd_user (id)
go

alter table sm_matchclass
   add constraint FK_SM_MATCH_REFERENCE_BD_USER3 foreign key (id_user)
      references bd_user (id)
go

alter table sm_matchdepartment
   add constraint FK_SM_MATCH_REFERENCE_SM_MATCH4 foreign key (id_matchgroup)
      references sm_matchgroup (id)
go

alter table sm_matchdepartment
   add constraint FK_SM_MATCH_REFERENCE_BD_USER6 foreign key (id_user)
      references bd_user (id)
go

alter table sm_matchdepartment
   add constraint FK_SM_MATCH_REFERENCE_BD_DEPAR foreign key (id_department)
      references bd_department (id)
go

alter table sm_matchgroup
   add constraint FK_SM_MATCH_REFERENCE_SM_SPORT1 foreign key (id_sportsmeet)
      references sm_sportsmeet (id)
go

alter table sm_matchpeople
   add constraint FK_SM_MATCH_REFERENCE_SM_MATCHH foreign key (id_matchsmallgroup)
      references sm_matchsmallgroup (id)
go

alter table sm_matchpeople
   add constraint FK_SM_MATCH_REFERENCE_SM_PROJE2 foreign key (id_project)
      references sm_sportsproject (id)
go

alter table sm_matchpeople
   add constraint FK_SM_MATCH_REFERENCE_SM_SPORT foreign key (id_sportsman)
      references sm_sportsman (id)
go

alter table sm_matchproject
   add constraint FK_MATCHPROJECT_SPORTSPROJECT foreign key (id_project)
      references sm_sportsproject (id)
go

alter table sm_matchproject
   add constraint FK_SM_MATCH_REFERENCE_SM_MATCH7 foreign key (id_matchgroup)
      references sm_matchgroup (id)
go

alter table sm_matchproject
   add constraint FK_SM_MATCH_REFERENCE_SM_SPORT2 foreign key (id_sportsmeet)
      references sm_sportsmeet (id)
go

alter table sm_matchsmallgroup
   add constraint FK_SM_MATCH_REFERENCE_SM_MATCH foreign key (id_matchproject)
      references sm_matchproject (id)
go

alter table sm_scoringtemplate
   add constraint FK_SM_SCORI_REFERENCE_SM_SPORT foreign key (id_sportsmeet)
      references sm_sportsmeet (id)
go

alter table sm_sportsman
   add constraint FK_SM_SPORT_REFERENCE_SM_SPORT33 foreign key (id_sportsmeet)
      references sm_sportsmeet (id)
go

alter table sm_sportsman
   add constraint FK_SM_SPORT_REFERENCE_SM_MATCHUH foreign key (id_matchclass)
      references sm_matchclass (id)
go

alter table sm_sportsman
   add constraint FK_SM_SPORT_REFERENCE_SM_MATCHHH foreign key (id_matchorganization)
      references sm_matchdepartment (id)
go

alter table sm_sportsman
   add constraint FK_SM_SPORT_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table sm_sportsmeet
   add constraint FK_SM_SPORT_REFERENCE_BD_SCHOO foreign key (id_schoolyear)
      references bd_schoolyear (id)
go

alter table sm_sportsmeetdocument
   add constraint FK_SM_SPORT_REFERENCE_SM_SPORT44 foreign key (id_sportsmeet)
      references sm_sportsmeet (id)
go

alter table sm_sportsprojectrecord
   add constraint FK_SM_SPORT_REFERENCE_SM_SPORT99 foreign key (id_project)
      references sm_sportsproject (id)
go

alter table so_society
   add constraint FK_SO_SOCIE_REFERENCE_BD_SCHO2 foreign key (id_startschoolterm)
      references bd_schoolterm (id)
go

alter table so_society
   add constraint Reference_447 foreign key (id_endschoolterm)
      references bd_schoolterm (id)
go

alter table so_societyActivity
   add constraint FK_SO_activity_REFERENCE_SO_SO foreign key (id_society)
      references so_society (id)
go

alter table so_societyActivity
   add constraint FK_SO_SOCIE_REFERENCE_BD_SCHO9 foreign key (id_schoolTerm)
      references bd_schoolterm (id)
go

alter table so_societyAssessment
   add constraint Reference_235 foreign key (id_society)
      references so_society (id)
go

alter table so_societyAssessment
   add constraint FK_SO_SOCIE_REFERENCE_BD_SCHO3 foreign key (id_schoolTerm)
      references bd_schoolterm (id)
go

alter table so_societyMaterial
   add constraint FK_SO_SOCIE_REFERENCE_SO_activ foreign key (id_societyActivity)
      references so_societyActivity (id)
go

alter table so_societyMaterial
   add constraint Reference_227 foreign key (id_society)
      references so_society (id)
go

alter table so_societyMaterial
   add constraint Reference_921 foreign key (id_societyRecruitActivity)
      references so_societyRecruitActivity (id)
go

alter table so_societyMaterial
   add constraint Reference_922 foreign key (id_societyaudit)
      references so_societyaudit (id)
go

alter table so_societyMaterial
   add constraint Reference_923 foreign key (id_schoolterm)
      references bd_schoolterm (id)
go

alter table so_societyMember
   add constraint FK_SO_member_REFERENCE_SO_SO foreign key (id_society)
      references so_society (id)
go

alter table so_societyMember
   add constraint Reference_238 foreign key (id_societyRecruitActivity)
      references so_societyRecruitActivity (id)
go

alter table so_societyMember
   add constraint FK_SO_SOCIE_REFERENCE_BD_SCHO5 foreign key (id_tenureSchoolTerm)
      references bd_schoolterm (id)
go

alter table so_societyMember
   add constraint FK_SO_SOCIE_REFERENCE_BD_SCHO6 foreign key (id_enterSchoolTerm)
      references bd_schoolterm (id)
go

alter table so_societyMember
   add constraint FK_SO_SOCIE_REFERENCE_BD_USE2 foreign key (id_user)
      references bd_user (id)
go

alter table so_societyMember
   add constraint FK_SO_SOCIE_REFERENCE_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
go

alter table so_societyRecruitActivity
   add constraint Reference_225 foreign key (id_society)
      references so_society (id)
go

alter table so_societyRecruitActivity
   add constraint FK_SO_SOCIE_REFERENCE_BD_SCHO4 foreign key (id_schoolTerm)
      references bd_schoolterm (id)
go

alter table so_societyaudit
   add constraint Reference_796 foreign key (id_society)
      references so_society (id)
go

alter table so_societyaudit
   add constraint Reference_797 foreign key (id_schoolTerm)
      references bd_schoolterm (id)
go

alter table so_societyaudit
   add constraint FK_SO_SOCIE_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table sr_goodsinfo
   add constraint FK_SR_GOODS_REFERENCE_SR_GOODS foreign key (id_goodskind)
      references sr_goodskind (id)
go

alter table sr_warehouserecord
   add constraint FK_USER_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table sr_warehouserecordgoods
   add constraint FK_SR_WAREH_REFERENCE_SR_GOODS foreign key (id_goodsinfo)
      references sr_goodsinfo (id)
go

alter table sr_warehouserecordgoods
   add constraint FK_SR_WAREH_REFERENCE_SR_WAREH foreign key (id_warehouserecord)
      references sr_warehouserecord (id)
go

alter table sr_warehouserecordgoods
   add constraint FK_SR_WAREH_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table ss_guidanceteacher
   add constraint FK_SS_GUIDA_REFERENCE_SS_SPECI foreign key (id_specialstudent)
      references ss_specialstudent (id)
go

alter table ss_guidanceteacher
   add constraint FK_SS_GUIDA_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table ss_specialstudent
   add constraint FK_SS_SPECI_REFERENCE_BD_USE1 foreign key (id_checkUser)
      references bd_user (id)
go

alter table ss_specialstudent
   add constraint FK_SS_SPECI_REFERENCE_BD_USE2 foreign key (id_cancelUser)
      references bd_user (id)
go

alter table ss_specialstudent
   add constraint FK_SS_SPECI_REFERENCE_BD_USE3 foreign key (id_nominateUser)
      references bd_user (id)
go

alter table ss_specialstudent
   add constraint FK_SS_SPECI_REFERENCE_BD_STUDE foreign key (id_student)
      references bd_student (id)
go

alter table ta_teacherattendancerecord
   add constraint Reference_680 foreign key (id_checkUser)
      references bd_user (id)
go

alter table ta_teacherattendancerecord
   add constraint Reference_681 foreign key (id_user)
      references bd_user (id)
go

alter table tf_abroadstudy
   add constraint Reference_495 foreign key (id_teacher)
      references bd_teacher (id)
go

alter table tf_abroadstudy
   add constraint Reference_539 foreign key (id_auditteacher)
      references bd_teacher (id)
go

alter table tf_abroadstudy
   add constraint FK_TF_ABROA_REFERENCE_BD_SCHOO foreign key (id_schoolitemAdd)
      references bd_schoolterm (id)
go

alter table tf_awardwining
   add constraint Reference_502 foreign key (id_teacher)
      references bd_teacher (id)
go

alter table tf_awardwining
   add constraint Reference_546 foreign key (id_auditteacher)
      references bd_teacher (id)
go

alter table tf_awardwining
   add constraint FK_TF_AWARD_REFERENCE_BD_SCHOO foreign key (id_schoolitemAdd)
      references bd_schoolterm (id)
go

alter table tf_awardwining
   add constraint FK_TF_AWARD_REFERENCE_BD_SUBJE foreign key (id_subject)
      references bd_subject (id)
go

alter table tf_courseware
   add constraint Reference_493 foreign key (id_teacher)
      references bd_teacher (id)
go

alter table tf_courseware
   add constraint Reference_516 foreign key (id_schoolitem)
      references bd_schoolterm (id)
go

alter table tf_courseware
   add constraint FK_TF_COURS_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table tf_courseware
   add constraint Reference_536 foreign key (id_auditteacher)
      references bd_teacher (id)
go

alter table tf_courseware
   add constraint Reference_555 foreign key (id_schoolitemAdd)
      references bd_schoolterm (id)
go

alter table tf_coursewareeclass
   add constraint FK_TF_COURS_REFERENCE_TF_COURS foreign key (id_courseware)
      references tf_courseware (id)
go

alter table tf_coursewareeclass
   add constraint FK_TF_COURS_REFERENCE_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
go

alter table tf_customdetail
   add constraint FK_TF_CUSTO_REFERENCE_BD_TEAC2 foreign key (id_teacher)
      references bd_teacher (id)
go

alter table tf_customdetail
   add constraint FK_TF_CUSTO_REFERENCE_BD_TEAC3 foreign key (id_auditteacher)
      references bd_teacher (id)
go

alter table tf_customdetail
   add constraint FK_TF_CUSTO_REFERENCE_BD_SCHOO foreign key (id_schoolitemAdd)
      references bd_schoolterm (id)
go

alter table tf_customdetail
   add constraint FK_TF_CUSTO_CUSTOMITE_TF_FILES foreign key (id_filesitem)
      references tf_filesitem (id)
go

alter table tf_developmentplan
   add constraint Reference_490 foreign key (id_teacher)
      references bd_teacher (id)
go

alter table tf_developmentplan
   add constraint Reference_522 foreign key (id_schoolyear)
      references bd_schoolyear (id)
go

alter table tf_developmentplan
   add constraint Reference_533 foreign key (id_auditteacher)
      references bd_teacher (id)
go

alter table tf_developmentplan
   add constraint Reference_552 foreign key (id_schoolitemAdd)
      references bd_schoolterm (id)
go

alter table tf_domesticstudy
   add constraint Reference_494 foreign key (id_teacher)
      references bd_teacher (id)
go

alter table tf_domesticstudy
   add constraint Reference_538 foreign key (id_auditteacher)
      references bd_teacher (id)
go

alter table tf_domesticstudy
   add constraint FK_TF_DOMES_REFERENCE_BD_SCHOO foreign key (id_schoolitemAdd)
      references bd_schoolterm (id)
go

alter table tf_examinationsubject
   add constraint Reference_517 foreign key (id_schoolitem)
      references bd_schoolterm (id)
go

alter table tf_examinationsubject
   add constraint FK_TF_EXAMI_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table tf_examinationsubject
   add constraint Reference_537 foreign key (id_auditteacher)
      references bd_teacher (id)
go

alter table tf_examinationsubject
   add constraint Reference_556 foreign key (id_schoolitemAdd)
      references bd_schoolterm (id)
go

alter table tf_examinationsubject
   add constraint Reference_570 foreign key (id_teacher)
      references bd_teacher (id)
go

alter table tf_examsubjecteclass
   add constraint FK_TF_EXAMS_REFERENCE_TF_EXAMI foreign key (id_examinationsubject)
      references tf_examinationsubject (id)
go

alter table tf_examsubjecteclass
   add constraint FK_TF_EXAMS_REFERENCE_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
go

alter table tf_filesitem
   add constraint FK_TF_FILES_REFERENCE_TF_FILE2 foreign key (id_parent)
      references tf_filesitem (id)
go

alter table tf_filesitemteacher
   add constraint FK_TF_FILES_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table tf_filesitemteacher
   add constraint Reference_531 foreign key (id_filesitem)
      references tf_filesitem (id)
go

alter table tf_honorarytitle
   add constraint Reference_501 foreign key (id_teacher)
      references bd_teacher (id)
go

alter table tf_honorarytitle
   add constraint Reference_545 foreign key (id_auditteacher)
      references bd_teacher (id)
go

alter table tf_honorarytitle
   add constraint FK_TF_HONOR_REFERENCE_BD_SCHOO foreign key (id_schoolitemAdd)
      references bd_schoolterm (id)
go

alter table tf_publicclass
   add constraint Reference_505 foreign key (id_teacher)
      references bd_teacher (id)
go

alter table tf_publicclass
   add constraint Reference_532 foreign key (id_schoolitem)
      references bd_schoolterm (id)
go

alter table tf_publicclass
   add constraint Reference_549 foreign key (id_auditteacher)
      references bd_teacher (id)
go

alter table tf_publicclass
   add constraint Reference_566 foreign key (id_schoolitemAdd)
      references bd_schoolterm (id)
go

alter table tf_readingessay
   add constraint Reference_496 foreign key (id_teacher)
      references bd_teacher (id)
go

alter table tf_readingessay
   add constraint Reference_540 foreign key (id_auditteacher)
      references bd_teacher (id)
go

alter table tf_readingessay
   add constraint FK_TF_READI_REFERENCE_BD_SCHOO foreign key (id_schoolitemAdd)
      references bd_schoolterm (id)
go

alter table tf_sbcd
   add constraint FK_TF_SBCD_REFERENCE_BD_SCHOO foreign key (id_schoolitemAdd)
      references bd_schoolterm (id)
go

alter table tf_sbcd
   add constraint Reference_572 foreign key (id_teacher)
      references bd_teacher (id)
go

alter table tf_sbcd
   add constraint FK_TF_SBCD_REFERENCE_BD_SUBJE foreign key (id_subject)
      references bd_subject (id)
go

alter table tf_sbcd
   add constraint Reference_574 foreign key (id_auditteacher)
      references bd_teacher (id)
go

alter table tf_subjectstudy
   add constraint Reference_497 foreign key (id_teacher)
      references bd_teacher (id)
go

alter table tf_subjectstudy
   add constraint Reference_541 foreign key (id_auditteacher)
      references bd_teacher (id)
go

alter table tf_subjectstudy
   add constraint FK_TF_SUBJE_REFERENCE_BD_SCHOO foreign key (id_schoolitemAdd)
      references bd_schoolterm (id)
go

alter table tf_teachingdesign
   add constraint FK_TF_TEACH_REFERENCE_BD_TEAC2 foreign key (id_teacher)
      references bd_teacher (id)
go

alter table tf_teachingdesign
   add constraint FK_TF_TEACH_REFERENCE_BD_SCHO3 foreign key (id_schoolitem)
      references bd_schoolterm (id)
go

alter table tf_teachingdesign
   add constraint FK_TF_TEACH_REFERENCE_BD_COUR4 foreign key (id_course)
      references bd_course (id)
go

alter table tf_teachingdesign
   add constraint FK_TF_TEACH_REFERENCE_BD_TEAC4 foreign key (id_auditteacher)
      references bd_teacher (id)
go

alter table tf_teachingdesign
   add constraint Reference_554 foreign key (id_schoolitemAdd)
      references bd_schoolterm (id)
go

alter table tf_teachingeclass
   add constraint FK_TF_TEACH_REFERENCE_TF_TEACH foreign key (id_teachingdesign)
      references tf_teachingdesign (id)
go

alter table tf_teachingeclass
   add constraint FK_TF_TEACH_REFERENCE_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
go

alter table tf_teachingnotes
   add constraint FK_TF_TEACH_REFERENCE_BD_TEA5H foreign key (id_teacher)
      references bd_teacher (id)
go

alter table tf_teachingnotes
   add constraint FK_TF_TEACH_REFERENCE_BD_SCH6O foreign key (id_schoolitem)
      references bd_schoolterm (id)
go

alter table tf_teachingnotes
   add constraint Reference_543 foreign key (id_auditteacher)
      references bd_teacher (id)
go

alter table tf_teachingnotes
   add constraint Reference_562 foreign key (id_schoolitemAdd)
      references bd_schoolterm (id)
go

alter table tf_teachingprogram
   add constraint FK_TF_TEACH_REFERENCE_BD_TEAC5 foreign key (id_teacher)
      references bd_teacher (id)
go

alter table tf_teachingprogram
   add constraint FK_TF_TEACH_REFERENCE_BD_SCHO6 foreign key (id_schoolitem)
      references bd_schoolterm (id)
go

alter table tf_teachingprogram
   add constraint FK_TF_TEACH_REFERENCE_BD_COUR2 foreign key (id_course)
      references bd_course (id)
go

alter table tf_teachingprogram
   add constraint Reference_534 foreign key (id_auditteacher)
      references bd_teacher (id)
go

alter table tf_teachingprogram
   add constraint Reference_553 foreign key (id_schoolitemAdd)
      references bd_schoolterm (id)
go

alter table tf_teachingreflection
   add constraint FK_TF_TEACH_REFERENCE_BD_TEA2H foreign key (id_teacher)
      references bd_teacher (id)
go

alter table tf_teachingreflection
   add constraint FK_TF_TEACH_REFERENCE_BD_SCH3O foreign key (id_schoolitem)
      references bd_schoolterm (id)
go

alter table tf_teachingreflection
   add constraint Reference_542 foreign key (id_auditteacher)
      references bd_teacher (id)
go

alter table tf_teachingreflection
   add constraint Reference_563 foreign key (id_schoolitemAdd)
      references bd_schoolterm (id)
go

alter table tf_tutorteaching
   add constraint FK_TF_TUTOR_REFERENCE_BD_TEA3H foreign key (id_teacher)
      references bd_teacher (id)
go

alter table tf_tutorteaching
   add constraint FK_TF_TUTOR_REFERENCE_BD_TEACH foreign key (id_auditteacher)
      references bd_teacher (id)
go

alter table tf_tutorteaching
   add constraint FK_TF_TUTOR_REFERENCE_BD_SCHOO foreign key (id_schoolitemAdd)
      references bd_schoolterm (id)
go

alter table tf_tutorteachingteacher
   add constraint FK_TF_TUTOR_REFERENCE_BD_TEA4H foreign key (id_teacher)
      references bd_teacher (id)
go

alter table tf_tutorteachingteacher
   add constraint FK_TF_TUTOR_REFERENCE_TF_TUTOR foreign key (id_tutorteaching)
      references tf_tutorteaching (id)
go

alter table tf_worksummary
   add constraint Reference_500 foreign key (id_teacher)
      references bd_teacher (id)
go

alter table tf_worksummary
   add constraint Reference_520 foreign key (id_schoolitem)
      references bd_schoolterm (id)
go

alter table tf_worksummary
   add constraint Reference_544 foreign key (id_auditteacher)
      references bd_teacher (id)
go

alter table tf_worksummary
   add constraint Reference_561 foreign key (id_schoolitemAdd)
      references bd_schoolterm (id)
go

alter table tr_issueguide
   add constraint FK_TR_ISSUE_REFERENCE_BD_US7R foreign key (id_user)
      references bd_user (id)
go

alter table tr_issuemember
   add constraint FK_TR_ISSUE_REFERENCE_TR_TEACH foreign key (id_teachingissue)
      references tr_teachingissue (id)
go

alter table tr_issuemember
   add constraint FK_TR_ISSUE_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table tr_issueresource
   add constraint FK_TR_ISSUE_REFERENCE_TR_TEAC2 foreign key (id_teachingissue)
      references tr_teachingissue (id)
go

alter table tr_issueresource
   add constraint FK_TR_ISSUE_REFERENCE_BD_US8R foreign key (id_user)
      references bd_user (id)
go

alter table tr_teachingissue
   add constraint FK_TR_TEACH_REFERENCE_TR_ISSUE foreign key (id_issueguide)
      references tr_issueguide (id)
go

alter table tr_teachingissue
   add constraint FK_TR_TEACH_REFERENCE_BD_USER foreign key (id_declareUser)
      references bd_user (id)
go

alter table va_voluntaryactivities
   add constraint Reference_443 foreign key (id_school)
      references bd_school (id)
go

alter table va_voluntaryactivities
   add constraint FK_VA_VOLUN_REFERENCE_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
go

alter table va_voluntaryactivities
   add constraint FK_VA_VOLUN_REFERENCE_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
go

alter table va_voluntaryactivities
   add constraint FK_VA_VOLUN_REFERENCE_BD_STUDE foreign key (id_student)
      references bd_student (id)
go

alter table va_voluntaryactivities
   add constraint Reference_468 foreign key (id_schoolterm)
      references bd_schoolterm (id)
go

alter table wc_warning
   add constraint FK_WC_WARNI_REFERENCE_WC_WCREC foreign key (id_wcrecord)
      references wc_wcrecord (id)
go

alter table wc_warning
   add constraint FK_WC_WARNI_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table wc_wcrecord
   add constraint FK_CW_WEEKRECORD_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table wc_wcrecord
   add constraint fk_custom_key_1 foreign key (id_weekcalendar)
      references wc_weekcalendar (id)
go

alter table wc_wcteacher
   add constraint FK_WC_WCTEA_REFERENCE_WC_WEEKC foreign key (id_weekcalendar)
      references wc_weekcalendar (id)
go

alter table wc_weekcalendar
   add constraint FK_CW_TEACHERWEEK_BD_SCHOOL foreign key (id_school)
      references bd_school (id)
go

alter table wc_weekcalendar
   add constraint FK_CW_TEACHERWEEK_BD_SCHOOLTER foreign key (id_schoolterm)
      references bd_schoolterm (id)
go

alter table wk_coursework
   add constraint Reference_1000 foreign key (id_schoolterm)
      references bd_schoolterm (id)
go

alter table wk_coursework
   add constraint Reference_1001 foreign key (id_course)
      references bd_course (id)
go

alter table wk_coursework
   add constraint Reference_1002 foreign key (id_user)
      references bd_user (id)
go

alter table wk_workeclass
   add constraint Reference_1003 foreign key (id_eclass)
      references bd_eclass (id)
go

alter table wk_workeclass
   add constraint Reference_698 foreign key (id_coursework)
      references wk_coursework (id)
go

alter table wl_makeloguser
   add constraint FK_WL_MAKEL_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table wl_worklog
   add constraint FK_WL_WORKL_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
go

alter table wp_boarditem
   add constraint FK_WP_BOARD_REFERENCE_WP_WORKP foreign key (id_workplan)
      references wp_workplan (id)
go

alter table wp_executionAssistance
   add constraint Reference_671 foreign key (id_workplan)
      references wp_workplan (id)
go

alter table wp_executionAssistance
   add constraint Reference_679 foreign key (id_supportDepartment)
      references bd_department (id)
go

alter table wp_executionAssistance
   add constraint Reference_696 foreign key (id_demandDepartment)
      references bd_department (id)
go

alter table wp_executionAssistance
   add constraint Reference_697 foreign key (id_demandPlan)
      references wp_workplan (id)
go

alter table wp_planchange
   add constraint FK_WP_PLANC_REFERENCE_WP_WORKP foreign key (id_workplan)
      references wp_workplan (id)
go

alter table wp_schoolopinions
   add constraint FK_WP_SCHOO_REFERENCE_WP_WORKP foreign key (id_workplan)
      references wp_workplan (id)
go

alter table wp_teachingideas
   add constraint FK_WP_TEACH_REFERENCE_WP_WORKP foreign key (id_workplan)
      references wp_workplan (id)
go

alter table wp_timearrangement
   add constraint FK_WP_TIMEA_REFERENCE_WP_WORKP foreign key (id_workplan)
      references wp_workplan (id)
go

alter table wp_workideas
   add constraint FK_WP_WORKI_REFERENCE_WP_WORKP foreign key (id_workplan)
      references wp_workplan (id)
go

alter table wp_workobjectives
   add constraint FK_WP_WORKO_REFERENCE_WP_WORKP foreign key (id_workplan)
      references wp_workplan (id)
go

alter table wp_workplan
   add constraint Reference_673 foreign key (id_schoolterm)
      references bd_schoolterm (id)
go

alter table wp_workplan
   add constraint Reference_674 foreign key (id_school)
      references bd_school (id)
go

alter table wp_workplan
   add constraint FK_WP_WORKP_REFERENCE_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
go

alter table wp_workplan
   add constraint FK_WP_WORKP_REFERENCE_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
go

alter table wp_workplan
   add constraint FK_WP_WORKP_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
go

alter table wp_workplan
   add constraint FK_WP_WORKP_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
go

alter table wp_workplan
   add constraint FK_WP_WORKP_REFERENCE_BD_DEPAR foreign key (id_department)
      references bd_department (id)
go

alter table wp_workpoint
   add constraint FK_WP_WORKP_REFERENCE_WP_WORKP foreign key (id_workplan)
      references wp_workplan (id)
go

alter table wp_workpoint
   add constraint FK_WP_WORKP_REFERENCE_WP_BOARD foreign key (id_boarditem)
      references wp_boarditem (id)
go
