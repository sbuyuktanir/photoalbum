alter table album_photos
    drop constraint FK_album_photos_2_photos ;

alter table album_photos
    drop constraint FK_album_photos_2_album ;

alter table photographer_emails
    drop constraint FK_photographer_emails ;

alter table photographers
    drop constraint FK_photographers_billing_country ;

alter table photographers
    drop constraint FK_photographers_studio_country ;

alter table photos
    drop constraint FK_photo_photographer ;

alter table tagged_persons
    drop constraint FK_tagged_persons_2_person ;

alter table tagged_persons
    drop constraint FK_tagged_persons_2_photo ;

drop table if exists album_photos cascade;

drop table if exists albums cascade;

drop table if exists countries cascade;

drop table if exists persons cascade;

drop table if exists photographer_emails cascade;

drop table if exists photographers cascade;

drop table if exists photos cascade;

drop table if exists tagged_persons cascade;

drop sequence if exists hibernate_sequence;

drop table if exists flyway_schema_history;



    drop table if exists album_photo_containments CASCADE ;

    drop table if exists album_photos CASCADE ;

    drop table if exists albums CASCADE ;

    drop table if exists countries CASCADE ;

    drop table if exists persons CASCADE ;

    drop table if exists photographer_emails CASCADE ;

    drop table if exists photographers CASCADE ;

    drop table if exists photos CASCADE ;

    drop table if exists tagged_persons CASCADE ;

    drop sequence if exists hibernate_sequence;

    drop table if exists album_photo_containments CASCADE ;

    drop table if exists album_photos CASCADE ;

    drop table if exists albums CASCADE ;

    drop table if exists countries CASCADE ;

    drop table if exists persons CASCADE ;

    drop table if exists photographer_emails CASCADE ;

    drop table if exists photographers CASCADE ;

    drop table if exists photos CASCADE ;

    drop table if exists tagged_persons CASCADE ;

    drop sequence if exists hibernate_sequence;

