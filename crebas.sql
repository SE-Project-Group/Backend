/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/6/27 14:50:27                           */
/*==============================================================*/


drop table if exists Administrator;

drop table if exists Client;




/*==============================================================*/
/* Table: Client                                                */
/*==============================================================*/
create table Client
(
   user_ID              int not null,
   phone                char(20),
   gender               char(20),
   birthday             date,
   user_name            char(20),
   password             char(20),
   email                char(20),
   primary key (user_ID)
);

/*==============================================================*/
/* Table: Administrator                                                       */
/*==============================================================*/
create table Administrator
(
   admin_ID              int not null,
   admin_name            char(20),
   password             char(20),
  
   primary key (admin_ID)
);

