# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table charactor (
  id                        bigint not null,
  side                      integer,
  name                      varchar(255),
  password                  varchar(255),
  level                     integer,
  exp                       bigint,
  money                     bigint,
  mhp                       integer,
  hp                        integer,
  mmp                       integer,
  mp                        integer,
  str                       integer,
  agi                       integer,
  sen                       integer,
  wil                       integer,
  place                     integer,
  scene                     integer,
  nextplace                 integer,
  respawn                   integer,
  itemstr                   varchar(255),
  skills                    varchar(255),
  constraint pk_charactor primary key (id))
;

create sequence charactor_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists charactor;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists charactor_seq;

