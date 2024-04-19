create table korus_wms.warehouse
(
    id                        uuid         not null
        primary key,
    code                      varchar(100) not null,
    name                      varchar(255) not null,
    active                    boolean      not null,
    created_at                timestamp,
    created_by                varchar(50),
    updated_at                timestamp,
    updated_by                varchar(50),
    legal_entity_id           uuid         not null,
    yard_id                   uuid,
    application_module        varchar(100),
    size_unit_of_measure_id   uuid,
    volume_unit_of_measure_id uuid,
    weight_unit_of_measure_id uuid
);