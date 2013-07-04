/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2005                    */
/* Created on:     2012-9-4 16:29:12                            */
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
;

alter table ac_acresultteachingweek
   add constraint PK_AC_ACRESULTTEACHINGWEEK primary key nonclustered (id)
;

/*==============================================================*/
/* Table: ac_arrangecourseactivity                              */
/*==============================================================*/
create table ac_arrangecourseactivity (
   id                   char(32)             not null,
   id_parent            char(32)             null,
   id_schoolterm        char(32)             null,
   id_teacher           char(32)             null,
   name                 varchar(50)          null,
   description          varchar(200)         null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
;

alter table ac_arrangecourseactivity
   add constraint PK_AC_ARRANGECOURSEACTIVITY primary key nonclustered (id)
;

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
;

alter table ac_arrangecoursegrade
   add constraint PK_AC_ARRANGECOURSEGRADE primary key nonclustered (id)
;

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
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
;

alter table ac_arrangecourseresult
   add constraint PK_AC_ARRANGECOURSERESULT primary key nonclustered (id)
;

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
;

alter table bd_applicationgroup
   add constraint PK_BD_APPLICATIONGROUP primary key nonclustered (id)
;

/*==============================================================*/
/* Table: bd_classcurriculum                                    */
/*==============================================================*/
create table bd_classcurriculum (
   id                   char(32)             not null,
   id_arrangecourseactivity char(32)             null,
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
;

alter table bd_classcurriculum
   add constraint PK_BD_CLASSCURRICULUM primary key nonclustered (id)
;

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
;

alter table bd_classcurriculumchange
   add constraint PK_BD_CLASSCURRICULUMCHANGE primary key nonclustered (id)
;

/*==============================================================*/
/* Table: bd_classroom                                          */
/*==============================================================*/
create table bd_classroom (
   id                   char(32)             not null,
   id_school            char(32)             null,
   code                 varchar(20)          null,
   name                 varchar(20)          null,
   position             varchar(20)          null,
   capacity             int                  null,
   status               char(1)              null,
   kindDict             varchar(20)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
;

alter table bd_classroom
   add constraint PK_BD_CLASSROOM primary key nonclustered (id)
;

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
;

alter table bd_classroomseat
   add constraint PK_BD_CLASSROOMSEAT primary key nonclustered (id)
;

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
;

alter table bd_classstudent
   add constraint PK_BD_CLASSSTUDENT primary key nonclustered (id)
;

/*==============================================================*/
/* Table: bd_configitem_configresult                            */
/*==============================================================*/
create table bd_configitem_configresult (
   id_configurationitem char(32)             not null,
   id_configurationresult char(32)             not null
)
;

alter table bd_configitem_configresult
   add constraint PK_BD_CONFIGITEM_CONFIGRESULT primary key nonclustered (id_configurationitem, id_configurationresult)
;

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
;

alter table bd_configuration
   add constraint PK_BD_CONFIGURATION primary key nonclustered (id)
;

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
;

alter table bd_configurationitem
   add constraint PK_BD_CONFIGURATIONITEM primary key nonclustered (id)
;

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
;

alter table bd_configurationresult
   add constraint PK_BD_CONFIGURATIONRESULT primary key nonclustered (id)
;

/*==============================================================*/
/* Table: bd_course                                             */
/*==============================================================*/
create table bd_course (
   id                   char(32)             not null,
   id_subject           char(32)             null,
   name                 varchar(200)         null,
   shortName            varchar(10)          null,
   num                  int                  null,
   displayName          varchar(200)         null,
   courseKindDict       varchar(20)          null,
   directionDict        varchar(20)          null,
   enableFlag           char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
;

alter table bd_course
   add constraint PK_BD_COURSE primary key nonclustered (id)
;

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
;

alter table bd_datascopeclass
   add constraint PK_BD_DATASCOPECLASS primary key nonclustered (id)
;

/*==============================================================*/
/* Table: bd_department                                         */
/*==============================================================*/
create table bd_department (
   id                   char(32)             not null,
   id_parent            char(32)             null,
   id_school            char(32)             null,
   name                 varchar(20)          null,
   num                  int                  null,
   departmentKind       char(1)              null,
   enableFlag           char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
;

alter table bd_department
   add constraint PK_BD_DEPARTMENT primary key nonclustered (id)
;

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
;

alter table bd_departmentposition
   add constraint PK_BD_DEPARTMENTPOSITION primary key nonclustered (id)
;

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
;

alter table bd_departmentscope
   add constraint PK_BD_DEPARTMENTSCOPE primary key nonclustered (id)
;

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
;

alter table bd_departmentuser
   add constraint PK_BD_DEPARTMENTUSER primary key nonclustered (id)
;

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
;

alter table bd_dictionary
   add constraint PK_BD_DICTIONARY primary key nonclustered (id)
;

/*==============================================================*/
/* Table: bd_dictionaryvalue                                    */
/*==============================================================*/
create table bd_dictionaryvalue (
   id                   char(32)             not null,
   id_dictionary        char(32)             null,
   id_parent            char(32)             null,
   code                 varchar(20)          null,
   value                varchar(50)          null,
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
;

alter table bd_dictionaryvalue
   add constraint PK_BD_DICTIONARYVALUE primary key nonclustered (id)
;

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
;

alter table bd_dividescore
   add constraint PK_BD_DIVIDESCORE primary key (id)
;

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
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
;

alter table bd_eclass
   add constraint PK_BD_ECLASS primary key nonclustered (id)
;

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
;

alter table bd_eclassteachingmaterial
   add constraint PK_BD_ECLASSTEACHINGMATERIAL primary key nonclustered (id)
;

/*==============================================================*/
/* Table: bd_grade                                              */
/*==============================================================*/
create table bd_grade (
   id                   char(32)             not null,
   num                  int                  null,
   name                 varchar(20)          null,
   shortName            varchar(10)          null,
   enableFlag           char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
;

alter table bd_grade
   add constraint PK_BD_GRADE primary key nonclustered (id)
;

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
;

alter table bd_gradecourse
   add constraint PK_BD_GRADECOURSE primary key nonclustered (id)
;

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
;

alter table bd_gradeteachingmaterial
   add constraint PK_BD_GRADETEACHINGMATERIAL primary key nonclustered (id)
;

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
;

alter table bd_groupapplication
   add constraint PK_BD_GROUPAPPLICATION primary key nonclustered (id)
;

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
;

alter table bd_module
   add constraint PK_BD_MODULE primary key nonclustered (id)
;

/*==============================================================*/
/* Table: bd_operation                                          */
/*==============================================================*/
create table bd_operation (
   id                   char(32)             not null,
   id_module            char(32)             null,
   id_parent            char(32)             null,
   name                 varchar(20)          null,
   nameI18n             varchar(50)          null,
   code                 varchar(20)          null,
   operationDict        varchar(20)          null,
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
;

alter table bd_operation
   add constraint PK_BD_OPERATION primary key nonclustered (id)
;

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
   descriptioni18n      varchar(50)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
;

alter table bd_operationlog
   add constraint PK_BD_OPERATIONLOG primary key nonclustered (id)
;

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
;

alter table bd_parent
   add constraint PK_BD_PARENT primary key nonclustered (id)
;

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
;

alter table bd_period
   add constraint PK_BD_PERIOD primary key nonclustered (id)
;

/*==============================================================*/
/* Table: bd_position                                           */
/*==============================================================*/
create table bd_position (
   id                   char(32)             not null,
   name                 varchar(20)          null,
   code                 varchar(20)          null,
   num                  int                  null,
   systemFlag           char(1)              null,
   enableFlag           char(1)              null,
   positionDict         varchar(20)          null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
;

alter table bd_position
   add constraint PK_BD_POSITION primary key nonclustered (id)
;

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
;

alter table bd_positionchange
   add constraint PK_BD_POSITIONCHANGE primary key nonclustered (id)
;

/*==============================================================*/
/* Table: bd_postionprivilege                                   */
/*==============================================================*/
create table bd_postionprivilege (
   id_position          char(32)             not null,
   id_privilege         char(32)             not null
)
;

alter table bd_postionprivilege
   add constraint PK_BD_POSTIONPRIVILEGE primary key nonclustered (id_position, id_privilege)
;

/*==============================================================*/
/* Table: bd_privilege                                          */
/*==============================================================*/
create table bd_privilege (
   id                   char(32)             not null,
   id_module            char(32)             null,
   name                 varchar(20)          null,
   description          varchar(200)         null,
   num                  int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
;

alter table bd_privilege
   add constraint PK_BD_PRIVILEGE primary key nonclustered (id)
;

/*==============================================================*/
/* Table: bd_privilegeoperation                                 */
/*==============================================================*/
create table bd_privilegeoperation (
   id_privilege         char(32)             not null,
   id_operation         char(32)             not null
)
;

alter table bd_privilegeoperation
   add constraint PK_BD_PRIVILEGEOPERATION primary key nonclustered (id_privilege, id_operation)
;

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
;

alter table bd_publisher
   add constraint PK_BD_PUBLISHER primary key nonclustered (id)
;

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
;

alter table bd_schedule
   add constraint PK_BD_SCHEDULE primary key nonclustered (id)
;

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
;

alter table bd_schedulegrade
   add constraint PK_BD_SCHEDULEGRADE primary key nonclustered (id)
;

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
;

alter table bd_scheduleitem
   add constraint PK_BD_SCHEDULEITEM primary key nonclustered (id)
;

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
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
;

alter table bd_school
   add constraint PK_BD_SCHOOL primary key nonclustered (id)
;

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
;

alter table bd_schoolgrade
   add constraint PK_BD_SCHOOLGRADE primary key nonclustered (id)
;

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
;

alter table bd_schoolterm
   add constraint PK_BD_SCHOOLTERM primary key nonclustered (id)
;

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
;

alter table bd_schoolyear
   add constraint PK_BD_SCHOOLYEAR primary key nonclustered (id)
;

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
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
;

alter table bd_student
   add constraint PK_BD_STUDENT primary key nonclustered (id)
;

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
;

alter table bd_studentchange
   add constraint PK_BD_STUDENTCHANGE primary key nonclustered (id)
;

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
;

alter table bd_studentparent
   add constraint PK_BD_STUDENTPARENT primary key nonclustered (id)
;

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
;

alter table bd_studentregistration
   add constraint PK_BD_STUDENTREGISTRATION primary key nonclustered (id)
;

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
;

alter table bd_studentseat
   add constraint PK_BD_STUDENTSEAT primary key nonclustered (id)
;

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
;

alter table bd_studentteachingmaterial
   add constraint PK_BD_STUDENTTEACHINGMATERIAL primary key nonclustered (id)
;

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
   lu                   char(32)             null
)
;

alter table bd_subject
   add constraint PK_BD_SUBJECT primary key nonclustered (id)
;

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
   lu                   char(32)             null
)
;

alter table bd_teacher
   add constraint PK_BD_TEACHER primary key nonclustered (id)
;

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
;

alter table bd_teachercourse
   add constraint PK_BD_TEACHERCOURSE primary key nonclustered (id)
;

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
;

alter table bd_teachercoursechange
   add constraint PK_BD_TEACHERCOURSECHANGE primary key nonclustered (id)
;

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
;

alter table bd_teachingmaterial
   add constraint PK_BD_TEACHINGMATERIAL primary key nonclustered (id)
;

/*==============================================================*/
/* Table: bd_user                                               */
/*==============================================================*/
create table bd_user (
   id                   char(32)             not null,
   name                 varchar(50)          null,
   displayName          varchar(50)          null,
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
;

alter table bd_user
   add constraint PK_BD_USER primary key nonclustered (id)
;

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
;

alter table bd_useroperation
   add constraint PK_BD_USEROPERATION primary key nonclustered (id)
;

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
;

alter table bd_useroperationchange
   add constraint PK_BD_USEROPERATIONCHANGE primary key nonclustered (id)
;

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
;

alter table bd_useroperationdatascope
   add constraint PK_BD_USEROPERATIONDATASCOPE primary key nonclustered (id)
;

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
;

alter table bd_userposition
   add constraint PK_BD_USERPOSITION primary key nonclustered (id)
;

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
;

alter table ca_attendanceitem
   add constraint PK_CA_ATTENDANCEITEM primary key (id)
;

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
;

alter table ca_attendancerecord
   add constraint PK_CA_ATTENDANCERECORD primary key (id)
;

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
;

alter table ca_classroomattendance
   add constraint PK_CA_CLASSROOMATTENDANCE primary key (id)
;

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
;

alter table ca_leaverecord
   add constraint PK_CA_LEAVERECORD primary key (id)
;

/*==============================================================*/
/* Table: ca_performanceitem                                    */
/*==============================================================*/
create table ca_performanceitem (
   id                   char(32)             not null,
   id_performanceKind   char(32)             null,
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
;

alter table ca_performanceitem
   add constraint PK_CA_PERFORMANCEITEM primary key (id)
;

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
;

alter table ca_performancekind
   add constraint PK_CA_PERFORMANCEKIND primary key (id)
;

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
;

alter table ca_performancerecord
   add constraint PK_CA_PERFORMANCERECORD primary key (id)
;

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
   note                 varchar(1000)        null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
;

alter table ec_ecactivity
   add constraint PK_EC_ECACTIVITY primary key nonclustered (id)
;

/*==============================================================*/
/* Table: ec_ecactivitycourse                                   */
/*==============================================================*/
create table ec_ecactivitycourse (
   id                   char(32)             not null,
   id_ecactivity        char(32)             null,
   id_course            char(32)             null,
   status               char(1)              null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
;

alter table ec_ecactivitycourse
   add constraint PK_EC_ECACTIVITYCOURSE primary key nonclustered (id)
;

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
   lu                   char(32)             null
)
;

alter table ec_ecactivityeclass
   add constraint PK_EC_ECACTIVITYECLASS primary key nonclustered (id)
;

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
   lu                   char(32)             null
)
;

alter table ec_ececlass
   add constraint PK_EC_ECECLASS primary key nonclustered (id)
;

/*==============================================================*/
/* Table: ec_ecrule                                             */
/*==============================================================*/
create table ec_ecrule (
   id                   char(32)             not null,
   id_ecactivity        char(32)             null,
   name                 varchar(20)          null,
   ecQuantity           int                  null,
   minQuantity          int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
;

alter table ec_ecrule
   add constraint PK_EC_ECRULE primary key nonclustered (id)
;

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
;

alter table ec_ecruleclass
   add constraint PK_EC_ECRULECLASS primary key nonclustered (id)
;

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
;

alter table ec_ecrulecourse
   add constraint PK_EC_ECRULECOURSE primary key nonclustered (id)
;

/*==============================================================*/
/* Table: ec_ecstudentcourse                                    */
/*==============================================================*/
create table ec_ecstudentcourse (
   id                   char(32)             not null,
   id_ecactivity        char(32)             null,
   id_schoolterm        char(32)             null,
   id_student           char(32)             null,
   id_course            char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
;

alter table ec_ecstudentcourse
   add constraint PK_EC_ECSTUDENTCOURSE primary key nonclustered (id)
;

/*==============================================================*/
/* Table: ec_ecstudentcoursechange                              */
/*==============================================================*/
create table ec_ecstudentcoursechange (
   id                   char(32)             null,
   id_student           char(32)             null,
   id_ecactivity        char(32)             null,
   id_fromcourse        char(32)             null,
   id_tocourse          char(32)             null,
   changeTime           char(19)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
;

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
;

alter table es_program
   add constraint PK_ES_PROGRAM primary key nonclustered (id)
;

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
;

alter table fw_attachment
   add constraint PK_FW_ATTACHMENT primary key nonclustered (id)
;

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
;

alter table fw_attachmentconfig
   add constraint PK_FW_ATTACHMENTCONFIG primary key nonclustered (id)
;

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
;

alter table fw_attachmentsetting
   add constraint PK_FW_ATTACHMENTSETTING primary key nonclustered (id)
;

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
;

alter table ma_materials
   add constraint PK_MA_MATERIALS primary key (id)
;

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
;

alter table ma_materialsapprovaluser
   add constraint PK_MA_MATERIALSAPPROVALUSER primary key (id)
;

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
;

alter table ma_materialshandinuser
   add constraint PK_MA_MATERIALSHANDINUSER primary key (id)
;

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
;

alter table ma_materialskind
   add constraint PK_MA_MATERIALSKIND primary key (id)
;

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
   lu                   char(32)             null
)
;

alter table ma_materialsnotice
   add constraint PK_MA_MATERIALSNOTICE primary key (id)
;

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
;

alter table me_memo
   add constraint PK_ME_MEMO primary key nonclustered (id)
;

/*==============================================================*/
/* Table: me_memomemotemplate                                   */
/*==============================================================*/
create table me_memomemotemplate (
   id_memo              char(32)             not null,
   id_memotemplate      char(32)             null
)
;

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
;

alter table me_memotemplate
   add constraint PK_ME_MEMOTEMPLATE primary key nonclustered (id)
;

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
;

alter table mo_mood
   add constraint PK_MO_MOOD primary key nonclustered (id)
;

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
;

alter table mo_moodkind
   add constraint PK_MO_MOODKIND primary key nonclustered (id)
;

/*==============================================================*/
/* Table: mo_moodmoodkind                                       */
/*==============================================================*/
create table mo_moodmoodkind (
   id_mood              char(32)             null,
   id_moodkind          char(32)             null
)
;

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
;

alter table no_notice
   add constraint PK_NO_NOTICE primary key nonclustered (id)
;

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
;

alter table no_noticereply
   add constraint PK_NO_NOTICEREPLY primary key nonclustered (id)
;

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
;

alter table no_receivenotice
   add constraint PK_NO_RECEIVENOTICE primary key nonclustered (id)
;

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
;

alter table ns_entrancetestcourse
   add constraint PK_NS_ENTRANCETESTCOURSE primary key (id)
;

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
;

alter table ns_entrancetestscore
   add constraint PK_NS_ENTRANCETESTSCORE primary key (id)
;

/*==============================================================*/
/* Table: pc_privatedepartment                                  */
/*==============================================================*/
create table pc_privatedepartment (
   id                   char(32)             not null,
   id_teacher           char(32)             null,
   name                 varchar(20)          null,
   num                  int                  null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
;

alter table pc_privatedepartment
   add constraint PK_PC_PRIVATEDEPARTMENT primary key nonclustered (id)
;

/*==============================================================*/
/* Table: pc_privatedepartmentteacher                           */
/*==============================================================*/
create table pc_privatedepartmentteacher (
   id                   char(32)             not null,
   id_privatedepartment char(32)             null,
   id_teacher           char(32)             null,
   ft                   char(19)             null,
   lt                   char(19)             null,
   fu                   char(32)             null,
   lu                   char(32)             null
)
;

alter table pc_privatedepartmentteacher
   add constraint PK_PC_PRIVATEDEPARTMENTTEACHER primary key nonclustered (id)
;

/*==============================================================*/
/* Table: pu_publicity                                          */
/*==============================================================*/
create table pu_publicity (
   id                   char(32)             not null,
   id_publishuser       char(32)             null,
   id_department        char(32)             null,
   receiveRange         varchar(20)          null,
   title                varchar(100)         null,
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
;

alter table pu_publicity
   add constraint PK_PU_PUBLICITY primary key nonclustered (id)
;

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
;

alter table pu_publicityapproval
   add constraint PK_PU_PUBLICITYAPPROVAL primary key nonclustered (id)
;

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
;

alter table pu_publicityfeedback
   add constraint PK_PU_PUBLICITYFEEDBACK primary key nonclustered (id)
;

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
;

alter table sa_assessmentitem
   add constraint PK_SA_ASSESSMENTITEM primary key nonclustered (id)
;

/*==============================================================*/
/* Table: sa_assessmentitempostion                              */
/*==============================================================*/
create table sa_assessmentitempostion (
   id_assessmentitem    char(32)             null,
   id_position          char(32)             null
)
;

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
;

alter table sa_assessmentkind
   add constraint PK_SA_ASSESSMENTKIND primary key nonclustered (id)
;

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
;

alter table sa_assessmentlevel
   add constraint PK_SA_ASSESSMENTLEVEL primary key nonclustered (id)
;

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
;

alter table sa_assessmentreply
   add constraint PK_SA_ASSESSMENTREPLY primary key nonclustered (id)
;

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
;

alter table sa_studentassessment
   add constraint PK_SA_STUDENTASSESSMENT primary key nonclustered (id)
;

alter table ac_acresultteachingweek
   add constraint FK_AC_ACRES_REFERENCE_AC_ARRAN foreign key (id_arrangecourseactivity)
      references ac_arrangecourseactivity (id)
;

alter table ac_arrangecourseactivity
   add constraint FK_AC_ARRAN_REFERENCE_AC_ARRA2 foreign key (id_parent)
      references ac_arrangecourseactivity (id)
;

alter table ac_arrangecourseactivity
   add constraint FK_AC_ARRAN_REFERENCE_BD_SCHOO foreign key (id_schoolterm)
      references bd_schoolterm (id)
;

alter table ac_arrangecourseactivity
   add constraint FK_AC_ARRAN_REFERENCE_BD_TEAC1 foreign key (id_teacher)
      references bd_teacher (id)
;

alter table ac_arrangecoursegrade
   add constraint FK_AC_ARRAN_RELATIONS_AC_ARRAN foreign key (id_arrangecourseactivity)
      references ac_arrangecourseactivity (id)
;

alter table ac_arrangecoursegrade
   add constraint FK_AC_ARRAN_RELATIONS_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
;

alter table ac_arrangecoursegrade
   add constraint FK_AC_ARRAN_RELATIONS_BD_SCHOO foreign key (id_school)
      references bd_school (id)
;

alter table ac_arrangecourseresult
   add constraint FK_AC_ARRAN_REFERENCE_AC_ARRA1 foreign key (id_arrangecourseactivity)
      references ac_arrangecourseactivity (id)
;

alter table ac_arrangecourseresult
   add constraint FK_AC_ARRAN_REFERENCE_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
;

alter table ac_arrangecourseresult
   add constraint FK_AC_ARRAN_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
;

alter table ac_arrangecourseresult
   add constraint FK_AC_ARRAN_REFERENCE_BD_TEAC2 foreign key (id_teacher)
      references bd_teacher (id)
;

alter table bd_applicationgroup
   add constraint FK_BD_APPLI_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
;

alter table bd_classcurriculum
   add constraint FK_BD_CLASS_REFERENCE_BD_CLAS2 foreign key (id_classroom)
      references bd_classroom (id)
;

alter table bd_classcurriculum
   add constraint FK_AC_CLASS_RELATIONS_BD_ECLA2 foreign key (id_eclass)
      references bd_eclass (id)
;

alter table bd_classcurriculum
   add constraint FK_AC_CLASS_RELATIONS_BD_COUR2 foreign key (id_course)
      references bd_course (id)
;

alter table bd_classcurriculum
   add constraint FK_AC_CLASS_RELATIONS_BD_TEAC2 foreign key (id_teacher)
      references bd_teacher (id)
;

alter table bd_classcurriculum
   add constraint FK_BD_CLASS_RELATIONS_AC_ARRAN foreign key (id_arrangecourseactivity)
      references ac_arrangecourseactivity (id)
;

alter table bd_classcurriculumchange
   add constraint FK_AC_CLASS_RELATIONS_BD_COUR3 foreign key (id_fromcourse)
      references bd_course (id)
;

alter table bd_classcurriculumchange
   add constraint FK_AC_CLASS_RELATIONS_BD_TEAC3 foreign key (id_fromteacher)
      references bd_teacher (id)
;

alter table bd_classcurriculumchange
   add constraint FK_BD_CLASS_RELATIONS_BD_ECLA3 foreign key (id_eclass)
      references bd_eclass (id)
;

alter table bd_classcurriculumchange
   add constraint FK_BD_CLASS_RELATIONS_BD_COURS foreign key (id_tocourse)
      references bd_course (id)
;

alter table bd_classcurriculumchange
   add constraint FK_BD_CLASS_RELATIONS_BD_TEACH foreign key (id_toteacher)
      references bd_teacher (id)
;

alter table bd_classroom
   add constraint FK_BD_CLASS_REFERENCE_BD_SCHOO foreign key (id_school)
      references bd_school (id)
;

alter table bd_classroomseat
   add constraint FK_BD_CLASS_REFERENCE_BD_CLAS1 foreign key (id_classroom)
      references bd_classroom (id)
;

alter table bd_classstudent
   add constraint FK_BD_CLASS_RELATIONS_BD_STUDE foreign key (id_student)
      references bd_student (id)
;

alter table bd_classstudent
   add constraint FK_BD_CLASS_RELATIONS_BD_ECLA1 foreign key (id_eclass)
      references bd_eclass (id)
;

alter table bd_configitem_configresult
   add constraint FK_BD_CONFI_REFERENCE_BD_CONF3 foreign key (id_configurationitem)
      references bd_configurationitem (id)
;

alter table bd_configitem_configresult
   add constraint FK_BD_CONFI_REFERENCE_BD_CONF4 foreign key (id_configurationresult)
      references bd_configurationresult (id)
;

alter table bd_configuration
   add constraint FK_BD_CONFI_REFERENCE_BD_MODUL foreign key (id_module)
      references bd_module (id)
;

alter table bd_configurationitem
   add constraint FK_BD_CONFI_REFERENCE_BD_CONF2 foreign key (id_configuration)
      references bd_configuration (id)
;

alter table bd_configurationresult
   add constraint FK_BD_CONFI_REFERENCE_BD_CONFI foreign key (id_configuration)
      references bd_configuration (id)
;

alter table bd_configurationresult
   add constraint FK_BD_CONFI_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
;

alter table bd_course
   add constraint FK_BD_COURS_RELATIONS_BD_SUBJE foreign key (id_subject)
      references bd_subject (id)
;

alter table bd_department
   add constraint FK_BD_DEPAR_REFERENCE_BD_SCHO2 foreign key (id_school)
      references bd_school (id)
;

alter table bd_department
   add constraint FK_BD_DEPAR_RELATIONS_BD_DEPA2 foreign key (id_parent)
      references bd_department (id)
;

alter table bd_departmentposition
   add constraint FK_BD_DEPAR_RELATIONS_BD_DEPA4 foreign key (id_department)
      references bd_department (id)
;

alter table bd_departmentposition
   add constraint FK_BD_DEPAR_RELATIONS_BD_POSIT foreign key (id_position)
      references bd_position (id)
;

alter table bd_departmentscope
   add constraint FK_BD_DEPAR_REFERENCE_BD_SCHOO foreign key (id_school)
      references bd_school (id)
;

alter table bd_departmentscope
   add constraint FK_BD_DEPAR_RELATIONS_BD_COURS foreign key (id_course)
      references bd_course (id)
;

alter table bd_departmentscope
   add constraint FK_BD_DEPAR_RELATIONS_BD_DEPA3 foreign key (id_department)
      references bd_department (id)
;

alter table bd_departmentscope
   add constraint FK_BD_DEPAR_RELATIONS_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
;

alter table bd_departmentuser
   add constraint FK_BD_DEPAR_RELATIONS_BD_DEPAR foreign key (id_department)
      references bd_department (id)
;

alter table bd_departmentuser
   add constraint FK_BD_DEPAR_RELATIONS_BD_USER foreign key (id_user)
      references bd_user (id)
;

alter table bd_dictionaryvalue
   add constraint FK_BD_DICTI_REFERENCE_BD_DI110 foreign key (id_parent)
      references bd_dictionaryvalue (id)
;

alter table bd_dictionaryvalue
   add constraint FK_BD_DICTI_REFERENCE_BD_DI122 foreign key (id_dictionary)
      references bd_dictionary (id)
;

alter table bd_dividescore
   add constraint FK_BD_DIVID_REFERENCE_BD_STUDE foreign key (id_student)
      references bd_student (id)
;

alter table bd_eclass
   add constraint FK_BD_ECLAS_RELATIONS_BD_TEAC2 foreign key (id_classteacher2)
      references bd_teacher (id)
;

alter table bd_eclass
   add constraint FK_BD_ECLAS_RELATIONS_BD_TEACH foreign key (id_classteacher)
      references bd_teacher (id)
;

alter table bd_eclass
   add constraint FK_BD_ECLAS_RELATIONS_BD_SCHO2 foreign key (id_school)
      references bd_school (id)
;

alter table bd_eclass
   add constraint FK_BD_ECLAS_RELATIONS_BD_SCHOO foreign key (id_schoolterm)
      references bd_schoolterm (id)
;

alter table bd_eclass
   add constraint FK_BD_ECLAS_RELATIONS_BD_PERIO foreign key (id_period)
      references bd_period (id)
;

alter table bd_eclass
   add constraint FK_BD_ECLAS_RELATIONS_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
;

alter table bd_eclassteachingmaterial
   add constraint FK_BD_ECLAS_REFERENCE_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
;

alter table bd_eclassteachingmaterial
   add constraint FK_BD_ECLAS_REFERENCE_BD_SCHOO foreign key (id_schoolterm)
      references bd_schoolterm (id)
;

alter table bd_eclassteachingmaterial
   add constraint FK_BD_ECLAS_REFERENCE_BD_TEACH foreign key (id_teachingmaterial)
      references bd_teachingmaterial (id)
;

alter table bd_gradecourse
   add constraint FK_BD_GRADE_REFERENCE_BD_SCHO2 foreign key (id_school)
      references bd_school (id)
;

alter table bd_gradecourse
   add constraint FK_BD_GRADE_RELATIONS_BD_COURS foreign key (id_course)
      references bd_course (id)
;

alter table bd_gradecourse
   add constraint FK_BD_GRADE_RELATIONS_BD_GRAD2 foreign key (id_grade)
      references bd_grade (id)
;

alter table bd_gradecourse
   add constraint FK_BD_GRADE_RELATIONS_BD_SCHOO foreign key (id_schoolterm)
      references bd_schoolterm (id)
;

alter table bd_gradeteachingmaterial
   add constraint FK_BD_GRADE_REFERENCE_BD_SCHOO foreign key (id_schoolterm)
      references bd_schoolterm (id)
;

alter table bd_gradeteachingmaterial
   add constraint FK_BD_GRADE_REFERENCE_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
;

alter table bd_gradeteachingmaterial
   add constraint FK_BD_GRADE_REFERENCE_BD_TEACH foreign key (id_teachingmaterial)
      references bd_teachingmaterial (id)
;

alter table bd_groupapplication
   add constraint FK_BD_GROUP_REFERENCE_BD_APPLI foreign key (id_applicationgroup)
      references bd_applicationgroup (id)
;

alter table bd_groupapplication
   add constraint FK_BD_GROUP_REFERENCE_BD_OPERA foreign key (id_operation)
      references bd_operation (id)
;

alter table bd_operation
   add constraint FK_BD_OPERA_REFERENCE_BD_OPERA foreign key (id_parent)
      references bd_operation (id)
;

alter table bd_operation
   add constraint FK_BD_OPERA_RELATIONS_BD_MODUL foreign key (id_module)
      references bd_module (id)
;

alter table bd_operationlog
   add constraint FK_BD_OPERA_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
;

alter table bd_operationlog
   add constraint FK_BD_OPERA_RELATIONS_BD_OPERA foreign key (id_operation)
      references bd_operation (id)
;

alter table bd_positionchange
   add constraint FK_BD_POSIT_RELATIONS_BD_SCHOO foreign key (id_schoolterm)
      references bd_schoolterm (id)
;

alter table bd_positionchange
   add constraint FK_BD_POSIT_RELATIONS_BD_DEPAR foreign key (id_fromdepartment)
      references bd_department (id)
;

alter table bd_positionchange
   add constraint FK_BD_POSIT_RELATIONS_BD_POSI2 foreign key (id_fromposition)
      references bd_position (id)
;

alter table bd_positionchange
   add constraint FK_BD_POSIT_RELATIONS_BD_DEPA2 foreign key (id_todepartment)
      references bd_department (id)
;

alter table bd_positionchange
   add constraint FK_BD_POSIT_RELATIONS_BD_POSIT foreign key (id_toposition)
      references bd_position (id)
;

alter table bd_positionchange
   add constraint FK_BD_POSIT_RELATIONS_BD_USER foreign key (id_user)
      references bd_user (id)
;

alter table bd_postionprivilege
   add constraint FK_BD_POSTI_RELATIONS_BD_POSIT foreign key (id_position)
      references bd_position (id)
;

alter table bd_postionprivilege
   add constraint FK_BD_POSTI_RELATIONS_BD_PRIVI foreign key (id_privilege)
      references bd_privilege (id)
;

alter table bd_privilege
   add constraint FK_BD_PRIVI_RELATIONS_BD_MODUL foreign key (id_module)
      references bd_module (id)
;

alter table bd_privilegeoperation
   add constraint FK_BD_PRIVI_RELATIONS_BD_PRIVI foreign key (id_privilege)
      references bd_privilege (id)
;

alter table bd_privilegeoperation
   add constraint FK_BD_PRIVI_RELATIONS_BD_OPERA foreign key (id_operation)
      references bd_operation (id)
;

alter table bd_schedulegrade
   add constraint FK_BD_SCHED_REFERENCE_BD_SC129 foreign key (id_schedule)
      references bd_schedule (id)
;

alter table bd_schedulegrade
   add constraint FK_BD_SCHED_REFERENCE_BD_SCHOO foreign key (id_school)
      references bd_school (id)
;

alter table bd_schedulegrade
   add constraint FK_BD_SCHED_REFERENCE_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
;

alter table bd_scheduleitem
   add constraint FK_BD_SCHED_REFERENCE_BD_SC128 foreign key (id_schedule)
      references bd_schedule (id)
;

alter table bd_school
   add constraint FK_BD_SCHOO_RELATIONS_BD_SCHO2 foreign key (id_parent)
      references bd_school (id)
;

alter table bd_schoolgrade
   add constraint FK_BD_SCHOO_REFERENCE_BD_SCHOO foreign key (id_school)
      references bd_school (id)
;

alter table bd_schoolgrade
   add constraint FK_BD_SCHOO_RELATIONS_BD_PERIO foreign key (id_period)
      references bd_period (id)
;

alter table bd_schoolgrade
   add constraint FK_BD_SCHOO_RELATIONS_BD_SCHOO foreign key (id_schoolterm)
      references bd_schoolterm (id)
;

alter table bd_schoolgrade
   add constraint FK_BD_SCHOO_RELATIONS_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
;

alter table bd_schoolterm
   add constraint FK_BD_SCHOO_RELATIONS_BD_SCHO3 foreign key (id_schoolyear)
      references bd_schoolyear (id)
;

alter table bd_studentchange
   add constraint FK_BD_STUDE_REFERENCE_BD_ECLA2 foreign key (id_toclass)
      references bd_eclass (id)
;

alter table bd_studentchange
   add constraint FK_BD_STUDE_RELATIONS_BD_ECLAS foreign key (id_fromclass)
      references bd_eclass (id)
;

alter table bd_studentchange
   add constraint FK_BD_STUDE_RELATIONS_BD_STUDE foreign key (id_student)
      references bd_student (id)
;

alter table bd_studentparent
   add constraint FK_BD_STUDE_REFERENCE_BD_STUDE foreign key (id_student)
      references bd_student (id)
;

alter table bd_studentparent
   add constraint FK_BD_STUDE_REFERENCE_BD_PAREN foreign key (id_parent)
      references bd_parent (id)
;

alter table bd_studentregistration
   add constraint FK_BD_STUDE_REFERENCE_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
;

alter table bd_studentregistration
   add constraint FK_BD_STUDE_REFERENCE_BD_SCHO1 foreign key (id_school)
      references bd_school (id)
;

alter table bd_studentregistration
   add constraint FK_BD_STUDE_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
;

alter table bd_studentseat
   add constraint FK_BD_STUDE_REFERENCE_BD_CL133 foreign key (id_classroomseat)
      references bd_classroomseat (id)
;

alter table bd_studentseat
   add constraint FK_BD_STUDE_REFERENCE_BD_CL134 foreign key (id_classroom)
      references bd_classroom (id)
;

alter table bd_studentseat
   add constraint FK_BD_STUDE_REFERENCE_BD_STUD2 foreign key (id_student)
      references bd_student (id)
;

alter table bd_studentseat
   add constraint FK_BD_STUDE_REFERENCE_BD_ECLA1 foreign key (id_eclass)
      references bd_eclass (id)
;

alter table bd_studentteachingmaterial
   add constraint FK_BD_STUDE_REFERENCE_BD_SCHOO foreign key (id_schoolterm)
      references bd_schoolterm (id)
;

alter table bd_studentteachingmaterial
   add constraint FK_BD_STUDE_REFERENCE_BD_STUD3 foreign key (id_student)
      references bd_student (id)
;

alter table bd_studentteachingmaterial
   add constraint FK_BD_STUDE_REFERENCE_BD_TEACH foreign key (id_teachingmaterial)
      references bd_teachingmaterial (id)
;

alter table bd_teacher
   add constraint FK_BD_TEACH_RELATIONS_BD_SCHOO foreign key (id_school)
      references bd_school (id)
;

alter table bd_teachercourse
   add constraint FK_BD_TEACH_REFERENCE_BD_CLASS foreign key (id_classroom)
      references bd_classroom (id)
;

alter table bd_teachercourse
   add constraint FK_BD_TEACH_RELATIONS_BD_ECLA2 foreign key (id_eclass)
      references bd_eclass (id)
;

alter table bd_teachercourse
   add constraint FK_BD_TEACH_RELATIONS_BD_COUR2 foreign key (id_course)
      references bd_course (id)
;

alter table bd_teachercourse
   add constraint FK_BD_TEACH_RELATIONS_BD_TEAC2 foreign key (id_teacher)
      references bd_teacher (id)
;

alter table bd_teachercoursechange
   add constraint FK_BD_TEACH_REFERENCE_BD_TEACH foreign key (id_toteacher)
      references bd_teacher (id)
;

alter table bd_teachercoursechange
   add constraint FK_BD_TEACH_REFERENCE_BD_COURS foreign key (id_tocourse)
      references bd_course (id)
;

alter table bd_teachercoursechange
   add constraint FK_BD_TEACH_RELATIONS_BD_COURS foreign key (id_fromcourse)
      references bd_course (id)
;

alter table bd_teachercoursechange
   add constraint FK_BD_TEACH_RELATIONS_BD_TEACH foreign key (id_fromteacher)
      references bd_teacher (id)
;

alter table bd_teachercoursechange
   add constraint FK_BD_TEACH_RELATIONS_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
;

alter table bd_teachingmaterial
   add constraint FK_BD_TEACH_REFERENCE_BD_COUR2 foreign key (id_course)
      references bd_course (id)
;

alter table bd_teachingmaterial
   add constraint FK_BD_TEACH_REFERENCE_BD_GRADE foreign key (id_grade)
      references bd_grade (id)
;

alter table bd_teachingmaterial
   add constraint FK_BD_TEACH_REFERENCE_BD_PUBLI foreign key (id_publisher)
      references bd_publisher (id)
;

alter table bd_useroperation
   add constraint FK_BD_USERO_RELATIONS_BD_POSIT foreign key (id_position)
      references bd_position (id)
;

alter table bd_useroperation
   add constraint FK_BD_USERO_RELATIONS_BD_USER3 foreign key (id_user)
      references bd_user (id)
;

alter table bd_useroperation
   add constraint FK_BD_USERO_RELATIONS_BD_OPER2 foreign key (id_operation)
      references bd_operation (id)
;

alter table bd_useroperationchange
   add constraint FK_BD_USERO_RELATIONS_BD_OPERA foreign key (id_operation)
      references bd_operation (id)
;

alter table bd_useroperationchange
   add constraint FK_BD_USERO_RELATIONS_BD_USER foreign key (id_granteduser)
      references bd_user (id)
;

alter table bd_useroperationchange
   add constraint FK_BD_USERO_RELATIONS_BD_USER2 foreign key (id_grantuser)
      references bd_user (id)
;

alter table bd_useroperationdatascope
   add constraint FK_BD_USERO_RELATIONS_BD_USERO foreign key (id_useroperation)
      references bd_useroperation (id)
;

alter table bd_useroperationdatascope
   add constraint FK_BD_USERO_RELATIONS_BD_DATAS foreign key (id_datascopeclass)
      references bd_datascopeclass (id)
;

alter table bd_useroperationdatascope
   add constraint FK_BD_USERO_RELATIONS_BD_USER1 foreign key (id_parent)
      references bd_useroperationdatascope (id)
;

alter table bd_userposition
   add constraint FK_BD_USERP_RELATIONS_BD_USER2 foreign key (id_user)
      references bd_user (id)
;

alter table bd_userposition
   add constraint FK_BD_USERP_RELATIONS_BD_POSIT foreign key (id_position)
      references bd_position (id)
;

alter table bd_userposition
   add constraint FK_BD_USERP_RELATIONS_BD_DEPAR foreign key (id_department)
      references bd_department (id)
;

alter table ca_attendancerecord
   add constraint FK_CA_ATTEN_REFERENCE_BD_STUDE foreign key (id_student)
      references bd_student (id)
;

alter table ca_attendancerecord
   add constraint FK_CA_ATTEN_REFERENCE_CA_CLASS foreign key (id_classroomattendance)
      references ca_classroomattendance (id)
;

alter table ca_attendancerecord
   add constraint FK_CA_ATTEN_REFERENCE_CA_ATTEN foreign key (id_attendanceitem)
      references ca_attendanceitem (id)
;

alter table ca_classroomattendance
   add constraint FK_CA_CLASS_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
;

alter table ca_leaverecord
   add constraint FK_CA_LEAVE_REFERENCE_BD_STUDE foreign key (id_student)
      references bd_student (id)
;

alter table ca_leaverecord
   add constraint FK_CA_LEAVE_REFERENCE_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
;

alter table ca_leaverecord
   add constraint FK_CA_LEAVE_REFERENCE_BD_SCHOO foreign key (id_schoolterm)
      references bd_schoolterm (id)
;

alter table ca_performanceitem
   add constraint FK_CA_PERFO_REFERENCE_CA_PERFO foreign key (id_performanceKind)
      references ca_performancekind (id)
;

alter table ca_performancerecord
   add constraint FK_CA_PERFO_REFERENCE_BD_STUDE foreign key (id_student)
      references bd_student (id)
;

alter table ca_performancerecord
   add constraint FK_CA_PERFO_REFERENCE_CA_CLASS foreign key (id_classroomattendance)
      references ca_classroomattendance (id)
;

alter table ca_performancerecord
   add constraint FK_CA_PERFO_REFERENCE_CA_PERF2 foreign key (id_performanceitem)
      references ca_performanceitem (id)
;

alter table ec_ecactivitycourse
   add constraint FK_EC_ECACT_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
;

alter table ec_ecactivitycourse
   add constraint FK_EC_ECACT_RELATIONS_EC_ECAC2 foreign key (id_ecactivity)
      references ec_ecactivity (id)
;

alter table ec_ecactivityeclass
   add constraint FK_EC_ECACT_REFERENCE_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
;

alter table ec_ecactivityeclass
   add constraint FK_EC_ECACT_RELATIONS_EC_ECACT foreign key (id_ecactivity)
      references ec_ecactivity (id)
;

alter table ec_ececlass
   add constraint FK_EC_ECECL_REFERENCE_EC_ECACT foreign key (id_ecactivitycourse)
      references ec_ecactivitycourse (id)
;

alter table ec_ececlass
   add constraint FK_EC_ECECL_RELATIONS_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
;

alter table ec_ecrule
   add constraint FK_EC_ECRUL_RELATIONS_EC_ECACT foreign key (id_ecactivity)
      references ec_ecactivity (id)
;

alter table ec_ecruleclass
   add constraint FK_EC_ECRUL_REFERENCE_BD_ECLAS foreign key (id_eclass)
      references bd_eclass (id)
;

alter table ec_ecruleclass
   add constraint FK_EC_ECRUL_RELATIONS_EC_ECRUL foreign key (id_ecrule)
      references ec_ecrule (id)
;

alter table ec_ecrulecourse
   add constraint FK_EC_ECRUL_RELATIONS_BD_COURS foreign key (id_course)
      references bd_course (id)
;

alter table ec_ecrulecourse
   add constraint FK_EC_ECRUL_RELATIONS_EC_ECRU2 foreign key (id_ecerule)
      references ec_ecrule (id)
;

alter table ec_ecstudentcourse
   add constraint FK_EC_ECSTU_REFERENCE_BD_STUDE foreign key (id_student)
      references bd_student (id)
;

alter table ec_ecstudentcourse
   add constraint FK_EC_ECSTU_REFERENCE_BD_SCHOO foreign key (id_schoolterm)
      references bd_schoolterm (id)
;

alter table ec_ecstudentcourse
   add constraint FK_EC_ECSTU_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
;

alter table ec_ecstudentcourse
   add constraint FK_EC_ECSTU_RELATIONS_EC_ECACT foreign key (id_ecactivity)
      references ec_ecactivity (id)
;

alter table ma_materials
   add constraint FK_MA_MATER_REFERENCE_BD_USER4 foreign key (id_approvaluser)
      references bd_user (id)
;

alter table ma_materials
   add constraint FK_MA_MATER_REFERENCE_MA_MATER4 foreign key (id_materialskind)
      references ma_materialskind (id)
;

alter table ma_materials
   add constraint FK_MA_MATER_REFERENCE_MA_MATER5 foreign key (id_materialshandinuser)
      references ma_materialshandinuser (id)
;

alter table ma_materialsapprovaluser
   add constraint FK_MA_MATER_REFERENCE_BD_USER2 foreign key (id_user)
      references bd_user (id)
;

alter table ma_materialsapprovaluser
   add constraint FK_MA_MATER_REFERENCE_MA_MATER3 foreign key (id_materialskind)
      references ma_materialskind (id)
;

alter table ma_materialsapprovaluser
   add constraint FK_MA_MATER_REFERENCE_MA_MATER1 foreign key (id_materialsnotice)
      references ma_materialsnotice (id)
;

alter table ma_materialshandinuser
   add constraint FK_MA_MATER_REFERENCE_BD_USER3 foreign key (id_user)
      references bd_user (id)
;

alter table ma_materialshandinuser
   add constraint FK_MA_MATER_REFERENCE_MA_MATER2 foreign key (id_materialsnotice)
      references ma_materialsnotice (id)
;

alter table ma_materialskind
   add constraint FK_MA_MATER_REFERENCE_MA_MATER6 foreign key (id_parent)
      references ma_materialskind (id)
;

alter table ma_materialsnotice
   add constraint FK_MA_MATER_REFERENCE_BD_DEPAR foreign key (id_department)
      references bd_department (id)
;

alter table ma_materialsnotice
   add constraint FK_MA_MATER_REFERENCE_BD_USER1 foreign key (id_user)
      references bd_user (id)
;

alter table me_memo
   add constraint FK_ME_MEMO_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
;

alter table me_memomemotemplate
   add constraint FK_MEMOMEMOTEMPL_MEMOTEMPL foreign key (id_memotemplate)
      references me_memotemplate (id)
;

alter table me_memomemotemplate
   add constraint FK_SP_MEMOMEMOTEMPLATE_SP_MEMO foreign key (id_memo)
      references me_memo (id)
;

alter table mo_mood
   add constraint FK_MO_MOOD_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
;

alter table mo_moodmoodkind
   add constraint FK_SP_MOODMOODCATEGORY_SP_MOOD foreign key (id_mood)
      references mo_mood (id)
;

alter table mo_moodmoodkind
   add constraint FK_MOODMOODCATEG_MOODCATEG foreign key (id_moodkind)
      references mo_moodkind (id)
;

alter table no_notice
   add constraint FK_NO_NOTIC_REFERENCE_BD_USE2 foreign key (id_user)
      references bd_user (id)
;

alter table no_notice
   add constraint FK_NO_NOTIC_REFERENCE_BD_DEPAR foreign key (id_department)
      references bd_department (id)
;

alter table no_noticereply
   add constraint FK_NO_NOTIC_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
;

alter table no_noticereply
   add constraint FK_NO_NOTIC_REFERENCE_NO_RECEI foreign key (id_receivenotice)
      references no_receivenotice (id)
;

alter table no_receivenotice
   add constraint FK_NO_RECEI_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
;

alter table no_receivenotice
   add constraint FK_NO_RECEI_REFERENCE_BD_DEPAR foreign key (id_department)
      references bd_department (id)
;

alter table no_receivenotice
   add constraint FK_NO_RECEI_REFERENCE_NO_NOTIC foreign key (id_notice)
      references no_notice (id)
;

alter table ns_entrancetestcourse
   add constraint FK_NS_ENTRA_REFERENCE_BD_COURS foreign key (id_course)
      references bd_course (id)
;

alter table ns_entrancetestscore
   add constraint FK_NS_ENTRA_REFERENCE_BD_STUDE foreign key (id_studentregistration)
      references bd_studentregistration (id)
;

alter table ns_entrancetestscore
   add constraint FK_NS_ENTRA_REFERENCE_NS_ENTRA foreign key (id_entrancetestcourse)
      references ns_entrancetestcourse (id)
;

alter table pc_privatedepartment
   add constraint FK_PC_PRIVA_RELATIONS_BD_TEACH foreign key (id_teacher)
      references bd_teacher (id)
;

alter table pc_privatedepartmentteacher
   add constraint FK_PC_PRIVA_RELATIONS_BD_TEAC2 foreign key (id_teacher)
      references bd_teacher (id)
;

alter table pu_publicity
   add constraint FK_PU_PUBLI_REFERENCE_BD_USER1 foreign key (id_publishuser)
      references bd_user (id)
;

alter table pu_publicity
   add constraint FK_PU_PUBLI_REFERENCE_BD_USER2 foreign key (id_dealuser)
      references bd_user (id)
;

alter table pu_publicity
   add constraint FK_PU_PUBLI_REFERENCE_BD_DEPAR foreign key (id_department)
      references bd_department (id)
;

alter table pu_publicityapproval
   add constraint FK_PU_PUBLI_REFERENCE_PU_PUBL2 foreign key (id_publicity)
      references pu_publicity (id)
;

alter table pu_publicityapproval
   add constraint FK_PU_PUBLI_REFERENCE_BD_USER3 foreign key (id_user)
      references bd_user (id)
;

alter table pu_publicityfeedback
   add constraint FK_PU_PUBLI_REFERENCE_PU_PUBL1 foreign key (id_publicity)
      references pu_publicity (id)
;

alter table pu_publicityfeedback
   add constraint FK_PU_PUBLI_REFERENCE_BD_USER4 foreign key (id_user)
      references bd_user (id)
;

alter table sa_assessmentitem
   add constraint FK_SA_ASSES_REFERENCE_SA_ASSE7 foreign key (id_assessmentkind)
      references sa_assessmentkind (id)
;

alter table sa_assessmentitempostion
   add constraint FK_SA_ASSES_REFERENCE_BD_POSIT foreign key (id_position)
      references bd_position (id)
;

alter table sa_assessmentitempostion
   add constraint FK_SA_ASSES_REFERENCE_SA_ASSES foreign key (id_assessmentitem)
      references sa_assessmentitem (id)
;

alter table sa_assessmentlevel
   add constraint FK_SA_ASSES_REFERENCE_SA_ASSE5 foreign key (id_assessmentitem)
      references sa_assessmentitem (id)
;

alter table sa_assessmentreply
   add constraint FK_SA_ASSES_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
;

alter table sa_assessmentreply
   add constraint FK_SA_ASSES_REFERENCE_SA_ASSE6 foreign key (id_studentassessment)
      references sa_studentassessment (id)
;

alter table sa_assessmentreply
   add constraint FK_SA_ASSES_REFERENCE_SA_ASSE2 foreign key (id_assessmentreply)
      references sa_assessmentreply (id)
;

alter table sa_studentassessment
   add constraint FK_SA_ASSES_REFERENCE_SA_ASSE3 foreign key (id_assessmentitem)
      references sa_assessmentitem (id)
;

alter table sa_studentassessment
   add constraint FK_SA_STUDE_REFERENCE_BD_USER foreign key (id_user)
      references bd_user (id)
;

alter table sa_studentassessment
   add constraint FK_SA_STUDE_REFERENCE_BD_STUDE foreign key (id_student)
      references bd_student (id)
;

alter table sa_studentassessment
   add constraint FK_SA_ASSES_REFERENCE_SA_ASSE4 foreign key (id_assessmentlevel)
      references sa_assessmentlevel (id)
;