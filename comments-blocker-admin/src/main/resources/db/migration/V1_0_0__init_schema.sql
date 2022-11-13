select 'create schema';

create sequence public.heartbeat_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

create table heartbeat (
                           id int8 not null,
                           heartbeat timestamp not null,
                           primary key (id)
);

ALTER SEQUENCE public.heartbeat_seq OWNED BY public.heartbeat.id;

create sequence public.heartbeat_fu_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

create table heartbeat_fu (
                           id int8 not null,
                           heartbeat timestamp not null,
                           primary key (id)
);

ALTER SEQUENCE public.heartbeat_fu_seq OWNED BY public.heartbeat_fu.id;

create sequence public.ip_block_actions_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

create table ip_block_actions (
                                  id int8 not null,
                                  block_period varchar(32) not null,
                                  end_time timestamp,
                                  is_active boolean,
                                  start_time timestamp not null,
                                  record_id int8,
                                  user_id int8,
                                  user_exclude_id int8,
                                  is_synchronised boolean not null default false,
                                  primary key (id)
);

ALTER SEQUENCE public.ip_block_actions_seq OWNED BY public.ip_block_actions.id;

create sequence public.ip_record_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

create table ip_records (
                            id int8 not null,
                            city varchar(255),
                            country varchar(255),
                            ip varchar(255),
                            primary key (id)
);

ALTER SEQUENCE public.ip_record_seq OWNED BY public.ip_records.id;

create sequence public.user_settings_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

create table user_settings (
                               id int8 not null,
                               rows_per_page int4,
                               user_id int8,
                               primary key (id)
);

ALTER SEQUENCE public.user_settings_seq OWNED BY public.user_settings.id;

create sequence public.who_role_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

create table who_role (
                          id int8 not null,
                          role_name varchar(255) not null,
                          who_user_id int8,
                          primary key (id)
);

ALTER SEQUENCE public.who_role_seq OWNED BY public.who_role.id;

create sequence public.user_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

create table who_user (
                          id int8 not null,
                          email varchar(255) not null,
                          first_name varchar(255),
                          last_name varchar(255),
                          password varchar(255),
                          primary key (id)
);

ALTER SEQUENCE public.user_seq OWNED BY public.who_user.id;

alter table if exists ip_records
    add constraint UK_l78upl9953hm4aojnkuqhpjq8 unique (ip);

alter table if exists who_user
    add constraint UK_3icv9djsklf2n74p4i4p4d4rs unique (email);

alter table if exists ip_block_actions
    add constraint FK2t7kakabbmg1lh9oshfk5xnth
    foreign key (record_id)
    references ip_records;

alter table if exists ip_block_actions
    add constraint FK2f3t659hwxt2waxwuvurlg5ji
    foreign key (user_id)
    references who_user;

alter table if exists who_role
    add constraint FKdjapom5yg2hu3ce13du32kvv6
    foreign key (who_user_id)
    references who_user;
