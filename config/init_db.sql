create table resume
(
    uuid char(36) primary key not null,
    full_name text not null
);

alter table resume owner to postgres;

create table contact
(
    id serial not null
        constraint contact_pk
            primary key,
    resume_uuid char(36) not null references resume on delete cascade,
    type text not null,
    value text not null
);

alter table contact owner to postgres;

create unique index contact_uuid_type_index
on contact (resume_uuid, type);
