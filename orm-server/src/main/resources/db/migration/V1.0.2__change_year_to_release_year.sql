
    alter table if exists Book 
       add column release_year int4 not null;

    alter table if exists Book
           drop column year;
