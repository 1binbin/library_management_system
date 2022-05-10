create table administrators
(
    aid       char(10) not null
        primary key,
    apassword char(32) null
);

create table books
(
    bisbn     char(32)  not null
        primary key,
    bname     char(128) null,
    bauthor   char(128) null,
    bcategory char(128) null,
    bprice    char(64)  null,
    bcount    char(16)  null
);

create table borrowing
(
    rid           char(10) null,
    bisbn         char(32) null,
    borrowingdate date     null,
    duedate       date     null,
    returndate    date     null,
    borrownum     int      null,
    remarks       char(16) null
);

create table readers
(
    rid       char(10)      not null
        primary key,
    rpassword char(32)      null,
    rname     char(10)      null,
    rbirthday date          null,
    rage      int default 0 null,
    rsex      char(4)       null,
    rphone    char(12)      null,
    rcredit   int           null
);